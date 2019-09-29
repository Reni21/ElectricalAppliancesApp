package service;

import entity.ApplianceBrand;
import entity.ApplianceColor;
import entity.ApplianceName;
import entity.ElectricalAppliance;
import exception.BusinessException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ElectricalApplianceServiceTest {
    private ElectricalApplianceService instance;
    private ElectricalAppliance appliance;

    @Before
    public void setUp() throws Exception {
        this.instance = new ElectricalApplianceService();
        this.appliance = new ElectricalAppliance(200, false, 0.45,
                ApplianceColor.YELLOW, ApplianceName.HAIRDRYER, ApplianceBrand.PHILIPS);
    }

    @Test
    public void shouldSetIsConnectToSocketInTrue() throws BusinessException {
        instance.connectToSocket(appliance);
        boolean res = appliance.isConnectToSocket();
        assertTrue(res);
    }

    @Test
    public void shouldSetIsConnectToSocketInFalse() throws BusinessException {
        instance.disconnectFromSocket(appliance);
        boolean res = appliance.isConnectToSocket();
        assertFalse(res);
    }

    @Test
    public void shouldSetIsTurnOnInTrue() throws BusinessException {
        instance.turnOn(appliance);
        boolean res = appliance.isTurnOn();
        assertTrue(res);
    }

    @Test
    public void shouldSetIsTurnOnInFalse() throws BusinessException {
        instance.turnOff(appliance);
        boolean res = appliance.isTurnOn();
        assertFalse(res);
    }
}
