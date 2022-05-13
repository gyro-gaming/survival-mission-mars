package com.mars.client;

import com.mars.items.Item;
import com.mars.locations.ChallengeRoom;
import com.mars.locations.Room;
import com.mars.objects.Player;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

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
                    clearConsole();
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
        catch(ArrayIndexOutOfBoundsException e){
                System.out.println("Check your input");
            }
        catch(NullPointerException e){
                System.out.println("Can't go that way .... yet");
            }
            return currentLocation;
        }
    public void setStat() {
      try{
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
      }
      catch (NullPointerException e){
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
    public List<String> getCommand(String userInput){
        List<String> cmdInput = new ArrayList<>(); //empty arraylist to store parsed command
        StringTokenizer cleanInput = new StringTokenizer(userInput); //pass string through function to strip out extra words
        while(cleanInput.hasMoreTokens()){
            cmdInput.add(cleanInput.nextToken().toLowerCase());
        }
        return cmdInput; //return the list of verb, noun
    }
    public Vector<String> forItem(String location){
        Vector<String> items = new Vector<>();
        for(Item i : Game.getItems()){
            if(i.getLocation().getName().equals(location)){
                items.add(i.getName());
            }
        }
        System.out.println(items.toString());
        return items;
    }
    public Room forGo(List<String> command){

        try{
            // need a way to grab directions from map
            if(currentLocation.getDirections().containsKey(command.get(1))) {                                           // checking if currentLocation has direction of movement provided by user input as an option
                nextLocation = currentLocation.getDirections().get(command.get(1));                                     // moving to nextLocation
            } else {
                currentLocation.setName("Can't go that way");
                currentLocation.setDescription(".... ");
                return currentLocation;                                       // otherwise informing user that pathway is not accessible
            }

        }
        catch (NullPointerException e ) {
            currentLocation.setName("Can't go that way");
            return currentLocation;

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

       ChallengeRoom.getInstance(game, newRoom.getName().toString(), Game.getSolved(), Game.getPuzzles());
        return newRoom;
    }
    public void forGet(List<String> command){
          try{
              String noun = " ";
              // 'get' functionality enabled to allow user to acquire items, add to inventory
              for(Item item : locationItems){
                  noun = command.get(1);

                  if (item.getName().equals(noun) && inventory.size() < 3 && item.getLocation().getName().equals(currentLocation.getName()) &&!(player.getInventory().lookItem().contains(item.getName()))) {
                      // adding to inventory;
                      player.getInventory().add(item);
                      System.out.println("You've retrieved the " + item.getName() + " and added it to your inventory.");     // output to user informing item added to inventory
                      System.out.println(player.getInventory().toString());
                      break;
                      // display.displayPlayerInventory();
                  }
                  else if (item.getName().equals(noun) && inventory.size() == 3 && item.getLocation().getName().equals(currentLocation.getName())){
                      System.out.println("You can only have 3 items in inventory");
                      break;
                  }
                  else if (command.get(1).equals(" ")){
                      System.out.println("What do you want to get");
                      break;
                  }
              }
          }
          catch(NullPointerException e){
              System.out.println("Get what?");
          }
    }
    public void forLook(List<String> command){
          try {
              for (Item i : locationItems){
                  if(i.getName().equals(command.get(1)) && i.getLocation().getName().equals(currentLocation.getName())){
                      System.out.println("Upon examination you find " + i.getDescription());
                      break;
                  }
                  if(player.getInventory().lookItem().contains(command.get(1))) {                                                  // if not in currentLocation, check if in inventory
                      System.out.println("Upon examination you find " + player.getInventory().getItemDescription(command.get(1)));
                      break;// if item present in inventory, output to user description of item
                  }

              }
              switch (command.get(1)) {
                  case "room":
                      System.out.println("Looking around this room, you see: " + currentLocation.getDescription());
                      break;
                  case "inventory":
                      if (player.getInventory().getInventory().size() > 0) {
                          System.out.println("Inventory: " + player.getInventory().toString());
                          ;
                      } else {
                          System.out.println("You currently have nothing in your inventory.");
                      }
                      break;
                  case "oxygen":
                  case "O2":
                      Boolean tempValue = false;
                      for (Item item : locationItems) {
                          if (item.getName().equals("oxygen") && currentLocation.getName().equals(item.getLocation().getName())) {
                              tempValue = true;
                          }
                      }
                      if (tempValue.equals(true)) {
                          System.out.println("The O2 Sensor indicates the oxygen levels are: SAFE");
                      } else {
                          System.out.println("The O2 Sensor indicates the oxygen levels are: DANGEROUS");
                      }
                      break;
                  case " ":
                      System.out.println("Look where");
                      break;
              }
          }
          catch(NullPointerException e){
              System.out.println("No item to look");
          }

    }
    public void forDrop(List<String> command) {

        for (Item item : locationItems) {
            if (player.getInventory().lookItem().contains(command.get(1))) {
                Item dropping = player.getInventory().drop(command.get(1));// if so, assigning it a variable named 'dropping'
                dropping.setLocation(currentLocation);
                item.setLocation(currentLocation);
                System.out.println("You have dropped the " + command.get(1) + ", it is no longer in your " +        // output to user to inform them of the change
                        "inventory. It has been placed in this location.");
                System.out.println("Inventory: " + player.getInventory().toString());
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
            System.out.println("can't clear console");
        }
    }
}
