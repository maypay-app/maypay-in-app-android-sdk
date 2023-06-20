package com.maypay.sdk.intent;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;

import androidx.annotation.NonNull;

public final class MaypayIntent {
    private static final String MAYPAY_CUSTOM_SCHEME = "maypay://";
    private static final String MAYPAY_PAYMENT_REQUEST_URL = "maypay://paymentRequest?id=";
    private static final String MAYPAY_PLAYSTORE_URL = "https://play.google.com/store/apps/details?id=com.maypay.buyer_app";

    public static boolean canOpenMaypay(@NonNull Context context) {
        return context.getPackageManager()
                .queryIntentActivities(
                        new Intent(Intent.ACTION_VIEW,
                                Uri.parse(MAYPAY_CUSTOM_SCHEME)),
                        PackageManager.MATCH_DEFAULT_ONLY).size() > 0;
    }

    public static void openMaypay(@NonNull Context context, String requestId) {
        if (requestId == null || requestId.isEmpty() ) {
            throw new IllegalArgumentException("Payment request id cannot be empty");
        }

        if (canOpenMaypay(context)) {
            Intent maypayIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(MAYPAY_PAYMENT_REQUEST_URL + requestId));
            context.startActivity(maypayIntent);
        } else {
            Intent fallBackIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(MAYPAY_PLAYSTORE_URL));
            context.startActivity(fallBackIntent);
        }
    }
}



