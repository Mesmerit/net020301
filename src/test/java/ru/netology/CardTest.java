package ru.netology;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CardTest {

    private WebDriver driver;

    @BeforeAll
    static void setUpAll() {
        System.setProperty("webdriver.chrome.driver", "driver/win/chromedriver.exe");
    }

    @BeforeEach
    void setUp() {
//        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--disable-dev-shm-usage");
//        options.addArguments("--no-sandbox");
//        options.addArguments("--headless");
//        driver = new ChromeDriver(options);
        driver = new ChromeDriver();
        driver.get("http://localhost:9999/");
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void testFormOk() {
        WebElement form = driver.findElement(By.cssSelector("form"));

        form.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Иван Иванов");
        form.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79876543232");
        form.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        form.findElement(By.cssSelector(".button_view_extra")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='order-success']")).getText();

        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.",
                text.trim());
    }

    @Test
    void testDualFamily() {
        WebElement form = driver.findElement(By.cssSelector("form"));

        form.findElement(By.cssSelector("[data-test-id='name'] input"))
                .sendKeys("Лиза Тарасова-Гаевская");
        form.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79234567878");
        form.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        form.findElement(By.cssSelector(".button_view_extra")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='order-success']")).getText();

        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.",
                text.trim());
    }

    @Test
    void testFormNoCheckBox() {
        WebElement form = driver.findElement(By.cssSelector("form"));

        form.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Стив Возняк");
        form.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79876345687");
        form.findElement(By.cssSelector(".button_view_extra")).click();
        form.findElement(By.cssSelector("[data-test-id='agreement'].input_invalid"));
    }

    @Test
    void testFormLatinSymbols() {
        WebElement form = driver.findElement(By.cssSelector("form"));

        form.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Ivanov Ivan");
        form.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79876542323");
        form.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        form.findElement(By.cssSelector(".button_view_extra")).click();
        String text = form.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText();

        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.",
                text.trim());
    }

    @Test
    void testFormSpecialSymbols() {
        WebElement form = driver.findElement(By.cssSelector("form"));

        form.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Иван& Иванов");
        form.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79998889898");
        form.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        form.findElement(By.cssSelector(".button_view_extra")).click();
        String text = form.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText();

        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.",
                text.trim());
    }

    @Test
    void testFormEmptyNameAndPhone() {
        WebElement form = driver.findElement(By.cssSelector("form"));

        form.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        form.findElement(By.cssSelector(".button_view_extra")).click();
        String text = form.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText();

        assertEquals("Поле обязательно для заполнения", text.trim());
    }

    @Test
    void testFormEmptyName() {
        WebElement form = driver.findElement(By.cssSelector("form"));

        form.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79876345687");
        form.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        form.findElement(By.cssSelector(".button_view_extra")).click();
        String text = form.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText();

        assertEquals("Поле обязательно для заполнения", text.trim());
    }

    @Test
    void testFormEmptyPhone() {
        WebElement form = driver.findElement(By.cssSelector("form"));

        form.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Стив Возняк");
        form.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        form.findElement(By.cssSelector(".button_view_extra")).click();
        String text = form.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText();

        assertEquals("Поле обязательно для заполнения", text.trim());
    }

    @Test
    void testFormShortPhone() {
        WebElement form = driver.findElement(By.cssSelector("form"));

        form.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Стив Возняк");
        form.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+7921");
        form.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        form.findElement(By.cssSelector(".button_view_extra")).click();
        String text = form.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText();

        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }

    @Test
    void testFormPhoneWithoutPlus() {
        WebElement form = driver.findElement(By.cssSelector("form"));

        form.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Иванов Иван");
        form.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("79875553322");
        form.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        form.findElement(By.cssSelector(".button_view_extra")).click();
        String text = form.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText();

        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }

    @Test
    void testFormWithSpaces() {
        WebElement form = driver.findElement(By.cssSelector("form"));

        form.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Иванов Иван");
        form.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+7 987 444 33 11");
        form.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        form.findElement(By.cssSelector(".button_view_extra")).click();
        String text = form.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText();

        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }

    @Test
    void testFormWithHyphen() {
        WebElement form = driver.findElement(By.cssSelector("form"));

        form.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Стив Возняк");
        form.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+7987-111-23-45");
        form.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        form.findElement(By.cssSelector(".button_view_extra")).click();
        String text = form.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText();

        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }

    @Test
    void testFormPhoneLetter() {
        WebElement form = driver.findElement(By.cssSelector("form"));

        form.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Стив Возняк");
        form.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("телефон");
        form.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        form.findElement(By.cssSelector(".button_view_extra")).click();
        String text = form.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText();

        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }

}