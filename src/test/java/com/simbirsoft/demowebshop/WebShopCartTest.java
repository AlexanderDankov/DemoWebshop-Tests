package com.simbirsoft.demowebshop;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class WebShopCartTest extends TestBase {

    private static Cookie cookie;
    private static String count;

    @Test
    void checkOrderInCart() {
        step("Open books page", () ->
                open("/books"));

        step("Set browser cookie as variable", () ->
                cookie = getWebDriver().manage().getCookieNamed("Nop.customer"));

        step("Get book 'Computing and Internet' in list", () ->
                $$(".product-title").findBy(Condition.text("Computing and Internet")).click());

        step("Add book to cart", () ->
                $(".add-to-cart-button").click());

        step("UI verify cart notification", () ->
                $("#bar-notification .content").shouldHave(Condition.text("The product has been added to your shopping cart")));

        step("UI verify cart count = 1", () ->
                assertThat($(".cart-qty").getOwnText()).isEqualTo("(1)"));

        step("Send request to add another item to cart", () ->
                count =
                        given()
                                .cookie(String.valueOf(cookie))
                                .when()
                                .post("https://demowebshop.tricentis.com/addproducttocart/catalog/13/1/1")
                                .then()
                                .statusCode(200)
                                .extract()
                                .path("updatetopcartsectionhtml")
        );

        step("API verify cart count=2", () ->
                assertThat(count).isEqualTo("(2)"));
    }
}
