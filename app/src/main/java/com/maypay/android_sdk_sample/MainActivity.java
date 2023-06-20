package com.maypay.android_sdk_sample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import com.maypay.android_sdk_sample.databinding.ActivityMainBinding;
import com.maypay.sdk.intent.MaypayIntent;

public class MainActivity extends AppCompatActivity {
    // sample request ID, in production use a valid payment request ID.
    // See README (https://github.com/maypay-app/maypay-in-app-android-sdk) for further information.
    private final String paymentRequestId = "paymentRequestId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.maypaybutton.setPaymentRequestId(paymentRequestId);

        binding.payWithMaypayCustom.setOnClickListener(v -> {
            System.out.println(MaypayIntent.canOpenMaypay(this));
            MaypayIntent.openMaypay(this, paymentRequestId);
        });

    }


}