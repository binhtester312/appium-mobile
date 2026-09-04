package testcases;

import commons.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.android.saucelabs.LoginPage;
import pageObjects.android.saucelabs.ProductsPage;

/**
 * FirstScriptAndroiTest — Sauce Labs Demo App sample tests with Page Object Model.
 */
@Epic("SauceLabs Demo")
@Feature("Navigation & Product List")
public class FirstScriptAndroiTest extends BaseTest {

    @Test(
        description = "TC_001: Verify opening menu drawer and navigating to Login screen",
        groups = {"smoke", "regression"}
    )
    public void testFirstScript() {
        ProductsPage productsPage = new ProductsPage(getDriver());
        productsPage.openMenu();
        productsPage.clickLoginMenuItem();

        LoginPage loginPage = new LoginPage(getDriver());
        Assert.assertTrue(loginPage.isLoginPageDisplayed(), "Login page should be displayed after tapping Login Menu Item.");
    }

    @Test(
        description = "TC_002: Verify App Product List / Header logo is visible",
        groups = {"smoke"}
    )
    public void testAppProductList() {
        ProductsPage productsPage = new ProductsPage(getDriver());
        boolean isVisible = productsPage.isAppLogoDisplayed();
        Assert.assertTrue(isVisible, "App Product List header or logo should be visible.");
    }

    @Test(
        description = "TC_003: Verify Login Menu Item is visible in side menu",
        groups = {"smoke"}
    )
    public void testAppMenu() {
        ProductsPage productsPage = new ProductsPage(getDriver());
        boolean isMenuVisible = productsPage.isLoginMenuItemDisplayed();
        Assert.assertTrue(isMenuVisible, "Login Menu Item should be visible in side menu.");
    }
}