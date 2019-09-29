package cmd;

import entity.ElectricalAppliance;
import entity.Flat;
import exception.FireSafetyException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import service.FlatService;

import java.util.List;

@RequiredArgsConstructor
public class MainMenuState implements MenuState {
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
        if (input.startsWith("--s-")) {
            switchToApplianceMenu(context, input.substring(4));
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
            case "--sort":
                sortAppliancesByPower();
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
                System.out.format("Unknown command \"%s\"%n", input);
        }
    }

    private void sortAppliancesByPower() {
        flatService.sortAppliancesByPower(flat)
                .forEach(appliance -> System.out.println(appliance.toString()));
    }

    private void switchToApplianceMenu(MenuContext context, String applianceName) {
        ElectricalAppliance appliance = flatService.findApplianceByName(flat, applianceName);
        ApplianceMenuState applianceMenu = menuStateProvider.getApplianceMenuState(appliance);
        context.changeState(applianceMenu);
    }

    private void showWorkingAppliances() {
        List<String> turnedOnAppliances = flatService.getNamesOfAllTurnedOnAppliances(flat);
        if (turnedOnAppliances.isEmpty()) {
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
            System.out.println("Quit the app"); // add check on turn on appliances
            System.exit(0);
        } else {
            System.out.print("Danger appliances which not turned off: ");
            System.out.println(String.join(", ", flatService.getDangerousTurnedOnAppliancesNames(flat)));
            throw new FireSafetyException("ATTENTION! You leave some appliances TURN ON, it can be dangerous!");
        }
    }

    @Override
    public void printHelp() {
        System.out.println(AppCommand.MAIN_MENU_CMD);
    }
}
