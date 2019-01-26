package com.slava.dropboxclient.model.info;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DropBoxInfoResponse {

    @JsonProperty("account_id")
    private String accountId;

    @JsonProperty("email")
    private String email;

    @JsonProperty("country")
    private String country;

    @JsonProperty("referral_link")
    private String referralLink;

    @JsonProperty("name")
    private DropBoxAccountName name;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public DropBoxAccountName getName() {
        return name;
    }

    public void setName(DropBoxAccountName name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getReferralLink() {
        return referralLink;
    }

    public void setReferralLink(String referralLink) {
        this.referralLink = referralLink;
    }
}
