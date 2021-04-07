import com.sun.xml.internal.ws.api.server.WebServiceContextDelegate;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class test_class {
    private WebDriver webDriver;
    private String pathToChromeDriver = "D:\\Program Files\\Eclipse\\Selenium\\chromedriver.exe";
    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", pathToChromeDriver);
        webDriver = new ChromeDriver();
        webDriver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        webDriver.get("https://rasp.yandex.ru/");
    }
    @After
    public void closeConn() {
        if (webDriver != null)
        webDriver.quit();
    }
    @Test
    public void testCase1() {
        WebElement webElement  = webDriver.findElement(By.className("Slogan"));
        Assert.assertEquals("Расписание пригородного и междугородного транспорта", webElement.getText());
    }
    @Test
    public void testCase2() {
        WebElement webElement  = webDriver.findElement(By.id("from"));
        webElement.click();
        webElement.sendKeys("Уфа");
        webElement  = webDriver.findElement(By.id("to"));
        webElement.click();
        webElement.sendKeys("Кумертау");
        webElement  = webDriver.findElement(By.id("when"));
        webElement.click();
        webElement.clear();
        webElement.sendKeys("12.04.2021");
        webElement  = webDriver.findElement(By.className("SearchForm__submit"));
        webElement.click();
        //wait
        List<WebElement> webElements = webDriver.findElement(By.className("SearchSegments")).findElements(By.className("SearchSegment"));
        ArrayList<String> times_string = new ArrayList<String>();
        String price = "";
        for (WebElement webElementRasp:webElements)
        {
            String type = webElementRasp.findElement(By.className("TransportIcon")).getAttribute("aria-label");
            if (type.equals("электричка")){
                List<WebElement> times = webElementRasp.findElement(By.className("SearchSegment__times")).findElements(By.className("SearchSegment__dateTime"));
                for (WebElement time:times) {
                    times_string.add(time.getText());
                }
                price = webElementRasp.findElement(By.className("SegmentPrices")).findElement(By.className("Price")).getText();
            }


        }
        Assert.assertEquals(times_string.get(0),"18:39");
        Assert.assertEquals(times_string.get(1),"22:29");
        Assert.assertEquals(price,"576 Р");
    }
    @Test
    public void testCase3(){
        WebDriverWait wait = new WebDriverWait(webDriver,10);
        WebElement webElement = webDriver.findElement(By.className("ChangeCity")).findElement(By.className("Button"));
        webElement.click();
        //slow
        WebElement popup_content = webDriver.findElement(By.className("Popup__content"));
        webElement = popup_content.findElement(By.name("cityFrom"));
        webElement.sendKeys("Уфа");
        webElement = popup_content.findElement(By.className("Button2"));
        webElement.click();
        //wait
        //webDriver.findElements(By.className("StationsGroup")).get(1).findElement(By.className("StationsGroupTitle")).getText()
        //wait.until(ExpectedConditions.presenceOfNestedElementsLocatedBy (By.className("Stations"), By.className("StationsGroup")));
        //wait.until(ExpectedConditions.presenceOfNestedElementsLocatedBy (By.className("StationsGroup"), By.className("StationsGroup")));
        while (webDriver.findElements(By.className("StationsGroup")).size()<3){}
        List <WebElement> stationsGroups = webDriver.findElements(By.className("StationsGroup"));
        for (WebElement station_group:stationsGroups) {
            if (station_group.findElement(By.className("StationsGroupTitle")).getText().equals("Расписание поездов")) {
                //wait
                boolean brk = false;
                while (station_group.findElements(By.className("Station")).size()<2){}
                for (WebElement station : station_group.findElements(By.className("Station"))) {
                    if (station.getText().equals("Вокзал Уфа")) {
                        station.click();
                        brk = true;
                        break;
                    }
                }
                if (brk) break;
            }
        }
        //WebElement table_railway_stations = webDriver.findElement(By.className("StationsGroup StationsGroup_type_train")).findElement(By.className("StationsGroup__list"));
        //wait.until(ExpectedConditions.presenceOfNestedElementLocatedBy(table_railway_stations, By.className("Station")));
        /*
        List<WebElement> stations = table_railway_stations.findElements(By.className("Station"));
        for (WebElement station : stations) {
            String vokzal = station.findElement(By.className("Station__title")).getText();
            if (vokzal.equals("Вокзал Уфа")) {
                station.click();
                break;
            }
        }
        */
        //wait
        while (webDriver.findElement(By.className("PageSubtypeTabs")).findElements(By.className("PageSubtypeTabs__tab")).size()<4)
        {
        }
        List<WebElement> subtupe_tabs = webDriver.findElement(By.className("PageSubtypeTabs")).findElements(By.className("PageSubtypeTabs__tab"));
        for (WebElement subtupe_tab : subtupe_tabs) {
            String name = subtupe_tab.getText();
            if (name.equals("Электрички")) {
                subtupe_tab.findElement(By.className("Link")).click();
                break;
            }
        }
        //wait
        while (webDriver.findElement(By.className("DirectionTabs")).findElements(By.className("DirectionTabs__directionContainer")).size()<8)
        {
        }
        List<WebElement> DirectionTabs = webDriver.findElement(By.className("DirectionTabs")).findElements(By.className("DirectionTabs__directionContainer"));
        for (WebElement DirectionTab : DirectionTabs) {
            String name = DirectionTab.findElement(By.className("Link")).getText();
            if (name.equals("Все направления")) {
                DirectionTab.findElement(By.className("Link")).click();
                break;
            }
        }
        //wait
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan (By.className("StationTable__tableRow"), 2));
        wait.until(ExpectedConditions.presenceOfNestedElementsLocatedBy (By.className("StationTable"), By.className("Link")));
        //WebElement load = webDriver.findElement(By.className("StationDatePicker")).findElement(By.className("Icon"));
        //load = webDriver.findElement(By.className("StationTable__rows")).findElement(By.className("StationTable__tableRow"));
        List<WebElement> RadioGroup2 = webDriver.findElement(By.className("StationDatePicker")).findElements(By.className("RadioGroup2__button"));
        WebElement icon = RadioGroup2.get(3).findElement(By.className("Icon"));
        wait.until(ExpectedConditions.elementToBeClickable(icon));
        icon.click();
        /*
        for (WebElement button : RadioGroup2) {
            List<WebElement> DataSwitcher = button.findElements(By.className("Icon"));
            if (DataSwitcher.size()==1) {
                DataSwitcher.get(0).click();
                break;
            }
        }
         */
        //wait
        wait.until(ExpectedConditions.presenceOfNestedElementsLocatedBy (By.className("StationTable"), By.className("StationTable__tableRow")));
        popup_content = webDriver.findElement(By.className("Popup__content"));
        List <WebElement> months = popup_content.findElements(By.className("Month"));
        for (WebElement month:months) {
            WebElement monthName = month.findElement(By.className("Month__name"));
            if (monthName.getText().equals("АПРЕЛЬ"))
            {
                List<WebElement> calendar = month.findElements(By.className("CalendarDayLink"));
                for (WebElement day:calendar)
                {
                    if (day.getAttribute("data-date").equals("2021-04-12")) {
                        wait.until(ExpectedConditions.elementToBeClickable(day));
                        day.click();
                        break;
                    }
                }
                break;
            }
        }
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan (By.className("StationTable__tableRow"), 10));
        wait.until(ExpectedConditions.presenceOfNestedElementsLocatedBy (By.className("StationTable"), By.className("Link")));
        //wait
        WebElement stationTable = webDriver.findElement(By.className("StationTable"));
        wait.until(ExpectedConditions.presenceOfNestedElementsLocatedBy (By.className("StationTable"), By.className("Link")));
        List <WebElement> table_rows = stationTable.findElements(By.className("StationTable__tableRow"));
        boolean found = false;
        for (WebElement stationTableRow:table_rows)
        {
            if (stationTableRow.findElement(By.className("StationTable__threadName")).findElement(By.className("Link")).getText().equals("Уфа — Кумертау")) {
                found = true;
                break;
            }
        }

        Assert.assertTrue(found);
        //test commit
    }
}
