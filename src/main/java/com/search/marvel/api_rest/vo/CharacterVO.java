package com.search.marvel.api_rest.vo;

import java.io.Serializable;

/**
 * CharacterVO clase para poder mapear la respuesta de cada Character que regrese el Api.
 */
public class CharacterVO implements Serializable {

    private long id;
    private String description;
    private String name;
    private ThumbnailVO thumbnail;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ThumbnailVO getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(ThumbnailVO thumbnail) {
        this.thumbnail = thumbnail;
    }
}
