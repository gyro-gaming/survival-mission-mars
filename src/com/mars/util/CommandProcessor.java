package com.mars.util;

import com.mars.Engine;
import com.mars.display.Display;
import com.mars.objects.Inventory;
import com.mars.objects.Item;
import com.mars.objects.Location;
import com.mars.stats.Stats;

import java.util.*;

public class CommandProcessor {
    private Display display = new Display();
    Stats stat = new Stats();
    private String nextLocation = "";
    private int newStamina = 0;
    private TextParser parser = new TextParser();
    private JSONHandler jsonhandler = new JSONHandler();
    private Engine engine = new Engine();
    private Map<String, Location> locationMap = jsonhandler.loadLocationMap();
    private Stats playerStats = new Stats();
    private boolean isGhSolved = false;
    private boolean isHydroSolved = false;
    private boolean isReactorSolved = false;
    private boolean isSolarSolved = false;

    // method to resolve action command inputs from user
    public String processCommand(List<String> command, Location currentLocation, Map<String, Location> locationMap) {
        nextLocation = currentLocation.getName();                                                                // getting name of currentLocation and assign to nextLocation
        clearConsole();
        switch (command.get(0)){
            case "go":
                forGo(command, currentLocation, locationMap);
                break;
            case "eat":
                if (!nextLocation.equals("Docking Station")){
                    System.out.println("You can't eat outside docking station");
                }
                else {
                    forUse(command, currentLocation, locationMap);
                }
                break;
            case "quit":
                System.out.println("Fine then! Bye!!");                                                                     // sends quit message
                System.exit(0);
                break;
            case "help":
                System.out.println(" ");
                display.displayText("text/help.txt");
                break;
            case "look":
                forLook(command,currentLocation,locationMap);
                break;
            case "get":
                forGet(command, currentLocation, locationMap);
                break;
            case "drop":
                forDrop(command, currentLocation, locationMap);
                break;
            case "use":
                forUse(command, currentLocation, locationMap);
                break;
            default:
                System.out.println("That is an invalid command. Please try again.");
                break;
        }
            if (!nextLocation.equals("Docking Station") && stat.getStamina() > 0) {
                newStamina = stat.getStamina() - 5;
                stat.setOxygenLevel(newStamina);
                System.out.println("Stamina Level: " + newStamina);
            }
            else {
                System.out.println("Stamina Level: " + stat.getStamina());
            }
            if (stat.getStamina() <= 0){
                System.out.println("no more STAMINA......YOU DIED AND IS NOW FLOATING IN SPACE....");
                System.out.println("goodbye");
                System.exit(0);
            }

        return nextLocation;                                                                                      // returns new location if needed elsewhere
    }
    // method to actually run the application
    public void runApp() {
        display.displayText("text/splash.txt");
        //display.displaySplash();                        // Display welcome screen to user
        boolean isRunning = false;                      // establish & setting boolean to default off for game execution
        String answer = display.playGame();             // Ask if user wants to play a game
        if(answer.equals("y")){
            isRunning = true;                           // setting boolean on for game execution
        }
        else{
            System.out.println("You chose to not play :(");     // message showing user their choice
            System.exit(0);                              // exiting game load
        }
        Location currentLocation = locationMap.get("Docking Station");          // setting game start location on Map
        display.displayText("text/game_info.txt");                      // display of game information
        display.displayText("text/game_menu.txt");                      // display of game menu

        // this is the game clock / countdown timer logic
        Timer timer = new Timer();                          // create a timer
        TimerTask task = new Task();                        // create a task -- task.java executes shutdown
        long delay = 336 * 60000L;       // change when done testing    --  sets the length of delay
        timer.schedule(task, delay);                        // schedules the timer to execute task after delay
        long start = System.currentTimeMillis()/1000;       // marks start time of game, reduces from millisecs to secs
        long markDelay = delay/1000;                        // creates variable for delay, reduces from millisecs to secs
        long endTime = start + markDelay;                   // marks endTime of game
        String currentTime = new java.text.SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new java.util.Date (start*1000));   // formats currentTime to SDF
        String dieTime = new java.text.SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new java.util.Date (endTime*1000));     // formats endTime to SDF


        // functions while game is running
        while (isRunning) {


            // game clock output
            System.out.println("-----------------------------------------");
            System.out.println("You have 14 mission days to complete the tasks. " +
                    "1 minute of elapsed real time is equal 1 hour of elapsed game time within the Martian Outpost.");

            System.out.println("Start Time: " + currentTime);
            System.out.println("Time Until Death: " + dieTime);
            TimeCalc.findDifference(dieTime);




            display.displayCurrentStatus(currentLocation, playerStats);                      // display of location


            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter a command: \n>> ");                            // asking for input from user
            String userInput = scanner.nextLine();                              // getting input from user
            List<String> nextCommand = parser.getCommand(userInput);            // calling upon Parser to begin parse process
            currentLocation = locationMap.get(processCommand(nextCommand, currentLocation, locationMap));    // setting location
            engine.checkPuzzles(currentLocation);

            if(isHydroSolved && isGhSolved && isReactorSolved && isSolarSolved){
                display.displayText("text/win.txt");
                isRunning = false;
            }
        }


    }
    public String forGo(List<String> command, Location currentLocation, Map<String, Location> locationMap){
        try{
            if(currentLocation.getDirections().containsKey(command.get(1))) {                                           // checking if currentLocation has direction of movement provided by user input as an option
                nextLocation = currentLocation.getDirections().get(command.get(1));                                     // moving to nextLocation
            } else {
                System.out.println("C'mon, get right, you can't go that way!");                                         // otherwise informing user that pathway is not accessible
            }
        }
        catch (ArrayIndexOutOfBoundsException e){
            System.out.println("Go where?");
        }
        return nextLocation;
    }
    public String forGet(List<String> command, Location currentLocation, Map<String, Location> locationMap){
        try{
            // 'get' functionality enabled to allow user to acquire items, add to inventory
            if(currentLocation.getItemNames().contains(command.get(1)) && Inventory.getInstance().getInventory().size() <= 3) {                                               // checking if second parsed word is valid inside currentLocation
                Item carry = currentLocation.removeItem(command.get(1));                                                // if so, then assigning it a variable named 'carry'
                Inventory.getInstance().add(carry);                                                                     // adding to inventory
                System.out.println("You've retrieved the " + carry.getName() + " and added it to your inventory.");     // output to user informing item added to inventory
                display.displayPlayerInventory();                                                                       // output to user showing full inventory
            }
            else if (Inventory.getInstance().getInventory().size() >= 3){
                System.out.println("You can only have 3 items in inventory");
            }
            else {
                System.out.println("There is nothing here to pick up. Are you seeing things?  Maybe check your sugar levels...");       // output to user reminding them there is nothing to acquire from this room
            }
        }
        catch (ArrayIndexOutOfBoundsException e){
            System.out.println("What do you want to get");
        }
        return nextLocation;
    }
    public String forLook(List<String> command, Location currentLocation, Map<String, Location> locationMap){
        try {
            if(currentLocation.getItemNames().contains(command.get(1))) {                                                       // checking if second parsed word is valid inside currentLocation
                System.out.println("Upon examination you find " + currentLocation.getItemDescription(command.get(1)));          // output to user showing description of item, if valid in location
            }
            else if(Inventory.getInstance().lookItem().contains(command.get(1))) {                                                  // if not in currentLocation, check if in inventory
                System.out.println("Upon examination you find " + Inventory.getInstance().getItemDescription(command.get(1)));      // if item present in inventory, output to user description of item
            }
            else if(command.get(1).equals("room")) {
                System.out.println("Looking around this room, you see: " + currentLocation.getDescription());
                if(currentLocation.getPuzzle()){
                    System.out.println("Lucky you, it also appears there is a challenge here for you to solve!" +
                            "Perhaps you could 'look challenge' to find out more");                                             //if puzzle present, let the user know it is there
                }
            }
            else if (command.get(1).equals("challenge") && currentLocation.getPuzzle()) {                                       //if user says "look challenge"
                //currentLocation.createPuzzle();                                                                                //create the challenge/puzzle
                currentLocation.startPuzzle();                                                                             //start or kick off the challenge for them to solve
            }
            else if(command.get(1).equals("inventory")) {
                if(Inventory.getInstance().getInventory().size() > 0) {
                    display.displayPlayerInventory();
                }
                else {
                    System.out.println("You currently have nothing in your inventory.");
                }
            }
            else if((command.get(1).equals("oxygen")) || (command.get(1).equals("O2"))) {
                if(currentLocation.getOxygen()) {
                    System.out.println("The O2 Sensor indicates the oxygen levels are: SAFE");
                }
                else {
                    System.out.println("The O2 Sensor indicates the oxygen levels are: DANGEROUS");
                }
            }
            else {
                System.out.println("There is no item to examine.");                                                     // if nothing to examine, output to user informing as such
            }
        } catch (ArrayIndexOutOfBoundsException e){
            System.out.println("Look where?");
        }
        return nextLocation;
    }
    public String forDrop(List<String> command, Location currentLocation, Map<String, Location> locationMap){
        try {
            // 'drop' functionality enabled to allow user to drop items from inventory, add to currentLocation
            if (Inventory.getInstance().lookItem().contains(command.get(1))) {                                           // checking to see if item in inventory
                Item dropping = Inventory.getInstance().drop(command.get(1));                                           // if so, assigning it a variable named 'dropping'
                currentLocation.addItem(dropping);                                                                      // adding 'dropping' item to currentLocation
                System.out.println("You have dropped the " + dropping.getName() + ", it is no longer in your " +        // output to user to inform them of the change
                        "inventory. It has been placed in this location.");
                display.displayPlayerInventory();
            } else {
                System.out.println("There is no item with that name in your inventory. Please try again.");             // output to user to inform them of invalid attempt to drop item
            }
        }
        catch(ArrayIndexOutOfBoundsException e){
            System.out.println("Drop what?");
        }
        return nextLocation;
    }
    public String forUse(List<String> command, Location currentLocation, Map<String, Location> locationMap){
        try{
            // TODO: what about consumable items? (mealkit) ...or Items that actuate something else? (key -> reactor)
            if(Inventory.getInstance().lookItem().contains(command.get(1))) {
                System.out.println("Item is here");

            }
            else {
                System.out.println("Use what?");
            }
        }
        catch(ArrayIndexOutOfBoundsException e){
            System.out.println("Use what?");
        }
        return nextLocation;
    }
    public static void clearConsole() {
        try {
            String operatingSystem = System.getProperty("os.name"); //Check the current operating system

            if (operatingSystem.contains("Windows")) {
                ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "cls");
                Process startProcess = pb.inheritIO().start();
                startProcess.waitFor();
            } else {
                ProcessBuilder pb = new ProcessBuilder("clear");
                Process startProcess = pb.inheritIO().start();

                startProcess.waitFor();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}