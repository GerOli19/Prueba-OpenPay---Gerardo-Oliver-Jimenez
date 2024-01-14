package com.search.marvel.api_rest.vo;

import java.io.Serializable;

/**
 * ThumbnailVO clase para poder mapear la respuesta Thumbnail de cada Character que regrese el Api.
 */
public class ThumbnailVO implements Serializable {

    private String extension;
    private String path;

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
