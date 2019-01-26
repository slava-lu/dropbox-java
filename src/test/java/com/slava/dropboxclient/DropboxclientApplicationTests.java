package com.slava.dropboxclient;

import static org.assertj.core.api.Assertions.assertThat;
import com.slava.dropboxclient.service.DropBoxNetworkClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DropboxclientApplicationTests {

    private final WebTestClient webTestClient;

    @Autowired
    private DropBoxNetworkClient dropBoxNetworkClient;

    public DropboxclientApplicationTests() {
        final String rootUrl = "https://api.dropboxapi.com";
        this.webTestClient = WebTestClient
                .bindToServer()
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .baseUrl(rootUrl)
                .build();
    }


    @Test
    public void contextLoads() {
        assertThat(dropBoxNetworkClient).isNotNull();
    }

    @Test
    public  void getAccountInfoTest() {
        String accessToken = "RRAAfUyoB7UAAAAAAAAATN889ngo-ckcXdqkAR-a2v__Y6xyI-d0sYlSK-KdovA7";

        webTestClient.post()
                .uri("/2/users/get_current_account")
                .header("Authorization", "Bearer " + accessToken)
                .syncBody("null")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_VALUE)
                .expectBody()
                .jsonPath("$.email").isNotEmpty()
                .jsonPath("$.email").isEqualTo("slaval@mail.ru");
    }

}
