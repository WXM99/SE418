package com.weixm.wordLadder.controller;

import com.weixm.wordLadder.models.wordLadder;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.ArrayList;


@RestController
@CrossOrigin("*")
public class ladderController {
    private wordLadder ladder = new wordLadder();

    @RequestMapping(value = "/generate", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public ArrayList<String> generate(@RequestBody JSONObject input) throws IOException {
        String word1 = (String) input.get("word1");
        String word2 = (String) input.get("word2");
        ArrayList<String> res = this.ladder.generateChain(word1, word2);
        return res;
    }

    @RequestMapping(value = "/validate", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String validWord(@RequestBody JSONObject input) {
        String word1 = (String) input.get("word1");
        System.out.println(word1);
        if (this.ladder.validWord(word1)){
            return "1";
        } else {
            return "0";
        }
    }
}
