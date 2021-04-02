package com.example.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.NumberFormat;


/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        EditText nameField=(EditText)findViewById(R.id.name_field);
        String name=nameField.getText().toString();
        CheckBox whippedCreamCheckBox=(CheckBox)findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream=whippedCreamCheckBox.isChecked();
        CheckBox chocolateCheckBox=(CheckBox)findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate=chocolateCheckBox.isChecked();
        int price=calculate(hasWhippedCream,hasChocolate);
        String summary=createOrderSummary(price,hasWhippedCream,hasChocolate,name);
        Intent intent=new Intent(Intent.ACTION_SEND);
        intent.setData(Uri.parse("mailto:tupesoham@gmail.com"));
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT,getString(R.string.order_summary_emailsubject,name));
        intent.putExtra(Intent.EXTRA_TEXT,summary);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
        else
        {
            Toast.makeText(this,"Couldn't find an App for sending email.",Toast.LENGTH_SHORT).show();
        }
    }
    private int calculate(boolean hasWhippedCream,boolean hasChocolate) {
        int price=0;
        if(hasWhippedCream==true)
        {
            price+=1;
        }
        if(hasChocolate==true)
        {
            price+=2;
        }
        return quantity*(price+5);
    }
    private String createOrderSummary(int price,boolean hasWhippedCream,boolean hasChocolate,String name){
        String summary=getString(R.string.order_summary_name,name)+"\n";
        summary+=getString(R.string.order_summary_wc,hasWhippedCream)+"\n";
        summary+=getString(R.string.order_summary_chocolate,hasChocolate)+"\n";
        summary+=getString(R.string.order_summary_quantity,quantity)+"\n";
        summary+=getString(R.string.order_summary_price,NumberFormat.getCurrencyInstance().format(price))+"\n";
        summary+=getString(R.string.thank_you);
        return summary;
    }
    public void increment(View view) {
        if(quantity==100) {
            Toast.makeText(this,getString(R.string.increment_error),Toast.LENGTH_SHORT).show();
            return;
        }
        quantity += 1;
        display(quantity);
    }

    public void decrement(View view) {
        if(quantity==1) {
            Toast.makeText(this,getString(R.string.decrement_error),Toast.LENGTH_SHORT).show();
            return;
        }
        quantity -= 1;
        display(quantity);
    }



    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(
                R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given text on the screen.
     */

}