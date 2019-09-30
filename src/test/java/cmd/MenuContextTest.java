package cmd;

import exception.BusinessException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MenuContextTest {
    @Mock
    private MenuState menuState;
    @InjectMocks
    private MenuContext instance;

    @Before
    public void setUp() {
        instance = new MenuContext(menuState);
    }

    @Test
    public void shouldChangeMenuState() {
        MenuState newState = mock(MenuState.class);

        instance.changeState(newState);
        verify(newState).printHelp();
    }

    @Test
    public void shouldDelegateHandleUserInputMethodCallToMenuState() throws BusinessException {
        String input = "test";
        instance.handleUserInput(input);
        verify(menuState).handleUserInput(input, instance);
    }

    @Test(expected = BusinessException.class)
    public void shouldThrowsBusinessExceptionWheCalledHandleUserInputOnMenuStateTrow() throws BusinessException {
        String input = "test";
        doThrow(new BusinessException()).when(menuState).handleUserInput(input, instance);
        instance.handleUserInput(input);
    }
}
