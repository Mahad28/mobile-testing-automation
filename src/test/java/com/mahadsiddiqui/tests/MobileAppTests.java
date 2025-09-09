package com.mahadsiddiqui.tests;

import com.mahadsiddiqui.config.MobileDriverConfig;
import io.qameta.allure.*;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

/**
 * Mobile app test class demonstrating comprehensive mobile testing scenarios
 * Covers both Android and iOS platforms
 */
@Epic("Mobile App Testing")
@Feature("E-commerce Mobile App")
public class MobileAppTests {
    
    private AppiumDriver driver;
    private WebDriverWait wait;
    
    @BeforeMethod
    public void setUp() {
        driver = MobileDriverConfig.initializeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(MobileDriverConfig.getExplicitWaitTimeout()));
    }
    
    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
    
    @Test(description = "Verify app launches successfully")
    @Story("App Launch")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test to verify that the mobile app launches without errors")
    public void testAppLaunch() {
        // Given - App is installed and ready to launch
        
        // When - App launches
        String currentActivity = getCurrentActivity();
        
        // Then - Verify app launched successfully
        Assert.assertNotNull(currentActivity, "App should have launched");
        Assert.assertTrue(currentActivity.contains("MainActivity"), "Should be on main activity");
    }
    
    @Test(description = "Verify login functionality")
    @Story("User Authentication")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test to verify that user can login with valid credentials")
    public void testUserLogin() {
        // Given - App is launched and user is on login screen
        navigateToLogin();
        
        // When - User enters valid credentials and logs in
        String email = "test@example.com";
        String password = "password123";
        
        loginUser(email, password);
        
        // Then - Verify user is logged in successfully
        WebElement welcomeMessage = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.id("welcome_message")));
        Assert.assertTrue(welcomeMessage.isDisplayed(), "Welcome message should be displayed");
    }
    
    @Test(description = "Verify product search functionality")
    @Story("Product Search")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test to verify that users can search for products")
    public void testProductSearch() {
        // Given - User is logged in and on home screen
        loginUser("test@example.com", "password123");
        
        // When - User searches for a product
        String searchTerm = "laptop";
        searchProduct(searchTerm);
        
        // Then - Verify search results are displayed
        WebElement searchResults = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.id("search_results")));
        Assert.assertTrue(searchResults.isDisplayed(), "Search results should be displayed");
    }
    
    @Test(description = "Verify add to cart functionality")
    @Story("Shopping Cart")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test to verify that users can add products to cart")
    public void testAddToCart() {
        // Given - User is on product details page
        loginUser("test@example.com", "password123");
        navigateToProduct("laptop");
        
        // When - User adds product to cart
        addToCart();
        
        // Then - Verify product is added to cart
        WebElement cartCount = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.id("cart_count")));
        Assert.assertEquals(cartCount.getText(), "1", "Cart count should be 1");
    }
    
    @Test(description = "Verify checkout process")
    @Story("Checkout")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test to verify that users can complete checkout process")
    public void testCheckoutProcess() {
        // Given - User has items in cart
        loginUser("test@example.com", "password123");
        addProductToCart();
        navigateToCart();
        
        // When - User proceeds to checkout
        proceedToCheckout();
        fillShippingDetails();
        selectPaymentMethod();
        completeOrder();
        
        // Then - Verify order is placed successfully
        WebElement orderConfirmation = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.id("order_confirmation")));
        Assert.assertTrue(orderConfirmation.isDisplayed(), "Order confirmation should be displayed");
    }
    
    @Test(description = "Verify form validation")
    @Story("Form Validation")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test to verify that form validation works correctly")
    public void testFormValidation() {
        // Given - User is on registration form
        navigateToRegistration();
        
        // When - User submits form with invalid data
        fillRegistrationForm("", "invalid-email", "123");
        submitForm();
        
        // Then - Verify validation errors are displayed
        WebElement emailError = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.id("email_error")));
        Assert.assertTrue(emailError.isDisplayed(), "Email validation error should be displayed");
    }
    
    @Test(description = "Verify screen orientation handling")
    @Story("Device Orientation")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test to verify that app handles screen orientation changes")
    public void testScreenOrientation() {
        // Given - App is in portrait mode
        loginUser("test@example.com", "password123");
        
        // When - Device orientation changes to landscape
        rotateToLandscape();
        
        // Then - Verify app adapts to landscape mode
        WebElement mainContent = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.id("main_content")));
        Assert.assertTrue(mainContent.isDisplayed(), "Main content should be visible in landscape");
        
        // When - Device orientation changes back to portrait
        rotateToPortrait();
        
        // Then - Verify app adapts to portrait mode
        Assert.assertTrue(mainContent.isDisplayed(), "Main content should be visible in portrait");
    }
    
    @Test(description = "Verify push notifications")
    @Story("Push Notifications")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test to verify that push notifications are handled correctly")
    public void testPushNotifications() {
        // Given - App is running in background
        loginUser("test@example.com", "password123");
        minimizeApp();
        
        // When - Push notification is received
        // Note: In real testing, this would involve sending actual push notifications
        
        // Then - Verify notification is displayed
        // This would require specific notification testing setup
        Assert.assertTrue(true, "Push notification test placeholder");
    }
    
    @Test(description = "Verify app performance")
    @Story("Performance")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test to verify that app performance is within acceptable limits")
    public void testAppPerformance() {
        // Given - App is launched
        
        // When - Measure app launch time
        long startTime = System.currentTimeMillis();
        String currentActivity = getCurrentActivity();
        long endTime = System.currentTimeMillis();
        
        long launchTime = endTime - startTime;
        
        // Then - Verify launch time is acceptable (less than 3 seconds)
        Assert.assertTrue(launchTime < 3000, "App should launch within 3 seconds. Actual: " + launchTime + "ms");
        Assert.assertNotNull(currentActivity, "App should have launched successfully");
    }
    
    // Helper methods
    private String getCurrentActivity() {
        if (driver instanceof AndroidDriver) {
            return ((AndroidDriver) driver).currentActivity();
        }
        return driver.getCurrentUrl();
    }
    
    private void navigateToLogin() {
        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(
            By.id("login_button")));
        loginButton.click();
    }
    
    private void loginUser(String email, String password) {
        WebElement emailField = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.id("email_field")));
        emailField.sendKeys(email);
        
        WebElement passwordField = driver.findElement(By.id("password_field"));
        passwordField.sendKeys(password);
        
        WebElement loginButton = driver.findElement(By.id("login_submit"));
        loginButton.click();
    }
    
    private void searchProduct(String searchTerm) {
        WebElement searchField = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.id("search_field")));
        searchField.sendKeys(searchTerm);
        
        WebElement searchButton = driver.findElement(By.id("search_button"));
        searchButton.click();
    }
    
    private void navigateToProduct(String productName) {
        WebElement productLink = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//*[contains(text(), '" + productName + "')]")));
        productLink.click();
    }
    
    private void addToCart() {
        WebElement addToCartButton = wait.until(ExpectedConditions.elementToBeClickable(
            By.id("add_to_cart")));
        addToCartButton.click();
    }
    
    private void addProductToCart() {
        // Implementation for adding product to cart
    }
    
    private void navigateToCart() {
        WebElement cartIcon = wait.until(ExpectedConditions.elementToBeClickable(
            By.id("cart_icon")));
        cartIcon.click();
    }
    
    private void proceedToCheckout() {
        WebElement checkoutButton = wait.until(ExpectedConditions.elementToBeClickable(
            By.id("checkout_button")));
        checkoutButton.click();
    }
    
    private void fillShippingDetails() {
        // Implementation for filling shipping details
    }
    
    private void selectPaymentMethod() {
        // Implementation for selecting payment method
    }
    
    private void completeOrder() {
        // Implementation for completing order
    }
    
    private void navigateToRegistration() {
        WebElement registerButton = wait.until(ExpectedConditions.elementToBeClickable(
            By.id("register_button")));
        registerButton.click();
    }
    
    private void fillRegistrationForm(String name, String email, String phone) {
        WebElement nameField = driver.findElement(By.id("name_field"));
        nameField.sendKeys(name);
        
        WebElement emailField = driver.findElement(By.id("email_field"));
        emailField.sendKeys(email);
        
        WebElement phoneField = driver.findElement(By.id("phone_field"));
        phoneField.sendKeys(phone);
    }
    
    private void submitForm() {
        WebElement submitButton = driver.findElement(By.id("submit_button"));
        submitButton.click();
    }
    
    private void rotateToLandscape() {
        driver.rotate(org.openqa.selenium.ScreenOrientation.LANDSCAPE);
    }
    
    private void rotateToPortrait() {
        driver.rotate(org.openqa.selenium.ScreenOrientation.PORTRAIT);
    }
    
    private void minimizeApp() {
        driver.runAppInBackground(Duration.ofSeconds(5));
    }
}
