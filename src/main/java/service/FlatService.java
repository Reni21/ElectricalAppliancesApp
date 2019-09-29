package service;

import entity.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FlatService {

    public ElectricalAppliance findApplianceByName(Flat flat, String applianceName) {
                return flat.getAppliances().stream()
                .filter(appliance -> appliance.getName().toString().toLowerCase()
                        .equals(applianceName.toLowerCase()))
                .findFirst().orElseThrow(() -> new IllegalArgumentException(
                String.format("Flat does not contain such appliance \"%s\". Try to choose another one%n", applianceName)));
    }

    public List<String> getAllAppliancesNames(Flat flat) {
        return flat.getAppliances().stream()
                .map(appliance -> appliance.getName().toString())
                .collect(Collectors.toList());
    }

    public List<String> getDangerousTurnedOnAppliancesNames(Flat flat) {
        return flat.getAppliances().stream()
                .filter(ElectricalAppliance::isTurnOn)
                .filter(appliance -> !appliance.isForContinuousWork())
                .map(appliance -> appliance.getName().toString())
                .collect(Collectors.toList());
    }

    public List<String> getNamesOfAllTurnedOnAppliances(Flat flat) {
        return flat.getAppliances().stream()
                .filter(ElectricalAppliance::isTurnOn)
                .map(appliance -> appliance.getName().toString())
                .collect(Collectors.toList());
    }

    public int countCurrentElectricityLoad(Flat flat) {
        return flat.getAppliances().stream()
                .filter(ElectricalAppliance::isTurnOn)
                .mapToInt(ElectricalAppliance::getPowerConsumption)
                .sum();
    }

    public List<ElectricalAppliance> sortAppliancesByPower(Flat flat) {
        return flat.getAppliances().stream
                ().sorted()
                .collect(Collectors.toList());
    }

    public List<ElectricalAppliance> getAllAppliances(Flat flat) {
        return flat.getAppliances();
    }

    public List<ElectricalAppliance> findAppliancesByParams(Flat flat, double weight, ApplianceColor color, ApplianceBrand brand, int power) {
        Stream<ElectricalAppliance> result = flat.getAppliances().stream();
        if (weight > 0) {
            result = result.filter(appliance -> appliance.getWeight() == weight);
        }
        if (color != null) {
            result = result.filter(appliance -> appliance.getColor().equals(color));
        }
        if (brand != null) {
            result = result.filter(appliance -> appliance.getBrand().equals(brand));
        }
        if (power > 0) {
            result = result.filter(appliance -> appliance.getPowerConsumption() == power);
        }
        return result.collect(Collectors.toList());
    }
}