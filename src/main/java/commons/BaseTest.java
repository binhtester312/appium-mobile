package commons;

import driver.AppiumServerManager;
import driver.DriverFactory;
import io.appium.java_client.AppiumDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import reports.AllureManager;

import java.lang.reflect.Method;

/**
 * BaseTest — Parent class for ALL test classes using Allure Report.
 *
 * LIFECYCLE ORDER:
 *   @BeforeSuite  → runs once before the entire test suite (starts Appium Server if run_local_server=true)
 *   @BeforeMethod → runs before each @Test method (initializes AndroidDriver per thread)
 *   @AfterMethod  → runs after each @Test method (captures screenshot to Allure on failure, quits driver)
 *   @AfterSuite   → runs once after all tests finish (stops Appium Server)
 */
public class BaseTest {

    private static final Logger log = LoggerFactory.getLogger(BaseTest.class);

    /**
     * Returns the AndroidDriver for the current thread.
     * Use this in test classes: getDriver().findElement(...)
     *
     * @return AndroidDriver instance
     */
    protected AppiumDriver getDriver() {
        return DriverFactory.getDriver();
    }

    // =========================================================
    //  SUITE LEVEL — Runs once for the entire test run
    // =========================================================

    /**
     * Initializes the Appium Local Server (if run_local_server=true)
     * at the start of the test suite.
     */
    @BeforeSuite(alwaysRun = true)
    public void setUpSuite() {
        log.info("========================================");
        log.info("  🚀 TEST SUITE STARTING (ALLURE ENABLED)");
        log.info("========================================");

        // Start Appium Local Server programmatically if enabled
        AppiumServerManager.startServer();
    }

    /**
     * Stops Appium Local Server after all tests complete.
     */
    @AfterSuite(alwaysRun = true)
    public void tearDownSuite() {
        // Stop Appium Local Server if it was started programmatically
        AppiumServerManager.stopServer();

        log.info("========================================");
        log.info("  ✅ TEST SUITE COMPLETE");
        log.info("  Allure results stored in target/allure-results");
        log.info("========================================");
    }

    // =========================================================
    //  METHOD LEVEL — Runs before/after each @Test
    // =========================================================

    /**
     * Sets up AndroidDriver before each test method.
     *
     * @param method The current @Test method (injected by TestNG)
     */
    @BeforeMethod(alwaysRun = true)
    public void setUpTest(Method method) {
        String testName = method.getName();
        log.info("--- Starting Test: {} ---", testName);

        // Initialize the AndroidDriver for this test thread
        DriverFactory.initDriver();
        log.info("Driver initialized for test: {}", testName);
    }

    /**
     * Captures results after each test method.
     * - PASS  → logs success
     * - FAIL  → captures failure screenshot & attaches to Allure Report
     * - SKIP  → logs skip reason
     *
     * Always quits the driver regardless of result.
     *
     * @param result The test result (injected by TestNG)
     */
    @AfterMethod(alwaysRun = true)
    public void tearDownTest(ITestResult result) {
        String testName = result.getName();

        try {
            switch (result.getStatus()) {
                case ITestResult.SUCCESS -> {
                    log.info("✅ PASSED: {}", testName);
                }
                case ITestResult.FAILURE -> {
                    log.error("❌ FAILED: {} — {}", testName, result.getThrowable().getMessage());
                    // Capture failure screenshot and attach directly to Allure Report
                    AllureManager.saveScreenshot(testName, DriverFactory.getDriver());
                }
                case ITestResult.SKIP -> {
                    log.warn("⚠️ SKIPPED: {}", testName);
                }
            }
        } finally {
            // CRITICAL: Always quit driver to prevent session leaks
            DriverFactory.quitDriver();
            log.info("--- Finished Test: {} ---", testName);
        }
    }
}
