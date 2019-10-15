package service;

import cmd.ApplianceMenuState;
import entity.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FlatService {
    private static final Logger LOG = LogManager.getLogger(FlatService.class);

    public ElectricalAppliance findApplianceByName(Flat flat, String applianceName) {
        LOG.info("Finding appliances by name.\nFlat={}",flat);
        return flat.getAppliances().stream()
                .filter(appliance -> appliance.getName().toString().toLowerCase()
                        .equals(applianceName.toLowerCase()))
                .findFirst().orElseThrow(() -> new IllegalArgumentException(
                        String.format("Flat does not contain such appliance \"%s\". Try to choose another one%n", applianceName)));
    }

    public List<String> getAllAppliancesNames(Flat flat) {
        LOG.info("Getting all appliances names.\nFlat={}", flat);
        return flat.getAppliances().stream()
                .map(appliance -> appliance.getName().toString())
                .collect(Collectors.toList());
    }

    public List<String> getDangerousTurnedOnAppliancesNames(Flat flat) {
        LOG.info("Getting turned on dangerous appliances names.\nFlat={}", flat);
        return flat.getAppliances().stream()
                .filter(ElectricalAppliance::isTurnOn)
                .filter(appliance -> !appliance.isForContinuousWork())
                .map(appliance -> appliance.getName().toString())
                .collect(Collectors.toList());
    }

    public List<String> getNamesOfAllTurnedOnAppliances(Flat flat) {
        LOG.info("Getting all turned on appliances names.\nFlat={}", flat);
        return flat.getAppliances().stream()
                .filter(ElectricalAppliance::isTurnOn)
                .map(appliance -> appliance.getName().toString())
                .collect(Collectors.toList());
    }

    public int countCurrentElectricityLoad(Flat flat) {
        LOG.info("Counting current electricity load.\nFlat={}", flat);
        return flat.getAppliances().stream()
                .filter(ElectricalAppliance::isTurnOn)
                .mapToInt(ElectricalAppliance::getPowerConsumption)
                .sum();
    }

    public List<ElectricalAppliance> sortAppliancesByPowerAscending(Flat flat) {
        LOG.info("Sorting appliances by power ascending.\nFlat={}", flat);
        return flat.getAppliances().stream
                ().sorted()
                .collect(Collectors.toList());
    }

    public List<ElectricalAppliance> sortAppliancesByPowerDescending(Flat flat) {
        LOG.info("Sorting appliances by power descending.\nFlat={}", flat);
        return flat.getAppliances().stream
                ().sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());
    }

    public List<ElectricalAppliance> getAllAppliances(Flat flat) {
        LOG.info("Getting all appliances.\nFlat={}", flat);
        return flat.getAppliances();
    }

    public List<ElectricalAppliance> findAppliancesByParams(Flat flat, double weight, ApplianceColor color, ApplianceBrand brand, int power) {
        LOG.info("Finding appliances by parameters.\nFlat={}", flat);
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