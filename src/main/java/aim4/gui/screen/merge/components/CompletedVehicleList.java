package aim4.gui.screen.merge.components;

import aim4.sim.simulator.merge.CoreMergeSimulator;
import aim4.sim.simulator.merge.MergeSimulator;
import aim4.vehicle.merge.MergeVehicleSimModel;

import javax.swing.*;
import java.util.List;

/**
 * Created by Callum on 26/03/2017.
 */
public class CompletedVehicleList extends JPanel implements MergeStatScreenComponent {
    private MapKeyTableModel model;
    private JTable table;
    private JScrollPane scrollPane;

    public CompletedVehicleList() {
        this.model = new MapKeyTableModel(new String[]{
                "VIN",
                "Final Velocity",
                "Final X Position",
                "Final Y Position",
                "Max Acceleration",
                "Max Deceleration",
                "Delay"
        });
        this.table = new JTable(model);
        scrollPane = new JScrollPane(table);
        scrollPane.setVisible(true);
        this.add(scrollPane);
    }

    @Override
    public void update(MergeSimulator sim, List<CoreMergeSimulator.CoreMergeSimStepResult> results) {
        for(CoreMergeSimulator.CoreMergeSimStepResult stepResult : results) {
            for (int vin : stepResult.getCompletedVehicles().keySet()) {
                MergeVehicleSimModel vehicle = stepResult.getCompletedVehicles().get(vin);
                model.addOrUpdateRow(vin, new Object[]{
                        vehicle.getVIN(),
                        vehicle.getVelocity(),
                        vehicle.getPosition().getX(),
                        vehicle.getPosition().getY(),
                        vehicle.getMaxAcceleration(),
                        vehicle.getMaxDeceleration(),
                        sim.calculateDelay(vehicle)
                });
            }
        }
    }
}