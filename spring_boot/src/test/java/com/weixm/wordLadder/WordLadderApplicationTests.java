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

@RunWith(SpringRunner.class)
@SpringBootTest
public class WordLadderApplicationTests {
	private wordLadder ladder;
	protected MockMvc mockMvc;
	@Autowired
	protected WebApplicationContext webApp;
	@Before
	public void before() throws Exception {
		this.ladder = new wordLadder();
		mockMvc = MockMvcBuilders.webAppContextSetup(webApp).build();
	}

	@Test
	public void contextLoads() {
		this.ladder = new wordLadder();
		int load_size = this.ladder.wholeDictionary().size();
		Assert.assertNotEquals("loaded dictionary shall not be empty.", load_size, 0);
	}
	@Test
	public void validityTest() {
		Boolean not_in = this.ladder.validWord("wxmwxm");
		Boolean shall_in = this.ladder.validWord("code");
		Assert.assertEquals("\"wxmwxm\" shall not be in the dictionary.", not_in, false);
		Assert.assertEquals("\"code\" shall be in the dictionary.", shall_in, true);
	}
	@Test
	public void neighborsTest() {
		ArrayList<String> neighbors = this.ladder.neighbers("code");
		Assert.assertNotEquals("neighbors of \"code\" is not empty", neighbors.size(), 0);
	}
	@Test
	public void generateTest() {
		ArrayList<String> emp_chain = this.ladder.generateChain("xxxx", "sad");
		Assert.assertEquals("no chain connect \"xxxx\" and \"sad\". ", emp_chain.size(), 0);
		ArrayList<String> real_chain = this.ladder.generateChain("code", "data");
		Assert.assertNotEquals("a chain exists to connect \"code\" and \"data\". ", real_chain.size(), 0);
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
