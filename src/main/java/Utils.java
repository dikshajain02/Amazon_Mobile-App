import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import org.openqa.selenium.Keys;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Created by Diksha on 12/1/2016.
 */
public class Utils {

    public static void signin( String uname, String pwd, AndroidDriver<AndroidElement> driver){

        driver.findElementById("com.amazon.mShop.android.shopping:id/sign_in_button").click();
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        ArrayList<AndroidElement> wb =
                (ArrayList<AndroidElement>) driver.findElementsByClassName("android.widget.EditText");

       // String del = Keys.chord(Keys.CONTROL, "a") + Keys.DELETE;

        // Email  By: Id   Val: ap_customer_name
        wb.get(0).clear();
        wb.get(0).sendKeys(uname);

        // pwd    By: Id   Val: ap_email
        wb.get(1).clear();
        wb.get(1).sendKeys(pwd);

        driver.navigate().back();

        //continue button
        driver.findElementByClassName("android.widget.Button").click();

        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);

    }
}
