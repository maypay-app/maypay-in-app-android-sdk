package com.maypay.sdk.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.LinearLayout;

import com.maypay.sdk.R;
import com.maypay.sdk.intent.MaypayIntent;

public class MaypayButton extends LinearLayout {

    Button button;
    String paymentRequestId;

    public MaypayButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        inflate(context, R.layout.maypaybutton, this);
        button = findViewById(R.id.button);

        configureButton(context);
    }

    private void configureButton(Context context) {
        button.setOnClickListener(v -> {
            System.out.println("Opening Maypay: " + paymentRequestId);
            MaypayIntent.openMaypay(context, paymentRequestId);
        });
    }

    public void setPaymentRequestId(String paymentRequestId) {
        if (paymentRequestId == null || paymentRequestId.isEmpty()) {
            throw new IllegalArgumentException("Payment request id cannot be empty");
        }

        this.paymentRequestId = paymentRequestId;
    }
}
