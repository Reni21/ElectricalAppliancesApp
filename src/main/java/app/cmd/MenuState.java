package app.cmd;

import app.exception.BusinessException;

public interface MenuState {
    void handleUserInput(String input, MenuContext context) throws BusinessException;

    void printHelp();
}

