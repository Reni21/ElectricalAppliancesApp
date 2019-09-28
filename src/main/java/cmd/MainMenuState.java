package cmd;

import entity.ApplianceName;
import entity.ElectricalAppliance;
import entity.Flat;
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
    public void handleUserInput(String input, MenuContext context) {
        if (input.isEmpty()){
            return;
        }
        if (input.startsWith("--s-")) {
            switchToApplianceMenu(context, input);
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
                System.out.format("Unknown command \"%s\"", input);
                printHelp();
        }
    }

    private void sortAppliancesByPower() {
        flatService.sortAppliancesByPower(flat)
                .forEach(appliance -> System.out.println(appliance.toString()));
    }

    private void switchToApplianceMenu(MenuContext context, String input) {
        String extractedName = input.substring(4);
        ApplianceName applianceSimpleName = ApplianceName.getValueByName(extractedName);
        if (applianceSimpleName == null) {
            throw new IllegalArgumentException(
                    String.format("Appliance \"%s\" doesn't exist. Try one more%n", extractedName));
        }
        ElectricalAppliance appliance = flatService.findApplianceByName(flat, applianceSimpleName);
        ApplianceMenuState applianceMenu = menuStateProvider.getApplianceMenuState(appliance);
        context.changeState(applianceMenu);
    }

    private void showWorkingAppliances() {
        List<String> turnedOnAppliances = flatService.getNamesOfAllTurnedOnAppliances(flat);
        if (turnedOnAppliances.isEmpty()) {
            System.out.println("All electrical appliance are turn of");
        } else {
            System.out.format("Turned on appliances: %s%n",
                    String.join(", ", turnedOnAppliances));
        }
    }

    private boolean isAllDangerApplianceTurnedOff() {
        return flatService.getNamesOfNotForContinuesWorkAppliances(flat).isEmpty();
    }

    private void quiteApp() {
        if (isAllDangerApplianceTurnedOff()) {
            System.out.println("Quit the app"); // add check on turn on appliances
            System.exit(0);
        } else {
            System.out.println((char) 27 + "[31m" +
                    "ATTENTION! You leave some appliances TURN ON, it can be dangerous!" +
                    (char) 27 + "[0m");
            System.out.print("Must be turned off: ");
            System.out.println(String.join(", ", flatService.getNamesOfNotForContinuesWorkAppliances(flat)));
        }
    }

    @Override
    public void printHelp() {
        System.out.println(AppCommand.MAIN_MENU_CMD);
    }
}
