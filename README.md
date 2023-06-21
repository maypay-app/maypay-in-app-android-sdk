
# Maypay Android SDK


This library allows you to check whether the maypay app is installed on your mobile device and open it to confirm a payment. 
The maypay app needs the payment id, so it is necessary to provide the library with the ``paymentRequestId`` received after creating the payment request via the [Maypay API](https://developers.maypay.com/introduction/welcome/). 

To simplify integration, the library has two UI components that manage the opening of the app :
#### `MaypayButton` 
![Simple Button](https://github.com/maypay-app/maypay-in-app-android-sdk/assets/137157182/f3973f38-937f-4ee5-ae5e-970524918978)
#### `MaypayButtonBox` 
![Extended Button](https://github.com/maypay-app/maypay-in-app-android-sdk/assets/137157182/19773c67-029e-4fda-a97c-cec94e82434c)

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
implementation 'com.maypay.sdk:maypay-android-sdk-test-deploy'
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
        setContentView(R.layout.activity_main);

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

### Custom Integration

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
        setContentView(R.layout.activity_main);

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
