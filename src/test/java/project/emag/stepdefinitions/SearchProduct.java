package project.emag.stepdefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import org.apache.poi.ss.usermodel.*;
import org.openqa.selenium.*;
import org.testng.Assert;

import project.emag.ExcelInitializer;
import project.emag.SeleniumInitializer;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class SearchProduct {

    private final WebDriver driver;
    private Workbook workbook;
    private String nameOfProductToBeSearched;
    private static final String filePath = "/home/razvanpustea/Desktop/excel";
    private static final String fileName = "produs-emag.xlsx";

    public SearchProduct() {
        try {
            ExcelInitializer excelInitializer = new ExcelInitializer(SearchProduct.filePath, SearchProduct.fileName);
            workbook = excelInitializer.getWorkbook();
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        driver = SeleniumInitializer.getDriver();
    }

    @Given("user is on eMAG's homepage")
    public void navigateToHomepage() {
        final String homepageUrl = "https://emag.ro";
        driver.get(homepageUrl);
    }

    @When("he enters the product from Excel file in the search bar and presses Enter")
    public void enterProduct() {
        final String sheetName = "TestSheet";
        Sheet sheet = workbook.getSheet(sheetName);
        int rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();

        for (int i = sheet.getFirstRowNum() + 1; i <= rowCount; i++) {
            WebElement searchBar = driver.findElement(By.xpath("//input[@type='search']"));
            String product = sheet.getRow(i).getCell(0).getStringCellValue();
            nameOfProductToBeSearched = product;

            searchBar.sendKeys(product, Keys.ENTER);
        }
    }

    @Then("he should see the product with some details or a message suggesting that the product isn't available")
    public void displayFinalResult() {
        try {
            String result = driver.findElement(By.xpath("//a[contains(text(), '" + nameOfProductToBeSearched + "')]")).getText();
            Assert.assertTrue(result.contains(nameOfProductToBeSearched));
        } catch (NoSuchElementException ex) {
            Assert.fail("The element couldn't be found");
        }
    }

    @And("the Excel file should be updated with the name and prices of related products")
    public void insertPriceInExcelFile() throws IOException {
        final String sheetName = "Results";

        Sheet results= workbook.getSheet(sheetName);

        Cell firstHeader = results.createRow(0).createCell(0);
        firstHeader.setCellValue("Nume produs");
        styleCell(firstHeader);

        Cell secondHeader = results.getRow(0).createCell(1);
        secondHeader.setCellValue("Pret");
        styleCell(secondHeader);

        List<WebElement> products = driver.findElements(By.xpath("//a[@class='card-v2-title semibold mrg-btm-xxs js-product-url' and contains(text(), '" + nameOfProductToBeSearched + "')]"));
        List<WebElement> prices = driver.findElements(By.xpath("" +
                "//a[contains(text(), '" + nameOfProductToBeSearched + "')]//parent::div[@class='pad-hrz-xs']//parent::div[@class='card-v2-info']//following-sibling::div[@class='card-v2-content']//div[@class='card-v2-pricing']//p[@class='product-new-price']"));

        FileOutputStream outputStream = new FileOutputStream(SearchProduct.filePath + "/" + SearchProduct.fileName);

        for (int i = 0; i < products.size(); i++) {
            Cell firstCell = results.createRow(i + 1).createCell(0);
            firstCell.setCellValue(products.get(i).getText());
            styleCell(firstCell);

            Cell secondCell = results.getRow(i + 1).createCell(1);
            secondCell.setCellValue(prices.get(i).getText());
            styleCell(secondCell);
        }

        CellStyle cellStyle = firstHeader.getCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        cellStyle.setFont(font);
        firstHeader.setCellStyle(cellStyle);
        secondHeader.setCellStyle(cellStyle);

        styleSheet(results);

        workbook.write(outputStream);
        outputStream.close();
    }

    private void styleSheet(Sheet sheet) {
        sheet.autoSizeColumn(0, true);
        sheet.autoSizeColumn(1, true);
        sheet.setHorizontallyCenter(true);
        sheet.setFitToPage(true);
    }

    private void styleCell(Cell cell) {
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setFont(setFont());

        cell.setCellStyle(cellStyle);
    }

    private Font setFont() {
        Font font = workbook.createFont();
        font.setFontName("Times New Roman");

        return font;
    }
}
