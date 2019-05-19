package org.skramer.spodddify.contract;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.WebApplicationContext;

import io.restassured.module.mockmvc.RestAssuredMockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
public abstract class BaseMockMvcTest {
    @Autowired
    private WebApplicationContext webAppContext;

    @Before
    public void setupRestAssured() {
        RestAssuredMockMvc.webAppContextSetup(webAppContext);
    }
}
