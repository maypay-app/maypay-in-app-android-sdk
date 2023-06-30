package com.maypay.android_sdk_sample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import com.maypay.android_sdk_sample.databinding.ActivityMainBinding;
import com.maypay.android_sdk_sample.databinding.SampleCheckoutBinding;
import com.maypay.android_sdk_sample.databinding.SampleRecapBinding;
import com.maypay.sdk.intent.MaypayIntent;

public class MainActivity extends AppCompatActivity {
    // sample request ID, in production use a valid payment request ID.
    // See README (https://github.com/maypay-app/maypay-in-app-android-sdk) for further information.
    private final String paymentRequestId = "paste_your_request_ID_here";
    private final String amount = "2,99 €"; // set your actual payment amount

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SampleCheckoutBinding binding = SampleCheckoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Provide maypayButton with paymentRequest ID
        binding.maypayButton.setPaymentRequestId(paymentRequestId);

        this.handleAppLink();
    }


    @Override
    public void onStart() {
        super.onStart();
        try {
            this.handleAppLink();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    private void handleAppLink() {
        // ATTENTION: This was auto-generated to handle app links.
        Intent appLinkIntent = getIntent();
        String appLinkAction = appLinkIntent.getAction();
        Uri appLinkData = appLinkIntent.getData();

        if (appLinkData != null) {
            System.out.println("App opened by url " + appLinkData);
            SampleRecapBinding binding = SampleRecapBinding.inflate(getLayoutInflater());
            setContentView(binding.getRoot());
        }
    }

}