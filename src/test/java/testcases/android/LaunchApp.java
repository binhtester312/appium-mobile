package testcases.android;

import java.net.URI;
import java.net.URL;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;

public class LaunchApp {

    public static void main(String[] args) {
        // Setup the Appium server URL to connect to
        try {
            // driver instance
            AppiumDriver appiumDriver = null;

            // set capabilities to send to Appium server
            UiAutomator2Options options = new UiAutomator2Options();
            options.setPlatformName("Android");
            options.setAutomationName("UiAutomator2");
            options.setUdid("emulator-5554");
            options.setAppPackage("com.wdiodemoapp");
            options.setAppActivity("com.wdiodemoapp.MainActivity");

            URL appiumServer = URI.create("http://localhost:4723").toURL();
            appiumDriver = new AndroidDriver(appiumServer, options);
            appiumDriver = new AppiumDriver(appiumServer, options);
            // send get text command to Appium server

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
