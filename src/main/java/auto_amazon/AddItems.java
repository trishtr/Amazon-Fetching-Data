package auto_amazon;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class AddItems {
	
	public static void main(String args[]) throws InterruptedException {
		
		WebDriver driver; 
		WebDriverManager.chromedriver().setup();
		
		driver = new ChromeDriver();
		
		driver.get("https://www.amazon.com/");
		
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		
		//find products with keyword
		driver.findElement(By.xpath("//input[@aria-label='Search']")).sendKeys("women shoes");
		
		driver.findElement(By.xpath("//input[@id='nav-search-submit-button']")).click();
		
		WebDriverWait wait = new WebDriverWait(driver, 30);
		
		//choose the third product in list
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@data-component-type='s-search-result'][3]"))).click();
		
		//select size of product
		WebElement select_size = driver.findElement(By.xpath("//select[@name='dropdown_selected_size_name']"));
		
		Select select = new Select(select_size);
		select.selectByVisibleText("6");
		
		//add to cart
		driver.findElement(By.xpath("//input[@value='Add to Cart']")).click();
		
		Thread.sleep(3000);
		
		driver.quit();
		
		
		
		
	}
}
