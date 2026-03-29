package photo;

import com.photo.model.user.User;
import io.quarkus.logging.Log;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
class PhotoResourceTest extends CommonTestResource {

    private User mockUser;
    private String userId;

    @BeforeEach
    void init() {
        mockUser = new User();
        mockUser.setName("Simone");

        persistMockUser(mockUser);

        userId = mockUser.id.toString();
    }

    @Test
    void singlePhotoUploadShouldReturn200() {
        given()
                .multiPart("files", loadImage(LOVELESS_JPG))
                .multiPart("userId", userId)
                .when()
                .post("/photos/upload")
                .then()
                .statusCode(200);
    }

    @Test
    void multiplePhotoUploadShouldReturn200() {
        given()
                .multiPart("files", loadImage(LOVELESS_JPG))
                .multiPart("files", loadImage(RAVEN_PNG))
                .multiPart("userId", "1")
                .when()
                .post("/photos/upload")
                .then()
                .statusCode(200);
    }

    @Transactional
    void persistMockUser(User mockUser) {
        userRepository.persist(mockUser);

        Log.infof("Persisted mock user: %s", mockUser.toString());
    }

}