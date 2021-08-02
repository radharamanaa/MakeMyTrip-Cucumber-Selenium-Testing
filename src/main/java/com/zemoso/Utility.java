package com.zemoso;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;

public class Utility {
    public synchronized static void waitTillSelectorVisible(WebDriver driver, String selector, int seconds) {
        FluentWait<WebDriver> wait = new FluentWait<>(driver);
        wait.withTimeout(Duration.ofSeconds(seconds));
        wait.pollingEvery(Duration.ofMillis(500));
        wait.ignoring(NoSuchElementException.class);
        WebElement selectVis = driver.findElement(By.cssSelector(selector));
        wait.until(ExpectedConditions.elementToBeClickable(selectVis));
    }
}
