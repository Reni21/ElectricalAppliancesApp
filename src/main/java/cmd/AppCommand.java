package cmd;

public class AppCommand {
    public static final String MAIN_MENU_CMD =
            String.format(
                    "\n\n < - - - - - - - - - - - - - - MAIN MENU - - - - - - - - - - - - - - >\n" +
                            "    %-23s%s%n    %-23s%s%n    %-23s%s%n    %-23s%s%n" +
                            "    %-23s%s%n    %-23s%s%n    %-23s%s%n    %-23s%s%n",
                    "  --all", "\"show all appliances in the flat\"",
                    "  --s-appliance_name", "\"select specific appliance\"",
                    "  --search", "\"search appliance\"",
                    "  --show", "\"show working appliances\"",
                    "  --sort", "\"sort appliances by power\"",
                    "  --load", "\"show current flat electricity load\"",
                    "  --q", "\"quite flat\"",
                    "  --help", "\"show menu command\""
            );


    public static final String APPLIANCE_MENU_CMD =
            String.format(
                    "    %-23s%s%n    %-23s%s%n    %-23s%s%n    %-23s%s%n" +
                            "    %-23s%s%n    %-23s%s%n    %-23s%s%n    %-23s%s%n",
                    "  --connect", "\"connect to socket\"",
                    "  --d-connect", "\"disconnect from socket\"",
                    "  --on", "\"turn on\"",
                    "  --off", "\"turn off\"\n",
                    "  --s-appliance_name", "\"select another appliance\"",
                    "  --all", "\"show all appliances in the flat\"",
                    "  --return", "\"return to main menu\"",
                    "  --help", "\"show menu command\""
            );

    public static final String SEARCH_MENU =
            String.format(
                    "\n < - - - - - - - - - - - - - - SEARCH MENU - - - - - - - - - - - - - - >\n" +
                            "   --w-weight   &|   --c-color   &|   --b-brand   &|   --p-power \n" +
                      " - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -\n" +
                            "    %-23s%s%n    %-23s%s%n    %-23s%s%n",
                    "   --return", "\"return to main menu\"",
                    "  --all", "\"find all appliances in flat\"",
                    "  --help", "\"show menu command\""
            );
}
