# ASX Online Selenium Automation Tests

This project contains Selenium automation tests for the ASX Online website (https://www.asxonline.com/) using Java and WebDriver Manager for multiple browser support.


```bash
# Run tests using testng.xml configuration
mvn test -DsuiteXmlFile=testng.xml

# Run with detailed output
mvn test -DsuiteXmlFile=testng.xml -Dtestng.show.stack.frames=true

# Run specific test suite
mvn test -DsuiteXmlFile=testng.xml -Dtestng.suite.name="ASX Test Suite"
```


```bash
# Run all tests (Maven will automatically detect TestNG)
mvn test

# Run specific test class
mvn test -Dtest=SimpleASXOnlineTest

# Run with parallel execution (if configured)
mvn test -Dparallel=true -DthreadCount=2
```