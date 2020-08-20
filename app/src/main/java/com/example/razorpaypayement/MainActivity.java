package com.example.razorpaypayement;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements PaymentResultListener {

    private static final String TAG = "MainActivity";
    private Button btn_pay;
    private TextView txt_status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        btn_pay=(Button)findViewById(R.id.btn_pay);
        txt_status=(TextView)findViewById(R.id.txt_status);


        /**
         * Preload payment resources
         */
        Checkout.preload(getApplicationContext());

        btn_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              startPayment();
            }
        });
    }

    //START PAYMENT
    public void startPayment() {


        /**
         * Instantiate Checkout
         */
        Checkout checkout = new Checkout();


        /**
         * Set your logo here
         */
        //checkout.setImage(R.drawable.logo);

        /**
         * Reference to current activity
         */
        final Activity activity = this;

        /**
         * Pass your payment options to the Razorpay Checkout as a JSONObject
         */
        try {
            JSONObject options = new JSONObject();

            options.put("name", "Merchant Name");
            options.put("description", "Reference No. #123456");
            options.put("currency", "INR");

            /*
            * Amount is always passed in PAISE
            * Eg: "500" = Rs 5
            * Eg: "5000"= Rs 50
            *
            * MULTIPLY BY 100
            *
            * */

            //pass amount in currency subunits
            options.put("amount", "50000");
            options.put("prefill.email", "ashispatel1998@gmail.com");
            options.put("prefill.contact","7749003888");
            checkout.open(activity, options);
        }
        catch(Exception e) {
            Log.e(TAG, "Error in starting Razorpay Checkout", e);
        }

    }

    @Override
    public void onPaymentSuccess(String s) {
          txt_status.setText("Payment successfull");
          txt_status.setTextColor(Color.GREEN);
    }

    @Override
    public void onPaymentError(int i, String s) {
        txt_status.setText(s);
        txt_status.setTextColor(Color.RED);
    }
}