package config;

import io.cucumber.java.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class Configuration {
    private static boolean isInitialized = false;
    private static Properties props;
    private static WebDriver driver;
    public static WebDriver getDriver(){
        return driver;
    }
    public static String getBaseURL(){
        return props.getProperty("baseUrl");
    }
    public static String getLandingPageTitle(){
        return props.getProperty("pageTitle");
    }

    public Configuration() {
        if(isInitialized) return;//since this constructor is called multiple times
        Path resourceDir = Paths.get("src","test", "resources");
        String absPath = resourceDir.toFile().getAbsolutePath();
        File f = new File(absPath + "/application.properties");
        FileReader fileReader = null;
        props = new Properties();
        try {
            fileReader = new FileReader(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        try {
            props.load(fileReader);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        try {
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.setProperty("webdriver.chrome.driver", props.getProperty("driverPath"));
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Before
    public void beforeAll(){
        if(!isInitialized){
            isInitialized = true;
            Runtime.getRuntime().addShutdownHook(new Thread(() -> driver.quit()));
        }
    }
}
