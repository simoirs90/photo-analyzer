package com.photo;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.nio.file.Path;

@ApplicationScoped
public class PathService {

    @Inject
    private AppConfig config;

    public Path baseDir() {
        return resolve(config.baseDir());
    }

    public Path downloadDir() {
        return resolve(config.downloadDir());
    }

    private Path resolve(String path) {
        return Path.of(path)
                .toAbsolutePath()
                .normalize();
    }
}