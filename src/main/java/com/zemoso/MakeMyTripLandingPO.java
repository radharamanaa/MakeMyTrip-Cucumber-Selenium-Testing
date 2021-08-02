package com.zemoso;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class MakeMyTripLandingPO extends BasePagePO {
    By roundTripSelector = By.cssSelector("div[data-cy='flightSW'] ul.fswTabs li[data-cy='roundTrip']");
    By oneWaySelector = By.cssSelector("div[data-cy='flightSW'] ul.fswTabs li[data-cy='oneWayTrip']");
    By fromCitySelector;
    final String fromCityDOMId = "label[for='fromCity']";
    {
        fromCitySelector = By.cssSelector(fromCityDOMId);
    }

    By fromCityTextEntry =
            By.cssSelector("input.react-autosuggest__input.react-autosuggest__input--open[placeholder='From']");
    By toCityTextEntry = By.cssSelector("input.react-autosuggest__input.react-autosuggest__input--open[placeholder='To']");
    By toCitySelector = By.id("toCity");
    By returnDateSelector = By.cssSelector("label[for='return']");
    By cityDropDownList = By.cssSelector("div#react-autowhatever-1 ul[role='listbox'] li");
    By departureDate = By.cssSelector("label[for='departure']");
    By monthsInDatePicker = By.cssSelector("div.DayPicker-Month[role='grid']");
    By verifyMonthHeading = By.cssSelector("div.DayPicker-Caption>div");
    By dayPickerWeeks = By.cssSelector("div.DayPicker-Body div.DayPicker-Week");
    By integerDayInsideWeek = By.cssSelector("div.DayPicker-Day div.dateInnerCell p");
    By dayInDateVisibleField = By.cssSelector("span.font30.latoBlack");
    By monthInDateVisibleField = By.cssSelector("span:nth-child(2)");
    public void clickOnDepartureDate(){
        driver.findElement(departureDate).click();
    }
    public void clickOnReturnDate(){
        driver.findElement(returnDateSelector).click();
    }
    public void selectADepartureDate(LocalDate date){
        selectDateFromPicker(date);
    }
    public boolean isDepartureDateValue(LocalDate date){
        return driver.findElement(departureDate).findElement(dayInDateVisibleField)
                .getText().equals(date.getDayOfMonth());
    }
    public boolean isReturnDateValue(LocalDate date){
        return driver.findElement(returnDateSelector).findElement(dayInDateVisibleField)
                .getText().equals(date.getDayOfMonth());
    }
    public void selectReturnDate(LocalDate date){
        selectDateFromPicker(date);
    }

    private boolean selectDateFromPicker(LocalDate date) {
        int dayOfMonth = date.getDayOfMonth();
        String month = date.getMonth().name();
        List<WebElement> twoMonthsDisplayed = driver.findElements(monthsInDatePicker);
        for(WebElement element: twoMonthsDisplayed){
            if (element.findElement(verifyMonthHeading).getText().contains(month)) {
                List<WebElement> weeksElements = element.findElements(dayPickerWeeks);
                for (WebElement weekElement : weeksElements) {
                    Optional<WebElement> dateToClick = weekElement.findElements(integerDayInsideWeek)
                            .stream().filter(item -> item.getText().equals(dayOfMonth)).findFirst();
                    if(dateToClick.isPresent()){
                        dateToClick.get().click();
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void enterFromCity(String city){
//        Utility.waitTillSelectorVisible(driver, fromCityDOMId, 5);
        driver.findElement(fromCitySelector).click();
        driver.findElement(fromCityTextEntry).sendKeys(city);
    }
    public void enterToCity(String city){
        driver.findElement(toCityTextEntry).sendKeys(city);
    }
    public boolean isSelectFromCityInDropDownSuccessful(String city){
        List<WebElement> cities = driver.findElements(cityDropDownList);
        Optional<WebElement> cityElement = cities.stream().filter(
                item -> item.findElement(
                        By.cssSelector("ul[role='listbox'] div.makeFlex div[class='calc60'] p.font14"))
                        .getText().contains(city)).findFirst();
        if(!cityElement.isPresent()) return false;
        else{
            cityElement.get().click();
            return true;
        }
    }
    public boolean isSelectToCityFromDropDownSuccessful(String toCity){
        return isSelectFromCityInDropDownSuccessful(toCity);
    }
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
    public String getFromCityDOMId(){
        return driver.findElement(fromCitySelector).getAttribute("value");
    }
    public String getToCity(){
        return driver.findElement(toCitySelector).getAttribute("value");
    }

    public MakeMyTripLandingPO(WebDriver driver) {
        super(driver);
    }
}
