import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Diksha on 11/30/2016.
 */
public class SignUpAutomation {

    AndroidDriver<AndroidElement> driver;

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






}
