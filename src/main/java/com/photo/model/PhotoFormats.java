package com.photo.model;

import java.util.Arrays;

public enum PhotoFormats {

    JPG("image/jpeg"),
    JPEG("image/jpeg"),
    PNG("image/png"),
    GIF("image/gif"),
    BMP("image/bmp"),
    WEBP("image/webp"),
    TIFF("image/tiff"),
    SVG("image/svg+xml"),
    HEIC("image/heic");

    private final String mimeType;

    PhotoFormats(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getMimeType() {
        return mimeType;
    }

    public static boolean isImageMimeType(String mimeType) {
        return Arrays.stream(values())
                .map(PhotoFormats::getMimeType)
                .toList()
                .contains(mimeType);
    }
}
