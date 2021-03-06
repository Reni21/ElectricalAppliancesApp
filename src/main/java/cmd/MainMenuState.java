package cmd;

import entity.ElectricalAppliance;
import entity.Flat;
import exception.FireSafetyException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.FlatService;

import java.util.List;

@RequiredArgsConstructor
public class MainMenuState implements MenuState {
    private static final Logger LOG = LogManager.getLogger(MainMenuState.class);
    @NonNull
    private MenuStateProvider menuStateProvider;
    @NonNull
    private FlatService flatService;
    @NonNull
    private Flat flat;

    @Override
    public void handleUserInput(String input, MenuContext context) throws FireSafetyException {
        if (input.isEmpty()) {
            return;
        }
        input = input.trim();
        if (input.startsWith("--s-")) {
            String applianceName = input.substring(4);
            switchToApplianceMenu(context, applianceName);
            return;
        }

        switch (input) {
            case "--all":
                List<String> names = flatService.getAllAppliancesNames(flat);
                System.out.println(String.join(", ", names));
                break;
            case "--show":
                showWorkingAppliances();
                break;
            case "--sort+":
                printAppliances(flatService.sortAppliancesByPowerAscending(flat));
                break;
            case "--sort-":
                printAppliances(flatService.sortAppliancesByPowerDescending(flat));
                break;
            case "--search":
                context.changeState(menuStateProvider.getSearchMenuState());
                break;
            case "--load":
                System.out.format("Current electricity load: %d watt%n", flatService.countCurrentElectricityLoad(flat));
                break;
            case "--q":
                quiteApp();
                break;
            case "--help":
                printHelp();
                break;
            default:
                LOG.warn("Unknown command.");
                System.out.format("Unknown command \"%s\"%n", input);
        }
    }

    private void printAppliances(List<ElectricalAppliance> appliances) {
        appliances.forEach(appliance -> System.out.println(appliance.toString()));
    }

    private void switchToApplianceMenu(MenuContext context, String applianceName) {
        ElectricalAppliance appliance = flatService.findApplianceByName(flat, applianceName);
        ApplianceMenuState applianceMenu = menuStateProvider.getApplianceMenuState(appliance);
        context.changeState(applianceMenu);
    }

    private void showWorkingAppliances() {
        List<String> turnedOnAppliances = flatService.getNamesOfAllTurnedOnAppliances(flat);
        if (turnedOnAppliances.isEmpty()) {
            LOG.info("Empty appliances names list.");
            System.out.println("All electrical appliance are turned of");
        } else {
            System.out.format("Turned on appliances: %s%n",
                    String.join(", ", turnedOnAppliances));
        }
    }

    private boolean isAllDangerApplianceTurnedOff() {
        return flatService.getDangerousTurnedOnAppliancesNames(flat).isEmpty();
    }

    private void quiteApp() throws FireSafetyException {
        if (isAllDangerApplianceTurnedOff()) {
            System.out.println("Quit the app...");
            LOG.debug("Quit the app");
            System.exit(0);
        } else {
            String appliances = String.join(", ", flatService.getDangerousTurnedOnAppliancesNames(flat));
            System.out.format("\033[91mDanger appliances which not turned off: %s%n\033[0m", appliances);
            LOG.warn("Turned off danger appliances:\n{}", appliances);
            throw new FireSafetyException("ATTENTION! You leave some appliances TURN ON, it can be dangerous!");
        }
    }

    @Override
    public void printHelp() {
        System.out.println(AppCommand.MAIN_MENU_CMD);
    }
}
