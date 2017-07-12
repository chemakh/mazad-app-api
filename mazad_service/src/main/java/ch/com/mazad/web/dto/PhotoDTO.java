package ch.com.mazad.web.dto;

import java.io.Serializable;

/**
 * Created by Chemakh on 18/06/2017.
 */

public class PhotoDTO implements Serializable
{

    private String reference;

    private String label;

    private String name;

    private String articleReference;

    private String url;

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArticleReference() {
        return articleReference;
    }

    public void setArticleReference(String articleReference) {
        this.articleReference = articleReference;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
