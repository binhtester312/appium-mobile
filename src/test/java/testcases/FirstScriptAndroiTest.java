package testcases;

import commons.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.android.LoginPage;
import pageObjects.android.ProductsPage;
import reports.ExtentReportManager;

/**
 * FirstScriptAndroiTest — Refactored to strictly adhere to Page Object Model (POM).
 * Extends BaseTest to automatically leverage driver management, reporting, and screenshot capture.
 */
public class FirstScriptAndroiTest extends BaseTest {

    @Test(
        description = "TC_001: Verify opening menu drawer and navigating to Login screen",
        groups = {"smoke", "regression"}
    )
    public void testFirstScript() {
        ProductsPage productsPage = new ProductsPage(getDriver());
        productsPage.openMenu();
        ExtentReportManager.logInfo("Opened side menu.");

        productsPage.clickLoginMenuItem();
        ExtentReportManager.logInfo("Clicked Login Menu Item.");

        LoginPage loginPage = new LoginPage(getDriver());
        Assert.assertTrue(loginPage.isLoginPageDisplayed(), "Login page should be displayed after tapping Login Menu Item.");
        ExtentReportManager.logInfo("Verified Login page is displayed successfully.");
    }

    @Test(
        description = "TC_002: Verify App Product List / Header logo is visible",
        groups = {"smoke"}
    )
    public void testAppProductList() {
        ProductsPage productsPage = new ProductsPage(getDriver());
        boolean isVisible = productsPage.isAppLogoDisplayed();
        ExtentReportManager.logInfo("App Product List header logo visible: " + isVisible);
        Assert.assertTrue(isVisible, "App Product List header or logo should be visible.");
    }

    @Test(
        description = "TC_003: Verify Login Menu Item is visible in side menu",
        groups = {"smoke"}
    )
    public void testAppMenu() {
        ProductsPage productsPage = new ProductsPage(getDriver());
        boolean isMenuVisible = productsPage.isLoginMenuItemDisplayed();
        ExtentReportManager.logInfo("Login Menu Item visible: " + isMenuVisible);
        Assert.assertTrue(isMenuVisible, "Login Menu Item should be visible in side menu.");
    }
}