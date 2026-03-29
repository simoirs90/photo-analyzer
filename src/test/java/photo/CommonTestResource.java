package photo;

import com.photo.AppConfig;
import com.photo.repository.UserRepository;
import io.quarkus.logging.Log;
import io.quarkus.test.InjectMock;
import jakarta.inject.Inject;

import java.io.File;
import java.net.URL;

public class CommonTestResource {

    @Inject
    UserRepository userRepository;

    protected static final String LOVELESS_JPG = "loveless.JPG";
    protected static final String RAVEN_PNG = "raven.png";

    protected File loadImage(String name) {
        try {
            URL url = getClass().getResource("/images/" + name);
            if (url == null) {
                throw new RuntimeException("Resource not found: " + name);
            }

            return new File(url.toURI());
        } catch (Exception e) {
            Log.infof("Error loading image %s - %s", name, e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
