# Mobile Testing Automation Framework

A comprehensive mobile testing framework using Appium, TestNG, and Page Object Model for demonstrating professional mobile app automation capabilities across iOS and Android platforms.

## 🚀 Features

- **Appium Framework**: Cross-platform mobile testing for iOS and Android
- **Page Object Model**: Maintainable and scalable test architecture
- **TestNG Framework**: Advanced test execution, grouping, and reporting
- **Real Device Testing**: Support for physical devices and simulators/emulators
- **Cloud Testing**: Integration with BrowserStack, Sauce Labs, and AWS Device Farm
- **Visual Testing**: Screenshot comparison and visual regression testing
- **Performance Testing**: App performance monitoring and optimization
- **CI/CD Integration**: GitHub Actions and Jenkins pipeline ready

## 🛠️ Technologies Used

- Java 11+
- Appium 2.0+
- TestNG 7.7.0
- Maven 3.8+
- Selenium WebDriver
- Allure Reporting
- ExtentReports
- iOS Simulator
- Android Emulator

## 📁 Project Structure

```
mobile-testing-automation/
├── src/
│   ├── main/java/
│   │   ├── pages/               # Page Object Model classes
│   │   ├── utils/               # Utility classes
│   │   ├── config/              # Configuration management
│   │   └── drivers/             # Appium driver management
│   └── test/java/
│       ├── tests/               # Test classes
│       ├── data/                # Test data files
│       ├── listeners/           # TestNG listeners
│       └── utils/               # Test utilities
├── src/test/resources/
│   ├── apps/                    # Mobile app files (.apk, .ipa)
│   ├── config/                  # Configuration files
│   └── testng.xml              # TestNG configuration
├── reports/                     # Test reports
├── screenshots/                 # Screenshot captures
└── pom.xml                     # Maven configuration
```

## 🚀 Quick Start

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd mobile-testing-automation
   ```

2. **Install dependencies**
   ```bash
   mvn clean install
   ```

3. **Start Appium server**
   ```bash
   appium server
   ```

4. **Run tests**
   ```bash
   mvn test
   ```

5. **Run specific platform tests**
   ```bash
   mvn test -Dplatform=android
   mvn test -Dplatform=ios
   ```

## 📱 Test Coverage

- **E-commerce Mobile App**: Product browsing, cart management, checkout
- **User Authentication**: Login, registration, password reset
- **Form Validation**: Input validation, error handling
- **Navigation Testing**: Tab navigation, back button, menu interactions
- **Performance Testing**: App launch time, memory usage, battery consumption
- **Cross-Platform Testing**: iOS and Android compatibility
- **Device-Specific Testing**: Different screen sizes and orientations

## 🔧 Configuration

Update `src/test/resources/config/mobile-config.properties`:

```properties
# Platform Configuration
platform.name=android
platform.version=13.0
device.name=Pixel_7
app.package=com.example.app
app.activity=MainActivity

# Appium Configuration
appium.server.url=http://localhost:4723
appium.server.port=4723
implicit.wait=10
explicit.wait=20

# App Configuration
app.path=src/test/resources/apps/app.apk
app.wait.activity=5000
```

## 📊 Reports

- **Allure Reports**: Detailed test execution with screenshots
- **ExtentReports**: HTML reports with test categorization
- **TestNG Reports**: Standard TestNG reporting
- **Video Recording**: Test execution videos for debugging

## 🎯 Test Scenarios

### Mobile App Testing
- App installation and launch
- User registration and login
- Product search and filtering
- Shopping cart operations
- Checkout process
- Push notifications
- Deep linking

### Device Testing
- Screen orientation changes
- Network connectivity testing
- Background/foreground transitions
- Memory and performance testing
- Battery usage optimization

## 👨‍💻 Author

**Mahad Siddiqui** - Senior Test Automation Engineer
- GitHub: [@Mahad28](https://github.com/Mahad28)
- Upwork: [Profile](https://www.upwork.com/freelancers/~0184717814212a8366)
- Email: mahadsiddiqui@gmail.com
