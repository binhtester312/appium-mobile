# 📱 Appium Mobile Automation Framework (Page Object Model)

Dự án kiểm thử tự động hóa ứng dụng di động Android & iOS chuyên nghiệp, được xây dựng theo chuẩn kiến trúc **Page Object Model (POM)** trên nền tảng **Appium 9.x**, **Java 17**, **TestNG**, và **Maven**.

---

## 🌟 Tính Năng Nổi Bật (Key Features)

- **Page Object Model (POM) Chuẩn Quốc Tế**: Phân tách độc lập 100% giữa **Locators (`pageUIs`)**, **Actions / Page Methods (`pageObjects`)** và **Assertions (`testcases`)**.
- **Cross-Platform Ready**: Hỗ trợ mở rộng đa nền tảng (Android & iOS).
- **W3C Actions Gestures (`SwipeHelper`)**: Thực hiện các thao tác vuốt màn hình (vuốt ngang carousel, cuộn dọc, vuốt tìm phần tử có điều kiện) mượt mà, độc lập độ phân giải thiết bị.
- **Smart Dynamic Wait Strategy**: Loại bỏ hoàn toàn `Thread.sleep()` thô, thay thế bằng cơ chế Explicit Wait linh hoạt kế thừa từ `BasePage`.
- **Báo Cáo & Media Trực Quan**: Tích hợp **ExtentReports** sinh file HTML trực quan, tự động chụp screenshot khi test fail, hỗ trợ quay video toàn bộ phiên chạy test.
- **Đa Dạng Phương Thức Khởi Chạy**: Hỗ trợ chạy qua **Maven CLI**, **TestNG XML Suite**, và chạy trực tiếp từng file qua **`main(String[] args)`** trong IDE.

---

## 🛠 Tech Stack

| Công Nghệ / Thư Viện | Phiên Bản | Mục Đích |
|---|---|---|
| **Java** | `17` (LTS) | Ngôn ngữ lập trình chính |
| **Appium Java Client** | `9.4.0` | Thư viện điều khiển Appium (W3C Actions API) |
| **Selenium WebDriver** | `4.27.0` | Core Web/Mobile Driver engine |
| **TestNG** | `7.10.2` | Test runner, lifecycle hooks, assertions |
| **ExtentReports** | `5.1.2` | Báo cáo kiểm thử HTML chuyên nghiệp |
| **SLF4J / Logback** | `2.0.16` | Ghi log hệ thống |
| **Maven** | `3.9+` | Quản lý dependencies & build tự động |
| **Target App** | WDIO Demo App (`com.wdiodemoapp`) | Ứng dụng mobile mẫu kiểm thử |

---

## 📁 Cấu Trúc Bộ Source Code (Project Structure)

```
appium-mobile/
├── src/
│   ├── main/java/
│   │   ├── commons/                       # Core Framework Abstractions
│   │   │   ├── BasePage.java              # Lớp cha của mọi Page Object (wait, click, sendKeys, count...)
│   │   │   ├── BaseTest.java              # Lớp cha của mọi Test class (driver lifecycle, reporting, hooks)
│   │   │   └── ScreenshotUtil.java        # Tiện ích chụp ảnh màn hình và lưu disk
│   │   ├── driver/                        # Driver Management
│   │   │   ├── DriverFactory.java         # Khởi tạo và quản lý AppiumDriver đa luồng (ThreadLocal)
│   │   │   └── AppiumServerManager.java   # Quản lý Appium Server (auto start/stop)
│   │   ├── pageUIs/                       # UI LOCATORS LAYER (Chỉ chứa định danh phần tử)
│   │   │   ├── android/
│   │   │   │   └── wdio/
│   │   │   │       ├── BottomNavUI.java   # Locators các tab bottom menu (Home, Webview, Login, Forms, Swipe, Drag)
│   │   │   │       ├── LoginUI.java       # Locators màn hình Login, input credentials, buttons, alert dialog
│   │   │   │       ├── FormsUI.java       # Locators màn hình Forms (input, switch toggle, dropdown menu)
│   │   │   │       └── SwipeUI.java       # Locators màn hình Swipe & thẻ carousel
│   │   │   └── ios/                       # Locators dành cho nền tảng iOS
│   │   ├── pageObjects/                   # PAGE ACTIONS LAYER (Chứa nghiệp vụ tương tác màn hình)
│   │   │   ├── android/
│   │   │   │   └── wdio/
│   │   │   │       ├── BottomNavComponent.java # Điều hướng giữa các tab màn hình
│   │   │   │       ├── LoginPage.java     # Thao tác: login(), enterEmail(), getAlertTitle(), dismissAlert()...
│   │   │   │       ├── FormsPage.java     # Thao tác: enterText(), clickSwitch(), selectDropdownOption()...
│   │   │   │       └── SwipePage.java     # Thao tác: swipeCardNext(), swipeVertical(), swipeUntilCardVisible()...
│   │   │   └── ios/                       # Page Objects dành cho nền tảng iOS
│   │   ├── reports/
│   │   │   └── ExtentReportManager.java   # Khởi tạo và quản lý log báo cáo HTML ExtentReports
│   │   ├── utilities/
│   │   │   └── ConfigReader.java          # Đọc dữ liệu cấu hình từ file config.properties
│   │   └── utils/                         # Helpers & System Tools
│   │       ├── AppiumDriverEx.java        # Driver provider bổ trợ cho standalone runner
│   │       ├── SwipeHelper.java           # Thực hiện vuốt chạm theo W3C standard
│   │       └── DeviceHelper.java          # Tương tác thiết bị: Wi-Fi toggle, backgrounding, quay video
│   └── test/
│       ├── java/
│       │   ├── listeners/
│       │   │   └── TestListener.java      # TestNG Listener tích hợp ExtentReports tự động
│       │   └── testcases/
│       │       └── android/
│       │           ├── LoginTest.java     # Test Suite chức năng đăng nhập
│       │           └── apilearning/       # BỘ 13 KỊCH BẢN KIỂM THỬ ĐÃ REFACTOR SANG POM
│       │               ├── clickOnElement.java              # Điều hướng & kiểm tra hiển thị Login Screen
│       │               ├── setValues.java                   # Nhập form credentials và submit
│       │               ├── GetValue.java                    # Lấy text tiêu đề dialog & assert 'Success'
│       │               ├── HandleDropdown.java              # Mở dropdown & chọn option 'webdriver.io is awesome'
│       │               ├── HandleToggleButton.java          # Bật/tắt Switch widget & kiểm tra đổi label
│       │               ├── HandleMultipleMatchedElements.java # Xử lý danh sách element trùng locator
│       │               ├── SwipeHorizontally.java           # Vuốt ngang Next / Previous card
│       │               ├── SwipeVertically.java             # Cuộn dọc màn hình lên / xuống
│       │               ├── SwipeUntil.java                  # Vuốt ngang có điều kiện tìm thẻ 'EXTENDABLE'
│       │               ├── TakingScreenshot.java            # Chụp ảnh màn hình Forms và lưu file
│       │               ├── RecordVideo.java                 # Ghi hình toàn bộ luồng chạy test ra file MP4
│       │               ├── PutAppInBackground.java          # Đưa app ra nền, bật tắt Wi-Fi, kích hoạt lại app
│       │               └── emailTxtBx.java                  # Kiểm tra nhập liệu trường email
│       └── resources/
│           ├── configs/
│           │   └── config.properties      # Cấu hình thiết bị, timeout, platform, capabilities
│           ├── logback.xml                # Cấu hình log console & file
│           └── testng.xml                 # Cấu hình Suite TestNG
├── target/                                # Thư mục sinh ra sau khi build & test
├── test-output/                           # Báo cáo ExtentReport.html & screenshots
├── videos/                                # Video quay lại phiên chạy test (.mp4)
├── android.wdio.native.app.v2.2.0.apk     # Ứng dụng WebdriverIO Demo App
├── pom.xml                                # Maven dependencies & surefire plugin
└── README.md                              # Tài liệu hướng dẫn dự án
```

