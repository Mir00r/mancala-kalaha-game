package com.bol.mancala;

import com.bol.mancala.Route.Router;
import com.bol.mancala.domains.games.models.dtos.GameDto;
import com.bol.mancala.domains.games.models.mappers.GameMapper;
import com.bol.mancala.domains.games.services.IGameService;
import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.net.URI;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith({SpringExtension.class})
@SpringBootTest(classes = {
        MancalaApplication.class,
        URI.class
}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MancalaApplicationTests extends AbstractTest {

    @Autowired
    private IGameService gameService;

    @Autowired
    private GameMapper gameMapper;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    void contextLoads() {
    }

//    @BeforeAll
//    public void setUp() {
//        this.gameService.save(this.gameMapper.mapAutomatically());
//    }

    @Test
    @Order(1)
    public void shouldReturnNotNullGameService() {
        Assertions.assertNotNull(gameService);
    }

    @Test
    @Order(2)
    public void shouldReturnNotNullGameMapper() {
        Assertions.assertNotNull(gameMapper);
    }

    @Test
    @Order(3)
    @DisplayName("Test create a new game with two players and 6 pits")
    public void testCreatNewGame() {
        ResponseEntity<GameDto> response = this.testRestTemplate.postForEntity(baseUrl + port + Router.CREATE_GAMES_AUTO, null, GameDto.class);

        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getBody());
        assertEquals(200, response.getStatusCodeValue());

        logger.info(response.getBody().toString());
        assertThat("If new games created successfully then game completion status must be false", response.getBody().isCompleted(), equalTo(false));
    }

    @Test
    @Order(4)
    @DisplayName("Test Get All Games")
    public void testGameBoard() throws IOException {

        // get created game api test
        ResponseEntity<String> response = this.testRestTemplate.getForEntity(baseUrl + port + Router.SEARCH_ALL_GAMES, String.class);
        assertEquals(200, response.getStatusCodeValue());

        JsonNode body = getJsonNode(response.getBody());
        logger.info(String.valueOf(body.withArray("content").size()));
        GameDto gameResponse = mapFromJson(body.withArray("content").get(0).toString(), GameDto.class);
        assertThat("Number of Games must be 1", body.withArray("content").size(), equalTo(1));
        assertThat("When initially game created player turn should be null", gameResponse.getPlayerTurn(), equalTo(null));

        // prepare api request for first player pieces move data
        String pieceMoveEndPoint = baseUrl + port + Router.getMovePiecesTestUrl(gameResponse.getId(), 1L, gameResponse.getPlayerOneId());
        RequestEntity<Void> request = getHttpPostRequest(pieceMoveEndPoint);

        // first any player piece move api test
        ResponseEntity<String> moveResponse = this.testRestTemplate.exchange(request, String.class);
        gameResponse = mapFromJson(moveResponse.getBody(), GameDto.class);
        assertEquals(200, moveResponse.getStatusCodeValue());

        // Player turn changes test
        assertThat("After move first pieces from any pit from any player.. player turn should be changed", gameResponse.getPlayerTurn().name(), equalTo("PlayerA"));

        // prepare api request for player pieces move data
        pieceMoveEndPoint = baseUrl + port + Router.getMovePiecesTestUrl(gameResponse.getId(), 2L, gameResponse.getPlayerOneId());
        request = getHttpPostRequest(pieceMoveEndPoint);

        // second player piece move api test
        moveResponse = this.testRestTemplate.exchange(request, String.class);
        gameResponse = mapFromJson(moveResponse.getBody(), GameDto.class);
        assertEquals(200, moveResponse.getStatusCodeValue());

        // Player turn changes test
        assertThat("After move first pieces from any pit from any player.. player turn should be changed", gameResponse.getPlayerTurn().name(), equalTo("PlayerB"));

        // prepare api request for main pit move
        pieceMoveEndPoint = baseUrl + port + Router.getMovePiecesTestUrl(gameResponse.getId(), 7L, gameResponse.getPlayerOneId());
        request = getHttpPostRequest(pieceMoveEndPoint);

        // wrong or no move for main pit
        moveResponse = this.testRestTemplate.exchange(request, String.class);
        assertEquals(500, moveResponse.getStatusCodeValue());

        // prepare api request for wrong pit access from wrong player
        pieceMoveEndPoint = baseUrl + port + Router.getMovePiecesTestUrl(gameResponse.getId(), 9L, gameResponse.getPlayerOneId());
        request = getHttpPostRequest(pieceMoveEndPoint);

        // wrong pit access from wrong player
        moveResponse = this.testRestTemplate.exchange(request, String.class);
        assertEquals(500, moveResponse.getStatusCodeValue());

        // prepare api request for second player next pit stones
        pieceMoveEndPoint = baseUrl + port + Router.getMovePiecesTestUrl(gameResponse.getId(), 10L, gameResponse.getPlayerTwoId());
        request = getHttpPostRequest(pieceMoveEndPoint);

        // updated game response
        moveResponse = this.testRestTemplate.exchange(request, String.class);
        assertEquals(200, moveResponse.getStatusCodeValue());
        // Player turn changes test
        assertThat("After move first pieces from any pit from any player.. player turn should be changed", gameResponse.getPlayerTurn().name(), equalTo("PlayerB"));
    }
}
