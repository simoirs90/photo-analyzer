package com.photo.job;

import com.photo.AppConfig;
import com.photo.PathService;
import io.quarkus.logging.Log;
import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

@ApplicationScoped
public class DownloadFolderCleaner {

    @Inject
    PathService pathService;

    @Scheduled(every = "5m")
    void cleanTmpDir() {
        Log.infof("Tempfile cleaner activating - download directory: %s", pathService.downloadDir());
        Path tmpDir = Paths.get(pathService.downloadDir().toString());
        try (Stream<Path> files = Files.walk(tmpDir)) {
            files.filter(Files::isRegularFile)
                    .filter(p -> {
                        try {
                            return Files.getLastModifiedTime(p).toMillis() < System.currentTimeMillis() - (5 * 60 * 1000);
                        } catch (IOException e) {
                            return false;
                        }
                    })
                    .forEach(p -> {
                        try {
                            Files.deleteIfExists(p);
                            Log.debugf("Deleted temp file: %s", p);
                        } catch (IOException e) {
                            Log.errorf("Could not delete %s: %s", p, e.getMessage());
                        }
                    });
        } catch (IOException e) {
            Log.errorf("Temp cleanup failed: %s", e.getMessage());
        }
    }
}
