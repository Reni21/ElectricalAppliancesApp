package cmd;

import exception.BusinessException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class MenuContext {
    private MenuState menuState;

    public void changeState(MenuState newState) {
        menuState = newState;
        menuState.printHelp();
    }

    public void handleUserInput(String input) throws BusinessException {
        menuState.handleUserInput(input, this);
    }

    public void printHelp() {
        menuState.printHelp();
    }
}
