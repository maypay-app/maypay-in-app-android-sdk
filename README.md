
# Maypay Android SDK

## Overview
This library allows you to check whether the maypay app is installed on your mobile device and open it to confirm a payment.  
Integration is simplified through the use of the [Maypay Android SDK.](https://github.com/maypay-app/maypay-in-app-android-sdk) that provides default Maypay buttons. 
Additionally, generic functions are provided to allow integration of Maypay into a fully customized button.
As an additional option, the procedure for integrating Maypay into your app without using the SDK (not recommended) will be explained.

After successful integration, payment in your app via Maypay will follow a flow similar to that in the example below.


![Android app payment flow](https://github.com/maypay-app/maypay-in-app-android-sdk/assets/137157182/81a4117c-1572-4234-b8a9-00909ed997fb)


## Prerequisites


:::caution
The Android minSdk required version is **28**
:::

### Getting the `requestId`
To allow the user to complete the payment process for your order, you need to provide Maypay with a valid paymentRequestId.   
Please refer to the [Payment Flows](https://developers.maypay.com/category/payment-flows) section for details on how to retrieve it.

### Provide a `redirectUrl`

To enable Maypay to reopen your application after payment, when creating the request you must provide the `redirectUrl` which is a [universal link (iOS)](https://developer.apple.com/ios/universal-links/) or an [app link (android)](https://developer.android.com/training/app-links) of your application.

## Installation
The package is stored in the [GitHub Packages Registry](https://github.com/features/packages), which requires credentials to download packages from the repository.

### Get credentials for GitHub Package Registry
1. Access your GitHub account
2. If you do not yet have an account, create one using [Sign Up.](https://github.com/join)
3. Create a Personal Access Token with at least ```read:packages``` scope. Follow the official guide for details ([Manage Personal Access Token](https://docs.github.com/en/enterprise-server@3.4/authentication/keeping-your-account-and-data-secure/managing-your-personal-access-tokens))

### Add repository to your project
In your ```settings.gradle``` add the following repository under ```dependencyResolutionManagement```:

```gradle
repositories {
    maven {
        url "https://maven.pkg.github.com/maypay-app/maypay-in-app-android-sdk"
        credentials {
            username = "your_username"
            password = "your_personal_access_token"
        }
    }
}
```

Then, in your build.gradle add the following dependecy:
```gradle
implementation 'com.maypay.sdk:android'
```
## Usage

You can display the default Maypay button by adding the component in your layout xml resource file.

```xml
<!-- Simple version-->
<com.maypay.sdk.ui.MaypayButton
    android:id="@+id/maypayButton"
    android:layout_height="wrap_content"
    android:layout_width="wrap_content" />

<!-- Extended version-->
<com.maypay.sdk.ui.MaypayButtonBox
    android:id="@+id/maypayButtonBox"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content" />

```

In your activity you can handle buttons as in the following section: 

```java
package com.maypay.android_sdk_sample;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.maypay.android_sdk_sample.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private final String paymentRequestId = "your_paymentRequestId"; // sample request ID, in production use a valid payment request ID.
    private final String amount = "2,99 €"; // set your actual payment amount 

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Provide maypayButton with paymentRequest ID
        binding.maypayButton.setPaymentRequestId(paymentRequestId);

        // OR in case of MaypayButtonBox - Provide maypayButtonBox with paymentRequest ID and total amount 
        binding.maypayButtonBox.setPaymentRequestId(paymentRequestId);
        binding.maypayButtonBox.setAmount(amount);
    }
}
```

Use `setPaymentRequestId(paymentRequestId: String)` to provide buttons with payment request ID

:::caution
 `setPaymentRequestId` throws an IllegalArgumentException if `paymentRequestId` is `null` or empty
:::

Use `setAmount(amount: String)` to provide button box with amount value.

:::caution
`setAmount` throws an IllegalArgumentException if `amount` is `null` or empty
:::

### Custom Button

If you want to implement your custom button to handle the Maypay app opening you can use `MaypayIntent.canOpenMaypay` and `MaypayIntent.openMaypay` functions.

#### `MaypayIntent.canOpenMaypay(context: Context)`
Returns a Boolean value that indicates whether your app is available to open Maypay or not.

#### `MaypayIntent.openMaypay(context: Context, requestId: String)`
Redirect the user to Maypay handling the given requestId, or redirect to the Maypay PlayStore page.

```java
package com.maypay.android_sdk_sample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import com.maypay.android_sdk_sample.databinding.ActivityMainBinding;
import com.maypay.sdk.intent.MaypayIntent;

public class MainActivity extends AppCompatActivity {
    // sample request ID, in production use a valid payment request ID.
    // See README (https://github.com/maypay-app/maypay-in-app-android-sdk) for further information.
    private final String paymentRequestId = "paymentRequestId";
    private final String amount = "2,99 €"; // set your actual payment amount

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Set on Click listener to your custom button
        binding.payWithMaypayCustom.setOnClickListener(v -> {
            boolean canOpenMaypay = MaypayIntent.canOpenMaypay(this)
            System.out.println("Is Maypay app installed? " + canOpenMaypay);

            MaypayIntent.openMaypay(this, paymentRequestId);
        });
    }
}
```

### No SDK
It is possible to integrate the payment flow without using the SDK using Android native [Intent](https://developer.android.com/training/basics/intents/sending). Here is an example on how to create a function to open Maypay providing the `paymentRequestId`. 

```java
public void openMaypay(@NonNull Context context, String requestId) {
    if (requestId == null || requestId.isEmpty() ) {
        throw new IllegalArgumentException("Payment request id cannot be empty");
    }

    Intent maypayIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("maypay://paymentRequest?id=" + requestId));
    context.startActivity(maypayIntent);
}
```
