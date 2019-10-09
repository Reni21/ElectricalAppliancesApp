package app.service;

import app.entity.ElectricalAppliance;
import app.exception.BusinessException;
import org.junit.Before;
import org.junit.Test;
import util.DataProvider;

import static org.junit.Assert.*;

public class ElectricalApplianceServiceTest {
    private ElectricalApplianceService instance;
    private ElectricalAppliance appliance;

    @Before
    public void setUp() {
        this.instance = new ElectricalApplianceService();
        this.appliance = DataProvider.createLamp();
    }

    @Test
    public void shouldSetIsConnectToSocketInTrue() throws BusinessException {
        instance.connectToSocket(appliance);
        boolean res = appliance.isConnectToSocket();
        assertTrue(res);
    }

    @Test(expected = BusinessException.class)
    public void shouldThrowBusinessExceptionIfElectricalApplianceIsAlreadyConnectToSocket() throws BusinessException {
        appliance.setConnectToSocket(true);
        instance.connectToSocket(appliance);
    }

    @Test
    public void shouldSetIsConnectToSocketInFalse() throws BusinessException {
        appliance.setConnectToSocket(true);

        instance.disconnectFromSocket(appliance);
        boolean res = appliance.isConnectToSocket();
        assertFalse(res);
    }

    @Test(expected = BusinessException.class)
    public void shouldThrowBusinessExceptionIfElectricalApplianceIsAlreadyDisconnectFromSocket() throws BusinessException {
        appliance.setConnectToSocket(false);
        instance.disconnectFromSocket(appliance);
    }

    @Test
    public void shouldSetIsTurnOnInTrue() throws BusinessException {
        appliance.setConnectToSocket(true);

        instance.turnOn(appliance);
        boolean res = appliance.isTurnOn();
        assertTrue(res);
    }

    @Test(expected = BusinessException.class)
    public void shouldThrowBusinessExceptionIfElectricalApplianceIsAlreadyTurnedOn() throws BusinessException {
        appliance.setConnectToSocket(true);
        appliance.setTurnOn(true);
        instance.turnOn(appliance);
    }

    @Test
    public void shouldSetIsTurnOnInFalse() throws BusinessException {
        appliance.setTurnOn(true);

        instance.turnOff(appliance);
        boolean res = appliance.isTurnOn();
        assertFalse(res);
    }

    @Test(expected = BusinessException.class)
    public void shouldThrowBusinessExceptionIfElectricalApplianceIsAlreadyTurnedOff() throws BusinessException {
        appliance.setTurnOn(false);
        instance.turnOff(appliance);
    }
}
