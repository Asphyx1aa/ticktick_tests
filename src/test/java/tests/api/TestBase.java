package tests.api;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;


public class TestBase {
    @BeforeAll
    static void testSetup() {
        RestAssured.baseURI = System.getProperty("URL", "https://api.ticktick.com");
        RestAssured.basePath = "/open/v1/";
    }
}
