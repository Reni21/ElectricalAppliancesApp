package app;

import app.cmd.MenuContext;
import app.cmd.MenuStateProvider;
import app.entity.Flat;
import app.exception.ApplianceNotConnectToSocketException;
import app.exception.BusinessException;
import app.exception.FireSafetyException;
import app.exception.OverLoadElectricityException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

@SpringBootApplication
public class Main implements CommandLineRunner {
    private static final Logger LOG = LogManager.getLogger(Main.class);
    @Autowired
    private MenuStateProvider menuProvider;

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    public Flat getFlat() {
        Flat flat = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("flat.json");
            flat = objectMapper.readValue(is, Flat.class);

        } catch (IOException ex) {
            LOG.error("Program crashed.\n", ex);
            System.exit(-1);
        }
        return flat;
    }

    @Override
    public void run(String... args) {
        runElectricalApplianceApp();
    }

    private void runElectricalApplianceApp() {
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
                    LOG.warn("User action rejected: input=\"{}\" - app.exception message \"{}\"",
                            input, ex.getMessage());
                    System.out.format("\033[93mREJECT! %s%n\033[0m", ex.getMessage());
                } catch (OverLoadElectricityException | FireSafetyException ex) {
                    LOG.warn("User action rejected: input=\"{}\" - app.exception message \"{}\"",
                            input, ex.getMessage());
                    System.out.println("\033[91m" + ex.getMessage() + "\033[0m");
                } catch (BusinessException | IllegalArgumentException ex) {
                    LOG.warn("User action rejected: input=\"{}\" - app.exception message \"{}\"",
                            input, ex.getMessage());
                    System.out.println("\033[93m" + ex.getMessage() + "\033[0m");
                }
            }
        }
    }
}
