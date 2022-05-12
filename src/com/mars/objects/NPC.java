package com.mars.objects;

import java.util.List;

public class NPC {
    private String name;
    private List<String> quoteList;
    private String image;

    public NPC(String name) {
        setName(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getQuoteList() {
        return quoteList;
    }

    public void setQuoteList(List<String> quoteList) {
        this.quoteList = quoteList;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
