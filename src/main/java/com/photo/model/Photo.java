package com.photo.model;

import com.photo.model.user.User;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "photos")
public class Photo extends PanacheEntity {

    private String name;
    private String mimeType;
    private String createdAt;
    private String uploadedAt;
    private String storagePath;
    private String sizeMB;
    private String checksum;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
