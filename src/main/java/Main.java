import cmd.MenuContext;
import cmd.MenuStateProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import entity.Flat;
import service.ElectricalApplianceService;
import service.FlatService;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Flat flat = objectMapper.readValue(new File("src/main/resources/flat.json"), Flat.class);
            FlatService flatService = new FlatService();
            ElectricalApplianceService applianceService = new ElectricalApplianceService();
            MenuStateProvider menuProvider = new MenuStateProvider(applianceService, flatService, flat);

            runElectricalApplianceApp(menuProvider);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    private static void runElectricalApplianceApp(MenuStateProvider menuProvider) {
        MenuContext menuContext = new MenuContext(menuProvider.getMainMenuState());
        menuContext.printHelp();

        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                try {
                    String input = scanner.nextLine();
                    menuContext.handleUserInput(input);
                } catch (IllegalArgumentException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }
    }
}
