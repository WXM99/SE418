package com.weixm.wordLadder.controller;

import net.sf.json.JSONObject;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;


@RestController
@CrossOrigin("*")
public class loginController {

    @RequestMapping(value = "/generate", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public ResponseEntity<String> generate(@RequestBody JSONObject input) throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://wordladder:9090/generate";
        /* request head to another service container */
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        /* request body to another service container */
        MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
        map.add("word1", input.getString("word1"));
        map.add("word2", input.getString("word2"));
        /* request */
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
        ResponseEntity<String> response = restTemplate.postForEntity( url, request , String.class );

        return response;
    }
}
