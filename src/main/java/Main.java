import cmd.MenuContext;
import cmd.MenuStateProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import entity.Flat;
import exception.ApplianceNotConnectToSocketException;
import exception.BusinessException;
import exception.FireSafetyException;
import exception.OverLoadElectricityException;
import service.ElectricalApplianceService;
import service.FlatService;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("flat.json");
            Flat flat = objectMapper.readValue(is, Flat.class);
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
                } catch (ApplianceNotConnectToSocketException ex) {
                    System.out.format("REJECT! %s%n", ex.getMessage());
                } catch (OverLoadElectricityException | FireSafetyException ex) {
                    System.out.println((char) 27 + "[31m" + ex.getMessage() + (char) 27 + "[0m");
                } catch (BusinessException | IllegalArgumentException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }
    }
}
