package core;
import java.util.Random;

/**
 * Unocard class simulates a card and stores the cards color, value, and action (if action card).
 * 
 * cards 0-5
 * 2 reverse (card # 6,7),
 * 2 draw two (card # 8, 9)
 * 2 skip of each color (card # 10, 11)
 * and 2 wild (card # 12, 13)
 * 98 cards total
 * 
 * @version 1.0.0
 */

public class Unocard {

	private String color; // color of the card
	private int value; // value of the card
	private Random randomNum;
	private String action; // action of the card


	/**
	 * Sets default values to color, value, and action.
	 */
	public Unocard() {
		color = "black";
		value = -1;
		action = "default";
	}


	//=====================================================

	// Setters and Getters

	/**
	 * Returns the action.
	 * @return action
	 */
	public String getAction() {
		return action;
	}

	/**
	 * Sets the action.
	 * @param action
	 */
	public void setAction(String action) {
		this.action = action;
	}

	/**
	 * Gets the value.
	 * @return value.
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Sets the value.
	 * @param value
	 */
	public void setValue(int value) {
		this.value = value;
	}

	/**
	 * Gets the color.
	 * @return color
	 */
	public String getColor() {
		return color;
	}

	/**
	 * Sets the color.
	 * @param color
	 */
	public void setColor(String color) {
		this.color = color;
	}

	/**
	 * Returns string representation of card (ex. "red,5").
	 */
	public String toString() {
		if (value < 6) {
			return color + "," + value;
		} else {
			return color + "," + action;
		}
	}

	/**
	 * Prints the card to console.
	 */
	public void displayCard() {
		System.out.print(getColor());
		if(getValue()>5)System.out.println(","+getAction());
		else
			System.out.println(","+getValue());
	}

} // End UnoCard Class
//===================================================================
