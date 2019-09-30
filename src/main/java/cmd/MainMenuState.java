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
        input = input.trim();
        if (input.startsWith("--s-")) {
            switchToApplianceMenu(context, input.substring(4));
            return;
        }

        switch (input) {
            case "--all":
                List<String> names = flatService.getAllAppliancesNames(flat);
                System.out.println("\033[33m" + String.join(", ", names) + "\033[0m");
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
                System.out.format("\033[33mCurrent electricity load: %d watt%n\033[0m", flatService.countCurrentElectricityLoad(flat));
                break;
            case "--q":
                quiteApp();
                break;
            case "--help":
                printHelp();
                break;
            default:
                System.out.format("\033[33mUnknown command \"%s\"%n\033[0m", input);
        }
    }

    private void sortAppliancesByPower() {
        flatService.sortAppliancesByPower(flat)
                .forEach(appliance -> System.out.println("\033[33m" + appliance.toString() + "\033[0m"));
    }

    private void switchToApplianceMenu(MenuContext context, String applianceName) {
        ElectricalAppliance appliance = flatService.findApplianceByName(flat, applianceName);
        ApplianceMenuState applianceMenu = menuStateProvider.getApplianceMenuState(appliance);
        context.changeState(applianceMenu);
    }

    private void showWorkingAppliances() {
        List<String> turnedOnAppliances = flatService.getNamesOfAllTurnedOnAppliances(flat);
        if (turnedOnAppliances.isEmpty()) {
            System.out.println("\033[33mAll electrical appliance are turned of\033[0m");
        } else {
            System.out.format("\033[33mTurned on appliances: %s%n\033[0m",
                    String.join(", ", turnedOnAppliances));
        }
    }

    private boolean isAllDangerApplianceTurnedOff() {
        return flatService.getDangerousTurnedOnAppliancesNames(flat).isEmpty();
    }

    private void quiteApp() throws FireSafetyException {
        if (isAllDangerApplianceTurnedOff()) {
            System.out.println("\033[33mQuit the app\033[0m");
            System.exit(0);
        } else {
            String appliances = String.join(", ", flatService.getDangerousTurnedOnAppliancesNames(flat));
            System.out.format("\033[33mDanger appliances which not turned off: %s%n\033[0m", appliances);

            throw new FireSafetyException("ATTENTION! You leave some appliances TURN ON, it can be dangerous!");
        }
    }

    @Override
    public void printHelp() {
        System.out.println(AppCommand.MAIN_MENU_CMD);
    }
}
