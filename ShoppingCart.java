package theshop;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Nicholas Bavafa
 *         nbavafa@gmail.com
 * 11/5/17
 * @version 1.0
 */

/**
 * This class defines a ShoppingCart object. It handles adding items to the
 * shopping cart, removing items from the shopping cart, and changes to the
 * shopping cart items. It also handles the price totals and discount
 * calculations.
 *
 * The items are stored in an Arraylist of Arraylists, and a helper Arraylist
 * types keeps track of what specific cart item is stored at each index.
 */

public class ShoppingCart {
  int CHANGE_AMOUNT = 2; //Index of change amount in array
  int BULK_AMOUNT = 100; //Amount threshold for mass discount
  double MASS_DISCOUNT = .95; //Mass discount amount

  ArrayList<ArrayList<CartItem>> cart;  //Holds items in shoppping cart

  //keeps track of specific CartItems at each index of cart
  ArrayList<String> types = new ArrayList<String>();

  Scanner scan = new Scanner(System.in); //Defines a new Scanner

  String typeToAdd; //Type of CartItem to add
  String input;     //User input holder

  boolean typeExists; //Holds if the type of CartItem already is in the cart

  double total;       //Total price

  int numItems;       //Total items in the cart

  int itemIteration;

  /** Default Constructor */
  public ShoppingCart() {
    cart = new ArrayList<ArrayList<CartItem>>(); //Makes a new cart
    total = 0;    //Cost is 0
    numItems = 0; //The amount of items is 0
  }

  /**
   * removeItem removes an item from the cart
   * @param type the index of the type of element to removeItem
   * @param index index of the element to remove
   * @return void
   */
  public void removeItem(int type, int index) {
    total = total - (cart.get(type).get(index).getPrice() *
                      cart.get(type).get(index).getQuantity());

    numItems = numItems - cart.get(type).get(index).getQuantity();
    cart.get(type).remove(index); //Remove the object

  }

  /**
   * changeAmount changes the amount of an item in the shopping cart
   * @param type type index of item amount to change
   * @param index index of item amount to change
   * @param spliter stores user input to what to change to
   * @return void
   */
  public void changeAmount(int type, int index, String[] spliter) {
    //Remove previous amount from numItems
    numItems = numItems - cart.get(type).get(index).getQuantity();

    //Remove price total
    total = total - (cart.get(type).get(index).getPrice() *
                      cart.get(type).get(index).getQuantity());
    //Set new quantity
    cart.get(type).get(index).setQuantity(Integer.parseInt(spliter[CHANGE_AMOUNT]));

    //Add new quantity
    numItems = numItems + cart.get(type).get(index).getQuantity();

    //Add new price total
    total = total + (cart.get(type).get(index).getPrice() *
                      cart.get(type).get(index).getQuantity());
  }

  /**
   * add handles adding an item to the ShoppingCart from the store
   * @param item is the item to be added
   * @return void
   */
  public void add(CartItem item) {
    typeToAdd = item.getType(); //Get item type
    typeExists = false;         //Sets to default value

    for (int i = 0; i < types.size(); i++) {  //Iterate through helper ArrayList
      if (typeToAdd.equals(types.get(i))) { //If the types are equal
        addItem(i, item); //Add the item at that specific index
        typeExists = true;//The type exists in the ArrayList already
      }
    }
    if (typeExists == false) { //If the type was not found
      extendCart(item); //Expand the cart to hold that item
      add(item);        //Recursive call to add item
    }
  }

  /**
   * addItem does the actual addition of each item to the cart
   * @param index index to add the item at
   * @param item item to add
   * @return boolean true when completed
   */
  private boolean addItem(int index, CartItem item) {
    for (int i = 0; i < cart.size(); i++) {
      for (int j = 0; j < cart.get(i).size(); j++) {
        //If the item has already been added
        if (cart.get(i).get(j).identifier().equals(item.identifier())) {
          cart.get(i).get(j).incrementQuantity(); //increment its quantity
          numItems++; //increment the number of items
          total = total + item.getPrice();
          return true; //Break
        }
      }
    }
    cart.get(index).add(item); //Add to the ArrayList
    numItems++;  //increment items
    total = total + item.getPrice();
    return true; //Break
  }

