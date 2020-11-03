package ru.netology.rest;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class CardWithDelivery {

    LocalDate today = LocalDate.now();
    LocalDate newDate = today.plusDays(3);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    @Test
    void shouldSubmitRequest() {
        open("http://localhost:9999/");
        $("[data-test-id=city] input").setValue("Астрахань");
        $("[data-test-id=date] input").doubleClick().sendKeys(formatter.format(newDate));
        $("[data-test-id=name] input").setValue("Игорь Витальевич");
        $("[data-test-id=phone] input").setValue("+79012345678");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();
        $(withText("Успешно!")).waitUntil(visible, 15000);

    }

    @Test
    void shouldSubmitRequestWithDropDownList() {
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Мо");
        $$(".menu-item").first().click();
        $("[data-test-id=date]").click();
        $(".calendar__day_state_current").click();
        $("[data-test-id=name] input").setValue("Игорь Витальевич");
        $("[data-test-id=phone] input").setValue("+79012345678");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();
        $(withText("Успешно!")).waitUntil(visible, 15000);
    }

    @Test
    void shouldNotSubmitWithoutCity() {
        open("http://localhost:9999");
        $("[data-test-id=date] input").doubleClick().sendKeys(formatter.format(newDate));
        $("[name=name]").setValue("Игорь Витальевич");
        $("[name=phone]").setValue("+79012345678");
        $(".checkbox__box").click();
        $(".button__text").click();
        $(".input_theme_alfa-on-white.input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldNotSubmitWithoutName() {
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Мо");
        $$(".menu-item").first().click();
        $("[data-test-id=date]").click();
        $(".calendar__day_state_current").click();
        $("[name=phone]").setValue("+79012345678");
        $(".checkbox__box").click();
        $(".button__text").click();
        $(".input_theme_alfa-on-white.input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldNotSubmitWithIncorrectName() {
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Ка");
        $$(".menu-item").first().click();
        $("[data-test-id=date]").click();
        $(".calendar__day_state_current").click();
        $("[name=name]").setValue("Vasiliy Ivanov");
        $("[name=phone]").setValue("+79012345678");
        $(".checkbox__box").click();
        $(".button__text").click();
        $(".input_theme_alfa-on-white.input_invalid .input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldNotSubmitWithoutPhone() {
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Ка");
        $$(".menu-item").first().click();
        $("[data-test-id=date]").click();
        $(".calendar__day_state_current").click();
        $("[name=name]").setValue("Игорь Витальевич");
        $(".checkbox__box").click();
        $(".button__text").click();
        $(".input_theme_alfa-on-white.input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldNotSubmitWithoutCheckbox() {
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Ка");
        $$(".menu-item").first().click();
        $("[data-test-id=date]").click();
        $(".calendar__day_state_current").click();
        $("[name=name]").setValue("Игорь Витальевич");
        $("[name=phone]").setValue("+79012345678");
        $(".button__text").click();
        $(".input_invalid").shouldHave(exactText("Я соглашаюсь с условиями обработки и использования моих персональных данных"));
    }
}
