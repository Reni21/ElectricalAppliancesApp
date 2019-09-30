package cmd;

import entity.ElectricalAppliance;
import entity.Flat;
import exception.BusinessException;
import exception.FireSafetyException;
import lombok.NonNull;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import service.FlatService;
import util.DataProvider;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MainMenuStateTest {
    @Mock
    MenuContext menuContext;
    @Mock
    private MenuStateProvider menuStateProvider;
    @Mock
    private FlatService flatService;
    @Spy
    private Flat flat;
    @InjectMocks
    private MainMenuState instance;

    @Before
    public void setUp() {
        flat = DataProvider.createFlat();
        instance = new MainMenuState(menuStateProvider, flatService, flat);
    }

    @Test
    public void shouldSwitchToApplianceMenu() throws BusinessException {
        ElectricalAppliance tv = mock(ElectricalAppliance.class);
        when(flatService.findApplianceByName(flat, "tv")).thenReturn(tv);
        ApplianceMenuState applianceMenuState = mock(ApplianceMenuState.class);
        when(menuStateProvider.getApplianceMenuState(tv)).thenReturn(applianceMenuState);
        MenuContext context = mock(MenuContext.class);

        instance.handleUserInput("--s-tv", context);
        verify(context).changeState(applianceMenuState);
    }

    @Test
    public void shouldCallGetAllAppliancesNamesOnFlatService() throws BusinessException {
        instance.handleUserInput("--all", menuContext);
        verify(flatService).getAllAppliancesNames(flat);
    }

    @Test
    public void shouldShowWorkingAppliances() throws BusinessException {
        instance.handleUserInput("--show", menuContext);
        verify(flatService).getNamesOfAllTurnedOnAppliances(flat);
    }

    @Test
    public void shouldSortAppliances() throws BusinessException {
        instance.handleUserInput("--sort", menuContext);
        verify(flatService).sortAppliancesByPower(flat);
    }

    @Test
    public void shouldSwitchToSearchMenuState() throws BusinessException {
        MenuContext context = mock(MenuContext.class);
        SearchMenuState searchMenu = mock(SearchMenuState.class);
        when(menuStateProvider.getSearchMenuState()).thenReturn(searchMenu);

        instance.handleUserInput("--search", context);
        verify(context).changeState(searchMenu);
    }

    @Test
    public void shouldCountCurrentElectricityLoadInFlat() throws BusinessException {
        instance.handleUserInput("--load", menuContext);
        verify(flatService).countCurrentElectricityLoad(flat);
    }

    @Test
    public void shouldQuiteTheFlat() throws BusinessException {
        List<String> dangerousAppliances = new ArrayList<>();
        when(flatService.getDangerousTurnedOnAppliancesNames(flat)).thenReturn(dangerousAppliances);
        instance.handleUserInput("--q", menuContext);
    }

    @Test(expected = FireSafetyException.class)
    public void shouldThrowFireSafetyExceptionWhileQuiteTheFlat() throws BusinessException {
        List<String> dangerousAppliances = new ArrayList<>();
        dangerousAppliances.add("Iron");
        when(flatService.getDangerousTurnedOnAppliancesNames(flat)).thenReturn(dangerousAppliances);
        instance.handleUserInput("--q", menuContext);
    }
}
