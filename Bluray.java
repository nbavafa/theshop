package theshop;

/**
 * @author Nicholas Bavafa
 *         nbavafa@gmail.com
 * 11/5/17
 * @version 1.0
 */


/**
 * This class defines a Bluray object. It extends the abstract class
 * CartItem because it is an item to be added to the shopping cart.
 */
public class Bluray extends CartItem {

  /** Default Constructor */
  public Bluray() {
    setType("Blu-Ray"); //Set Type of the object
    setDiscount(.15);   //Set potential discount of object
    setQuantity(1);     //Iniital quanity of the object
  }

  /**
   * Specific type Constructor
   * @param price double that holds the price of the object
   * @param title String that holds the title of the object
   * @param genre String that holds the genre of the object
   */
  public Bluray (double price, String title, String genre) {
    setPrice(price); //Assign price to passed value
    setTitle(title); //Assign title to passed value
    setGenre(genre); //Assign genre to passed value
    setType("Blu-Ray"); //Set Type of the object
    setDiscount(.15);   //Set potential discount of object
    setQuantity(1);     //Iniital quanity of the object
  }

}
