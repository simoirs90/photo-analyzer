package photo;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;


import static io.restassured.RestAssured.given;

@QuarkusTest
class PhotoResourceTest extends CommonTestResource {

    @Test
    void singlePhotoUploadShouldReturn200() {
        given()
                .multiPart("file", loadImage(LOVELESS_JPG))
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
                .when()
                .post("/photos/upload")
                .then()
                .statusCode(200);
    }
}