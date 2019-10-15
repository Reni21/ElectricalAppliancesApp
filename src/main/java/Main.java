import cmd.MenuContext;
import cmd.MenuStateProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import entity.Flat;
import exception.ApplianceNotConnectToSocketException;
import exception.BusinessException;
import exception.FireSafetyException;
import exception.OverLoadElectricityException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.ElectricalApplianceService;
import service.FlatService;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class Main {
    private static final Logger LOG = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("flat.json");
            Flat flat = objectMapper.readValue(is, Flat.class);
            FlatService flatService = new FlatService();
            ElectricalApplianceService applianceService = new ElectricalApplianceService();
            MenuStateProvider menuProvider = new MenuStateProvider(applianceService, flatService, flat);

            runElectricalApplianceApp(menuProvider);
        } catch (IOException ex) {
            LOG.error("Program crashed.\n", ex);
            System.exit(-1);
        }
    }

    private static void runElectricalApplianceApp(MenuStateProvider menuProvider) {
        LOG.debug("Start app");
        MenuContext menuContext = new MenuContext(menuProvider.getMainMenuState());
        menuContext.printHelp();

        String input = null;
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                try {
                    input = scanner.nextLine();
                    LOG.info("User input= \"{}\"", input);
                    menuContext.handleUserInput(input);
                } catch (ApplianceNotConnectToSocketException ex) {
                    LOG.warn("User action rejected: input=\"{}\" - exception message \"{}\"",
                            input, ex.getMessage());
                    System.out.format("\033[93mREJECT! %s%n\033[0m", ex.getMessage());
                } catch (OverLoadElectricityException | FireSafetyException ex) {
                    LOG.warn("User action rejected: input=\"{}\" - exception message \"{}\"",
                            input, ex.getMessage());
                    System.out.println("\033[91m" + ex.getMessage() + "\033[0m");
                } catch (BusinessException | IllegalArgumentException ex) {
                    LOG.warn("User action rejected: input=\"{}\" - exception message \"{}\"",
                            input, ex.getMessage());
                    System.out.println("\033[93m" + ex.getMessage() + "\033[0m");
                }
            }
        }
    }
}
