package service;

import entity.ElectricalAppliance;
import exception.BusinessException;
import exception.ApplianceNotConnectToSocketException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ElectricalApplianceService {
    private static final Logger LOG = LogManager.getLogger(ElectricalApplianceService.class);

    public void connectToSocket(ElectricalAppliance appliance) throws BusinessException {
        LOG.info("Connecting appliance to socket. Appliance={}", appliance.getName());
        if(appliance.isConnectToSocket()){
            LOG.warn("Action reject.");
            throw new BusinessException(appliance.getName() + " is already connect to socket");
        }
        appliance.setConnectToSocket(true);
    }

    public void disconnectFromSocket(ElectricalAppliance appliance) throws BusinessException {
        LOG.info("Disconnecting appliance from socket. Appliance={}", appliance.getName());
        if(!appliance.isConnectToSocket()){
            LOG.warn("Action reject.");
            throw new BusinessException(appliance.getName() + " is already disconnect from socket");
        }
        appliance.setConnectToSocket(false);
    }

    public void turnOn(ElectricalAppliance appliance) throws BusinessException {
        LOG.info("Turning on appliance. Appliance={}", appliance.getName());
        if(!appliance.isConnectToSocket()){
            LOG.warn("Action reject.");
            throw new ApplianceNotConnectToSocketException(appliance.getName() + " is not connect to socket");
        }
        if(appliance.isTurnOn()){
            LOG.warn("Action reject.");
            throw new BusinessException(appliance.getName() + " is already turned on");
        }
        appliance.setTurnOn(true);
    }

    public void turnOff(ElectricalAppliance appliance) throws BusinessException {
        LOG.info("Turning off appliance. Appliance={}", appliance.getName());
        if(!appliance.isTurnOn()){
            LOG.warn("Action reject.");
            throw new BusinessException(appliance.getName() + " is already turned off");
        }
        appliance.setTurnOn(false);
    }

}
