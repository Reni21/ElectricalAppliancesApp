package cmd;

import entity.ElectricalAppliance;
import entity.Flat;
import exception.BusinessException;
import exception.OverLoadElectricityException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import service.ElectricalApplianceService;
import service.FlatService;

import java.util.List;

@RequiredArgsConstructor
public class ApplianceMenuState implements MenuState {
    @NonNull
    private MenuStateProvider menuStateProvider;
    @NonNull
    private ElectricalApplianceService applianceService;
    @NonNull
    private FlatService flatService;
    @NonNull
    private Flat flat;

    private ElectricalAppliance appliance;

    public void setAppliance(ElectricalAppliance appliance) {
        this.appliance = appliance;
    }

    @Override
    public void handleUserInput(String input, MenuContext context) throws BusinessException {
        if (input.isEmpty()) {
            return;
        }
        input = input.trim();
        if (input.startsWith("--s-")) {
            switchToApplianceMenu(context, input.substring(4));
            return;
        }
        switch (input) {
            case "--connect":
                connectApplianceToSocket();
                break;
            case "--d-connect":
                disconnectApplianceFromSocket();
                break;
            case "--on":
                turnOnAppliance();
                break;
            case "--off":
                turnOffAppliance();
                break;
            case "--all":
                List<String> names = flatService.getAllAppliancesNames(flat);
                System.out.println(String.join(", ", names));
                break;
            case "--return":
                MainMenuState mainMenuState = menuStateProvider.getMainMenuState();
                context.changeState(mainMenuState);
                break;
            case "--help":
                printHelp();
                break;
            default:
                System.out.format("Unknown command \"%s\"%n", input);
        }
    }

    private void connectApplianceToSocket() throws BusinessException {
        applianceService.connectToSocket(appliance);
        System.out.format("%s successfully connect to socket%n", appliance.getName());
    }

    private void turnOnAppliance() throws BusinessException {
        applianceService.turnOn(appliance);
        int totalElectricityLoad = flatService.countCurrentElectricityLoad(flat);
        if (totalElectricityLoad > flat.getMaxConductivity()) {
            throw new OverLoadElectricityException(
                    "Opps, the electricity went out... Electricity load is too height. Please, turn off something!");
        } else {
            System.out.format("%s successfully turned on%n", appliance.getName());
        }
    }

    private void turnOffAppliance() throws BusinessException {
        String msg = "";
        int totalElectricityLoad = flatService.countCurrentElectricityLoad(flat);
        if (totalElectricityLoad > flat.getMaxConductivity()) {
            msg = "The electricity turned on again! ";
        }
        applianceService.turnOff(appliance);
        totalElectricityLoad = flatService.countCurrentElectricityLoad(flat);
        if (totalElectricityLoad > flat.getMaxConductivity()) {
            System.out.format("%s is turned off%n", appliance.getName());
            throw new OverLoadElectricityException(
                    "Sorry, the electricity still went out... Please, turn off something else!");
        } else {
            System.out.format("%s%s successfully turned off%n", msg, appliance.getName());
        }
    }

    private void disconnectApplianceFromSocket() throws BusinessException {
        applianceService.disconnectFromSocket(appliance);
        System.out.format("%s successfully disconnect from socket%n", appliance.getName());
    }

    private void switchToApplianceMenu(MenuContext context, String applianceName) {
        ElectricalAppliance appliance = flatService.findApplianceByName(flat, applianceName);
        ApplianceMenuState applianceMenu = menuStateProvider.getApplianceMenuState(appliance);
        context.changeState(applianceMenu);
    }

    @Override
    public void printHelp() {
        System.out.format("\033[33m%n < - - - - - - - - - - - - - - %s MENU - - - - - - - - - - - - - - >%n\033[0m",
                appliance.getName().toString().toUpperCase());
        System.out.println(AppCommand.APPLIANCE_MENU_CMD);
    }
}
