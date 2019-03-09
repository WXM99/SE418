package com.weixm.wordLadder.controller;

import com.weixm.wordLadder.models.wordLadder;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.ArrayList;


@RestController
@CrossOrigin("http://localhost:8081")
public class ladderController {

    @RequestMapping(value = "/generate", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public ArrayList<String> generate(@RequestBody String input) throws IOException {
        System.out.println(input);
        int start1=0;
        int start2=0;
        int end1=0;
        int end2=0;
        for (int i = 0; i < input.length(); i++){
            if(input.charAt(i) == ':') {
                start1 = i+2;
                break;
            }
        }
        for (int i = start1; i < input.length(); i++){
            if (input.charAt(i) == '"') {
                end1 = i;
                break;
            }
        }
        for (int i = end1; i < input.length(); i++){
            if(input.charAt(i) == ':') {
                start2 = i+2;
                break;
            }
        }
        for (int i = start2; i < input.length(); i++){
            if (input.charAt(i) == '"') {
                end2 = i;
                break;
            }
        }
        String word1 = input.substring(start1, end1);
        String word2 = input.substring(start2, end2);
        System.out.println(word1);
        System.out.println(word2);
        wordLadder ladder = new wordLadder();
        ArrayList<String> res = ladder.generateChain(word1, word2);
        return res;
    }

    @RequestMapping(value = "/validate", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public int validWord(@RequestBody String input) {
        System.out.println(input);
        int start1=0;
        int end1=0;
        for (int i = 0; i < input.length(); i++){
            if(input.charAt(i) == ':') {
                start1 = i+2;
                break;
            }
        }
        for (int i = start1; i < input.length(); i++){
            if (input.charAt(i) == '"') {
                end1 = i;
                break;
            }
        }
        String word1 = input.substring(start1, end1);
        System.out.println(word1);
        wordLadder ladder = new wordLadder();
        if (ladder.validWord(word1)){
            return 1;
        } else {
            return 0;
        }
    }
}
