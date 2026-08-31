package utils;

import java.net.URI;
import java.net.URL;
import java.time.Duration;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;

public class AppiumDriverEx {

    public static AppiumDriver getAppiumDriver() {
        AppiumDriver appiumDriver = null;

        try {
            // set capabilities to send to Appium server
            UiAutomator2Options options = new UiAutomator2Options();
            options.setPlatformName("Android");
            options.setAutomationName("UiAutomator2");
            options.setUdid("emulator-5554");
            options.setAppPackage("com.wdiodemoapp");
            options.setAppActivity("com.wdiodemoapp.MainActivity");
            options.setNewCommandTimeout(Duration.ofSeconds(90));

            URL appiumServer = URI.create("http://localhost:4723").toURL();
            appiumDriver = new AndroidDriver(appiumServer, options);
            appiumDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to initialize AppiumDriver: " + e.getMessage(), e);
        }

        return appiumDriver;
    }
}
