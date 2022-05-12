package com.mars.client;
import com.mars.items.Item;
import com.mars.locations.Room;
import com.mars.objects.Inventory;
import com.mars.objects.Player;

import java.util.*;

public class CommandProcessor {
    private Display display = new Display();
    private String nextLocation = "";
    private int newStamina = 0;
    Player player = Game.getInstance().getPlayer();
    private List<Room> locationMap = Game.getRooms();
    private List<Item> locationItems = Game.getInstance().getThingsList();
    private Room currentLocation;


    // method to resolve action command inputs from user
    private void processCommand(List<String> command) {
        try {
            if (command.size() == 1){
                String command1 = " ";
                command.add(1,command1);
            }
            if (command.size() > 1){
            // getting name of currentLocation and assign to nextLocation
            clearConsole();
            switch (command.get(0)) {
                case "go":
                    currentLocation = forGo(command);
                    break;
                case "eat":
                    if (!currentLocation.equals("Docking Station")) {
                        System.out.println("You can't eat outside docking station");
                    } else {
                        forUse(command);
                    }
                    break;
                case "quit":
                    Game.quit();
                    break;
                case "help":
                    System.out.println(" ");
                    Display.displayText("text/help.txt");
                    break;
                case "look":
                    forLook(command);
                    break;
                case "get":
                    forGet(command);
                    break;
                case "drop":
                    forDrop(command);
                    break;
                case "use":
                    forUse(command);
                    break;
                default:
                    System.out.println("That is an invalid command. Please try again.");
                    break;
            }
        }
        }
        catch (ArrayIndexOutOfBoundsException e){
            System.out.println("Check your input");
        }
        }
    private void setStat() {
        if (!currentLocation.equals("Docking Station") && player.getStat().getStamina() > 0) {
            newStamina = player.getStat().getStamina() - 5;
            player.getStat().setOxygenLevel(newStamina);
            System.out.println("Stamina Level: " + newStamina);
        } else {
            System.out.println("Stamina Level: " + player.getStat().getStamina());
        }
        if (player.getStat().getStamina() <= 0) {
            System.out.println("no more STAMINA......YOU DIED AND IS NOW FLOATING IN SPACE....");
            System.out.println("goodbye");
            System.exit(0);
        }
    }

