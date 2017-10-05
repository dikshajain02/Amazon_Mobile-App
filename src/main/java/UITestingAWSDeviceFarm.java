import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import org.aspectj.lang.annotation.Before;
import org.junit.After;
import org.junit.Test;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Diksha on 11/29/2016.
 */
public class UITestingAWSDeviceFarm {

    AndroidDriver<AndroidElement> driver;

    @org.junit.Before
    public void setUp() throws MalformedURLException
    {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("deviceName","87aefefa");
        caps.setCapability("platformName","Android");
        caps.setCapability("app","C:\\Users\\Diksha\\Downloads\\amazon.apk");
        driver = new AndroidDriver<AndroidElement>(new URL("http://0.0.0.0:5767/wd/hub"),caps);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    //This test to be run only on AWS - Device Farm
    @Test
    public void UICompatibilityTesting() throws Exception{

        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();
        String dateformat = dateFormat.format(date);

        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        driver.findElementById("com.amazon.mShop.android.shopping:id/skip_sign_in_button").click();
        takeScreenshot("homescreen"+dateformat);

        date = new Date();
        dateformat = dateFormat.format(date);

        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        List<AndroidElement> Navigation = driver.findElementsByClassName("android.widget.ImageView");
        Navigation.get(0).click();
        takeScreenshot("navigationdrawer"+dateformat);

        date = new Date();
        dateformat = dateFormat.format(date);

        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        driver.findElementByName("Home").click();
        takeScreenshot("navigationhome"+dateformat);

        date = new Date();
        dateformat = dateFormat.format(date);

        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        Navigation.get(0).click();
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        driver.findElementByName("Your Recently Viewed Items").click();
        takeScreenshot("navigationrecentlyviewed"+dateformat);

        date = new Date();
        dateformat = dateFormat.format(date);

        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        Navigation.get(0).click();
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        driver.findElementByName("Shop by Department").click();
        takeScreenshot("navigationshopdept"+dateformat);

        date = new Date();
        dateformat = dateFormat.format(date);

        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        Navigation.get(0).click();
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        driver.findElementByName("Today's Deals").click();
        takeScreenshot("navigationtodaysdeals"+dateformat);

        date = new Date();
        dateformat = dateFormat.format(date);

        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        Navigation.get(0).click();
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        driver.findElementByName("Gift Cards").click();
        takeScreenshot("navigationgiftscards"+dateformat);

        date = new Date();
        dateformat = dateFormat.format(date);

        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        Navigation.get(0).click();
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        driver.findElementByName("Your Wish List").click();
        takeScreenshot("navigationwishlist"+dateformat);

    }

    public boolean takeScreenshot(final String name) {
        String screenshotDirectory = System.getProperty("appium.screenshots.dir", System.getProperty("java.io.tmpdir", ""));
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        return screenshot.renameTo(new File(screenshotDirectory, String.format("%s.png", name)));
    }

    @After
    public void tearDown() throws Exception{
        driver.quit();
    }
}
