package cmd;

import entity.ApplianceBrand;
import entity.ApplianceColor;
import entity.ElectricalAppliance;
import entity.Flat;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import service.FlatService;

import java.util.List;

@RequiredArgsConstructor
public class SearchMenuState implements MenuState {
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
        switch (input) {
            case "--all":
                List<ElectricalAppliance> appliances = flatService.getAllAppliances(flat);
                appliances.forEach(appliance -> System.out.println(appliance.toString()));
                break;
            case "--return":
                context.changeState(menuStateProvider.getMainMenuState());
                break;
            case "--help":
                printHelp();
            default:
                searchByParameters(input);
        }
    }

    private void searchByParameters(String input) {
        double weight = 0;
        ApplianceColor color = null;
        ApplianceBrand brand = null;
        int power = 0;

        for (String s : input.split(" ")) {
            if (s.startsWith("--w-")) {
                weight = parsFractionalNumber(s);
            } else if (s.startsWith("--c-")) {
                color = parsColor(s);
            } else if (s.startsWith("--b-")) {
                brand = parsBrand(s);
            } else if (s.startsWith("--p-")) {
                power = parsIntegerNumber(s);
            } else {
                System.out.format("Unknown command \"%s\"%n", s.substring(0, 4));
                return;
            }
        }
        findAndPrintResult(weight, color, brand, power);
    }

    private void findAndPrintResult(double weight, ApplianceColor color, ApplianceBrand brand, Integer power) {
        List<ElectricalAppliance> appliances = flatService.findAppliancesByParams(flat, weight, color, brand, power);
        if (appliances.isEmpty()) {
            System.out.println("Search result: not appliances with such parameters");
        } else {
            appliances.forEach(appliance -> System.out.println(appliance.toString()));
        }
    }

    private ApplianceColor parsColor(String s) {
        try {
            return ApplianceColor.valueOf(s.substring(4).toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException(
                    String.format("\"%s\" - this parameter is not valid and can not be used in search%n", s.substring(4)));
        }
    }

    private ApplianceBrand parsBrand(String s) {
        try {
            return ApplianceBrand.valueOf(s.substring(4).toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException(
                    String.format("\"%s\" - this parameter is not valid and can not be used in search%n", s.substring(4)));
        }
    }

    private int parsIntegerNumber(String s) {
        try {
            return Integer.parseInt(s.substring(4));
        } catch (NumberFormatException ex) {
            throw new NumberFormatException(
                    String.format("\"%s\" - this parameter is not valid and can not be used in search%n", s.substring(4))
            );
        }
    }

    private double parsFractionalNumber(String s) {
        try {
            return Double.parseDouble(s.substring(4));
        } catch (NumberFormatException ex) {
            throw new NumberFormatException(
                    String.format("\"%s\" - this parameter is not valid and can not be used in search%n", s.substring(4))
            );
        }
    }

    @Override
    public void printHelp() {
        System.out.println(AppCommand.SEARCH_MENU);
    }
}
