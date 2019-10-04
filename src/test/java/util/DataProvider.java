package util;

import cmd.ApplianceMenuState;
import cmd.MainMenuState;
import cmd.MenuState;
import cmd.MenuStateProvider;
import entity.*;
import service.ElectricalApplianceService;
import service.FlatService;

import java.util.ArrayList;
import java.util.List;

public class DataProvider {

    public static Flat createFlat() {
        Flat flat = new Flat(1000);
        flat.setAppliances(createElectricalAppliances());
        return flat;
    }

    public static List<ElectricalAppliance> createElectricalAppliances() {
        List<ElectricalAppliance> appliances = new ArrayList<>();
        appliances.add(new ElectricalAppliance(700, true, 7,
                ApplianceColor.BLACK, ApplianceName.TV, ApplianceBrand.SAMSUNG));
        appliances.add(new ElectricalAppliance(350, false, 0.45,
                ApplianceColor.YELLOW, ApplianceName.HAIRDRYER, ApplianceBrand.PHILIPS));
        return appliances;
    }

    public static ElectricalAppliance createLamp() {
        return new ElectricalAppliance(240, true, 7,
                ApplianceColor.BLACK, ApplianceName.LAMP, ApplianceBrand.MAXUS);
    }
}
