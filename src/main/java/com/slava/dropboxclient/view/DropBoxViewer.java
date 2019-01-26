package com.slava.dropboxclient.view;

import com.slava.dropboxclient.model.auth.DropBoxAuthResponse;
import com.slava.dropboxclient.model.info.DropBoxInfoResponse;
import com.slava.dropboxclient.model.list.DropBoxListEntries;
import com.slava.dropboxclient.model.list.DropBoxListResponse;
import com.slava.dropboxclient.model.list.DropBoxMetaResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Component
public class DropBoxViewer {

    public void showAuthWelcomeScreen(String authUrl) {
        System.out.println("1. Go to: " + authUrl);
        System.out.println("2. Click \"Allow\" (you might have to log in first)");
        System.out.println("3. Copy the authorization code and paste it here:");
    }

    public void showAccessToken(DropBoxAuthResponse dropBoxAuthResponse) {
        System.out.println("Your access token:: " + dropBoxAuthResponse.getAccessToken());
    }

    public void showAccountInfo(DropBoxInfoResponse dropBoxInfoResponse) {
        System.out.println("--------------------------------------------------------");
        System.out.println("Account ID:     " + dropBoxInfoResponse.getAccountId());
        System.out.println("Display name:   " + Optional.ofNullable(dropBoxInfoResponse.getName().getDisplayName()).orElse(""));
        System.out.println("Name:           " + dropBoxInfoResponse.getName().getGivenName()
                + " "
                + dropBoxInfoResponse.getName().getSurname()
                + " "
                + Optional.ofNullable(dropBoxInfoResponse.getName().getFamiliarName()).orElse(""));
        System.out.println("E-mail:         " + dropBoxInfoResponse.getEmail());
        System.out.println("Country:        " + dropBoxInfoResponse.getCountry());
        System.out.println("Referral link:  " + dropBoxInfoResponse.getReferralLink());
        System.out.println("--------------------------------------------------------");
    }

    public void showMetaList(List dropBoxMetaListResponse) {
        DropBoxMetaResponse dropBoxMetaResponse = ((DropBoxMetaResponse) dropBoxMetaListResponse.get(0));
        List<DropBoxListEntries> dropBoxListEntries = ((DropBoxListResponse) dropBoxMetaListResponse.get(1)).getEntries();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss").withZone(ZoneId.systemDefault());

        String metaInfo = dropBoxMetaResponse.getType().equals("file") ?
                dropBoxMetaResponse.getPathDisplay() + "     :" +
                        dropBoxMetaResponse.getType() + ", " +
                        dropBoxMetaResponse.getSize() + ", modified at: " +
                        formatter.format(dropBoxMetaResponse.getModified()) :
                dropBoxMetaResponse.getPathDisplay() + "     :" +
                        dropBoxMetaResponse.getType();
        System.out.println("--------------------------------------------------------");
        System.out.println(metaInfo);
        dropBoxListEntries.forEach(entry -> {
            String listInfo = entry.getType().equals("file") ?
                    entry.getName() + "     :" +
                            entry.getType() + ", " +
                            entry.getSize() + ", modified at: " +
                            formatter.format(entry.getModified()) :
                    entry.getName() + "     :" +
                            entry.getType();
            System.out.println(" - /" + listInfo);
        });
        System.out.println("--------------------------------------------------------");
    }

    public void showNetworkError(String message, WebClientResponseException ex) {
        System.out.println(message + ",  " + ex.getStatusText() + ",  " + ex.getResponseBodyAsString());
    }
}
