package ru.netology;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;

public class CardWithDeliveryTest {
    LocalDate today = LocalDate.now();
    LocalDate newDate = today.plusDays(3);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    @BeforeEach
    void setUp() {
        open("http://localhost:9999/");
    }

    @Test
    void shouldSubmitRequest() {
        $("[data-test-id=city] input").setValue("Астрахань");
        $("[data-test-id=date] input").doubleClick().sendKeys(formatter.format(newDate));
        $("[data-test-id=name] input").setValue("Игорь Витальевич");
        $("[data-test-id=phone] input").setValue("+79012345678");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();
        $("[data-test-id='success-notification'].notification__content").waitUntil(visible,15000).shouldHave(exactText("Встреча успешно запланирована на" + formatter.format(newDate)));
        $(withText("Успешно!")).waitUntil(visible, 15000);
    }


    @Test
    void shouldSubmitRequestWithDropDownList() {
        $("[data-test-id=city] input").setValue("Мо");
        $$(".menu-item").find(exactText("Москва")).click();
        $("[data-test-id=date]").click();
        $(".calendar__day_state_current").click();
        $("[data-test-id=name] input").setValue("Игорь Витальевич");
        $("[data-test-id=phone] input").setValue("+79012345678");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();
        $(withText("Успешно!")).waitUntil(visible, 15000);
        $("[data-test-id='notification'].notification__content").waitUntil(visible,15000).shouldHave(exactText("Встреча успешно запланирована на " + formatter.format(newDate)));
    }

    @Test
    void shouldNotSubmitWithoutCity() {
        $("[data-test-id=date] input").doubleClick().sendKeys(formatter.format(newDate));
        $("[name=name]").setValue("Игорь Витальевич");
        $("[name=phone]").setValue("+79012345678");
        $(".checkbox__box").click();
        $(".button__text").click();
        $("[data-test-id='city'].input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldNotSubmitWithoutName() {
        $("[data-test-id=city] input").setValue("Ас");
        $$(".menu-item").find(exactText("Астрахань")).click();
        $("[data-test-id=date]").click();
        $(".calendar__day_state_current").click();
        $("[name=phone]").setValue("+79012345678");
        $(".checkbox__box").click();
        $(".button__text").click();
        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldNotSubmitWithIncorrectName() {
        $("[data-test-id=city] input").setValue("Ка");
        $$(".menu-item").find(exactText("Калининград")).click();
        $("[data-test-id=date]").click();
        $(".calendar__day_state_current").click();
        $("[name=name]").setValue("Vasiliy Ivanov");
        $("[name=phone]").setValue("+79012345678");
        $(".checkbox__box").click();
        $(".button__text").click();
        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldNotSubmitWithoutPhone() {
        $("[data-test-id=city] input").setValue("Ка");
        $$(".menu-item").find(exactText("Калининград")).click();
        $("[data-test-id=date]").click();
        $(".calendar__day_state_current").click();
        $("[name=name]").setValue("Игорь Витальевич");
        $(".checkbox__box").click();
        $(".button__text").click();
        $("[data-test-id='phone'].input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldNotSubmitWithoutCheckbox() {
        $("[data-test-id=city] input").setValue("Кр");
        $$(".menu-item").find(exactText("Краснодар")).click();
        $("[data-test-id=date]").click();
        $(".calendar__day_state_current").click();
        $("[name=name]").setValue("Игорь Витальевич");
        $("[name=phone]").setValue("+79012345678");
        $(".button__text").click();
        $(".checkbox_size_m.input_invalid .checkbox__text").shouldHave(exactText("Я соглашаюсь с условиями обработки и использования моих персональных данных"));
    }
}
