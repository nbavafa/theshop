package theshop;

import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.NumberFormatException;
import java.io.File;
import java.util.Scanner;

/**
 * @author Nicholas Bavafa
 *         nbavafa@gmail.com
 * 11/5/17
 * @version 1.0
 */

/**
 * TheShop file defines the store in which the disks will
 * be sold. It uses a ShoppingCart object to keep track of what the user
 * adds to the cart. It also stocks and closes the shop.
 *
 * The stock of the store is stored in a .txt file called stock.txt in the
 * directory of the class.
 */

public class TheShop implements Store{
  /* Regarding the input file */
  int GENRE_INDEX = 0; //Where genre is stored
  int TITLE_INDEX = 1; //Where the title is stored
  int TYPE_INDEX = 2;  //Where the type is stored
  int PRICE_INDEX = 3; //Where the price is stored

  int REMOVE_LENGTH = 2; //Length of remove command
  int CHANGE_LENGTH = 3; //Length of change command

  int ITEM_EDITOR = 1;  //Item to edit in user input array
  int CHANGE_AMOUNT = 2;//Amount to change to in user input

  int itemIteration;  //Used for displaying number of each item

  ArrayList<CartItem> stock = new ArrayList<CartItem>();  //Stock of the store
  ArrayList<String> stockErrors = new ArrayList<String>();//Stocking Errors

  ShoppingCart cart = new ShoppingCart(); //Create a new ShoppingCart
  Scanner scan = new Scanner(System.in);  //Creates a new Scanner

  File file = new File("theShop/stock.txt");  //Defines the file of stock

  String input; //user input holder
  int userChoice; //parsed user input to an Integer

  /**
   * Constructor for TheShop
   * @param items stock of items the store holds
   */
  public TheShop(ArrayList<CartItem> items) {
    this.stock = items;
  }

  /**
   * main method for the store, gets it all started
   * @param args terminal arguments passed
   * @return void
   */
  public static void main(String [] args) {
    System.out.println("Welcome to the CD Shop");
    //Create a new store
    TheShop employee = new TheShop(new ArrayList<CartItem>());
    employee.stockStore(); //Stock the store
  }

  /**
   * closeStore shuts down the store
   * @return void
   */
  public void closeStore() {
    System.out.println("\nThank you for shopping at The CD Shop!");
    System.out.println("------------------------------------------\n");
    System.exit(0); //Exit at normal conditions
  }

  /**
   * printStore prints all the items in stock at the store
   * @return void
   */
  public void printStore() {
    for (int j = 0; j < stock.size(); j++) { //For all the items in stock
      System.out.print((j+1) + ") "); //Number the item
      System.out.println(stock.get(j).toString()); //Print the item
    }
    prompt(); //Prompt user for input

    closeStore(); //After all selections are done, close the store
  }

  /**
   * Prompt will prompt the user to selections on the store
   * return void
   */
  private void prompt() {
    //Print current amount items in cart
    System.out.println("Current items in cart: " + cart.getSize());
    System.out.println(cart.toString());
    System.out.println("[INFO]: Enter the NUMBER of the item you wish to add" +
                       " to the cart. " +
                       "\n\tExample: 1");
    System.out.println("[INFO]: To add more than one item to the cart, enter" +
                        " each number \n\tseperated by a space. " +
                        "\n\tExample: 1 2 3 3");
    System.out.println("[INFO]: For quick checkout, enter" +
                        " each number seperated by a space\n\t then CHECKOUT " +
                        "\n\tExample: 1 2 3 3 CHECKOUT");
    System.out.println("[INFO]: Enter CHECKOUT to checkout.\n" +
                       "[INFO]: Enter EXIT to leave the store.");
    System.out.print("\tInput: ");
    input = scan.next().toLowerCase(); //Get user input and make it lowercase
    System.out.println("\n");
    if (input.equals("checkout")) { //If user wants to checkout
      checkout(); //call checkout method from ShoppingCart
    }
    else if(input.equals("exit")) { //If user wants to exit
      closeStore(); //Call closeStore
    }
    else {
      try {
          userChoice = Integer.parseInt(input); //Parse the user input
      } catch(NumberFormatException e) {
          System.out.println("[ERROR: Invalid Choice]\n");
          prompt();
      } catch(NullPointerException e) {
          System.out.println("[ERROR: Invalid Choice]\n");
          prompt();
      }
      //If input in valid range
      if (userChoice > 0  && userChoice <= stock.size()) {
        cart.add(stock.get(userChoice-1)); //Add item to cart
        System.out.println("Sucessfully added item to cart!");
        prompt(); //Reprompt user
      }
      else {
        System.out.println("[ERROR: Invalid Choice]");
        System.out.println("-----------------------\n");

        printStore(); //Reprint options
      }
    }
  }

