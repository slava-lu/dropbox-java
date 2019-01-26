package com.slava.dropboxclient.model.list;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

public class DropBoxListEntries {

    @JsonProperty("name")
    private String name;

    @JsonProperty(".tag")
    private String type;

    @JsonProperty("server_modified")
    private Instant modified;

    @JsonProperty("size")
    private String size;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Instant getModified() {
        return modified;
    }

    public void setModified(Instant modified) {
        this.modified = modified;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String pathDisplay) {
        this.name = pathDisplay;
    }
}