    // method to actually run the application
    public void runApp() {
        boolean isRunning = false;                      // establish & setting boolean to default off for game execution
        String answer = display.playGame();             // Ask if user wants to play a game
        if(answer.equals("y")){
            isRunning = true;                           // setting boolean on for game execution
        }
        else{
            System.out.println("You chose to not play :(");     // message showing user their choice
            System.exit(0);                              // exiting game load
        }
        Game game = Game.getInstance();

        currentLocation = Game.getRooms().get(0);// setting game start location on Map
        // functions while game is running
        while (isRunning) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter a command: \n>> ");                       // asking for input from user
            String userInput = scanner.nextLine().toLowerCase();              // getting input from user
            List<String> nextCommand = getCommand(userInput);            // calling upon Parser to begin parse process
            processCommand(nextCommand);
            setStat();
        }
    }
    public List<String> getCommand(String userInput){
        List<String> cmdInput = new ArrayList<>(); //empty arraylist to store parsed command
        StringTokenizer cleanInput = new StringTokenizer(userInput); //pass string through function to strip out extra words
        while(cleanInput.hasMoreTokens()){
            cmdInput.add(cleanInput.nextToken().toLowerCase());
        }
        return cmdInput; //return the list of verb, noun
    }
    public Room forGo(List<String> command){
        try{
            // need a way to grab directions from map
            if(currentLocation.getDirections().containsKey(command.get(1))) {                                           // checking if currentLocation has direction of movement provided by user input as an option
                nextLocation = currentLocation.getDirections().get(command.get(1));                                     // moving to nextLocation
            } else {
                System.out.println("C'mon, get right, you can't go that way!");                                         // otherwise informing user that pathway is not accessible
            }

        }
        catch (ArrayIndexOutOfBoundsException e){
            System.out.println("Go where?");
        }
        Room newRoom = null;
        for (Room r : Game.getRooms()){
            if(r.getName().equals(nextLocation)){
                newRoom = r;
            }
        }
        return newRoom;
    }
    public void forGet(List<String> command){
            String noun = " ";
            // 'get' functionality enabled to allow user to acquire items, add to inventory
            for(Item item : locationItems){
                noun = command.get(1);
                if(item.getName().equals(noun) && player.getInventory().getInventory().size() < 3 && item.getLocation().getName().equals(currentLocation.getName())){
                    // adding to inventory;
                    player.getInventory().add(item);
                    System.out.println("You've retrieved the " + item.getName() + " and added it to your inventory.");     // output to user informing item added to inventory
                    System.out.println(player.getInventory().getInventory());
                    break;
                    // display.displayPlayerInventory();
                }
                else if (item.getName().equals(noun) && player.getInventory().getInventory().size() == 3 && item.getLocation().getName().equals(currentLocation.getName())){
                    System.out.println("You can only have 3 items in inventory");
                    break;
                }
                else if (command.get(1).equals(" ")){
                    System.out.println("What do you want to get");
                }
//                else if (!(item.getName().equals(noun) && player.getInventory().getInventory().size() <= 3 && item.getLocation().getName().equals(currentLocation.getName()))) {
//                    System.out.println("There is nothing here to pick up. Are you seeing things?  Maybe check your sugar levels...");       // output to user reminding them there is nothing to acquire from this room
//                }
            }
    }
    public void forLook(List<String> command){
            for (Item i : locationItems){
                if(i.getName().equals(command.get(1))&& i.getLocation().getName().equals(currentLocation.getName())){
                    System.out.println("Upon examination you find " + i.getDescription());
                    break;
                }
                if(player.getInventory().lookItem().contains(command.get(1))) {                                                  // if not in currentLocation, check if in inventory
                    System.out.println("Upon examination you find " + player.getInventory().getItemDescription(command.get(1)));
                    break;// if item present in inventory, output to user description of item
                }

            }
            if(command.get(1).equals("room")) {
                System.out.println("Looking around this room, you see: " + currentLocation.getDescription());
            }
            else if(command.get(1).equals("inventory")) {
                if(player.getInventory().getInventory().size() > 0) {
                    System.out.println("Inventory: "+player.getInventory().getInventory());;
                }
                else {
                    System.out.println("You currently have nothing in your inventory.");
                }
            }
            else if((command.get(1).equals("oxygen")) || (command.get(1).equals("O2"))) {
                Boolean tempValue = false;
               for(Item item : locationItems) {
                   if(item.getName().equals("oxygen")&& currentLocation.getName().equals(item.getLocation().getName())){
                       tempValue = true;
                   }
               }
               if (tempValue.equals(true)){
                   System.out.println("The O2 Sensor indicates the oxygen levels are: SAFE");
               }
               else{
                   System.out.println("The O2 Sensor indicates the oxygen levels are: DANGEROUS");
               }
            }
            else if(command.get(1).equals(" ")){
                System.out.println("Look where");
            }

    }
    public void forDrop(List<String> command) {

        for (Item item : locationItems) {
            if (player.getInventory().lookItem().contains(command.get(1))) {
                Item dropping = player.getInventory().drop(command.get(1));// if so, assigning it a variable named 'dropping'
                System.out.println("You have dropped the " + command.get(1) + ", it is no longer in your " +        // output to user to inform them of the change
                        "inventory. It has been placed in this location.");
                System.out.println("Inventory: " + player.getInventory().getInventory());
                break;
            }
        }
        // 'drop' functionality enabled to allow user to drop items from inventory, add to currentLocation
        if (command.get(1).equals(" ")) {
            System.out.println("Drop what");             // output to user to inform them of invalid attempt to drop item
        }
    }

    public void forUse(List<String> command){
        try{
            // TODO: what about consumable items? (mealkit) ...or Items that actuate something else? (key -> reactor)
            if(player.getInventory().lookItem().contains(command.get(1))) {
                System.out.println("Item is here");

            }
            else {
                System.out.println("Use what?");
            }
        }
        catch(ArrayIndexOutOfBoundsException e){
            System.out.println("Use what?");
        }
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

