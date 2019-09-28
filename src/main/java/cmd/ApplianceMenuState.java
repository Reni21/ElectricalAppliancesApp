package cmd;

import entity.ElectricalAppliance;
import entity.Flat;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import service.ElectricalApplianceService;
import service.FlatService;

@RequiredArgsConstructor
public class ApplianceMenuState implements MenuState {
    @NonNull
    private MenuStateProvider menuStateProvider;
    @NonNull
    private ElectricalApplianceService applianceService;
    @NonNull
    private FlatService flatService;
    @NonNull
    private Flat flat;

    private ElectricalAppliance appliance;
    private static final String MENU_CMD = AppCommand.APPLIANCE_MENU_CMD;

    public void setAppliance(ElectricalAppliance appliance) {
        this.appliance = appliance;
    }

    @Override
    public void handleUserInput(String input, MenuContext context) {
        if(input.isEmpty()){
            return;
        }
        switch (input) {
            case "--connect":
                connectApplianceToSocket();
                break;
            case "--d-connect":
                disconnectApplianceFromSocket();
                break;
            case "--on":
                turnOnAppliance();
                break;
            case "--off":
                turnOffAppliance();
                break;
            case "--return":
                context.changeState(menuStateProvider.getMainMenuState());
                break;
            case "--help":
                printHelp();
                break;
            default:
                System.out.format("Unknown command \"%s\"", input);
                printHelp();
        }
    }

    private void connectApplianceToSocket() {
        if(appliance.isConnectToSocket()){
            System.out.format("%s is already connect to socket%n", appliance.getName());
            return;
        }
        applianceService.connectToSocket(appliance);
        System.out.format("%s successfully connect to socket%n", appliance.getName());
    }

    private void turnOnAppliance(){
        if(!appliance.isConnectToSocket()){
            System.out.format("REJECT! %s is not connect to socket%n", appliance.getName());
            return;
        }
        if(appliance.isTurnOn()){
            System.out.format("%s is already turned on%n", appliance.getName());
            return;
        }
        applianceService.turnOn(appliance);
        int totalElectricityLoad = flatService.countCurrentElectricityLoad(flat);
        if (totalElectricityLoad > flat.getMaxConductivity()) {
            System.out.format("%s turned on faild%n", appliance.getName());
            System.out.println((char)27 + "[31m" +
                    "Opps, the electricity went out... Electricity load is too height. Please, turn off something!" +
                    (char)27 + "[0m");
        } else {
            System.out.format("%s successfully turned on%n", appliance.getName());
        }
    }

    private void turnOffAppliance(){
        if(!appliance.isTurnOn()){
            System.out.format("%s is already turned off%n", appliance.getName());
            return;
        }
        applianceService.turnOff(appliance);
        int totalElectricityLoad = flatService.countCurrentElectricityLoad(flat);
        if (totalElectricityLoad > flat.getMaxConductivity()) {
            System.out.format("%s successfully turned off%n", appliance.getName());
            System.out.println((char)27 + "[31m" +
                    "Sorry, the electricity still went out... Please, turn off something one more!" +
                    (char)27 + "[0m");
        } else {
            System.out.format("%s successfully turned off%n", appliance.getName());
        }
    }

    private void disconnectApplianceFromSocket() {
        if(!appliance.isConnectToSocket()){
            System.out.format("%s is already disconnect from socket%n", appliance.getName());
        }
        applianceService.disconnectFromSocket(appliance);
        System.out.format("%s successfully disconnect from socket%n", appliance.getName());
    }

    @Override
    public void printHelp() {
        System.out.format("<--------------------------- %s MENU ---------------------------->%n",
                appliance.getName().toString().toUpperCase());
        System.out.println(MENU_CMD);
    }
}
