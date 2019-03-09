package com.weixm.wordLadder.controller;

import com.weixm.wordLadder.models.wordLadder;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.ArrayList;


@RestController
public class ladderController {

    @RequestMapping(value = "/generate", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public ArrayList<String> generate(@RequestBody String input) throws IOException {
        System.out.println(input);
        wordLadder ladder = new wordLadder();
        ArrayList<String> res = ladder.generateChain("code", "copy");
        return res;
    }

    @RequestMapping(value = "/validate", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Boolean validWord(@RequestBody String input) {
        System.out.println(input);
        wordLadder ladder = new wordLadder();
        return ladder.validWord("code");
    }
}
