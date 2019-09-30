package entity;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Setter
@Getter
@EqualsAndHashCode(callSuper = true, exclude = {"isConnectToSocket", "isTurnOn"})
//@ToString(callSuper = true, exclude = {"isConnectToSocket", "isTurnOn"})
public class ElectricalAppliance extends Appliance implements Comparable<ElectricalAppliance> {
    private ApplianceBrand brand;
    private int powerConsumption;
    private boolean isForContinuousWork;

    private boolean isConnectToSocket;
    private boolean isTurnOn;

    public ElectricalAppliance(int powerConsumption, boolean isForContinuousWork, double weight,
                               ApplianceColor color, ApplianceName applianceName, ApplianceBrand brand) {
        super(weight, color, applianceName);
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

    @Override
    public String toString() {
        return String.format(
                "%-20s  brand | %-15s  color | %-10s  power | %-10s  weight | %-10s  isForContinuousWork | %-5s",
                super.getName() + ":", brand, super.getColor(), powerConsumption, super.getWeight(), isForContinuousWork
        );
    }
}
