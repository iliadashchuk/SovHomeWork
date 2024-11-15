package com.demoqa;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.CapabilityType;

import java.io.File;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class DemoqaTest {

    @BeforeEach
    public void setUp(){
        Configuration.browserSize = "1920x1080";
        Configuration.browserCapabilities.setCapability(CapabilityType.PAGE_LOAD_STRATEGY, "eager");
        open("https://demoqa.com/automation-practice-form");
    }

    @Test
    public void fillInFormsTest(){
        // Fill in first name
        $("#firstName").setValue("Test");

        // Fill in last name
        $("#lastName").shouldBe(visible).setValue("Test");

        // Fill in email
        $("#userEmail").setValue("idasktestovy@gmail.com");

        // Choose gender
        $("[for=\"gender-radio-1\"]").click();

        // Fill in mobile phone
        $("#userNumber").setValue("9001001010");

        // Fill in date of birth
        $("#dateOfBirthInput").click();
        $("[class=\"react-datepicker__year-select\"]").$("[value=\"1994\"]").click();
        $("[class=\"react-datepicker__month-select\"]").$("[value=\"1\"]").click();
        $("[class=\"react-datepicker__day react-datepicker__day--014\"]").click();

        // Fill in subjects
        $("#subjectsInput").setValue("Hindi").pressEnter();

        // Choose hobbies
        $("[for=\"hobbies-checkbox-1\"]").click();
        $("[for=\"hobbies-checkbox-2\"]").click();
        $("[for=\"hobbies-checkbox-3\"]").click();

        // Upload image file
        $("#uploadPicture").uploadFile(new File("src/test/resources/sky.png"));

        // Fill in address
        $("#currentAddress").setValue("Lenina st" + "Saint Petersburg");

        // Fill in sate and city
        $("#react-select-3-input").setValue("Haryana").pressEnter();
        $("#react-select-4-input").setValue("Karnal").pressEnter();

        // Click on submit button
        $("#submit").click();

        // Check success modal window
        $("#example-modal-sizes-title-lg").shouldHave(text("Thanks for submitting the form"));
        $("[class=\"table table-dark table-striped table-bordered table-hover\"]").$("tbody")
                .shouldHave(text("Test Test"),
                        text("idasktestovy@gmail.com"),
                        text("Male"),
                        text("9001001010"),
                        text("14 February,1994"),
                        text("Hindi"),
                        text("Music"),
                        text("sky.png"),
                        text("Lenina st" + "Saint Petersburg"),
                        text("Haryana Karnal"));
    }
    @Test
    public void negativeSubmitButtonTest(){
        $("#submit").click();
        $("#firstName").shouldHave(cssValue("border-color", "rgb(220, 53, 69)"));
        $("#lastName").shouldHave(cssValue("border-color", "rgb(220, 53, 69)"));
        $("#userNumber").shouldHave(cssValue("border-color", "rgb(220, 53, 69)"));
        $("[for=\"hobbies-checkbox-1\"]").shouldHave(cssValue("color", "rgba(40, 167, 69, 1)"));
        $("[for=\"hobbies-checkbox-2\"]").shouldHave(cssValue("color", "rgba(40, 167, 69, 1)"));
        $("[for=\"hobbies-checkbox-3\"]").shouldHave(cssValue("color", "rgba(40, 167, 69, 1)"));
        $(By.xpath("//*[@class='text-center' and text()='Practice Form']")).shouldHave(text("Practice Form"));
    }
}
