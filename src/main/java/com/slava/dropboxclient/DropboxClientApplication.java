package com.slava.dropboxclient;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class DropboxClientApplication implements ApplicationRunner {

    @Autowired
    DropBoxExecutor dropBoxExecutor;

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(DropboxClientApplication.class);
        app.setWebApplicationType(WebApplicationType.NONE);
        app.run(args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<String> arguments = args.getNonOptionArgs();
        if (arguments.size() == 0) {
            System.out.println("No command found, please use auth, info or list command");
        } else {
            dropBoxExecutor.executeOperation(arguments);
        }
    }
}


