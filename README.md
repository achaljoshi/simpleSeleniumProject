# ASX Online Selenium Automation Tests

This project contains Selenium automation tests for the ASX Online website (https://www.asxonline.com/) using Java and WebDriver Manager for multiple browser support.

## Project Structure

```
simpleSeleniumProject/
├── pom.xml                                    # Maven configuration
├── src/
│   └── test/
│       └── java/
│           └── com/
│               └── asx/
│                   └── test/
│                       ├── ASXOnlineTest.java        # Complex test with parameterized tests
│                       ├── SimpleASXOnlineTest.java  # Single test with configurable browser
│                       └── SimpleTestRunner.java     # Basic test runner
├── screenshots/                               # Generated screenshots (created during test execution)
└── README.md                                  # This file
```

## Prerequisites

- Java 11 or higher
- Maven 3.6 or higher
- Internet connection for downloading dependencies and accessing ASX Online

## Features

- **Configurable Browser Support**: Single test that can run on Chrome, Firefox, or Edge browsers
- **WebDriver Manager**: Automatically downloads and manages browser drivers
- **Screenshot Capture**: Takes screenshots during test execution for verification
- **TestNG**: Modern testing framework with comprehensive annotations and lifecycle management
- **Explicit and Implicit Waits**: Uses WebDriverWait and implicit waits for reliable element interactions
- **Session Management**: Proper WebDriver session configuration with timeouts

## Test Scenarios

1. **Navigation Test**: Verifies successful navigation to ASX Online website
2. **Page Title Verification**: Confirms page title contains "ASX Online"
3. **Main Heading Check**: Validates presence of "Welcome to ASX Online" heading
4. **Search Functionality**: Verifies search form is present and functional
5. **Sign-in Link**: Confirms sign-in functionality is accessible
6. **Content Verification**: Checks for key content elements on the page
7. **Screenshot Capture**: Takes screenshots for manual verification

## TestNG Lifecycle

The project uses TestNG annotations for proper test lifecycle management:

- **`@BeforeClass`**: Runs once before all test methods (class initialization)
- **`@BeforeMethod`**: Runs before each test method (WebDriver setup)
- **`@Test`**: The actual test execution
- **`@AfterMethod`**: Runs after each test method (WebDriver cleanup)
- **`@AfterClass`**: Runs once after all test methods (class cleanup)

## Running the Tests

### Option 1: Run tests using TestNG XML file (Recommended)

```bash
# Run tests using testng.xml configuration
mvn test -DsuiteXmlFile=testng.xml

# Run with detailed output
mvn test -DsuiteXmlFile=testng.xml -Dtestng.show.stack.frames=true

# Run specific test suite
mvn test -DsuiteXmlFile=testng.xml -Dtestng.suite.name="ASX Online Test Suite"
```

### Option 2: Run tests directly with Maven

```bash
# Run all tests (Maven will automatically detect TestNG)
mvn test

# Run specific test class
mvn test -Dtest=SimpleASXOnlineTest

# Run with parallel execution (if configured)
mvn test -Dparallel=true -DthreadCount=2
```

### Option 3: Run tests using TestNG command line

```bash
# Run tests directly with TestNG (requires TestNG to be in PATH)
java -cp "target/test-classes:target/dependency/*" org.testng.TestNG testng.xml

# Run with custom options
java -cp "target/test-classes:target/dependency/*" org.testng.TestNG -parallel methods -threadcount 2 testng.xml
```

### Option 4: Change browser in code

To test with a different browser, modify the `BROWSER` variable in `SimpleASXOnlineTest.java`:

```java
// Change this line to test different browsers
private static final String BROWSER = "firefox"; // Options: "chrome", "firefox", "edge"
```

### Option 5: Run from IDE

1. Open the project in your preferred Java IDE (IntelliJ IDEA, Eclipse, VS Code)
2. Navigate to `src/test/java/com/asx/test/`
3. Right-click on `SimpleASXOnlineTest.java` and select "Run Tests"
4. Or right-click on `testng.xml` and select "Run as TestNG Suite"

## Browser Support

The project supports the following browsers:

- **Chrome**: Uses ChromeDriver with optimized options
- **Firefox**: Uses GeckoDriver with Firefox-specific configurations
- **Edge**: Uses EdgeDriver for Microsoft Edge browser

## Configuration

### TestNG Configuration

The project uses `testng.xml` for test suite configuration:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<suite name="ASX Online Test Suite" verbose="1" parallel="false" thread-count="1">
    <test name="ASX Online Website Tests">
        <classes>
            <class name="com.asx.test.SimpleASXOnlineTest">
                <methods>
                    <include name="testASXOnlineWebsite"/>
                </methods>
            </class>
        </classes>
    </test>
</suite>
```

**Configuration Options:**
- `parallel="false"`: Tests run sequentially (set to "methods" for parallel execution)
- `thread-count="1"`: Number of threads for parallel execution
- `verbose="1"`: Detailed output level

### Browser Options

Each browser has specific configurations:

- **Chrome**: No-sandbox mode, disabled GPU, optimized memory usage
- **Firefox**: Custom window dimensions, optimized performance
- **Edge**: Default Edge configurations

### Timeouts

- **Implicit Wait**: 10 seconds for WebDriver session
- **Explicit Wait**: 10 seconds for element interactions
- **Page Load Timeout**: 10 seconds for page loading
- **Script Timeout**: 10 seconds for JavaScript execution

## Screenshots

Tests automatically capture screenshots and save them to the `screenshots/` directory:

- Screenshots are named with browser type and timestamp
- Directory is created automatically if it doesn't exist
- Screenshots help verify test execution and page state

## TestNG Reports

TestNG automatically generates detailed HTML reports:

- **Location**: `target/surefire-reports/` directory
- **Main Report**: `index.html` - Overview of all test results
- **Detailed Reports**: Individual HTML files for each test class
- **Console Output**: Detailed execution logs in terminal

### Viewing Reports

```bash
# Open the main report in your browser
open target/surefire-reports/index.html

# Or navigate to the reports directory
cd target/surefire-reports/
```

## Advanced TestNG Execution

### Parallel Execution

To run tests in parallel, modify `testng.xml`:

```xml
<suite name="ASX Online Test Suite" parallel="methods" thread-count="3">
    <!-- Tests will run in parallel with 3 threads -->
</suite>
```

### Test Groups

Organize tests into groups for selective execution:

```java
@Test(groups = {"smoke", "regression"})
public void testASXOnlineWebsite() { ... }
```

Then run specific groups:
```bash
mvn test -Dgroups=smoke
mvn test -Dgroups=regression
```

### Test Parameters

Pass parameters to tests:

```java
@Test
@Parameters({"browser", "url"})
public void testWithParameters(String browser, String url) { ... }
```

### Test Dependencies

Set test execution order:

```java
@Test(dependsOnMethods = {"setupTest"})
public void testExecution() { ... }
```

## Troubleshooting

### Common Issues

1. **Driver Download Issues**: Ensure internet connection and firewall settings allow downloads
2. **Browser Compatibility**: Make sure you have the latest browser versions installed
3. **Memory Issues**: Chrome tests may require more memory; adjust JVM settings if needed

### JVM Memory Settings

If you encounter memory issues, increase JVM memory:

```bash
mvn test -DargLine="-Xmx2g -Xms1g"
```

### Headless Mode

To run tests in headless mode (no browser UI), modify the browser options in the test classes:

```java
// For Chrome
options.addArguments("--headless");

// For Firefox
options.addArguments("--headless");

// For Edge
options.addArguments("--headless");
```

## Dependencies

- **Selenium WebDriver**: 4.15.0 - Core automation framework
- **WebDriver Manager**: 5.6.2 - Automatic driver management
- **TestNG**: 7.7.1 - Testing framework with comprehensive lifecycle management
- **Maven Surefire**: 3.1.2 - Test execution plugin

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests for new functionality
5. Submit a pull request

## License

This project is for educational and testing purposes. Please ensure compliance with ASX Online's terms of service when running tests.

## Support

For issues related to:
- **Selenium**: Check [Selenium documentation](https://selenium.dev/)
- **WebDriver Manager**: Visit [WebDriver Manager GitHub](https://github.com/bonigarcia/webdrivermanager)
- **JUnit 5**: Refer to [JUnit 5 documentation](https://junit.org/junit5/)