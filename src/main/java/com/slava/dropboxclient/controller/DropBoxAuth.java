package com.slava.dropboxclient.controller;

import com.slava.dropboxclient.model.auth.DropBoxAuthResponse;
import com.slava.dropboxclient.service.DropBoxNetworkClient;
import com.slava.dropboxclient.view.DropBoxViewer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;
import java.util.Scanner;
import java.util.function.Consumer;

@Component
public class DropBoxAuth implements Consumer<List<String>> {

    @Autowired
    private DropBoxNetworkClient dropBoxNetworkClient;

    @Autowired
    private DropBoxViewer dropBoxViewer;

    private static final String authBaseUrl = "https://www.dropbox.com/oauth2/authorize";

    @Override
    public void accept(List<String> arguments) {
        if (checkArguments(arguments)) {
            String appKey = arguments.get(1);
            String appSecret = arguments.get(2);

            String authUrl = authBaseUrl + "?client_id=" + appKey + "&response_type=code";
            dropBoxViewer.showAuthWelcomeScreen(authUrl);

            Scanner input = new Scanner(System.in);
            String authCode = input.nextLine().trim();

            try {
                DropBoxAuthResponse dropBoxAuthResponse = dropBoxNetworkClient.getAccessToken(authCode, appKey, appSecret);
                dropBoxViewer.showAccessToken(dropBoxAuthResponse);
            } catch (WebClientResponseException ex) {
                dropBoxViewer.showNetworkError("Could not get the auth code", ex);
            }
        }
    }

    private boolean checkArguments(List arguments) {
        if (arguments.size() == 3) {
            return true;
        } else {
            System.out.println("Wrong number of arguments, found " + arguments.size() + " but 3 are required");
            return false;
        }
    }
}
