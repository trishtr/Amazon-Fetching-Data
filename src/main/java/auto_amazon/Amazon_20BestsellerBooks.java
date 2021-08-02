package auto_amazon;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Amazon_20BestsellerBooks {

	public static void main(String args[]) throws IOException {
		
		WebDriver driver;
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		
		driver.get("https://www.amazon.com/");
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		
		WebDriverWait wait = new WebDriverWait(driver, 30);
		
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='nav-xshop']//a[contains(text(),'Books')][1]"))).click();
		
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@aria-label='See more Best sellers in Books']"))).click();
		
		List<WebElement> books_name = driver.findElements(By.xpath("//div[@class='p13n-sc-truncated']"));
	
		List<WebElement> authors = driver.findElements(By.xpath("//div[@class='a-row a-size-small']"));
	
		HashMap<String, String> map = new HashMap<String, String>();
		
		for(int i = 0; i <= 20; i++)
		{
			for(int j = 0; j <= 40; j+=2)
			{
				String name = books_name.get(i).getText();
				String author = authors.get(j).getText();
				map.put(name, author);
			}
		}
		
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("Book names and authors");
		
		int rownum = 0;
			
		for(Map.Entry entry: map.entrySet())
		{
			XSSFRow row = sheet.createRow(rownum++);
			row.createCell(0).setCellValue((String)entry.getKey());
			row.createCell(1).setCellValue((String)entry.getValue());
		}
		FileOutputStream fo = new FileOutputStream(".\\datafiles\\20books.xlsx");
		workbook.write(fo);
		fo.close();
		System.out.println("Excel file is created");
		
		driver.quit();
		
	}
}
