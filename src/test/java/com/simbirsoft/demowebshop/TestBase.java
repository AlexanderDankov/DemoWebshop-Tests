package com.simbirsoft.demowebshop;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;



public class TestBase {

    @BeforeAll
    static void setup() {
        Configuration.browserSize = "1366x768";
        Configuration.baseUrl = "https://demowebshop.tricentis.com";
    }
}
