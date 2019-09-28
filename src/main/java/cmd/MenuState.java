package cmd;

public interface MenuState {
    void handleUserInput(String input, MenuContext context);

    void printHelp();
}

