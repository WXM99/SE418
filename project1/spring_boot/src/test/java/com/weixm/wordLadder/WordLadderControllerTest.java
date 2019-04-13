package com.weixm.wordLadder;

import com.weixm.wordLadder.models.wordLadder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.Assert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import java.lang.reflect.Method;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WordLadderControllerTest {
    protected MockMvc mockMvc;
    @Autowired
    protected WebApplicationContext webApp;
    @Before
    public void before() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApp).build();
    }

    @Test
    public void mvcValidityTest() throws Exception {
        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.post("/validate").content("word1:\"code\"")
                        .header("Content-Type", "application/json")
        ).andReturn();
        Assert.assertEquals("front-end get a real valid word.", "1", result.getResponse().getContentAsString());
        MvcResult result_ = mockMvc.perform(
                MockMvcRequestBuilders.post("/validate").content("word1:\"wxm\"")
                        .header("Content-Type", "application/json")
        ).andReturn();
        Assert.assertEquals("front-end get a invalid word.", "0", result_.getResponse().getContentAsString());
    }
    @Test
    public void mvcGenerateTest() throws Exception {
        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.post("/generate").content("word1: \"code\", word2: \"data\"")
                        .header("Content-Type", "application/json")
        ).andReturn();
        Assert.assertNotEquals("front-end get a real chain.", 0, result.getResponse().getContentAsString().length());
    }

}
