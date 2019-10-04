package service;

import entity.ElectricalAppliance;
import exception.BusinessException;
import exception.ApplianceNotConnectToSocketException;

public class ElectricalApplianceService {

    public void connectToSocket(ElectricalAppliance appliance) throws BusinessException {
        if(appliance.isConnectToSocket()){
            throw new BusinessException(appliance.getName() + " is already connect to socket");
        }
        appliance.setConnectToSocket(true);
    }

    public void disconnectFromSocket(ElectricalAppliance appliance) throws BusinessException {
        if(!appliance.isConnectToSocket()){
            throw new BusinessException(appliance.getName() + " is already disconnect from socket");
        }
        appliance.setConnectToSocket(false);
    }

    public void turnOn(ElectricalAppliance appliance) throws BusinessException {
        if(!appliance.isConnectToSocket()){
            throw new ApplianceNotConnectToSocketException(appliance.getName() + " is not connect to socket");
        }
        if(appliance.isTurnOn()){
            throw new BusinessException(appliance.getName() + " is already turned on");
        }
        appliance.setTurnOn(true);
    }

    public void turnOff(ElectricalAppliance appliance) throws BusinessException {
        if(!appliance.isTurnOn()){
            throw new BusinessException(appliance.getName() + " is already turned off");
        }
        appliance.setTurnOn(false);
    }

}
