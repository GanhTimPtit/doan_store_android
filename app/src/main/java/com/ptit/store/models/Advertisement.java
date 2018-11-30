package com.ptit.store.models;

public class Advertisement {
    private String id;
    private String logoUrl;
    private String linkUrl;

    public Advertisement() {
    }

    public Advertisement(String id, String logoUrl, String linkUrl) {
        this.id = id;
        this.logoUrl = logoUrl;
        this.linkUrl = linkUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }
}
