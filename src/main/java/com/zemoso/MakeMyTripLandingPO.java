package com.zemoso;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.concurrent.TimeUnit;

public class MakeMyTripLandingPO extends BasePagePO {
    By roundTripSelector = By.cssSelector("div[data-cy='flightSW'] ul.fswTabs li[data-cy='roundTrip']");
    By oneWaySelector = By.cssSelector("div[data-cy='flightSW'] ul.fswTabs li[data-cy='oneWayTrip']");
    By fromCitySelector = By.id("fromCity");
    By toCitySelector = By.id("toCity");

    public boolean isRoundtripSelected(){
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        return driver.findElement(roundTripSelector).getAttribute("class").equals("selected");
    }
    public boolean isOneWaySelected(){
        return driver.findElement(roundTripSelector).isSelected();
    }
    public void selectOneWayTrip(){
        WebElement oneWayTrip = driver.findElement(oneWaySelector);
        oneWayTrip.click();
    }
    public void selectRoundTrip(){
        WebElement roundTripElement = driver.findElement(roundTripSelector);
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].click()",roundTripElement);
    }
    public String getFromCity(){
        return driver.findElement(fromCitySelector).getAttribute("value");
    }
    public String getToCity(){
        return driver.findElement(toCitySelector).getAttribute("value");
    }

    public MakeMyTripLandingPO(WebDriver driver) {
        super(driver);
    }
}
