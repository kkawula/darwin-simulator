package agh.ics.oop.simulation;

import agh.ics.oop.model.Animal;
import agh.ics.oop.model.Vector2d;
import agh.ics.oop.presenter.DisplayData;
import agh.ics.oop.view.SimulationLauncher;
import agh.ics.oop.presenter.SimulationObserver;
import agh.ics.oop.utils.ConfigurationData;
import agh.ics.oop.utils.CsvWriter;

import java.util.HashSet;
import java.util.Set;

public class Simulation implements Runnable {
    private Set<SimulationObserver> observers = new HashSet<>();
    private final DayManager dayManager;
    public final WorldMap worldMap;
    public final ConfigurationData config;
    private Animal followedAnimal;
    private boolean threadSuspended = false;
    private boolean interrupted = false;
    CsvWriter csvWriter = new CsvWriter();
    private final long refreshTime;

    public Simulation(ConfigurationData config, SimulationLauncher observer) {
        this.config = config;
        worldMap = new WorldMap(config.mapWidth(), config.mapHeight());
        dayManager = new DayManager(config, worldMap);
        dayManager.initializeFirstDay();
        this.refreshTime = config.refreshTime();
    }

    public void subscribe(SimulationObserver observer) {
        observers.add(observer);
    }

    private void notifyObservers(DisplayData displayData) {
        for (SimulationObserver observer : observers) {
            observer.update(displayData);
        }
    }
    public void setFollowedAnimal(Vector2d newPosition) {
        followedAnimal = worldMap.getLastAnimal(newPosition);
    }
    private DisplayData collectData() {
        return new DisplayData(worldMap, followedAnimal);
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
        if (config.csvWriting() == 1) {
            csvWriter.saveFile();
        }
        interrupted = true;
    }

    @Override
    public void run() {

        while(!interrupted){
            dayManager.updateDay();
            DisplayData stats = collectData();
            csvWriter.addDayToCsv(stats.getSimulationStats());

            notifyObservers(stats);

            try {
                Thread.sleep(refreshTime);
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
