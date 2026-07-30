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
import reports.ExtentReportManager;
import utilities.ConfigReader;

import java.lang.reflect.Method;

/**
 * BaseTest — Parent class for ALL test classes.
 *
 * WHY A BASE TEST CLASS:
 *   - Centralizes driver setup/teardown — you don't repeat @BeforeSuite in every test
 *   - Manages the test lifecycle: suite start → test start → test result → suite end
 *   - Handles screenshots on failure automatically
 *   - Manages ExtentReports logging
 *   - Manages Appium Server lifecycle (auto-start/stop if enabled in config)
 *
 * HOW TO USE:
 *   Every test class extends BaseTest:
 *   public class LoginTest extends BaseTest {
 *       @Test
 *       public void verifyLogin() { ... }
 *   }
 *
 * LIFECYCLE ORDER:
 *   @BeforeSuite  → runs once before the entire test suite (starts Appium Server if run_local_server=true)
 *   @BeforeMethod → runs before each @Test method
 *   @AfterMethod  → runs after each @Test method (captures result)
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
     * and ExtentReport at the start of the test suite.
     * Runs ONCE before any test class begins.
     */
    @BeforeSuite(alwaysRun = true)
    public void setUpSuite() {
        log.info("========================================");
        log.info("  🚀 TEST SUITE STARTING");
        log.info("========================================");

        // Start Appium Local Server programmatically if enabled
        AppiumServerManager.startServer();

        // Initialize the ExtentReport HTML file
        ExtentReportManager.initReport();

        log.info("ExtentReport initialized.");
    }

    /**
     * Flushes and saves the ExtentReport and stops Appium Local Server after all tests complete.
     * Runs ONCE after all test classes finish.
     */
    @AfterSuite(alwaysRun = true)
    public void tearDownSuite() {
        ExtentReportManager.flushReport();

        // Stop Appium Local Server if it was started programmatically
        AppiumServerManager.stopServer();

        log.info("========================================");
        log.info("  ✅ TEST SUITE COMPLETE");
        log.info("  Report: {}", ConfigReader.getInstance().get("extent.report.path"));
        log.info("========================================");
    }

    // =========================================================
    //  METHOD LEVEL — Runs before/after each @Test
    // =========================================================

    /**
     * Sets up AndroidDriver before each test method.
     * Also creates a new ExtentReport test entry.
     *
     * @param method The current @Test method (injected by TestNG)
     */
    @BeforeMethod(alwaysRun = true)
    public void setUpTest(Method method) {
        String testName = method.getName();
        log.info("--- Starting Test: {} ---", testName);

        // Create an entry in the HTML report for this test
        ExtentReportManager.createTest(testName);

        // Initialize the AndroidDriver for this test thread
        DriverFactory.initDriver();
        log.info("Driver initialized for test: {}", testName);
    }

    /**
     * Captures results after each test method.
     * - PASS  → logs success to report
     * - FAIL  → takes screenshot + logs failure with reason
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
                    ExtentReportManager.logPass("Test passed successfully.");
                }
                case ITestResult.FAILURE -> {
                    log.error("❌ FAILED: {} — {}", testName, result.getThrowable().getMessage());
                    // Take screenshot and attach to report
                    String screenshotPath = ScreenshotUtil.captureScreenshot(
                            DriverFactory.getDriver(), testName
                    );
                    ExtentReportManager.logFail(
                            result.getThrowable(),
                            screenshotPath
                    );
                }
                case ITestResult.SKIP -> {
                    log.warn("⚠️ SKIPPED: {}", testName);
                    ExtentReportManager.logSkip("Test was skipped: " +
                            (result.getThrowable() != null
                                    ? result.getThrowable().getMessage()
                                    : "No reason provided"));
                }
            }
        } finally {
            // CRITICAL: Always quit driver — even if report logging fails
            DriverFactory.quitDriver();
            log.info("--- Finished Test: {} ---", testName);
        }
    }
}
