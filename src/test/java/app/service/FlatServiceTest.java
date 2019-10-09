package app.service;

import app.entity.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class FlatServiceTest {
    private FlatService instance;
    private Flat flat;

    @Before
    public void setUp() {
        this.instance = new FlatService();
        this.flat = new Flat(1000);
    }

    private List<ElectricalAppliance> generateListOfElectricalAppliances() {
        List<ElectricalAppliance> appliances = new ArrayList<>();
        appliances.add(new ElectricalAppliance(700, true, 7,
                ApplianceColor.BLACK, ApplianceName.TV, ApplianceBrand.SAMSUNG));
        appliances.add(new ElectricalAppliance(350, false, 0.45,
                ApplianceColor.YELLOW, ApplianceName.HAIRDRYER, ApplianceBrand.PHILIPS));
        flat.setAppliances(appliances);
        return appliances;
    }

    @Test
    public void shouldReturnApplianceWithGivenNameIfFlatContainsIt() {
        flat.setAppliances(generateListOfElectricalAppliances());

        ElectricalAppliance appliance = instance.findApplianceByName(flat, "HAIRDRYER");
        ApplianceName res = appliance.getName();
        assertEquals(ApplianceName.HAIRDRYER, res);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThroeIllegalArgumentExceptionIfFlatDoesNotContainsApplianceWithGivenName() {
        flat.setAppliances(generateListOfElectricalAppliances());
        instance.findApplianceByName(flat, "FRIDGE");
    }

    @Test
    public void shouldReturnListStringOffAllAppliancesNamesInFlat() {
        flat.setAppliances(generateListOfElectricalAppliances());
        List<String> expected = new ArrayList<>();
        expected.add("TV");
        expected.add("Hairdryer");

        List<String> res = instance.getAllAppliancesNames(flat);
        assertEquals(expected, res);
    }

    @Test
    public void shouldReturnEmptyListIfThereAreAnyAppliancesInFlat() {
        flat.setAppliances(new ArrayList<>());
        List<String> res = instance.getAllAppliancesNames(flat);

        assertTrue(res.isEmpty());
    }

    @Test
    public void shouldReturnListOfStringNamesOfAllTurnedOnAppliancesWhichCanNotContinuesWork() {
        flat.setAppliances(generateListOfElectricalAppliances());
        flat.getAppliances().get(1).setTurnOn(true);
        List<String> expected = new ArrayList<>();
        expected.add("Hairdryer");

        List<String> res = instance.getDangerousTurnedOnAppliancesNames(flat);
        assertEquals(expected, res);
    }

    @Test
    public void shouldReturnEmptyListIfAllDangerousAppliancesAreTurnedOff() {
        flat.setAppliances(generateListOfElectricalAppliances());
        List<String> res = instance.getDangerousTurnedOnAppliancesNames(flat);
        assertTrue(res.isEmpty());
    }

    @Test
    public void shouldReturnListOfAllTurnedOnAppliances() {
        flat.setAppliances(generateListOfElectricalAppliances());
        flat.getAppliances().get(0).setTurnOn(true);
        flat.getAppliances().get(1).setTurnOn(true);
        List<String> expected = new ArrayList<>();
        expected.add("TV");
        expected.add("Hairdryer");

        List<String> res = instance.getNamesOfAllTurnedOnAppliances(flat);
        assertEquals(expected, res);
    }

    @Test
    public void shouldReturnEmptyListIfAllAppliancesAreTurnedOff() {
        flat.setAppliances(generateListOfElectricalAppliances());
        List<String> res = instance.getNamesOfAllTurnedOnAppliances(flat);
        assertTrue(res.isEmpty());
    }

    @Test
    public void shouldReturnNumberOfCurrentElectricityLoadIfSomeAppliancesAreTurnedOn() {
        flat.setAppliances(generateListOfElectricalAppliances());
        flat.getAppliances().get(0).setTurnOn(true);
        flat.getAppliances().get(1).setTurnOn(true);
        int res = instance.countCurrentElectricityLoad(flat);
        assertEquals(1050, res);
    }

    @Test
    public void shouldReturnZeroIfAnyElectricalAppliancesAreTurnOn() {
        flat.setAppliances(generateListOfElectricalAppliances());
        int res = instance.countCurrentElectricityLoad(flat);
        assertEquals(0, res);
    }

    @Test
    public void shouldReturnElectricalAppliancesListSortedByPowerAscending() {
        flat.setAppliances(generateListOfElectricalAppliances());
        ElectricalAppliance[] expected = {
                new ElectricalAppliance(350, false, 0.45,
                        ApplianceColor.YELLOW, ApplianceName.HAIRDRYER, ApplianceBrand.PHILIPS),
                new ElectricalAppliance(700, true, 7,
                        ApplianceColor.BLACK, ApplianceName.TV, ApplianceBrand.SAMSUNG)
        };

        List<ElectricalAppliance> res = instance.sortAppliancesByPowerAscending(flat);
        assertArrayEquals(expected, res.toArray());
    }

    @Test
    public void shouldReturnElectricalAppliancesListSortedByPowerDescending() {
        flat.setAppliances(generateListOfElectricalAppliances());
        ElectricalAppliance[] expected = {
                new ElectricalAppliance(700, true, 7,
                        ApplianceColor.BLACK, ApplianceName.TV, ApplianceBrand.SAMSUNG),
                new ElectricalAppliance(350, false, 0.45,
                        ApplianceColor.YELLOW, ApplianceName.HAIRDRYER, ApplianceBrand.PHILIPS)
        };

        List<ElectricalAppliance> res = instance.sortAppliancesByPowerDescending(flat);
        assertArrayEquals(expected, res.toArray());
    }

    @Test
    public void shouldReturnEmptyListIfThereAnyAppliancesInFlatFromAscendingSortMethod() {
        flat.setAppliances(new ArrayList<>());
        List<ElectricalAppliance> res = instance.sortAppliancesByPowerAscending(flat);
        assertTrue(res.isEmpty());
    }

    @Test
    public void shouldReturnEmptyListIfThereAnyAppliancesInFlatFromDescendingSortMethod() {
        flat.setAppliances(new ArrayList<>());
        List<ElectricalAppliance> res = instance.sortAppliancesByPowerDescending(flat);
        assertTrue(res.isEmpty());
    }

    @Test
    public void shouldReturnElectricalAppliancesList() {
        flat.setAppliances(generateListOfElectricalAppliances());
        List<ElectricalAppliance> expected = new ArrayList<>();
        expected.add(new ElectricalAppliance(700, true, 7,
                ApplianceColor.BLACK, ApplianceName.TV, ApplianceBrand.SAMSUNG));
        expected.add(new ElectricalAppliance(350, false, 0.45,
                ApplianceColor.YELLOW, ApplianceName.HAIRDRYER, ApplianceBrand.PHILIPS));

        List<ElectricalAppliance> res = instance.getAllAppliances(flat);
        assertEquals(expected, res);
    }

    @Test
    public void shouldReturnListOfElectricalAppliancesIfFlatContainsItemWhichMatchWithNotNullParameters() {
        flat.setAppliances(generateListOfElectricalAppliances());
        List<ElectricalAppliance> expected = new ArrayList<>();
        expected.add(new ElectricalAppliance(350, false, 0.45,
                ApplianceColor.YELLOW, ApplianceName.HAIRDRYER, ApplianceBrand.PHILIPS));

        List<ElectricalAppliance> res = instance
                .findAppliancesByParams(flat, 0.45, null, ApplianceBrand.PHILIPS, 0);
        assertEquals(expected, res);
    }

    @Test
    public void shouldReturnEmptyListOfElectricalAppliancesIfFlatDoesNotContainsItemWithGivenParameters() {
        flat.setAppliances(generateListOfElectricalAppliances());
        List<ElectricalAppliance> res = instance
                .findAppliancesByParams(flat, 0, ApplianceColor.RED, null, 0);
        assertTrue(res.isEmpty());
    }
}