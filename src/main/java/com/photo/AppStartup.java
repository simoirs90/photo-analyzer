package com.photo;

import io.quarkus.logging.Log;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@ApplicationScoped
public class AppStartup {

    @Inject
    AppConfig config;

    void onStart(@Observes StartupEvent ev) {
        String downloadDir = config.downloadDir();
        Path downloadPath = Path.of(downloadDir);

        try {
            if (Files.notExists(downloadPath)) {
                Files.createDirectories(downloadPath);
                Log.infof("Download folder created: %s", downloadPath.toAbsolutePath());
            } else {
                Log.infof("Download folder already exists: %s", downloadPath.toAbsolutePath());
            }
        } catch (IOException e) {
            Log.errorf(e, "Errore durante la creazione della cartella: %s", downloadDir);
            throw new RuntimeException("Impossibile inizializzare la cartella di download", e);
        }
    }
}
