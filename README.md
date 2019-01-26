# Java DropBox client

This is the Dropbox client demo app.  
The client is build with Java Spring Boot framework using Spring [WebFlux reactive 
framework](https://docs.spring.io/spring/docs/current/spring-framework-reference/web-reactive.html).  

Reactive, non-blocking WebClient is used to consume the [Dropbox REST API v2](https://www.dropbox.com/developers/documentation/http/overview).  

There is also an example of using WebTestClient for testing REST API.

### Installation and Usage
This is the maven application so all standard maven commands can be used.  
The easiest way to run it is `mvn spring-boot:run -Dspring-boot.run.arguments="auth, key1, key2"`   
You can also create the standard jar application with `mvn clean package`  

The Dropbox client supports following commands:

* `auth {appKey} {appSecret}` - authenticates and authorizes the access to Dropbox account
* `info {accessToken}` - retrieves and prints user's account information
* `list {accessToken} {path}` - retrieves and prints files and folders information for specified path

where:
* `{appKey}` - the Dropbox application key
* `{appSecret}` - the Dropbox application secret code
* `{accessToken}` - the access token, which is generated using the `auth` command





