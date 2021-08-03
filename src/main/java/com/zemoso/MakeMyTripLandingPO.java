package com.zemoso;

import org.openqa.selenium.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class MakeMyTripLandingPO extends BasePagePO {
    private final String multipleCityOptionsStr = "ul[role='listbox'] div.makeFlex div[class='calc60'] p.font14";
    By roundTripSelector = By.cssSelector("div[data-cy='flightSW'] ul.fswTabs li[data-cy='roundTrip']");
    By oneWaySelector = By.cssSelector("div[data-cy='flightSW'] ul.fswTabs li[data-cy='oneWayTrip']");
    By fromCityClickSelector = By.id("fromCity");
    By fromCityInputSelector = By.cssSelector("input.react-autosuggest__input." +
            "react-autosuggest__input--open[placeholder='From']");
    By fromCityTextEntry =
            By.cssSelector("input.react-autosuggest__input.react-autosuggest__input--open[placeholder='From']");
    By toCityTextEntry = By.cssSelector("input.react-autosuggest__input.react-autosuggest__input--open[placeholder='To']");
    By toCityCLickSelector = By.id("toCity");
    By returnDateSelector = By.cssSelector("label[for='return']");
    By cityDropDownListSeltr = By.cssSelector("div#react-autowhatever-1 ul[role='listbox'] li");
    By departureDate = By.cssSelector("label[for='departure']");
    By monthsInDatePicker = By.cssSelector("div.DayPicker-Month[role='grid']");
    By verifyMonthHeading = By.cssSelector("div.DayPicker-Caption>div");
    By dayPickerWeeks = By.cssSelector("div.DayPicker-Body div.DayPicker-Week");
    By integerDayInsideWeek = By.cssSelector("div.DayPicker-Day div.dateInnerCell p");
    By dayInDateVisibleField = By.cssSelector("span.font30.latoBlack");
    By monthInDateVisibleField = By.cssSelector("span:nth-child(2)");
    By fullModal = By.cssSelector("div[data-cy='outsideModal']");

    public void removeModals() {
        final List<WebElement> elements = driver.findElements(fullModal);
        if (!elements.isEmpty()) {
            elements.stream().findFirst().get().click();
        }
    }

    private boolean selectDateFromPicker(LocalDate date) {
        int dayOfMonth = date.getDayOfMonth();
        String month = date.getMonth().name();
        Utility.waitTillSelectorClickable(driver, monthsInDatePicker, 10);
        List<WebElement> twoMonthsDisplayed = driver.findElements(monthsInDatePicker);
        for(WebElement element: twoMonthsDisplayed){
            final String text = element.findElement(verifyMonthHeading).getText();
            if (text.toLowerCase(Locale.ROOT).contains(month.toLowerCase(Locale.ROOT))) {
                List<WebElement> weeksElements = element.findElements(dayPickerWeeks);
                for (WebElement weekElement : weeksElements) {
                    Optional<WebElement> dateToClick = weekElement.findElements(integerDayInsideWeek)
                            .stream().filter(item -> {
                                return item.getText().trim().equals(dayOfMonth+"");}).findFirst();
                    if(dateToClick.isPresent()){
                        dateToClick.get().click();
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public void clickOnDepartureDate(){
        Utility.waitTillSelectorClickable(driver, departureDate, 10);
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
                .getText().equals(date.getDayOfMonth()+"");
    }
    public boolean isReturnDateValue(LocalDate date){
        return driver.findElement(returnDateSelector).findElement(dayInDateVisibleField)
                .getText().trim().equals(date.getDayOfMonth()+"");
    }

    public void selectReturnDate(LocalDate date){
        selectDateFromPicker(date);
    }

    public void enterFromCity(String city){
        Utility.waitTillSelectorClickable(driver, fromCityClickSelector, 10 );
        driver.findElement(fromCityClickSelector).click();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.findElement(fromCityTextEntry).sendKeys(city);
    }
    public void enterToCity(String city){
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        Utility.waitTillSelectorClickable(driver, toCityTextEntry, 10 );
        driver.findElement(toCityTextEntry).sendKeys(city);
    }
    public boolean isSelectFromCityInDropDownSuccessful(String city){
        return isCitySelectionSuccessful(city);
    }

    private boolean isCitySelectionSuccessful(String city) {
        Utility.waitTillSelectorClickable(driver, cityDropDownListSeltr, 10);
        List<WebElement> cities = driver.findElements(cityDropDownListSeltr);
        Utility.waitTillSelectorClickable(driver, multipleCityOptionsStr, 10);
        Optional<WebElement> cityElement = cities.stream().filter(
                item -> item.findElement(
                        By.cssSelector(multipleCityOptionsStr))
                        .getText().contains(city)).findFirst();
        if(!cityElement.isPresent()) return false;
        else{
            cityElement.get().click();
            return true;
        }
    }

    public boolean isSelectToCityFromDropDownSuccessful(String toCity){
        return isCitySelectionSuccessful(toCity);
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
    public String getFromCityCLickSelectorStr(){
        Utility.waitTillSelectorClickable(driver, fromCityClickSelector, 10);
        return driver.findElement(fromCityClickSelector).getAttribute("value");
    }
    public String getToCityStr(){
        Utility.waitTillSelectorClickable(driver, toCityCLickSelector, 10);
        return driver.findElement(toCityCLickSelector).getAttribute("value");
    }

    public MakeMyTripLandingPO(WebDriver driver) {
        super(driver);
    }
}
