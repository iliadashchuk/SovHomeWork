package ru.sovcombank.people;

import com.codeborne.selenide.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class SovcomVacanciesTest {

    @BeforeEach
    public void setUp(){
        Configuration.browserSize = "1920x1080";
        open("https://people.sovcombank.ru/");
    }

    @Test
    public void pickUpAllFilteredVacancies(){
        $(By.xpath("//button[contains(@class, 'button close-btn')]")).shouldBe(visible).click();
        $(By.xpath("//*[text()='Вакансии']")).click();
        $("title").shouldHave(attribute("text", "Свежие и актуальные вакансии в Совкомбанке"));
        $(By.xpath("//*[@placeholder='Все города']")).setValue("Москва").press(Keys.ARROW_DOWN).pressEnter();
        $(By.xpath("//*[@placeholder='Все компании']/..")).click();
        $(By.xpath("//div[@class='v-list-item__title']/../../../../div[3]")).click();

        ElementsCollection items = $$(By.xpath("//div[@class='section-vacancies full-width']//a[contains(@href, vacancies)]"));

        for (SelenideElement number : items) {
            number.shouldHave(text("Москва"));
            number.shouldHave(text("Совкомбанк Технологии"));
            System.out.println(number.getText());
        }
    }
}
