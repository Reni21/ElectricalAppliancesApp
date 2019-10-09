package app.cmd;

import app.entity.ElectricalAppliance;
import app.entity.Flat;
import app.service.ElectricalApplianceService;
import app.service.FlatService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MenuStateProvider {
    private ElectricalApplianceService applianceService;
    private FlatService flatService;
    private Flat flat;

    private MainMenuState mainMenu;
    private ApplianceMenuState applianceMenu;
    private SearchMenuState searchMenuState;

    @Autowired
    public MenuStateProvider(ElectricalApplianceService applianceService, FlatService flatService, Flat flat) {
        this.applianceService = applianceService;
        this.flatService = flatService;
        this.flat = flat;
    }

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
