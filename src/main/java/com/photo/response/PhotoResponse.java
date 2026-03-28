package com.photo.response;

import com.photo.model.FindAllPhotosDTO;
import com.photo.model.Photo;
import lombok.*;

@Getter
@Setter
@ToString
public class PhotoResponse {

    private FindAllPhotosDTO metadata;

    public PhotoResponse(FindAllPhotosDTO metadata) {
        this.metadata = metadata;
    }
}
