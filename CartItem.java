package theshop;

/**
 * @author Nicholas Bavafa
 *         nbavafa@gmail.com
 * 11/5/17
 * @version 1.0
 */

/**
 * The abstract class CartItem defines a CartItem object that
 * has a set of properties making it avalible to be added to a
 * shopping cart.
 */
public abstract class CartItem {

  int quantity; //Quantity of items in the cart

  double price;  //price of each movie on the store
  double discount; //discount factor is total items are added

  String title;  //Title of each specific movie
  String genre;  //Stores specific catagory (series) of each movie
  String type;   //Stores type of disk (Blu-ray or DVD)

  boolean appliedDiscount = false;


  /* setters */

  /**
   * Setter for the price
   * @param price double holding the price of the object
   * @return void
   */
  void setPrice(double price) {
    this.price = price; //Assigns price to passed value
  }

  /**
   * Getter for if a discount was applied
   * @param toApply boolean whether discount is true or fla
   *
   */
  void setDiscountApplication(boolean toApply) {
    this.appliedDiscount = toApply;
  }
  /**
   * Setter for the title
   * @param title String holding the title of the object
   * @return void
   */
  void setTitle(String title) {
    this.title = title; //Assigns title to passed value
  }

  /**
   * Setter for the quantity
   * @param quantity int holding the quantity of the object
   * @return void
   */
  void setQuantity(int quantity) {
    this.quantity = quantity; //Assigns the quantity to the passed value
  }

  /**
   * Setter for the type
   * @param type String holding the type of the object
   * @return void
   */
  void setType(String type) {
    this.type = type; //Assigns the type to the passed value
  }

  /**
   * Setter for the genre
   * @param genre String holding the genre of the object
   * @return void
   */
  void setGenre(String genre) {
    this.genre = genre; //Assigns the genre to the passed value
  }

  /**
   * Setter for the discount
   * @param discount double holding the price of the object
   * @return void
   */
  void setDiscount(double discount) {
    this.discount = discount; //Assigns the discount to the passed value
  }

  /* getters */

  /**
   * Getter for the price
   * @return double price of the object
   */
  double getPrice() {
    return price;
  }

  /**
   * Getter for the discount
   * @return double discount of the object
   */
  double getDiscount() {
    return discount;
  }

  /**
   * Getter for the quantity
   * @return int quanity of the object
   */
  int getQuantity() {
    return quantity;
  }

  /**
   * Getter for the title
   * @return String title of the object
   */
  String getTitle() {
    return title;
  }

  /**
   * Getter for the type
   * @return String type of the object
   */
  String getType() {
    return type;
  }

  /**
   * Getter for the genre
   * @return String genre of the object
   */
  String getGenre() {
    return genre;
  }

  /**
   * Gives a unique identifier for each items
   * @return string that holds the identifier
   */
  String identifier() {
    return genre + " " + title + " " + type + "($" +
                       String.format("%.2f", price) + ")";
  }
  /**
   * increments the quantity of the object
   */
  void incrementQuantity() {
    quantity++;
  }

  /**
   * toString()
   * @return String holding the printed value of object
   */
  @Override
  public String toString() {
    return genre + " " + title + "\t" + type + "\t($" +
                       String.format("%.2f", price) + ")";

  }
}
