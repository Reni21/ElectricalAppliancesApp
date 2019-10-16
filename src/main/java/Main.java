import cmd.MenuContext;
import cmd.MenuStateProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import entity.*;
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
import java.sql.*;
import java.util.Scanner;

public class Main {
    private static final Logger LOG = LogManager.getLogger(Main.class);

    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DATABASE_URL = "jdbc:mysql://localhost/lab_01";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    public static void main(String[] args) throws ClassNotFoundException {
        // JDBC connection
        Class.forName(JDBC_DRIVER);
        String sql = "SELECT * FROM el_appliance";
        try (Connection conn = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
             Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            testJDBCConnection(resultSet);
        } catch ( SQLException e) {
            e.printStackTrace();
        }

        // App start

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

    private static void testJDBCConnection(ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            double weight = resultSet.getDouble("weight");
            String color = resultSet.getString("color");
            ApplianceColor color1 = ApplianceColor.valueOf(color.toUpperCase());
            String name = resultSet.getString("name");
            ApplianceName name1 = ApplianceName.getValueByName(name);
            String brand = resultSet.getString("brand");
            ApplianceBrand brand1 = ApplianceBrand.valueOf(brand.toUpperCase());
            int powerCons = resultSet.getInt("power_consumption");
            boolean isForContWork = resultSet.getBoolean("is_for_continuous_work");
            boolean isConnect = resultSet.getBoolean("is_connect_to_socket");
            boolean isTurnOn = resultSet.getBoolean("is_turn_on");

            System.out.println("Id=" + id);
            ElectricalAppliance appliance = new ElectricalAppliance(powerCons, isForContWork, weight, color1, name1, brand1);
            appliance.setTurnOn(isTurnOn);
            appliance.setConnectToSocket(isConnect);
            System.out.println(appliance);
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
