package testcases.android;

import commons.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * LaunchApp — Sanity check to verify WDIO app launches and driver session is healthy.
 */
@Epic("Application")
@Feature("App Launch & Session")
public class LaunchApp extends BaseTest {

    @Test(
        description = "TC_SANITY_001: Verify application launches successfully on target device",
        groups = {"smoke", "regression"}
    )
    @Severity(SeverityLevel.BLOCKER)
    @Description("Verify that Appium successfully initializes a driver session and launches the WDIO demo app.")
    public void testLaunchApp() {
        Assert.assertNotNull(getDriver(), "Appium driver session should be active and initialized.");
    }
}