  /**
   * changeHandler handles all changes to the shopping cart
   * @param stock stock of the store
   * @return void
   */
  private void changeHandler() {
    //Remove item directions
    System.out.println("[INFO]:\tIf you would like to remove an item, " +
                        "please type 'remove' \n\tfollowed by the number of " +
                        "the item\n\tExample: remove 1");

    //Change item directions
    System.out.println("[INFO]:\tIf you would like to change the amount of" +
                        "an item, please type 'change' \n\tfollowed by the " +
                        "number of the item you want to change, and how many" +
                        "of that item\n\tyou would like." +
                        "\n\tExample: change 1 3");

    //Pay directions
    System.out.println("[INFO]:\tIf you would like to pay, please" +
                        " type 'pay'");
    //Shop directions
    System.out.println("[INFO]:\tIf you would like to continue shopping, " +
                        "please type 'shop'");
    //prompt for input
    System.out.print("\t Input: ");

    input = scan.nextLine().toLowerCase(); //Converts input to lowercase
    if (input.equals("pay")) { //If the user wants to pay
      //Print total
      System.out.println("  Total to Pay: $" + String.format("%.2f",
                                                              cart.getTotal()));
      //Makes a new store with the same stock
      closeStore(); //Close down the store
    }
    else if (input.equals("shop")) {
      printStore();
    }

    else { //If a change wants to be done
      int newAmount = 0;  //Holds the spliced user input parsed
      String[] spliter = input.split(" "); //Splices user input

      if (spliter[0].equals("remove")) { //If user wants to remove an item
        if (spliter.length != REMOVE_LENGTH) { //If not to arguments passed
          checkout();
        }
        else {
          try {
              //Parse Integer input
              newAmount = Integer.parseInt(spliter[ITEM_EDITOR]) - 1;

              //If input is in the the proper range
              if (newAmount >= 0  && newAmount < cart.uniqueItems()) {
                int counter = 0; //Defines a new counter
                for (int i = 0; i < cart.getTypeLength(); i++) {
                  for (int j = 0; j < cart.getSpecificType(i).size(); j++) {
                    //If selected object exists
                    if (newAmount == counter) {
                      cart.removeItem(i, j);
                    }
                    counter++; //increment counter
                  }
                }
                checkout(); //Send back to checkout window
              }
              else { //Number out of range
                System.out.println("[ERROR: Invalid Choice]");
                checkout();
              }
          } catch(NumberFormatException e) { //Invalid number
              System.out.println("[ERROR: Invalid Choice]\n");
              checkout();
          } catch(NullPointerException e) { //Invalid number
              System.out.println("[ERROR: Invalid Choice]\n");
              checkout();
          }
        }
      }

      else if (spliter[0].equals("change")) {//User wants to change a quantity
        if (spliter.length != CHANGE_LENGTH) { //If input is missing arguments
          checkout();
        }
        else {
          try {
              //parse input to Integer
              newAmount = Integer.parseInt(spliter[ITEM_EDITOR]) - 1;

              //Checks for a valid range
              if (newAmount >= 0  && newAmount < cart.uniqueItems()) {
                int counter = 0; //Defines a new counter
                for (int i = 0; i < cart.getTypeLength(); i++) {
                  for (int j = 0; j < cart.getSpecificType(i).size(); j++) {

                    if (counter == newAmount) { //item user wants to edit exists
                      try {
                        //substract all previous quantities of that object
                        if (Integer.parseInt(spliter[CHANGE_AMOUNT]) <= 0) {
                          System.out.println("[ERROR: Invalid Choice]");
                          checkout();
                        }
                        cart.changeAmount(i, j, spliter);

                      } catch(NumberFormatException e) { //quantity invalid
                          System.out.println("[ERROR: Invalid Quantity]\n");
                          checkout();
                        }
                      }
                    counter++; //increment counter
                  }
                }
              }
              else { //Not a valid number choice
                System.out.println("[ERROR: Invalid Choice]");
                checkout();
              }
              checkout();
          } catch(NumberFormatException e) { //Can't parse
              System.out.println("[ERROR: Invalid Choice]\n");
              checkout();
          } catch(NullPointerException e) { //Can't parse
              System.out.println("[ERROR: Invalid Choice]\n");
              checkout();
          }
        }
      }

      else { //Choice by user is invalid
        System.out.println("[ERROR: Invalid Choice]");
        checkout();
      }
    }
  }

  /**
   * checkout handles the checkout process of the cart
   * @param stock the stock of the store
   */
  public void checkout() {

    itemIteration = 0;
    System.out.println("\n---------------------------");
    System.out.println("Your Current Shopping Cart");
    System.out.println("---------------------------");
    System.out.println(cart.toString());
    //Iterate through the cart and get total
    cart.calculateTotal();
    //Print amount of items
    System.out.println("  Number of Items: " + cart.getSize());
    //Print total before discounts
    System.out.println("  Total before discounts: $" +
                        String.format("%.2f", cart.getTotal()));

    //Check for all of 1 type discount
    cart.checkDiscount(stock);

    //Prints total after discounts
    System.out.println("  Total after discounts: $" + String.format("%.2f",
                                                              cart.getTotal()));
    //Ask for changes to the cart
    changeHandler();
  }


  /**
   * stockStore stocks TheShop
   * @return boid
   */
  public void stockStore() {
    try {
      Scanner stocker = new Scanner(file); //Make a new Scanner from the file

      while (stocker.hasNextLine()) { //While there can be more items
        input = stocker.nextLine(); //get the next items
        String[] spliter = input.split("_");  //Split up the string taken in

        //Get price of item
        double price = Double.parseDouble(spliter[PRICE_INDEX].substring(1));
        if (spliter[TYPE_INDEX].equals("DVD")) {  //If a new DVD is to be added
          //Make a new DVD
          stock.add(new DVD(price, spliter[TITLE_INDEX],
                    spliter[GENRE_INDEX]));
        }

        //If a new Bluray wants to be added
        else if (spliter[TYPE_INDEX].equals("Blu-Ray")) {
          //Create a new bluray object
          stock.add(new Bluray(price, spliter[TITLE_INDEX],
                    spliter[GENRE_INDEX]));
        }
        //Object wanting to be added is unknown
        else {
          stockErrors.add(input); //Add to errors
        }
      }
    }
    catch (FileNotFoundException e) { //File does not exist
      System.out.println("Missing file");
    }
    printStore(); //print the newly stocked store

  }
}
