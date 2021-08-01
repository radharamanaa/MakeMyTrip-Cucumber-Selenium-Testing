package com.zemoso;

import org.openqa.selenium.WebDriver;

public abstract class BasePagePO {
    protected WebDriver driver;
    public BasePagePO(WebDriver driver) {
        this.driver = driver;
    }
}
