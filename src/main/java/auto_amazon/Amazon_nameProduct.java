package auto_amazon;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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

public class Amazon_nameProduct {

	public static void main(String[] args) throws IOException {
		WebDriver driver;
		WebDriverManager.chromedriver().setup();
		
		driver = new ChromeDriver();
		driver.get("https://www.amazon.com/");
		driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
		
		//click bestseller btn		
		driver.findElement(By.xpath("//a[contains(text(),'Best Sellers')]")).click();
		
		//click see more of clothing sellers
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@aria-label='Best Sellers in Baby - See More']"))).click();
		
		List<WebElement> products_name= driver.findElements(By.xpath("//div[@class='p13n-sc-truncated']"));
		
		List <String> productlst = new ArrayList<String>();
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("Top 20 Best Sellers");
		int rowno = 0;
		
		for (int i = 0; i <=20; i ++)
		{
			String product = products_name.get(i).getText();
			productlst.add(product);
		}
		for (String pro : productlst)
		{
			XSSFRow row = sheet.createRow(rowno++);
			row.createCell(0).setCellValue(pro);
			
		}
		FileOutputStream fos = new FileOutputStream(".\\datafiles\\20bestsellers_2.xlsx");
		workbook.write(fos);
		fos.close();
		System.out.println("File is successfully created");
		
		driver.quit();
		
	}

}
