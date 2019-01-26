package com.slava.dropboxclient;

import com.slava.dropboxclient.controller.DropBoxAuth;
import com.slava.dropboxclient.controller.DropBoxInfo;
import com.slava.dropboxclient.controller.DropBoxList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DropBoxExecutor {

    @Autowired
    DropBoxAuth dropBoxAuth;

    @Autowired
    DropBoxInfo dropBoxInfo;

    @Autowired
    DropBoxList dropBoxlist;

    public void executeOperation(List<String> arguments) {
        String command = arguments.get(0);
        switch (command) {
            case "auth":
                dropBoxAuth.accept(arguments);
                break;
            case "list":
                dropBoxlist.accept(arguments);
                break;
            case "info":
                dropBoxInfo.accept(arguments);
                break;
            default:
                System.out.println("Wrong command, please use auth, info or list command");
        }
    }
}
