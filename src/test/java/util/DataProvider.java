package util;

import app.entity.*;

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
