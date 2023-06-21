package com.maypay.sdk.ui;

import android.content.Context;
import android.text.Html;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.maypay.sdk.R;
import com.maypay.sdk.intent.MaypayIntent;

public class MaypayButtonBox extends LinearLayout {
    Button button;
    TextView amountText;
    String paymentRequestId;

    Context ctx;

    public MaypayButtonBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.ctx = context;
        init();
    }

    private void init() {
        inflate(this.ctx, R.layout.maypaybuttonbox, this);
        button = findViewById(R.id.button);

        configureButton();
    }

    private void configureButton() {
        button.setOnClickListener(v -> {
            System.out.println("Opening Maypay: " + paymentRequestId);
            MaypayIntent.openMaypay(ctx, paymentRequestId);
        });
    }

    public void setPaymentRequestId(String paymentRequestId) {
        if (paymentRequestId == null || paymentRequestId.isEmpty()) {
            throw new IllegalArgumentException("Payment request id cannot be empty");
        }

        this.paymentRequestId = paymentRequestId;
    }

    public void setAmount(String amount) {
        if (amount == null || amount.isEmpty()) {
            throw new IllegalArgumentException("Amount cannot be empty");
        }

        amountText = findViewById(R.id.amount);
        amountText.setText(String.format("%s: %s",  ctx.getString(R.string.amountHeader), amount));
    }

}
