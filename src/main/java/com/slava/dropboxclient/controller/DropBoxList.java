package com.slava.dropboxclient.controller;

import com.slava.dropboxclient.service.DropBoxNetworkClient;
import com.slava.dropboxclient.view.DropBoxViewer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;
import java.util.function.Consumer;

@Component
public class DropBoxList implements Consumer<List<String>> {

    @Autowired
    private DropBoxNetworkClient dropBoxNetworkClient;

    @Autowired
    private DropBoxViewer dropBoxViewer;

    @Override
    public void accept(List<String> arguments) {
        if (checkArguments(arguments)) {
            String accessToken = arguments.get(1);
            String path = arguments.get(2);
            try {
                List<Object> dropBoxMetaListResponse = dropBoxNetworkClient.getMetaList(accessToken, path);
                dropBoxViewer.showMetaList(dropBoxMetaListResponse);
            } catch (WebClientResponseException ex) {
                dropBoxViewer.showNetworkError("Could not get the meta or list", ex);
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
