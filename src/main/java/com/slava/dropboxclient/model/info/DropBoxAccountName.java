package com.slava.dropboxclient.model.info;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DropBoxAccountName {
    @JsonProperty("display_name")
    private String displayName;

    @JsonProperty("given_name")
    private String givenName;

    @JsonProperty("familiar_name")
    private String familiarName;

    @JsonProperty("surname")
    private String surname;

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getFamiliarName() {
        return familiarName;
    }

    public void setFamiliarName(String familiarName) {
        this.familiarName = familiarName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
