package com.photo.model;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class FindAllPhotosDTO {

    private List<Photo> photos;
    private int page;
    private int size;
}
