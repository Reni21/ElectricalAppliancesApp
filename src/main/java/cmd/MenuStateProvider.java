package cmd;

import entity.ElectricalAppliance;
import entity.Flat;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import service.ElectricalApplianceService;
import service.FlatService;

@RequiredArgsConstructor
public class MenuStateProvider {
    @NonNull
    private ElectricalApplianceService applianceService;
    @NonNull
    private FlatService flatService;
    @NonNull
    private Flat flat;

    private MainMenuState mainMenu;
    private ApplianceMenuState applianceMenu;
    private SearchMenuState searchMenuState;

    public MainMenuState getMainMenuState() {
        if (mainMenu == null) {
            mainMenu = new MainMenuState(this, flatService, flat);
        }
        return mainMenu;
    }

    public ApplianceMenuState getApplianceMenuState(ElectricalAppliance appliance) {
        if (applianceMenu == null) {
            applianceMenu = new ApplianceMenuState(this, applianceService, flatService, flat);
        }
        applianceMenu.setAppliance(appliance);
        return applianceMenu;
    }

    public SearchMenuState getSearchMenuState() {
        if (searchMenuState == null) {
            searchMenuState = new SearchMenuState(this, flatService, flat);
        }
        return searchMenuState;
    }
}
