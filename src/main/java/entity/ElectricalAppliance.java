package entity;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Setter
@Getter
@EqualsAndHashCode(callSuper = true, exclude = {"isConnectToSocket", "isTurnOn"})
@ToString(callSuper = true, exclude = {"isConnectToSocket", "isTurnOn"})
public class ElectricalAppliance extends Appliance implements Comparable<ElectricalAppliance> {
    private ApplianceBrand brand;
    private int powerConsumption;
    private boolean isForContinuousWork;

    private boolean isConnectToSocket;
    private boolean isTurnOn;

    public ElectricalAppliance(int powerConsumption, boolean isForContinuousWork, int weight,
                               ApplianceColor color, ApplianceName type, ApplianceBrand brand) {
        super(weight, color, type);
        this.powerConsumption = powerConsumption;
        this.isForContinuousWork = isForContinuousWork;
        this.brand = brand;
    }

    @Override
    public int compareTo(ElectricalAppliance o) {
        if (o == null) {
            return 1;
        }
        return Integer.compare(powerConsumption, o.getPowerConsumption());
    }
}
