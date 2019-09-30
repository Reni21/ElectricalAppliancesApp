package cmd;

import entity.Flat;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import service.FlatService;
import util.DataProvider;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SearchMenuStateTest {
    @Mock
    private MenuContext menuContext;
    @Mock
    private MenuStateProvider menuStateProvider;
    @Mock
    private FlatService flatService;
    @Spy
    private Flat flat;
    @InjectMocks
    private SearchMenuState instance;

    @Before
    public void setUp() {
        flat = DataProvider.createFlat();
        instance = new SearchMenuState(menuStateProvider, flatService, flat);
    }

    @Test
    public void shouldCallGetAllAppliancesNamesOnFlatService() {
        instance.handleUserInput("--all", menuContext);
        verify(flatService).getAllAppliances(flat);
    }

    @Test
    public void shouldChangeMenuState() {
        MainMenuState newMock = mock(MainMenuState.class);
        MenuContext context = mock(MenuContext.class);
        when(menuStateProvider.getMainMenuState()).thenReturn(newMock);
        instance.handleUserInput("--return", context);

        verify(context).changeState(newMock);
    }

    @Test
    public void shouldSearchForGivenAppliancesInFlat() {
        instance.handleUserInput("--p-700", mock(MenuContext.class));
        verify(flatService).findAppliancesByParams(flat, 0, null, null, 700);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionIfInvalidPowerParamFormat() {
        instance.handleUserInput("--p-ert", menuContext);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionIfInvalidWeightParamFormat() {
        instance.handleUserInput("--w-ert", menuContext);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionIfInvalidColorParamFormat() {
        instance.handleUserInput("--c-76", menuContext);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionIfInvalidBrandParamFormat() {
        instance.handleUserInput("--b-76", menuContext);
    }
}
