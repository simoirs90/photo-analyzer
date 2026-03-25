package photo;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


import static io.restassured.RestAssured.given;

@QuarkusTest
class PhotoResourceTest extends CommonTestResource {

    @BeforeEach
    void init() {

    }

    @Test
    void singlePhotoUploadShouldReturn200() {
        given()
                .multiPart("file", loadImage(LOVELESS_JPG))
                .multiPart("sourceType", "PROFILE")
                .multiPart("userId", "1")
                .when()
                .post("/photos/upload")
                .then()
                .statusCode(200);
    }

    @Test
    void multiplePhotoUploadShouldReturn200() {
        given()
                .multiPart("file", loadImage(LOVELESS_JPG))
                .multiPart("file", loadImage(RAVEN_PNG))
                .multiPart("sourceType", "PROFILE")
                .multiPart("userId", "1")
                .when()
                .post("/photos/upload")
                .then()
                .statusCode(200);
    }
}