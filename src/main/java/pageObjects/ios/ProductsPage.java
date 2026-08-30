package pageObjects.ios;

import commons.BasePage;
import io.appium.java_client.AppiumDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pageUIs.ios.ProductsPageUI;

/**
 * ProductsPage (iOS) — Page Object for iOS Catalog / Products screen with exact
 * post-drag tap at (200, 615).
 */
public class ProductsPage extends BasePage {

    private static final Logger log = LoggerFactory.getLogger(ProductsPage.class);

    public ProductsPage(AppiumDriver driver) {
        super(driver);
    }

    /**
     * Taps the "More" tab to open the side menu.
     * Waits for More tab to be visible (ensures app fully loaded) before clicking.
     */
    public void openMenu() {
        log.info("Opening More menu on iOS...");
        waitForVisibility(ProductsPageUI.MENU_BUTTON); // wait for app to fully load
        click(ProductsPageUI.MENU_BUTTON);
        log.info("More menu opened successfully.");
    }

    /**
     * Taps the Login item in the More menu.
     * Locator verified: accessibilityId = "Login Button" on real iPhone 17
     * Simulator.
     * (NOT "Login" — that is the Login screen title / button, not the menu item)
     */
    public void clickLoginMenuItem() {
        log.info("Clicking Login Menu Item on iOS...");
        click(ProductsPageUI.MENU_LOGIN_ITEM);
        log.info("Login Menu Item tapped.");
    }

    /**
     * Checks if the Products screen is displayed (App logo or Products header
     * visible).
     * Locator verified: accessibilityId = "Products" on real iPhone 17 Simulator.
     */
    public boolean isAppLogoDisplayed() {
        log.info("Checking if Products title is displayed on iOS.");
        try {
            waitForVisibility(ProductsPageUI.APP_LOGO_HEADER);
            return true;
        } catch (Exception e) {
            log.warn("Products header not visible: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Checks if the More (menu) tab button is visible in the bottom tab bar.
     */
    public boolean isMenuButtonDisplayed() {
        log.info("Checking if More tab button is displayed on iOS.");
        return isElementPresent(ProductsPageUI.MENU_BUTTON);
    }

    /**
     * Opens the More menu and verifies the Login menu item is visible.
     * Locator verified: accessibilityId = "Login Button" on real iPhone 17
     * Simulator.
     */
    public boolean isLoginMenuItemDisplayed() {
        openMenu();
        log.info("Checking if Login Menu Item is displayed on iOS.");
        try {
            waitForVisibility(ProductsPageUI.MENU_LOGIN_ITEM);
            return true;
        } catch (Exception e) {
            log.warn("Login Menu Item not found: {}", e.getMessage());
            return false;
        }
    }
}
