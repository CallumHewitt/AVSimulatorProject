package aim4.sim.setup.cpm;

import aim4.map.cpm.CPMBasicMap;
import aim4.map.cpm.CPMMapUtil;
import aim4.map.cpm.CPMCarParkSingleLaneWidth;
import aim4.sim.simulator.cpm.CPMAutoDriverSimulator;
import aim4.sim.Simulator;

/**
 * Setup for simulation of AVs in an AV specific car park which are self-organising.
 */
public class CPMAutoDriverSimSetup extends BasicCPMSimSetup {

    /**
     * Create a setup for the simulator in which all vehicles are autonomous.
     *
     * @param basicSimSetup  the basic simulator setup
     */
    public CPMAutoDriverSimSetup(BasicCPMSimSetup basicSimSetup) {
        super(basicSimSetup);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Simulator getSimulator() {
        double currentTime = 0.0;

        CPMBasicMap layout = new CPMCarParkSingleLaneWidth(laneWidth, // laneWidth
                speedLimit,
                currentTime,
                numberOfParkingLanes,
                parkingLength,
                accessLength);

        // Set up the correct spawn point
        switch(spawnSpecType) {
            case SINGLE:
                if (!useCSVFile.getKey()){
                    CPMMapUtil.setUpInfiniteSingleSpecVehicleSpawnPoint(layout, trafficLevel, singleSpawnSpecName);
                } else {
                    CPMMapUtil.setUpSpecificSingleSpecVehicleSpawnPoint(layout, useCSVFile, singleSpawnSpecName);
                }
                break;
            case RANDOM:
                if (!useCSVFile.getKey()){
                    CPMMapUtil.setUpInfiniteRandomSpecVehicleSpawnPoint(layout, trafficLevel);
                } else {
                    CPMMapUtil.setUpSpecificRandomSpecVehicleSpawnPoint(layout, useCSVFile);
                }
                break;
            case MIXED:
                if (mixedSpawnDistribution == null) {
                    throw new RuntimeException("No distribution has been given!");
                }
                if (!useCSVFile.getKey()){
                    CPMMapUtil.setUpInfiniteMixedSpecVehicleSpawnPoint(layout, trafficLevel, mixedSpawnDistribution);
                } else {
                    CPMMapUtil.setUpSpecificMixedSpecVehicleSpawnPoint(layout, useCSVFile, mixedSpawnDistribution);
                }
                break;
        }

        return new CPMAutoDriverSimulator(layout, useSpecificSimTime.getValue());
    }
}
