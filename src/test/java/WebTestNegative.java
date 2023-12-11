import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WebTestNegative {

    private WebDriver driver;

    @BeforeAll
    public static void setupAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void beforeEach() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999");
    }

    @AfterEach
    public void afterEach() {
        driver.quit();
        driver = null;
    }

    @Test
    public void testNoName() {
        driver.findElement(By.cssSelector("[data-test-id = 'phone'] input")).sendKeys("+11111111111");
        driver.findElement(By.cssSelector("[data-test-id = 'agreement']")).click();
        driver.findElement(By.cssSelector("button.button")).click();
        var actualTextElement = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub"));
        var actualText = actualTextElement.getText().trim();
        assertEquals("Поле обязательно для заполнения", actualText);
        assertTrue(actualTextElement.isDisplayed());
    }

    @Test
    public void testInvalidName() {
        driver.findElement(By.cssSelector("[data-test-id = 'name'] input")).sendKeys("Andrey");
        driver.findElement(By.cssSelector("[data-test-id = 'phone'] input")).sendKeys("+11111111111");
        driver.findElement(By.cssSelector("[data-test-id = 'agreement']")).click();
        driver.findElement(By.cssSelector("button.button")).click();
        var actualTextElement = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub"));
        var actualText = actualTextElement.getText().trim();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", actualText);
        assertTrue(actualTextElement.isDisplayed());
    }

    @Test
    public void testNoPhone() {
        driver.findElement(By.cssSelector("[data-test-id = 'name'] input")).sendKeys("Шабров-леонидович Андрей");
        driver.findElement(By.cssSelector("[data-test-id = 'agreement']")).click();
        driver.findElement(By.cssSelector("button.button")).click();
        var actualTextElement = driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub"));
        var actualText = actualTextElement.getText().trim();
        assertEquals("Поле обязательно для заполнения", actualText);
        assertTrue(actualTextElement.isDisplayed());
    }

    @Test
    public void testInvalidPhone() {
        driver.findElement(By.cssSelector("[data-test-id = 'name'] input")).sendKeys("Шабров-леонидович Андрей");
        driver.findElement(By.cssSelector("[data-test-id = 'phone'] input")).sendKeys("12334111111");
        driver.findElement(By.cssSelector("[data-test-id = 'agreement']")).click();
        driver.findElement(By.cssSelector("button.button")).click();
        var actualTextElement = driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub"));
        var actualText = actualTextElement.getText().trim();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", actualText);
        assertTrue(actualTextElement.isDisplayed());
    }

    @Test
    public void noCheckBox() {
        driver.findElement(By.cssSelector("[data-test-id = 'name'] input")).sendKeys("Шабров-леонидович Андрей");
        driver.findElement(By.cssSelector("[data-test-id = 'phone'] input")).sendKeys("+11111111111");
        driver.findElement(By.cssSelector("button.button")).click();
        assertTrue(driver.findElement(By.cssSelector("[data-test-id = 'agreement'].input_invalid")).isDisplayed());

    }
}
