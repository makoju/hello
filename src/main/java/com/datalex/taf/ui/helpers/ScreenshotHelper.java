package com.datalex.taf.ui.helpers;

import com.datalex.taf.core.loggers.TAFLogger;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ScreenshotHelper class
 *
 * @author Aleksandar Vulovic
 */
public class ScreenshotHelper {

    private static org.apache.logging.log4j.Logger mLOG = LogManager.getLogger(ScreenshotHelper.class);
    private static final String WORK_DIR = "./work/";

    public void takeScreenshot(WebDriver driver, ITestResult result) throws IOException {

        if (result.getStatus() == ITestResult.FAILURE) {
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("MMddyyyyHHmmss");
                Date date = new Date();
                File screenDir = new File(WORK_DIR);
                if (!screenDir.exists()) {
                    FileUtils.forceMkdir(screenDir);
                }
                ImageIO.write(takeScreenshotFullPage(driver), "PNG",
                        new File(WORK_DIR + result.getMethod().getMethodName() + dateFormat.format(date) + ".png"));
                mLOG.info("Screenshot taken!");
                attachFileToReport(WORK_DIR + result.getMethod().getMethodName() + dateFormat.format(date) + ".png", "attached");
            } catch (IOException e) {
                mLOG.error("Exception during taking a screenshot " + e.getMessage());
                mLOG.error(ExceptionUtils.getStackTrace(e));
            }
        }
    }

    /**
     * Method to take a screenshot of entire webpage using AShot library
     *
     * @param driver WebDriver instance to use
     * @return BufferedImage or null if exception occurs during taking a screenshot
     */
    private BufferedImage takeScreenshotFullPage(WebDriver driver) {
        try {
            ru.yandex.qatools.ashot.Screenshot screenshot = new AShot()
                    .shootingStrategy(ShootingStrategies.viewportPasting(500))
                    .takeScreenshot(driver);
            return screenshot.getImage();
        } catch (Exception e) {
            mLOG.error("Error during taking a screenshot with AShot");
            mLOG.error(ExceptionUtils.getStackTrace(e));
            return null;
        }
    }

    private void attachFileToReport(String filePath, String message) {
        org.apache.logging.log4j.LogManager.getLogger(TAFLogger.class).info("RP_MESSAGE#FILE#{}#{}", filePath, message);
    }
}