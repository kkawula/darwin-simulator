package agh.ics.oop.simulation;

import agh.ics.oop.presenter.SimulationLauncher;
import agh.ics.oop.utils.ConfigurationData;
import javafx.application.Platform;

public class Simulation implements Runnable {

    private final DayManager dayManager;
    public final WorldMap worldMap;
    private final SimulationLauncher observer;
    private boolean threadSuspended = false;
    private boolean interrupted = false;

    public Simulation(ConfigurationData config, SimulationLauncher observer) {
        worldMap = new WorldMap(config.getMapWidth(), config.getMapHeight());
        dayManager = new DayManager(config);
        dayManager.initializeFirstDay(worldMap);
        this.observer = observer;
    }

    public void pause(){
        threadSuspended=true;
    }
    public void start(){
        if (threadSuspended) {
            threadSuspended = false;
            synchronized(this) {
                notify();
            }
        }
    }
    public void stop(){
        interrupted=true;
    }

    @Override
    public void run() {

        while(!interrupted){
            Platform.runLater(()->{
                dayManager.updateDay(worldMap);
                observer.updateGrid();
            });
            try {
                Thread.sleep(1000);
                synchronized(this) {
                    while (threadSuspended)
                        wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}
