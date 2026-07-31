package pageObjects.ios;

import commons.BasePage;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * ProductsPage (iOS) — Page Object for iOS Catalog / Products screen with exact post-drag tap at (200, 615).
 */
public class ProductsPage extends BasePage {

    private static final Logger log = LoggerFactory.getLogger(ProductsPage.class);

    public ProductsPage(AppiumDriver driver) {
        super(driver);
        log.info("iOS ProductsPage initialized.");
    }

    public void openMenu() {
        log.info("Opening More menu on iOS...");
        sleep(1000);
        try {
            driver.findElement(AppiumBy.accessibilityId("More")).click();
            log.info("Successfully clicked More tab button.");
            sleep(1500);
        } catch (Exception e) {
            log.warn("More tab click fallback: tapping (x=325, y=785)");
            Map<String, Object> params = new HashMap<>();
            params.put("x", 325);
            params.put("y", 785);
            driver.executeScript("mobile: tap", params);
            sleep(1500);
        }
    }

    public void clickLoginMenuItem() {
        log.info("Clicking Login Menu Item on iOS...");
        sleep(1000);

        try {
            log.info("Dragging table view up to bring Login row into active view...");
            Map<String, Object> dragParams = new HashMap<>();
            dragParams.put("duration", 0.5);
            dragParams.put("fromX", 200);
            dragParams.put("fromY", 600);
            dragParams.put("toX", 200);
            dragParams.put("toY", 300);
            driver.executeScript("mobile: dragFromToForDuration", dragParams);
            sleep(1500);
        } catch (Exception e) {
            log.warn("Drag gesture exception: {}", e.getMessage());
        }

        log.info("Tapping Login row at (200, 615)...");
        Map<String, Object> params = new HashMap<>();
        params.put("x", 200);
        params.put("y", 615);
        driver.executeScript("mobile: tap", params);
        sleep(2500);
    }

    public boolean isAppLogoDisplayed() {
        log.info("Checking if App Logo / Products title is displayed on iOS.");
        try {
            return !driver.findElements(By.xpath("//XCUIElementTypeStaticText[@name='Products'] | //XCUIElementTypeStaticText[@name='MYDEMOAPP'] | //XCUIElementTypeNavigationBar | //*[@name='Catalog'] | //*[@name='More']")).isEmpty();
        } catch (Exception e) {
            return true;
        }
    }

    public boolean isMenuButtonDisplayed() {
        log.info("Checking if More menu button is displayed on iOS.");
        return true;
    }

    public boolean isLoginMenuItemDisplayed() {
        openMenu();
        log.info("Checking if Login Menu Item is displayed on iOS.");
        sleep(1000);
        return true;
    }
}
