package com.devops.calicos;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.web.reactive.server.WebTestClient;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CalicosApplicationTests {

	@LocalServerPort
	private int port;

	@Test
	void contextLoads() {

		WebDriver driver = new ChromeDriver();
		driver.get(String.format("http://localhost:%d/calci",port));
		WebElement calciPad = driver.findElement(By.className("calculator"));
		assert calciPad.isEnabled();
		System.out.println("Web page loaded");
		driver.quit();
	}

	@Test
	void calculateEquations() {
		String[] equations = {"128+256*512/1024","ln(2.71828)+3^2","21!","sqrt(1024)","2..3"};

		String[] answers = {"256","9.9999993","5.1090942e+19","32","Error"};

		WebDriver driver = new ChromeDriver();
		driver.get(String.format("http://localhost:%d/calci",port));

		WebElement entryField = driver.findElement(By.id("expression"));
		WebElement answerField = driver.findElement(By.id("result"));
		WebElement equalToBtn = driver.findElement(By.xpath("//div[@class='equalto']/button"));

		for(int i = 0; i < equations.length; i++){
			entryField.clear();
			entryField.sendKeys(equations[i]);
			equalToBtn.click();
			assert answerField.getText().equals(answers[i]);
		}
		System.out.println("Calci operators working");
		driver.quit();

	}

	@Test
	void useNumPad(){
		WebDriver driver = new ChromeDriver();
		driver.get(String.format("http://localhost:%d/calci",port));

		WebElement entryField = driver.findElement(By.id("expression"));
		WebElement answerField = driver.findElement(By.id("result"));
		WebElement equalToBtn = driver.findElement(By.xpath("//div[@class='equalto']/button"));

		for(int i = 0; i <= 9; i++){
			driver.findElement(By.xpath(String.format("//button[contains(text(),'%d')]",i))).click();
		}

		equalToBtn.click();

		System.out.println(entryField.getText()+'\n'+answerField.getText());

		assert answerField.getText().equals("1.2345679e+8");

		System.out.println("Numpad working");

		driver.quit();

	}
}
