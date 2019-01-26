package com.slava.dropboxclient.controller;

import com.slava.dropboxclient.model.info.DropBoxInfoResponse;
import com.slava.dropboxclient.service.DropBoxNetworkClient;
import com.slava.dropboxclient.view.DropBoxViewer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;
import java.util.function.Consumer;

@Component
public class DropBoxInfo implements Consumer<List<String>> {

    @Autowired
    private DropBoxNetworkClient dropBoxNetworkClient;

    @Autowired
    private DropBoxViewer dropBoxViewer;

    @Override
    public void accept(List<String> arguments) {
        if (checkArguments(arguments)) {
            String accessToken = arguments.get(1);

            try {
                DropBoxInfoResponse dropBoxInfoResponse = dropBoxNetworkClient.getAccountInfo(accessToken);
                dropBoxViewer.showAccountInfo(dropBoxInfoResponse);
            } catch (WebClientResponseException ex) {
                dropBoxViewer.showNetworkError("Could not get the account info", ex);
            }
        }
    }

    private boolean checkArguments(List arguments) {
        if (arguments.size() == 2) {
            return true;
        } else {
            System.out.println("Wrong number of arguments, found " + arguments.size() + " but 2 are required");
            return false;
        }
    }
}
