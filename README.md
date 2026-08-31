# 📱 Appium Mobile Automation Framework

A clean, modern mobile test automation project built with **Appium 9.x**, **Java 17**, **TestNG**, and **Maven**.

---

## 🛠 Tech Stack & Prerequisites

* **Language**: Java 17
* **Mobile Driver**: Appium Java Client `9.4.0` (W3C Actions API)
* **Testing Framework**: TestNG `7.10.2`
* **Build Tool**: Maven
* **Target App**: WebdriverIO Demo App (`com.wdiodemoapp`)
* **Environment**: Android Emulator (API 35) / Real Devices

---

## 📁 Project Structure

```text
appium-mobile/
├── src/
│   ├── main/java/
│   │   ├── driver/        # Appium Server & Driver Factory setup
│   │   ├── pageObjects/   # Page Object Model (POM) classes
│   │   ├── pageUIs/       # Element locators (Android & iOS)
│   │   └── utils/         # Helper utilities (AppiumDriverEx)
│   └── test/java/
│       └── testcases/android/apilearning/
│           ├── GetValue.java               # Get element text & dialog validation
│           ├── HandleToggleButton.java      # Interact with toggle switches
│           ├── HandleDropdown.java          # Select dropdown options
│           ├── TakingScreenshot.java        # Capture screen screenshots
│           └── SwipeVertically.java         # W3C vertical swipe & Assertions
├── pom.xml                # Maven dependencies & build plugins
└── README.md              # Project documentation
```

---

## 🚀 How to Run Tests

### 1. Run a Specific Test Script
Run any test script directly from your terminal:

```bash
mvn test -Dtest=SwipeVertically
```

### 2. Run with Live Log Tracking
Run tests and save output logs to a file:

```bash
mvn test -Dtest=SwipeVertically | tee test-execution.log
```

### 3. Run All API Learning Test Scripts
Run all test cases inside the `apilearning` package:

```bash
mvn test -Dtest="testcases.android.apilearning.*"
```

---

## 📊 Test Reports

After running tests, view the automatically generated TestNG report:
* **HTML Report**: `target/surefire-reports/emailable-report.html`
