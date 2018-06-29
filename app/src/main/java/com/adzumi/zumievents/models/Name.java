
package com.adzumi.zumievents.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Name {

    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("html")
    @Expose
    private String html;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Name() {
    }

    /**
     * 
     * @param text
     * @param html
     */
    public Name(String text, String html) {
        super();
        this.text = text;
        this.html = html;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

}
