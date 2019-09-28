package cmd;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MenuContext {
    private MenuState menuState;

    public void changeState(MenuState newState) {
        menuState = newState;
        newState.printHelp();
    }

    public void handleUserInput(String input) {
        menuState.handleUserInput(input, this);
    }

    public void printHelp() {
        menuState.printHelp();
    }
}
