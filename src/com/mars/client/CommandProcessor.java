package com.mars.client;

import com.mars.items.Item;
import com.mars.locations.ChallengeRoom;
import com.mars.locations.Room;
import com.mars.objects.Player;

import java.util.*;

public class CommandProcessor {
    private Display display = new Display();
    private String nextLocation = "";
    private int newStamina = 0;
    private Game game = Game.getInstance();
    private Player player = game.getPlayer();
    private List<Room> rooms = Game.getRooms();
    private List<Item> locationItems = Game.getItems();
    private Map<String, Boolean> solvedPuzzles = Game.getSolved();
    private Map<String, Integer> stats = game.getStats();
    private List<Item> inventory = game.getInventory();
    private Vector<String> items;
    private Room currentLocation = rooms.get(0);

    // method to resolve action command inputs from user
    public Room processCommand(List<String> command) {
        try {
            if (command.size() == 1) {
                String command1 = " ";
                command.add(1, command1);
            }
            if (command.size() > 1) {
                // getting name of currentLocation and assign to nextLocation
                //clearConsole();
                switch (command.get(0)) {
                    case "go":
                        currentLocation = forGo(command);
                        break;
                    case "eat":
                        if (!currentLocation.getName().equals("Docking Station")) {
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
                    default:
                        System.out.println("That is an invalid command. Please try again.");
                        break;
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Check your input");
        } catch (NullPointerException e) {
            System.out.println("Can't go that way .... yet");
        }
        return currentLocation;
    }

    public void setStat() {
        try {
            stats.put("Stamina", 100);
            if (!currentLocation.getName().equals("Docking Station") && stats.get("Stamina") > 0) {
                newStamina = stats.get("Stamina") - 5;
                stats.put("Stamina", newStamina);
                game.setStats(stats);
                System.out.println("Stamina Level: " + newStamina);
            } else {
                System.out.println("Stamina Level: " + stats.get("Stamina"));
            }
            if (stats.get("Stamina") <= 0) {
                System.out.println("no more STAMINA......YOU DIED AND IS NOW FLOATING IN SPACE....");
                System.out.println("goodbye");
                System.exit(0);
            }
        } catch (NullPointerException e) {
            System.out.println("Need to use a verb");
        }
    }

    // method to actually run the application
    public Room runApp(String userInput) {
//        boolean isRunning = true;                      // establish & setting boolean to default off for game execution
//        String answer = display.playGame();             // Ask if user wants to play a game
//        if(answer.equals("y")){
//            isRunning = true;                           // setting boolean on for game execution
//        }
//        else{
//            System.out.println("You chose to not play :(");     // message showing user their choice
//            System.exit(0);                              // exiting game load
//        }

        // functions while game is running
        while (userInput.length() > 1) {

            List<String> nextCommand = getCommand(userInput);            // calling upon Parser to begin parse process
            currentLocation = processCommand(nextCommand);
            return currentLocation;
            // setStat();
        }
        return null;
    }

    public List<String> getCommand(String userInput) {
        List<String> cmdInput = new ArrayList<>(); //empty arraylist to store parsed command
        StringTokenizer cleanInput = new StringTokenizer(userInput); //pass string through function to strip out extra words
        while (cleanInput.hasMoreTokens()) {
            cmdInput.add(cleanInput.nextToken().toLowerCase());
        }
        return cmdInput; //return the list of verb, noun
    }

    public Vector<String> forItem(String location) {
        try {
            items = new Vector<>();
            for (Item i : currentLocation.getItems()) {
                    items.add(i.getName().replace(" ", "_"));

            }
        } catch (Exception e) {

        }
        return items;
    }

    public Room forGo(List<String> command) {
        try {
            // need a way to grab directions from map
            if (currentLocation.getDirections().containsKey(command.get(1))) {                                           // checking if currentLocation has direction of movement provided by user input as an option
                nextLocation = currentLocation.getDirections().get(command.get(1));                                     // moving to nextLocation
            } else {
                currentLocation.setName("Can't go that way");
                currentLocation.setDescription(".... ");
                return currentLocation;                                       // otherwise informing user that pathway is not accessible
            }

        } catch (NullPointerException e) {
            currentLocation.setName("Can't go that way");
            return currentLocation;

        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Go where?");
        }
        Room newRoom = null;
        for (Room r : Game.getRooms()) {
            if (r.getName().equals(nextLocation)) {
                newRoom = r;
            }
        }

        ChallengeRoom.getInstance(game, newRoom.getName().toString(), Game.getSolved(), Game.getPuzzles());
        return newRoom;
    }

    public String forGet(List<String> command) {
        if (inventory.size() == 4) {
            return "You bag is full.";
        }

        String noun = command.get(1).replace("_", " ").toLowerCase();
        StringBuilder sb = new StringBuilder();
        // 'get' functionality enabled to allow user to acquire items, add to inventory
        for (Item item : locationItems) {
            if (item.getName().equals(noun) && item.getLocation().getName().equals(currentLocation.getName()) && !(player.getInventory().lookItem().contains(item.getName()))) {
                // adding to inventory;
                player.getInventory().add(item);
                currentLocation.removeItem(item);
                //sb.append("You've retrieved the " + item.getName() + " and added it to your inventory.");     // output to user informing item added to inventory
                sb.append(item.getName());
                // TODO update inventory panel
                // sb.append(player.getInventory().toString());
            } else if (item.getName().equals(noun) && inventory.size() == 4 && item.getLocation().getName().equals(currentLocation.getName())) {
                sb.append("You can only have 4 items in inventory");
            } else if (noun.equals(" ")) {
                sb.append("What do you want to get");
            }
        }
        return sb.toString();
    }

    public String forLook(List<String> command) {
        String name = "";
        String result = "";
        try {
            for (Map.Entry<String, String> map : currentLocation.getDirections().entrySet()) {
                if (map.getKey().equals(command.get(1))) {
                    name = map.getValue();
                }
            }
            for (Room room : rooms) {
                if (room.getName().equals(name)) {
                    result = room.toString();
                }
            }
        } catch (NullPointerException e) {
        }
        return result;
    }

    public String forInspect(List<String> command) {
        String item = command.get(1).replace("_", " ").toLowerCase();
        try {
            for (Item i : locationItems) {
                if (i.getName().toLowerCase().equals(item) && i.getLocation().getName().equals(currentLocation.getName())) {
                    return i.toString();
                }
            }
        } catch (Exception e) {
            System.out.println("problem inspecting items");
        }
        return "";
    }


    public void forDrop(List<String> command) {
        try {
            Item droppedItem = game.getPlayer().getInventory().drop(command.get(1));
            currentLocation.addItem(droppedItem);
        } catch (Exception e) {

        }
    }


    public void forUse(List<String> command) {
        try {
            // TODO: what about consumable items? (mealkit) ...or Items that actuate something else? (key -> reactor)
            if (player.getInventory().lookItem().contains(command.get(1))) {
                System.out.println("Item is here");

            } else {
                System.out.println("Use what?");
            }
        } catch (ArrayIndexOutOfBoundsException e) {
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
            System.out.println("can't clear console");
        }
    }
}
