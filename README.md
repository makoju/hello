# TAF UI 2.0 - Page Object Model Framework - TestNG - POC

# PRECONDITIONS
## Lombok plugin
* Go to File > Settings > Plugins.
* Click on Browse repositories...
* Search for Lombok Plugin.
* Click on Install plugin.
* Restart IntelliJ IDEA.
## Annotation processing in IntelliJ IDEA
* Open Preferences (Ctrl + Alt + S)
* Build, Execution, Deployment > Compiler > Annotation Processors
* Enable annotation processing

# Examples of usage
To run smoke test suite
```bash
gradle task smoke
```

To run regression test suite
```bash
gradle task regression
```

# Properties of smoke or regression task
To configure the TestNG test task you can choose to set the following properties using:

* `-DprojectIp` : Specified the projectIP of the server
* `-DcsvFile` : Specified the CSV file with test data

We can run tests with projectIp property:
```bash
gradle task smoke -DprojectIp="http://10.160.7.108"
```
```bash
gradle task regression -DprojectIp="http://10.160.7.108"
```

# TestNG test suites
* Smoke test suite `/src/test/resources/suites/smoke.xml`
* Regression test suite `/src/test/resources/suites/regression.xml`

# Test classes, local helpers and test resources
* All test classes should be located under `/src/test/java/com.datalex.taf.ui` in smoke or regression package
* All page objects should be located under `/src/main/java/com.datalex.taf.ui.po`
* All local helpers should be located under `/src/main/java/com.datalex.taf.ui.helpers`
* All test resources should be located under `/src/test/resources/`
* All temp files should be in `/work/`

# Setting new project:
* Change project name in `settings.gradle`
* Change `rootProject.name = BRU-PageObject-POC` to match your project name
* Change description in `build.gradle`
* Change `description = "BRU_PageObject-POC"` to match team name like `description = "JBU_PO_TestAutomation"`

# In `project.properties` configuration file change following:
* Change `projectName=PAL`
* Change `customerCode=103`
* Change `pos=PALCUI_SABRE`
* Change `projectIp=http://palpst2.datalex.com`

# In `taf.properties` configuration file change following:
* Change `browserType=firefox`
* Change `gridHost=http://10.153.30.103` - if running locally set to `gridHost=http://localhost`
* Change `gridPort=4444`

# Running UI Tests locally:
* Download JAR [selenium-server-standalone-3.4.0.jar](http://selenium-release.storage.googleapis.com/3.4/selenium-server-standalone-3.4.0.jar)
* Download geckodriver 32-bit [geckodriver 32-bit](https://github.com/mozilla/geckodriver/releases/download/v0.16.1/geckodriver-v0.16.1-win32.zip)
* OR
* Download geckodriver 64-bit [geckodriver 64-bit](https://github.com/mozilla/geckodriver/releases/download/v0.16.1/geckodriver-v0.16.1-win64.zip)
* geckodriver.exe should be located in 'C:\drivers\geckodriver.exe'
* OR if you are using Linux should be located in '/home/user_name/drivers/geckodriver'
* Run following command in command line to start Selenium hub:
```bash
java -jar selenium-server-standalone-3.4.0.jar -role hub
```
* Run following command in command line to start Selenium node on windows:
```bash
java -Dwebdriver.gecko.driver="C:\drivers\geckodriver.exe" -Dwebdriver.firefox.marionette=true -jar selenium-server-standalone-3.4.0.jar -role webdriver
```
* OR on Linux:
```bash
java -Dwebdriver.gecko.driver="/home/user_name/drivers/geckodriver" -Dwebdriver.firefox.marionette=true -jar selenium-server-standalone-3.4.0.jar -role webdriver
```
* NOTE: If you have problems running tests. Please download latest geckodriver and update browser to latest!