  /**
   * extendCart adds the new type to the cart
   * @param item item that has a type to add
   * @return void
   */
  private void extendCart(CartItem item) {
    cart.add(new ArrayList<CartItem>());  //Makes new ArrayList for the item
    types.add(item.getType());  //Adds identifier to the types ArrayList
  }


  /**
   * uniqueItems() returns the amount of unique items in the cart
   * @return the amount of unique items in the shopping cart
   */
   public int uniqueItems() {
     int toReturn = 0;
     for (int i = 0; i < cart.size(); i++) {
      toReturn = toReturn + cart.get(i).size();
     }
     return toReturn;
  }

  /**
   * calculates total cost before any discounts are applied
   * @return void
   */
  public void calculateTotal() {
    total = 0;
    for (int i = 0; i < cart.size(); i++) { //iterate through types
      for (int j = 0; j < cart.get(i).size(); j++) { //iterate through
        total = total + cart.get(i).get(j).getPrice() *
                        cart.get(i).get(j).getQuantity();
      }
    }
  }

  /**
   * massDiscount applies the mass item discount
   * @return void
   */
  private void massDiscount() {
    System.out.println("\tDiscount on mass-order achieved!"); //Prints discount
    total = total * MASS_DISCOUNT; //Substracts total
  }

  /**
   * applyDiscount actually reflects discounts on the total and prints the
   * discount message
   * @param discounted ArrayList of items to be discounted
   */
  private void applyDiscount(ArrayList<CartItem> discounted) {
    //Prints type of discount
    System.out.println("\tDiscount on " + discounted.get(0).getType() +
                      " achieved!");
    //Iterates through and gives discount
    for (int i = 0; i <discounted.size(); i++) {
      //Edits the total to reflect lower price
      total = total - (discounted.get(i).getQuantity() *
                      discounted.get(i).getPrice() *
                      (discounted.get(i).getDiscount()));
    }
    //If theres more than 100 items in the cart
  }

  /**
   * Iterates through the cart and sees if a discount is valid
   * @param stock stock of the store
   * @return void
   */
  public void checkDiscount(ArrayList<CartItem> stock) {
    for (int i = 0; i < cart.size(); i++) {

      if (cart.get(i).size() == itemAmount(cart.get(i).get(0), stock)) {
        applyDiscount(cart.get(i)); //Update total and show discount
      }

      if (numItems >= BULK_AMOUNT) {
        massDiscount(); //apply discount
      }
    }
  }

  /**
   * getSize returns the size of the cart
   * @return int size of cart
   */
  public int getSize() {
    return numItems;
  }

  /**
   * returns current cart total
   * @return double holding the cart total
   */
  public double getTotal() {
    return total;
  }

  /**
   *  getTypeLength returns the length of the cart types
   * @return length of the cart types ArrayList
   */
  public int getTypeLength() {
     return cart.size();
  }

  /**
   *  getSpecificType type returns an arraylist of the type requested
   * @return arraylist of type cartitems in the cart
   */
  public ArrayList<CartItem> getSpecificType(int type) {
     return cart.get(type);
  }

  /**
   * itemAmount returns the amount of 1 type of item is in stock
   * @param item of type to checkout
   * @param stock stock of the store
   * @return int amount of that type in stock
   */
  public int itemAmount(CartItem item, ArrayList<CartItem> stock) {
    int toReturn = 0;
    for (int i = 0; i < stock.size(); i++) {
      //If the item type and stock item type are equal
      if (item.getType().equals(stock.get(i).getType())) {
        toReturn++; //increment
      }
    }
    return toReturn;
  }

  @Override
  /**
   * toString for ShoppingCart
   * @return string cart printed well
   */
   public String toString() {
     String toReturn = "";
     itemIteration = 0;
     //Iterate through the cart and add to string
     for (int i = 0; i < cart.size(); i++) {
       toReturn = toReturn + types.get(i) + ":\n";
       for (int j = 0; j < cart.get(i).size(); j++) {
         itemIteration++;
         toReturn = toReturn + "  " + itemIteration + ") ";
         toReturn = toReturn + cart.get(i).get(j).toString() +
                            " \n\t\tAmount: [" +
                           cart.get(i).get(j).getQuantity() + "]\n";
       }
     }
     return toReturn;
   }
}
