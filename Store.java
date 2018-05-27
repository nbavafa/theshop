package theshop;

import java.util.ArrayList;

/**
 * @author Nicholas Bavafa
 *         nbavafa@gmail.com
 * 11/5/17
 * @version 1.0
 */

/**
 * Interface Store gives minium directions to run a store with
 */
public interface Store {

  ArrayList<CartItem> stock = new ArrayList<CartItem>(); //Holds items in stock

  public void closeStore(); //Close the store down
  public void stockStore(); //Stock the store
  public void printStore(); //Print the stock in the store

}
