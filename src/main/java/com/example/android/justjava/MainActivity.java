package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int quantity = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void increment (View view ) {
       if (quantity == 10){
           Toast.makeText(this, "You cannot have more than 10 coffees", Toast.LENGTH_SHORT).show();
           return;
       }
        quantity = quantity + 1;
        display(quantity );

    }

    public void decrement (View view ) {
       if (quantity == 1){
          Toast.makeText(this, "You cannot have less than 1 coffee", Toast.LENGTH_SHORT).show();
           return;
       }
           quantity = quantity - 1;
        display(quantity );

    }
    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder (View view ) {
        EditText nameField = (EditText)findViewById(R.id.name_field);
        String name = nameField.getText().toString();

        CheckBox whippedCreamCheckBox = findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        CheckBox Chocolate = findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = Chocolate.isChecked();

        int price = calculatePrice(hasWhippedCream,hasChocolate);
        String priceMessage = createOrderSummary(price, hasWhippedCream, hasChocolate, name);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT,"Just Java order for " + name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null){
            startActivity(intent);
        }

    }

    private int calculatePrice(boolean addWhippedCream, boolean addChocolate){
        int basePrice = 5;
        if (addWhippedCream){
            basePrice = basePrice + 1;
        }
        if (addChocolate){
            basePrice = basePrice + 2;
        }
        return quantity * basePrice;
    }

    private void display ( int number ) {
        TextView quantityTextView =(TextView)findViewById(
                R.id.quantity_text_view);
        quantityTextView.setText( "" + number);
    }

    private String createOrderSummary(int price, boolean addWhippedCream,boolean addChocolate,String name){
        String priceMessage = "Name: " + name;
        priceMessage += "\nAdd whipped cream? " + addWhippedCream;
        priceMessage += "\nAdd chocolate? " + addChocolate;
        priceMessage += "\nQuantity: " + quantity;
        priceMessage += "\nTotal: $" + price;
        priceMessage += "\nThank You!";
        return priceMessage;
    }

}
