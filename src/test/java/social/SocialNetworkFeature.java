package social;

import io.restassured.RestAssured;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import social.infrastructure.Application;

@RunWith(SpringRunner.class)
@SpringBootTest(
    classes = Application.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
public class SocialNetworkFeature {

    private static final String BOB = "Bob";
    private static final String BOB_MESSAGE = "Hello World!";

    @LocalServerPort
    private int serverPort;

    @Test public void
    users_can_write_messages_to_a_timeline() {
        RestAssured
            .given()
                .port(serverPort)
                .body(BOB_MESSAGE)
                .post("/api/{user}/timeline", BOB)
            .then()
                .statusCode(HttpStatus.CREATED.value());

        MessageRepository messageRepository = new MessageRepository();
        Assertions.assertThat(messageRepository.messagesFor(BOB)).containsExactly(BOB_MESSAGE);
    }
}
