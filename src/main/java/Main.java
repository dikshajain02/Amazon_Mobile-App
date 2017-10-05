import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Diksha on 11/29/2016.
 */
public class Main {

    public static AndroidDriver driver;
    public static ArrayList<User> users;

    public static void main(String[] args) throws MalformedURLException {

        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("deviceName","3a8abcc8");
        caps.setCapability("platformName","Android");
        caps.setCapability("app","C:\\Users\\Diksha\\Downloads\\amazon.apk");
        driver = new AndroidDriver(new URL("http://0.0.0.0:5767/wd/hub"),caps);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        users = new ArrayList<User>();
        loadUsers();
        SignUp();
    }

    public static void loadUsers(){

        BufferedReader reader = null;
        String line = "";
        String cvsSplitBy = ",";
        String dataFile = "C:\\Users\\Diksha\\IdeaProjects\\amazomTesting\\src\\main\\Data\\SignUpData.csv";
        try {
            reader = new BufferedReader(new java.io.FileReader(dataFile));

            while ((line = reader.readLine()) != null) {

                // use comma as separator
                String[] data = line.split(cvsSplitBy);
                System.out.println(data.length);
                users.add(new User(data[0],data[1],data[2],data[3]));
                System.out.println("Data is " + data[0].toString());
            }

            System.out.println(users.size());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void SignUp(){

        //get New to Amazon and click on it -- by : resourceId  val: com.amazon.mShop.android.shopping:id/new_user

        driver.findElementById("com.amazon.mShop.android.shopping:id/new_user").click();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (User usr: users) {


            ArrayList<AndroidElement> wb =
                    ( ArrayList<AndroidElement> ) driver.findElementsByClassName("android.widget.EditText");
            // Name  By: Id   Val: ap_customer_name
            wb.get(0).sendKeys(usr.getStrName());

            // Email    By: Id   Val: ap_email
            wb.get(1).sendKeys(usr.getStrEmail());

            // PWD    By: Id   Val: ap_password
            wb.get(2).sendKeys(usr.getStrPwd());

            driver.navigate().back();
            // Submit   By: Id  Val:continue

            //continue button
            driver.findElementByClassName("android.widget.Button").click();

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("hello");
        }
    }
}
