package testcases;

import commons.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.ios.LoginPage;
import pageObjects.ios.ProductsPage;

/**
 * FirstScriptIOSTest — Test Script for iOS Mobile App (iPhone 17).
 * Follows the exact format and structure of FirstScriptAndroiTest using Page Object Model (POM).
 * Extends BaseTest to automatically leverage driver management, reporting, and screenshot capture.
 */
@Epic("SauceLabs iOS Demo")
@Feature("Navigation & Product List on iOS")
public class FirstScriptIOSTest extends BaseTest {

    @Test(
        description = "TC_001: Verify opening menu drawer and navigating to Login screen on iOS",
        groups = {"smoke", "regression"}
    )
    public void testFirstScript() {
        ProductsPage productsPage = new ProductsPage(getDriver());
        productsPage.openMenu();
        productsPage.clickLoginMenuItem();

        LoginPage loginPage = new LoginPage(getDriver());
        Assert.assertTrue(loginPage.isLoginPageDisplayed(), "Login page should be displayed after tapping Login Menu Item on iOS.");
    }

    @Test(
        description = "TC_002: Verify App Product List / Header logo is visible on iOS",
        groups = {"smoke"}
    )
    public void testAppProductList() {
        ProductsPage productsPage = new ProductsPage(getDriver());
        boolean isVisible = productsPage.isAppLogoDisplayed();
        Assert.assertTrue(isVisible, "App Product List header or logo should be visible on iOS.");
    }

    @Test(
        description = "TC_003: Verify Login Menu Item is visible in side menu on iOS",
        groups = {"smoke"}
    )
    public void testAppMenu() {
        ProductsPage productsPage = new ProductsPage(getDriver());
        boolean isMenuVisible = productsPage.isLoginMenuItemDisplayed();
        Assert.assertTrue(isMenuVisible, "Login Menu Item should be visible in side menu on iOS.");
    }
}
