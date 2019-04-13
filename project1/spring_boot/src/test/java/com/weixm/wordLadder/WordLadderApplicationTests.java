package com.weixm.wordLadder;

import com.weixm.wordLadder.models.wordLadder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.Assert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.ArrayList;
import java.lang.reflect.Method;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WordLadderApplicationTests {
	private wordLadder ladder;

	@Before
	public void before() throws Exception {
		this.ladder = new wordLadder();
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
	public void neighborsTest() throws Exception {
		Method get_neighbors = wordLadder.class.getDeclaredMethod("neighbers", String.class);
		get_neighbors.setAccessible(true);
		ArrayList<String> neighbors = (ArrayList<String>) get_neighbors.invoke(this.ladder, "code");
		Assert.assertNotEquals("neighbors of \"code\" is not empty", neighbors.size(), 0);
	}
	@Test
	public void generateTest() {
		ArrayList<String> emp_chain = this.ladder.generateChain("xxxx", "sad");
		Assert.assertEquals("no chain connect \"xxxx\" and \"sad\". ", emp_chain.size(), 0);
		ArrayList<String> real_chain = this.ladder.generateChain("code", "data");
		Assert.assertNotEquals("a chain exists to connect \"code\" and \"data\". ", real_chain.size(), 0);
	}

}
