# Task3. WordLadder in Docker

## Docker hub repo

```https://hub.docker.com/r/516015910018/se418/```

## Run application in local container

### 1. Pull the image

```bash
$terminal docker pull 516015910018/se418
```

### 2. Run the image

```bash
$terminal docker run -d -p 9090:9090 516015910018/se418
```

### 3. Access

check running container:

```bash
$terminal docker container ls
```

output:

| CONTAINERID  |       IMAGE        | COMMAND  | CREATED | PORTS                  |
| ------------ | :----------------: | :------: | ------- | ---------------------- |
| e5337e5d3ad3 | 516015910018/se418 | "sh -c…" | ...     | 0.0.0.0:9090->9090/tcp |

visit apis in ```localhost:9090``` (reflect when docker run -p 9090[outer]:9090[inner])

- /validate

  ```javascript
  /* Access sample in ajax */
  var settings = {
    "async": true,
    "crossDomain": true,
    "url": "http://localhost:9090/validate",
    "method": "POST",
    "headers": {
      "Content-Type": "application/json",
      "cache-control": "no-cache",
      "Postman-Token": "4c24b84d-7b0d-4d6e-a4e9-8fbb8b2147f7"
    },
    "processData": false,
    "data": "{\"word1\": \"data\"}"
  }
  
  $.ajax(settings).done(function (response) {
    console.log(response);
  });
  ```

- /generate

  ```javascript
  /* Access sample in ajax */
  var settings = {
    "async": true,
    "crossDomain": true,
    "url": "http://localhost:9090/generate",
    "method": "POST",
    "headers": {
      "Content-Type": "application/json",
      "cache-control": "no-cache",
      "Postman-Token": "6f2cf656-e00d-4cc3-b17b-e66be9d3b18c"
    },
    "processData": false,
    "data": "{\n\t\"word1\": \"code\",\n\t\"word2\": \"data\"\n}"
  }
  
  $.ajax(settings).done(function (response) {
    console.log(response);
  });
  ```

## 4. Dockerfile

```dockerfile
FROM java:8
EXPOSE 9090
VOLUME /tmp
ADD word_ladder-0.0.1-SNAPSHOT.jar app.jar
RUN sh -c 'touch /app.jar'
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]
```

- ```FROM```: base image. running at a image with java:8 env
- ```EXPOSE```: the port host reflect
- ```VOLUME```: permanent files store in host/tmp
- ```ADD```: copy word_ladder.tar into docker as app.jar
- ```ENTRYPOINT```: first command run in container. run app.jar





