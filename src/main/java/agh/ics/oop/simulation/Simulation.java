package agh.ics.oop.simulation;

import agh.ics.oop.presenter.SimulationLauncher;
import agh.ics.oop.presenter.StatsWriter;
import agh.ics.oop.utils.ConfigurationData;
import agh.ics.oop.utils.CsvWriter;
import javafx.application.Platform;

public class Simulation implements Runnable {

    private final DayManager dayManager;
    public final WorldMap worldMap;
    public final StatsWriter statsWriter;

    public final ConfigurationData config;
    private final SimulationLauncher observer;
    private boolean threadSuspended = false;
    private boolean interrupted = false;
    CsvWriter csvWriter= new CsvWriter();

    public Simulation(ConfigurationData config, SimulationLauncher observer) {
        this.config = config;
        worldMap = new WorldMap(config.getMapWidth(), config.getMapHeight());
        dayManager = new DayManager(config, worldMap);
        dayManager.initializeFirstDay();
        statsWriter = new StatsWriter(worldMap);
        this.observer = observer;
    }

    public void pause(){
        threadSuspended = true;
    }
    public void start(){
        if (threadSuspended) {
            threadSuspended = false;
            synchronized(this) {
                notify();
            }
        }
    }
    public void shutDown(){
        if (config.getCsvWriting() == 1) {
            csvWriter.saveFile();
        }
        interrupted = true;
    }

    @Override
    public void run() {

        while(!interrupted){
            dayManager.updateDay();
            statsWriter.updateStats();
            csvWriter.addDayToCsv(statsWriter);
            Platform.runLater(observer::updateStats);
            Platform.runLater(observer::updateGrid);
            try {
                Thread.sleep(1000);
                synchronized(this) {
                    while (threadSuspended)
                        wait();
                }
            } catch (InterruptedException e) {
                shutDown();
            }
        }
    }


}
