package entity;

import java.util.Arrays;

public enum ApplianceName {
    HAIRDRYER, VACUUM_CLEANER, WASHING_MACHINE, FRIDGE, IRON, LAMP, TV, LAPTOP;

    public static ApplianceName getValueByName(String name) {
        return Arrays.stream(ApplianceName.values())
                .filter(applianceName -> applianceName.toString().toUpperCase().equals(name.toUpperCase()))
                .findFirst()
                .orElse(null);
    }

    @Override
    public String toString() {
        if (super.equals(TV)) {
            return "TV";
        }
        String name = super.toString().toLowerCase();
        String firstChar = Character.toString(name.charAt(0));
        String firstCharInUpperCase = firstChar.toUpperCase();
        return name.replaceFirst(firstChar, firstCharInUpperCase)
                .replace('_', ' ');
    }
}