---

## ⚙️ Cài Đặt & Cấu Hình (Setup Guide)

### 1. Yêu Cầu Môi Trường
- **JDK 17+** (đã cấu hình `JAVA_HOME`).
- **Node.js 18+** & **Appium 2.x / 3.x**:
  ```bash
  npm install -g appium
  appium driver install uiautomator2
  ```
- **Android SDK** (đã cấu hình `ANDROID_HOME`, `adb` trong PATH).
- **Emulator** (ví dụ: `Pixel_8_API_35`) hoặc thiết bị thật.

### 2. Cấu Hình Test (`config.properties`)
File cấu hình đặt tại `src/test/resources/configs/config.properties`:
```properties
target.platform=android
appium.server.url=http://127.0.0.1:4723
appium.auto.start.server=false

# Android Capabilities
android.platform.name=Android
android.device.name=Pixel_8_API_35
android.udid=emulator-5554
android.platform.version=15.0
android.automation.name=UiAutomator2
android.app.package=com.wdiodemoapp
android.app.activity=com.wdiodemoapp.MainActivity
android.app.wait.activity=*

# Timeouts
implicit.wait=10
explicit.wait=20
```

---

## 🚀 Hướng Dẫn Chạy Test (Execution)

### 1. Khởi Động Appium & Emulator
```bash
# Terminal 1: Bật Appium Server
appium --allow-cors

# Terminal 2: Bật Android Emulator (nếu chưa chạy)
emulator -avd Pixel_8_API_35
```

### 2. Chạy Bằng Maven CLI

```bash
# Chạy 1 test case cụ thể (Ví dụ: SwipeUntil)
mvn test -Dtest=testcases.android.apilearning.SwipeUntil

# Chạy test quay video màn hình
mvn test -Dtest=testcases.android.apilearning.RecordVideo

# Chạy test background app & toggle Wi-Fi
mvn test -Dtest=testcases.android.apilearning.PutAppInBackground

# Chạy toàn bộ 13 kịch bản trong gói apilearning
mvn test -Dtest="testcases.android.apilearning.*"

# Chạy theo TestNG XML Suite
mvn clean test
```

### 3. Chạy Trực Tiếp Trong IDE (IntelliJ / VS Code / Eclipse)
- **Cách 1**: Mở bất kỳ file test nào trong `testcases/android/apilearning/`, nhấn icon **Run** bên cạnh method `@Test`.
- **Cách 2**: Right-click vào file test và chọn **Run 'ClassName.main()'** (Code đã hỗ trợ fallback standalone runner).

---

## 📊 Báo Cáo Kiểm Thử (Reports & Artifacts)

1. **ExtentReports HTML**: Mở file `test-output/ExtentReport.html` trên trình duyệt để xem timeline, số bước pass/fail, log chi tiết từng step.
2. **Failure Screenshots**: Tự động lưu tại `test-output/screenshots/` khi có test case bị fail.
3. **Screen Recordings**: Video được lưu tự động tại thư mục `videos/` (được sinh ra bởi `RecordVideo.java`).
