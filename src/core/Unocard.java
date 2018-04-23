package core;
import java.util.Random;

/* cards 0-5
 * 2 reverse (card # 6,7),
 * 2 draw two (card # 8, 9)
 * 2 skip of each color (card # 10, 11)
 * and 2 wild (card # 12, 13)
 * 98 cards total
 */

public class Unocard {

	private String color; // color of the card
	private int value; // value of the card
	private Random randomNum;
	private String action; // action of the card


	// General Constructor
	public Unocard() {
		color = "black";
		value = -1;
		action = "default";
	}


	//=====================================================

	// Setters and Getters

	public String getAction() {
		return action;
	}

	public void setAction(String pAction) {
		action = pAction;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int pValue) {
		value = pValue;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String pColor) {
		color = pColor;
	}

	public String toString() {
		if (value < 6) {
			return color + "," + value;
		} else {
			return color + "," + action;
		}
	}

	public void displayCard() {
		System.out.print(getColor());
		if(getValue()>5)System.out.println(","+getAction());
		else
			System.out.println(","+getValue());
	}

} // End UnoCard Class
//===================================================================
