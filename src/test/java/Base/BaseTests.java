package Base;

import Pages.HomePage;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;

import java.util.Map;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;


public class BaseTests {
    private WebDriver driver;
    protected HomePage homePage;
    ChromeOptions options = new ChromeOptions();

    @BeforeClass
    public void setUp() {
        String browser = "chrome";
        switch (browser.toLowerCase()) {
            case "firefox":
                driver = new FirefoxDriver();
                break;
            case "edge":
                driver = new EdgeDriver();
                break;
            case "chrome":
                // Removes "Chrome is being controlled..." message
                options.setExperimentalOption("excludeSwitches", Arrays.asList("enable-automation"));
                options.setExperimentalOption("useAutomationExtension", false);
                // Optional: Disable infobars and extensions
                options.addArguments("--disable-infobars");
                options.addArguments("--disable-extensions");
                options.addArguments("--disable-save-password-bubble");
                options.addArguments("--disable-password-manager");
                options.addArguments("--disable-features=PasswordManager");

                driver = new ChromeDriver(options);
                break;
        }
        driver.manage().window().maximize();
        homePage = new HomePage(driver);
        driver.get("https://www.amazon.eg/?language=en_AE");
    }

//    @AfterClass
//    public void tearDown(){
//        driver.quit();
//    }

    @DataProvider
    public static Object[][] ReadDataFromExcelSheet() throws IOException {

        String excelPath = "src/test/java/Address/Address.xlsx";

        DataFormatter formatter = new DataFormatter();

        FileInputStream fileInputStream = new FileInputStream(excelPath);
        XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
        XSSFSheet worksheet = workbook.getSheetAt(0);
        XSSFRow Row = worksheet.getRow(0);

        int RowNum = worksheet.getPhysicalNumberOfRows();// count my number of Rows
        int ColNum = Row.getLastCellNum();// get last ColNum

        Object Data[][] = new Object[RowNum - 1][ColNum]; // pass my  count data in array

        for (int i = 0; i < RowNum - 1; i++) //Loop work for Rows
        {
            XSSFRow row = worksheet.getRow(i + 1);
            for (int j = 0; j < ColNum; j++) //Loop work for colNum
            {
                if (row == null)
                    Data[i][j] = "";
                else {
                    XSSFCell cell = row.getCell(j);
                    if (cell == null)
                        Data[i][j] = ""; //if it gets Null value it pass no data
                    else {
                        String value = formatter.formatCellValue(cell);
                        Data[i][j] = value; //This formatter get my all values as string i.e integer, float all type data value
                    }
                }
            }
        }
        return Data;
    }
}
