import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.AndroidKeyCode;
import org.junit.*;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.cert.Certificate;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by Diksha on 12/1/2016.
 */

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AmazonAutomation {

    AndroidDriver<AndroidElement> driver;
    private String newName= "Diksha";
    @Before
    public void initializeDriver() throws MalformedURLException {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("deviceName","3a8abcc8");
        caps.setCapability("platformName","Android");
        caps.setCapability("app","C:\\Users\\Diksha\\Downloads\\amazon.apk");
        driver = new AndroidDriver(new URL("http://0.0.0.0:5767/wd/hub"),caps);
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
    }

    // Testing for SignUp
    @Test
    public void atestSignUp(){

        ArrayList<User> users = loadUsers();
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        driver.findElementById("com.amazon.mShop.android.shopping:id/new_user").click();
        for (User usr: users) {
            ArrayList<AndroidElement> wb =
                    ( ArrayList<AndroidElement> ) driver.findElementsByClassName("android.widget.EditText");

            // Name  By: Id   Val: ap_customer_name   -- set the user Name
         //   String del = Keys.chord(Keys.CONTROL, "a") + Keys.DELETE;

            wb.get(0).clear();
            wb.get(0).sendKeys(usr.getStrName());

            // Email    By: Id   Val: ap_email   -- set the user email
            wb.get(1).clear();
            wb.get(1).sendKeys(usr.getStrEmail());

            // PWD    By: Id   Val: ap_password   -- set the password
            wb.get(2).clear();
            wb.get(2).sendKeys(usr.getStrPwd());

            driver.navigate().back();

            // Submit   By: Id  Val:continue, continue button
            driver.findElementByClassName("android.widget.Button").click();
            driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);

            if(usr.getBisValid() == "I"){
                Assert.assertEquals(true,validateSignUp());
            }else{
                Assert.assertEquals(false,validateSignUp());
            }
        }
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
    }

    @Test
    public void btestSignIn() {

        ArrayList<Credential> credentials = loadCredentials();
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        driver.findElementById("com.amazon.mShop.android.shopping:id/sign_in_button").click();

        for (Credential crd : credentials) {
            ArrayList<AndroidElement> wb =
                    (ArrayList<AndroidElement>) driver.findElementsByClassName("android.widget.EditText");

            String del = Keys.chord(Keys.CONTROL, "a") + Keys.DELETE;

            // Email  By: Id   Val: ap_customer_name
            wb.get(0).clear();
            wb.get(0).sendKeys(crd.getStrEmail());

            // pwd    By: Id   Val: ap_email
            wb.get(1).clear();
            wb.get(1).sendKeys(crd.getStrPassword());

            driver.navigate().back();

            //continue button
            driver.findElementByClassName("android.widget.Button").click();

            driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);

            if(crd.getBisValid() == "I"){
                Assert.assertEquals(true,validateSignUp());
            }else{
                Assert.assertEquals(false,validateSignUp());
            }
        }
    }

    @Test
    public void cyourOrders()
    {
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        Utils.signin("apendawle@horizon.csueastbay.edu","123456",driver);
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        AndroidElement element;
        ArrayList<AndroidElement> elements;
        HashMap<String,String> hash = new HashMap<String, String>();


        String csvFile = "data/searchtypes.csv";
        String line = "";
        String line1 = "";
        String cvsSplitBy = ",";

        try {

            BufferedReader br1 = new BufferedReader(new FileReader(csvFile));
            while ((line1 = br1.readLine()) != null) {
                String[] data1 = line1.split(cvsSplitBy);
                hash.put(data1[0],data1[1]);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);

        driver.findElement(By.id(hash.get("optionbutton"))).click();
        driver.findElement(By.name("Your Orders")).click();
        elements = ( ArrayList<AndroidElement> ) driver.findElements(By.className(hash.get("view")));
        if(elements.get(4).getAttribute("name").contains("No orders"));
        {
            System.out.println("test successfull: No Orders found");
        }

        element = getElementByContentDesc(driver,hash.get("view"), "Last 6 months Filter orders » ");
        element.click();
        element = getElementByContentDesc(driver,hash.get("radio"), "2016");
        element.click();
        element = getElementByContentDesc(driver, hash.get("widget"), "Apply");
        element.click();
        elements = ( ArrayList<AndroidElement> ) driver.findElements(By.className(hash.get("view")));
        if(elements.get(5).getAttribute("name").contains("Sennheiser HD 429 S Headphones"));
        {
            System.out.println("test successfull: Orders found");
        }
    }

    @Test
    public   void search() throws IOException
    {
        HashMap<String, String> hash = new HashMap <String, String>();
        HashMap<String, String> identifier = new HashMap <String, String>();
        String csvFile = "Data/searchtypesData.csv";
        String identifierfile = "Data/identifier.csv";
        String line = "";
        String line1 = "";
        String cvsSplitBy = ",";
        BufferedReader reader = null;

        try {

            BufferedReader br1 = new BufferedReader(new FileReader(identifierfile));
            while ((line1 = br1.readLine()) != null) {
                String[] data1 = line1.split(cvsSplitBy);
                identifier.put(data1[0],data1[1]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try  {
            BufferedReader br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] data = line.split(cvsSplitBy);
                hash.put(data[0],data[1]);
                // System.out.println("Country [string = " + data[0] + " , status=" + data[1] + "]");

            }

            driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);

            driver.findElement(By.id(identifier.get("skipbutton"))).click();

            driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);

            Iterator<Map.Entry<String, String>> iterator = hash.entrySet().iterator();
            while(iterator.hasNext()){
                Map.Entry<String, String> entry = iterator.next();

                driver.findElement(By.id(identifier.get("selectsearch"))).click();
                WebElement wl = driver.findElement(By.id(identifier.get("entertext")));
                wl.sendKeys(entry.getKey());
                driver.pressKeyCode(AndroidKeyCode.ENTER );

                driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);

                String elem = present(driver,identifier);

                if (elem.equals(entry.getValue()))
                {
                    System.out.println("Test case Passed");
                }
                else
                {
                    System.out.println("Test Case Failed");
                }
                driver.navigate().back();
                driver.navigate().back();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void navigationLinks() throws InterruptedException{

        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        Utils.signin("apendawle@horizon.csueastbay.edu","123456",driver);
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);

        //Test if the navigation bar opens on click
        List<AndroidElement> navLinks = driver.findElementsByClassName("android.widget.ImageView");
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        navLinks.get(0).click();
        driver.findElement(By.className("android.widget.RelativeLayout")).isDisplayed();


        //Test weather elements are loaded when each link in Navigation bar is clicked
        navLinks.get(0).click();
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        driver.findElementByName("Shop by Department").click();
        Thread.sleep(3000);
        List<AndroidElement> shopByDeptLinks = driver.findElementsByClassName("android.widget.ListView");
        shopByDeptLinks.get(0).isEnabled();


        navLinks.get(0).click();
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        driver.findElementByName("More from Amazon").click();
        Thread.sleep(3000);
        List<AndroidElement> moreFromAmazon = driver.findElementsByClassName("android.widget.RelativeLayout");
        moreFromAmazon.get(1).isEnabled();

        navLinks.get(0).click();
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        driver.findElementByName("AmazonFresh").click();
        Thread.sleep(3000);
        List<AndroidElement> amazonFresh = driver.findElementsByClassName("android.view.View");
        amazonFresh.get(0).isEnabled();

        navLinks.get(0).click();
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        driver.findElementByName("Your Orders").click();
        Thread.sleep(3000);
        List<AndroidElement> orders = driver.findElementsByClassName("android.view.View");
        orders.get(1).isEnabled();

        navLinks.get(0).click();
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        driver.findElementByName("Your Lists").click();
        Thread.sleep(3000);
        List<AndroidElement> lists = driver.findElementsByClassName("android.widget.LinearLayout");
        lists.get(0).isEnabled();

        navLinks.get(0).click();
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        driver.findElementByName("Your Account").click();
        Thread.sleep(3000);
        List<AndroidElement> account = driver.findElementsByClassName("android.view.View");
        account.get(0).isEnabled();

        navLinks.get(0).click();
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        driver.findElementByName("Gift Cards and Registry").click();
        Thread.sleep(3000);
        List<AndroidElement> giftCard = driver.findElementsByClassName("android.view.View");
        giftCard.get(0).isEnabled();


        navLinks.get(0).click();
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        Thread.sleep(3000);
        scroll();
        driver.findElementByName("Try Prime").click();
        Thread.sleep(3000);
        List<AndroidElement> prime = driver.findElementsByClassName("android.widget.Button");
        prime.get(0).isEnabled();

        navLinks.get(0).click();
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        Thread.sleep(3000);
        scroll();
        driver.findElementByName("Settings").click();
        Thread.sleep(3000);
        List<AndroidElement> settings = driver.findElementsByClassName("android.widget.TextView");
        settings.get(1).isEnabled();

        navLinks.get(0).click();
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        Thread.sleep(3000);
        scroll();
        driver.findElementByName("Customer Service").click();
        Thread.sleep(3000);
        List<AndroidElement> cService = driver.findElementsByClassName("android.view.View");
        cService.get(1).isEnabled();
        Thread.sleep(3000);

    }

    @Test
    public void editCustName() throws InterruptedException{

        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        Utils.signin("apendawle@horizon.csueastbay.edu","123456",driver);
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);

        List<AndroidElement> navLinks = driver.findElementsByClassName("android.widget.ImageView");
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        navLinks.get(0).click();
        driver.findElement(By.className("android.widget.RelativeLayout")).isDisplayed();


        //go to customer service in Navigation bar
        navLinks.get(0).click();
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        Thread.sleep(3000);
        scroll();
        driver.findElementByName("Customer Service").click();
        Thread.sleep(3000);
        List<AndroidElement> cService = driver.findElementsByClassName("android.view.View");
        cService.get(1).isEnabled();
        Thread.sleep(3000);

        //Go to Account Settings--> Edit UserName
        List<AndroidElement> settings = driver.findElementsByClassName("android.view.View");
        settings.get(9).click();
        Thread.sleep(3000);
        List<AndroidElement> settings1 = driver.findElementsByClassName("android.widget.Button");
        settings1.get(0).click();
        Thread.sleep(3000);
        driver.findElement(By.className("android.widget.EditText")).sendKeys(newName);
        Thread.sleep(3000);
        driver.findElement(By.className("android.widget.Button")).click();

        //To get the name in Navigation bar
        navLinks.get(0).click();
        if(! driver.findElement(By.className("android.widget.TextView")).getText().toString().equalsIgnoreCase("Hello, "+newName.toString())){
            Assert.fail("Name not updated in Navigation bar");
        }
        driver.quit();

    }

    //test user name after relaunching application
    @Test
    public void testNameAfterAppRestart() throws InterruptedException{

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(CapabilityType.BROWSER_NAME, "");
        capabilities.setCapability("deviceName", "YT911581W7");
        capabilities.setCapability("platformVersion", "5.0.1");
        capabilities.setCapability("platformName", "Android");

        Thread.sleep(5000);
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        List<AndroidElement> navLinks = driver.findElementsByClassName("android.widget.ImageView");
        navLinks.get(0).click();
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        Thread.sleep(3000);
        if(! driver.findElement(By.className("android.widget.TextView")).getText().toString().equalsIgnoreCase("Hello, "+newName.toString())){
            Assert.fail("Name not updated in Navigation bar");
        }

    }

    @Test
    public void validateNavigation(){

        String inputFile = "Data/input.csv";
        String line = "";
        String delimeter = ",";

        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        Utils.signin("apendawle@horizon.csueastbay.edu","123456",driver);
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);

        driver.findElement(By.id("com.amazon.mShop.android.shopping:id/web_home_shop_by_department_label")).click();
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        AndroidElement element = getElementByContentDesc(driver,"android.view.View","All Departments");
        if(element != null)
        {
            try
            {
                //Get input from CSV file
                BufferedReader buffer = new BufferedReader(new FileReader(inputFile));
                while((line = buffer.readLine()) != null)
                {
                    // Split on basis of ',' and store it in array
                    String[] array =line.split(delimeter);
                    //Click on Element
                    AndroidElement element1 = getElementByContentDesc(driver,"android.view.View",array[1]);
                    if (element1 !=null)
                    {
                        //If element found click on that element
                        element1.click();
                        AndroidElement element2 = getElementByContentDesc(driver,"android.view.View",array[1]);
                        if(element2 != null)
                        {
                            //If element found navigate back.
                            driver.navigate().back();
                        }

                    }
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void addSingleProduct() throws InterruptedException{
        String specificBrand = "";

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        BufferedReader br = null;
        String strLine = "";
        try {
            // Reading the test data from the file
            br = new BufferedReader(
                    new FileReader("Data/ProductList.txt"));
            // Read the file Line by Line till Null value is encountered

            // Loop conditional statement
            while ((strLine = br.readLine()) != null) {

                String[] arrSplit = strLine.split(":");
                int i = 0;
                specificBrand = arrSplit[i + 1];

                //adding to cart
                //clicking the search and entering data
                List<AndroidElement> Search = driver.findElementsByClassName("android.widget.EditText");
                Search.get(0).click();
                Search.get(0).sendKeys(arrSplit[i]);
                driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);

                //selecting auto suggest
                List<AndroidElement> AutoSuggest = driver.findElementsByClassName("android.widget.LinearLayout");
                AutoSuggest.get(1).click();
                driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);

                driver.findElement(By.name("Acer Aspire E 15 E5-575G-53VG Laptop, 15.6 Full HD (Intel Core i5, NVIDIA 940MX, 8GB DDR4, 256GB SSD, Windows 10)")).click();

                //Scrolling till finding the Add to cart option
                scroll();
                Thread.sleep(3000);
                scroll();
                Thread.sleep(3000);
                scroll();
                Thread.sleep(3000);
                scroll();
                Thread.sleep(3000);
                scroll();

                //clicking add to cart button
                List<AndroidElement> AddtoCart = driver.findElementsByClassName("android.widget.Button");
                driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);

                System.out.println(AddtoCart.size());
                AddtoCart.get(12).click();
                System.out.println("Right product is added to cart");

                //to press back button of device
                driver.navigate().back();
                driver.navigate().back();


            }

        } catch (FileNotFoundException e) {
            // Display error message - File was not found
            System.err.println("Unable to find the file");
        } catch (IOException e) {
            // Display error message in case of exception
            System.err.println("Unable to read the file");
        }


    }

    public static String present(AndroidDriver driver,HashMap<String,String> identifier) {
        try {
            driver.findElement(By.id(identifier.get("result"))).getText();
            return "V";
        } catch (Exception e) {
            return "I";
        }

    }

    public void scroll(){

        Dimension dimensions = driver.manage().window().getSize();
        Double screenHeightStart = dimensions.getHeight() * 0.5;
        int scrollStart = screenHeightStart.intValue();
        Double screenHeightEnd = dimensions.getHeight() * 0.2;
        int scrollEnd = screenHeightEnd.intValue();
        driver.swipe(0,scrollStart,0,scrollEnd,2000);

    }

    // Method to SLeep the thread
    public void waitAsec(){
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //Method to load the CSV to user object
    public ArrayList<User> loadUsers(){

        ArrayList<User> users = new ArrayList<User>();

        BufferedReader reader = null;
        String line = "";
        String cvsSplitBy = ",";
        String dataFile = "data/SignUpData.csv";
        try {
            reader = new BufferedReader(new java.io.FileReader(dataFile));
            while ((line = reader.readLine()) != null) {
                // use comma as separator
                String[] data = line.split(cvsSplitBy);
                users.add(new User(data[0],data[1],data[2],data[3]));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  users;
    }

    //Method to read CSV for sign in data
    public ArrayList<Credential>  loadCredentials(){

        ArrayList<Credential> credentials = new ArrayList<Credential>();

        BufferedReader reader = null;
        String line = "";
        String cvsSplitBy = ",";
        String dataFile = "data/SignInData.csv";
        try {
            reader = new BufferedReader(new java.io.FileReader(dataFile));
            while ((line = reader.readLine()) != null) {
                // use comma as separator
                String[] data = line.split(cvsSplitBy);
                System.out.println(data.length);
                credentials.add(new Credential(data[0],data[1],data[2]));
                System.out.println("Data is " + data[0].toString());
            }
            System.out.println(credentials.size());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return credentials;
    }


    public boolean validateSignUp(){
        return((driver.findElementsByName("There was a problem")).isEmpty());
    }

    public boolean validateSignIn(){
        return((driver.findElementsByName("There was a problem")).isEmpty());
    }

    static AndroidElement getElementByContentDesc(AndroidDriver driver, String className, String contentDescName)
    {
        ArrayList<AndroidElement> elements = ( ArrayList<AndroidElement> ) driver.findElements(By.className(className));
        for (AndroidElement element : elements)
        {
            String contentDesc = element.getAttribute("name");
            if (contentDesc.contains(contentDescName))
            {
                return element;
            }
        }
        return null;
    }

    @After
    public void killDriver(){
        driver.quit();
    }


}
