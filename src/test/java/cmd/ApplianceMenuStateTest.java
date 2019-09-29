package cmd;

import entity.ElectricalAppliance;
import entity.Flat;
import exception.BusinessException;
import exception.OverLoadElectricityException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import service.ElectricalApplianceService;
import service.FlatService;
import util.DataProvider;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ApplianceMenuStateTest {
    @Mock
    private MenuStateProvider menuStateProvider;
    @Mock
    private ElectricalApplianceService applianceService;
    @Mock
    private FlatService flatService;
    @Spy
    private Flat flat;
    @Spy
    private ElectricalAppliance appliance;

    @InjectMocks
    private ApplianceMenuState instance;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
        flat = DataProvider.createFlat();
        instance = new ApplianceMenuState(menuStateProvider, applianceService, flatService, flat);
        appliance = DataProvider.createLamp();
        instance.setAppliance(appliance);
    }

    @Test
    public void shouldSwitchToApplianceMenu() throws BusinessException {
        ElectricalAppliance tv = mock(ElectricalAppliance.class);
        when(flatService.findApplianceByName(flat, "tv")).thenReturn(tv);
        ApplianceMenuState applianceMenuState = mock(ApplianceMenuState.class);
        when(menuStateProvider.getApplianceMenuState(tv)).thenReturn(applianceMenuState);
        MenuContext menuContext = mock(MenuContext.class);

        instance.handleUserInput("--s-tv", menuContext);
        verify(menuContext).changeState(applianceMenuState);
    }

    @Test
    public void shouldCallConnectApplianceToSocket() throws BusinessException {
        instance.handleUserInput("--connect", mock(MenuContext.class));
        verify(applianceService).connectToSocket(appliance);
    }

    @Test
    public void shouldCallDisconnectApplianceFromSocket() throws BusinessException {
        instance.handleUserInput("--d-connect", mock(MenuContext.class));
        verify(applianceService).disconnectFromSocket(appliance);
    }

    @Test
    public void shouldTurnOnAppliance() throws BusinessException {
        when(flatService.countCurrentElectricityLoad(flat)).thenReturn(200);

        instance.handleUserInput("--on", mock(MenuContext.class));
        verify(applianceService).turnOn(appliance);
        verify(flatService).countCurrentElectricityLoad(flat);
    }

    @Test(expected = OverLoadElectricityException.class)
    public void shouldThrowOverLoadElectricityExceptionWhenTurnOnAppliance() throws BusinessException {
        when(flatService.countCurrentElectricityLoad(flat)).thenReturn(3500);

        instance.handleUserInput("--on", mock(MenuContext.class));
        verify(applianceService).turnOn(appliance);
        verify(flatService).countCurrentElectricityLoad(flat);
    }

    @Test
    public void shouldTurnOffAppliance() throws BusinessException {
        when(flatService.countCurrentElectricityLoad(flat)).thenReturn(200).thenReturn(250);

        instance.handleUserInput("--off", mock(MenuContext.class));
        verify(applianceService).turnOff(appliance);
    }

    @Test(expected = OverLoadElectricityException.class)
    public void shouldThrowOverLoadElectricityExceptionOffAppliance() throws BusinessException {
        when(flatService.countCurrentElectricityLoad(flat)).thenReturn(3500).thenReturn(3200);

        instance.handleUserInput("--off", mock(MenuContext.class));
        verify(applianceService).turnOff(appliance);
    }

    @Test
    public void shouldCallGetAllAppliancesNamesOnFlatService() throws BusinessException {
        instance.handleUserInput("--all", mock(MenuContext.class));
        verify(flatService).getAllAppliancesNames(flat);
    }

    @Test
    public void shouldChangeMenuState() throws BusinessException {
        MainMenuState newMock = mock(MainMenuState.class);
        MenuContext context = mock(MenuContext.class);
        when(menuStateProvider.getMainMenuState()).thenReturn(newMock);
        instance.handleUserInput("--return", context);

        verify(context).changeState(newMock);
    }



}