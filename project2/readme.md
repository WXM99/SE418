# Project4 Distributed Service With Docker

## ServerA: wordLadderLogin

- Controller:

  Transmit request to http://wordladder:9090/generate after login in 'ServerA container'

  ```java
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
  ```

- Security Config

  ```java
  /* ... */
  http
                  .cors().and().csrf().disable()
                  .authorizeRequests()
                  /* Public APIs for every user */
                  .antMatchers("/login").permitAll()
                  /* Security protected APIs for USER*/
                  .antMatchers("/generate").hasRole("USER")
  /*...*/
  ```

## ServerB: wordLadderService

The same as project 1 at https://cloud.docker.com/repository/docker/516015910018/se418

```bash
docker pull 516015910018/se418:latest
```

## Deploy and Link Services

### 1. Pull Services

- Service A: login

```bash
docker pull 516015910018/word-ladder-login:v1
```

- Service B: wordLadder

```bash
docker pull 516015910018/se418:latest
```

### 2. Run Services

- Run Service B: wordLadder in container ```wl-service```

```bash
docker run -itd -P --name=wl-service 516015910018/se418:latest
```

- Run Sevice A: login and Link with ```wl-service```

```bash
docker run -itd -p 9090:9090 --link=wordladder:wl-service 516015910018/word-ladder-login:v1
```

### 3. Access

Login in server A @ http:localhost:9090/login.

WordLadder in server A and transmit to server B @ http:localhost:9090/generate.