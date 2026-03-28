package com.photo.model;

import com.drew.lang.annotations.NotNull;
import com.photo.model.user.User;
import lombok.*;
import org.jboss.resteasy.reactive.RestForm;

import java.io.File;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PhotoUploadForm {
    @RestForm("file")
    @NotNull
    private File file;
    private String name;
    private String mimeType;
    private String size;
    private String checksum;
    private String createdAt;
    private String uploadedAt;
    private User user;
}
