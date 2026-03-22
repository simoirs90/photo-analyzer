package com.photo;

import io.smallrye.config.ConfigMapping;
import io.smallrye.config.WithDefault;

@ConfigMapping(prefix = "app")
public interface AppConfig {
    String baseDir();
    String downloadDir();
    @WithDefault("/data/photos")
    String photosSystemDir();
    @WithDefault("10")
    int maxUploadNumber();
}
