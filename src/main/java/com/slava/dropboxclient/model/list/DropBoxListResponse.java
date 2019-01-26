package com.slava.dropboxclient.model.list;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class DropBoxListResponse {

    @JsonProperty("entries")
    private List<DropBoxListEntries> entries;

    public List<DropBoxListEntries> getEntries() {
        return entries;
    }

    public void setEntries(List<DropBoxListEntries> entries) {
        this.entries = entries;
    }
}
