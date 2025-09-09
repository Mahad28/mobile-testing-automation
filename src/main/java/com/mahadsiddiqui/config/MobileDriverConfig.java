package com.mahadsiddiqui.config;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.Duration;
import java.util.Properties;

/**
 * Mobile driver configuration and management class
 * Handles Appium driver setup for iOS and Android platforms
 */
public class MobileDriverConfig {
    
    private static Properties properties;
    private static AppiumDriver driver;
    private static AppiumDriverLocalService service;
    
    static {
        loadProperties();
    }
    
    /**
     * Load configuration properties
     */
    private static void loadProperties() {
        properties = new Properties();
        try (InputStream input = new FileInputStream("src/test/resources/config/mobile-config.properties")) {
            properties.load(input);
        } catch (IOException e) {
            System.err.println("Error loading configuration file: " + e.getMessage());
            setDefaultProperties();
        }
    }
    
    /**
     * Set default properties if config file is not found
     */
    private static void setDefaultProperties() {
        properties.setProperty("platform.name", "android");
        properties.setProperty("platform.version", "13.0");
        properties.setProperty("device.name", "Pixel_7");
        properties.setProperty("appium.server.url", "http://localhost:4723");
        properties.setProperty("appium.server.port", "4723");
        properties.setProperty("implicit.wait", "10");
        properties.setProperty("explicit.wait", "20");
        properties.setProperty("app.path", "src/test/resources/apps/app.apk");
    }
    
    /**
     * Get property value by key
     */
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
    
    /**
     * Get property value with default
     */
    public static String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }
    
    /**
     * Get integer property value
     */
    public static int getIntProperty(String key) {
        return Integer.parseInt(getProperty(key, "0"));
    }
    
    /**
     * Get boolean property value
     */
    public static boolean getBooleanProperty(String key) {
        return Boolean.parseBoolean(getProperty(key, "false"));
    }
    
    /**
     * Initialize Appium driver based on platform
     */
    public static AppiumDriver initializeDriver() {
        String platform = getProperty("platform.name", "android").toLowerCase();
        
        try {
            if (platform.equals("android")) {
                driver = createAndroidDriver();
            } else if (platform.equals("ios")) {
                driver = createIOSDriver();
            } else {
                throw new IllegalArgumentException("Unsupported platform: " + platform);
            }
            
            // Set implicit wait
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(getIntProperty("implicit.wait")));
            
            return driver;
        } catch (Exception e) {
            System.err.println("Failed to initialize driver: " + e.getMessage());
            throw new RuntimeException("Driver initialization failed", e);
        }
    }
    
    /**
     * Create Android driver
     */
    private static AppiumDriver createAndroidDriver() throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        
        // Basic capabilities
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("platformVersion", getProperty("platform.version", "13.0"));
        capabilities.setCapability("deviceName", getProperty("device.name", "Pixel_7"));
        capabilities.setCapability("automationName", "UiAutomator2");
        
        // App capabilities
        capabilities.setCapability("app", getProperty("app.path"));
        capabilities.setCapability("appPackage", getProperty("app.package"));
        capabilities.setCapability("appActivity", getProperty("app.activity"));
        
        // Additional capabilities
        capabilities.setCapability("noReset", getBooleanProperty("no.reset"));
        capabilities.setCapability("fullReset", getBooleanProperty("full.reset"));
        capabilities.setCapability("newCommandTimeout", getIntProperty("command.timeout"));
        capabilities.setCapability("autoGrantPermissions", true);
        capabilities.setCapability("unicodeKeyboard", true);
        capabilities.setCapability("resetKeyboard", true);
        
        // Performance capabilities
        capabilities.setCapability("skipDeviceInitialization", false);
        capabilities.setCapability("skipServerInstallation", false);
        
        URL serverUrl = new URL(getProperty("appium.server.url", "http://localhost:4723"));
        return new AndroidDriver(serverUrl, capabilities);
    }
    
    /**
     * Create iOS driver
     */
    private static AppiumDriver createIOSDriver() throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        
        // Basic capabilities
        capabilities.setCapability("platformName", "iOS");
        capabilities.setCapability("platformVersion", getProperty("platform.version", "16.0"));
        capabilities.setCapability("deviceName", getProperty("device.name", "iPhone 14"));
        capabilities.setCapability("automationName", "XCUITest");
        
        // App capabilities
        capabilities.setCapability("bundleId", getProperty("bundle.id"));
        capabilities.setCapability("app", getProperty("app.path"));
        
        // Additional capabilities
        capabilities.setCapability("noReset", getBooleanProperty("no.reset"));
        capabilities.setCapability("fullReset", getBooleanProperty("full.reset"));
        capabilities.setCapability("newCommandTimeout", getIntProperty("command.timeout"));
        capabilities.setCapability("autoAcceptAlerts", true);
        capabilities.setCapability("autoDismissAlerts", false);
        
        // Performance capabilities
        capabilities.setCapability("skipDeviceInitialization", false);
        capabilities.setCapability("skipServerInstallation", false);
        
        URL serverUrl = new URL(getProperty("appium.server.url", "http://localhost:4723"));
        return new IOSDriver(serverUrl, capabilities);
    }
    
    /**
     * Start Appium server
     */
    public static void startAppiumServer() {
        AppiumServiceBuilder builder = new AppiumServiceBuilder();
        builder.withIPAddress("127.0.0.1");
        builder.usingPort(getIntProperty("appium.server.port"));
        builder.withTimeout(Duration.ofSeconds(60));
        
        service = builder.build();
        service.start();
        
        System.out.println("Appium server started on: " + service.getUrl());
    }
    
    /**
     * Stop Appium server
     */
    public static void stopAppiumServer() {
        if (service != null && service.isRunning()) {
            service.stop();
            System.out.println("Appium server stopped");
        }
    }
    
    /**
     * Get current driver instance
     */
    public static AppiumDriver getDriver() {
        if (driver == null) {
            driver = initializeDriver();
        }
        return driver;
    }
    
    /**
     * Quit driver and clean up
     */
    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
    
    /**
     * Get platform name
     */
    public static String getPlatformName() {
        return getProperty("platform.name", "android");
    }
    
    /**
     * Get device name
     */
    public static String getDeviceName() {
        return getProperty("device.name", "Pixel_7");
    }
    
    /**
     * Get app package
     */
    public static String getAppPackage() {
        return getProperty("app.package");
    }
    
    /**
     * Get app activity
     */
    public static String getAppActivity() {
        return getProperty("app.activity");
    }
    
    /**
     * Get bundle ID for iOS
     */
    public static String getBundleId() {
        return getProperty("bundle.id");
    }
    
    /**
     * Get explicit wait timeout
     */
    public static int getExplicitWaitTimeout() {
        return getIntProperty("explicit.wait");
    }
    
    /**
     * Get implicit wait timeout
     */
    public static int getImplicitWaitTimeout() {
        return getIntProperty("implicit.wait");
    }
}
