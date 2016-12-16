package com.iste776.jpavelw.coffeeorder;

// copy this code below your package statement

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays a simple coffee order form.
 */
public class MainActivity extends AppCompatActivity {

    // this variable represents the quantity ordered
    int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // This method is called when the order button is clicked
    public void submitOrder(View view) {

        // Get the user's name from the name field, convert it to a string
        EditText userNameTxt = (EditText) findViewById(R.id.name_field);
        String name = userNameTxt.getText().toString();
        if(name.equals("")){
            Toast.makeText(getApplicationContext(), "You must provide a name", Toast.LENGTH_LONG).show();
            return;
        }

        // Figure out if the user wants an extra shot of espresso
        CheckBox extraShotCheckBox = (CheckBox) findViewById(R.id.espresso_checkbox);
        boolean hasExtraEspresso = extraShotCheckBox.isChecked();

        // Figure out if the user wants large coffee
        CheckBox largeCheckBox = (CheckBox) findViewById(R.id.large_checkbox);
        boolean hasLarge = largeCheckBox.isChecked();

        // Calculate the price (call the calculatePrice method)
        int price = this.calculatePrice(hasExtraEspresso, hasLarge);

        // create the order summary (call createOrderSummary)
        String message = this.createOrderSummary(name, price, hasExtraEspresso, hasLarge);

        // Display the message to the screen
        this.displayMessage(message);
    }

    /**
     * add one to the number of coffees and call displayQuanity.
     *         only increment if quantity is less than 50
     *         and display the quantity
     */
    public void increment(View view) {
        if(this.quantity < 50) {
            this.quantity++;
            this.displayQuantity(this.quantity);
        } else {
            Toast.makeText(getApplicationContext(), "You cannot order more than 50 items", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * subtract one from the number of coffees call displayQuantity.
     *         only subtract if quantity is above 1
     *         and display the quantity
     */
    public void decrement(View view) {
        if(this.quantity > 1){
            this.quantity--;
            this.displayQuantity(this.quantity);
        } else {
            Toast.makeText(getApplicationContext(), "You cannot order less than 1 item", Toast.LENGTH_LONG).show();
        }
    }


    /**
     * Create the method to Display the given quantity value
     *          in the quantity text field.
     *          quantity is a number, convert it to a string for displaying
     */
    private void displayQuantity(int number) {
        TextView quantity_txt = (TextView) findViewById(R.id.quantity_text_view);
        quantity_txt.setText(String.valueOf(number));
    }

    /**
     * This method displays a order message  on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTxt = (TextView) findViewById(R.id.message_text_view);
        orderSummaryTxt.setText(message);
    }

    /**
     * Calculates the price of the order.
     *
     * @param addEspresso is whether or not we should include extra espresso in the price
     * @param addLarge    is whether or not we should add the large to the price
     * @return total price    as an integer value
     */
    private int calculatePrice(boolean addEspresso, boolean addLarge) {
        // First calculate the price of one cup of coffee
        int basePrice = 2;  // the base price is 2 dollars

        //  If the user wants extra espresso, add 1 dollar to the base price
        if(addEspresso)
            basePrice++;

        //  If the user wants large coffees, add 2 dollars to the base price
        if(addLarge)
            basePrice+=2;

        //  Calculate the total order price (base price times the quantity)
        return (basePrice * this.quantity);
    }

    /**
     * Create summary of the order.
     *
     * @param name        on the order
     * @param price       of the order
     * @param addEspresso is whether or not to add extra espresso to the coffee
     * @param addLarge    is whether or not to make it a Large coffee
     * @return text summary   as a String
     */
    private String createOrderSummary(String name, int price, boolean addEspresso,
                                      boolean addLarge) {
        String yORn;
        String priceMessage = getString(R.string.order_summary_name, name);
        if (addEspresso) yORn = "YES";
        else yORn = "NO";
        priceMessage += "\n" + getString(R.string.order_summary_add_espresso, yORn);
        if (addLarge) yORn = "YES";
        else yORn = "NO";
        priceMessage += "\n" + getString(R.string.order_summary_large, yORn);
        priceMessage += "\n" + getString(R.string.order_summary_quantity, quantity);
        priceMessage += "\n" + getString(R.string.order_summary_price,
                NumberFormat.getCurrencyInstance().format(price));
        priceMessage += "\n" + "Thank You";
        return priceMessage;
    }

}