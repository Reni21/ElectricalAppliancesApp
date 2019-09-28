package service;

import entity.ElectricalAppliance;

public class ElectricalApplianceService {

    public void connectToSocket(ElectricalAppliance appliance) {
        appliance.setConnectToSocket(true);
    }

    public void disconnectFromSocket(ElectricalAppliance appliance) {
        appliance.setConnectToSocket(false);
    }

    public void turnOn(ElectricalAppliance appliance) {
        appliance.setTurnOn(true);
    }

    public void turnOff(ElectricalAppliance appliance) {
        appliance.setTurnOn(false);
    }

}
