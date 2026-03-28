package com.photo.utils;

import com.photo.model.PhotoUploadForm;
import com.photo.model.user.User;
import io.quarkus.logging.Log;

import java.io.File;

public class PhotoUtils {

    public static PhotoUploadForm uploadedPhotoCreator(File uploadedFile,
                                                       String fileName,
                                                       String mimeType,
                                                       String size,
                                                       String checksum,
                                                       String createdAt,
                                                       String uploadedAt,
                                                       User user) {
        try {
            return new PhotoUploadForm(uploadedFile, fileName, mimeType, size, checksum, createdAt, uploadedAt, user);
        } catch (Exception e) {
            Log.infof("Error creating uploaded photo object for file: %s - %s", uploadedFile.getName(), e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
