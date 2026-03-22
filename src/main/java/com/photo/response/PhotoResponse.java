package com.photo.response;

import com.photo.model.FindAllPhotosDTO;
import com.photo.model.Photo;
import lombok.*;

@Getter
@Setter
@ToString
public class PhotoResponse {

    private FindAllPhotosDTO metadata;
    private Photo singlePhotoMetadata;

    public PhotoResponse(Photo singlePhotoMetadata) {
        this.singlePhotoMetadata = singlePhotoMetadata;
    }

    public PhotoResponse(FindAllPhotosDTO metadata) {
        this.metadata = metadata;
    }
}
