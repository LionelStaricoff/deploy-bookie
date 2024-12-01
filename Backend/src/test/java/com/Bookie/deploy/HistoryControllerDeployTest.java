package com.Bookie.deploy;

import com.Bookie.dto.HistoryDtoRequest;
import com.Bookie.dto.HistoryDtoRequestUpdate;
import com.Bookie.dto.HistoryDtoResponse;
import com.Bookie.enums.GenreLiterary;
import com.Bookie.util.JsonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class HistoryControllerDeployTest {


    private TestRestTemplate testRestTemplate;

    private HttpHeaders headers;


    @Autowired
    private RestTemplateBuilder restTemplateBuilder;



    @BeforeEach
    void setUp() {
        restTemplateBuilder = restTemplateBuilder.rootUri("https://deploy-bookie-production.up.railway.app:");
        testRestTemplate = new TestRestTemplate(restTemplateBuilder);
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

    }

    @Test
    void getAllHistoties() {

        HttpEntity<String> request = new HttpEntity<>(headers);

        ResponseEntity<JsonNode> result = testRestTemplate.exchange("/api/v1/history/all", HttpMethod.GET, request, JsonNode.class);
        System.out.println("getAllHistoties = " + result);

        assertAll(
                () -> assertEquals(HttpStatus.OK, result.getStatusCode()),
                () -> assertEquals(200, result.getStatusCode().value()),
                () -> assertTrue(!result.getBody().isEmpty())
        );
    }

    @Test
    void crateHistory() throws JsonProcessingException {

        HistoryDtoRequest HistoryDtoRequest = new HistoryDtoRequest("Historia del monte embrujado",
                "Encuantro sercano con almas en pena", 1L, GenreLiterary.NOVELA, "http://portada.jpg","ARGENTINA","BUENOS AIRES");

        String json = """
                {
                    "title": "Historia del monte embrujado",
                    "synopsis": "Encuantro cercano con almas en pena",
                    "creator_id": 11,
                    "genre": "NOVELA",
                    "img": "http://portada.jpg",
                     "country" :"ARGENTINA",
                    "province": "BUENOS AIRES"
                
                    
                }
                            
                """;
        HttpEntity<String> request = new HttpEntity<>(json, headers);
        ResponseEntity<HistoryDtoResponse> crateHistoryResult = testRestTemplate.exchange("/api/v1/history", HttpMethod.POST, request, HistoryDtoResponse.class);

        System.out.println("HistoryDtoRequest = " + json);
        JsonUtil.toJsonPrint("crateHistoryResult",crateHistoryResult);


        assertAll(
                () -> assertEquals(HttpStatus.CREATED, crateHistoryResult.getStatusCode()),
                () -> assertEquals(201, crateHistoryResult.getStatusCode().value()),
                () -> assertEquals(crateHistoryResult.getBody().title(), HistoryDtoRequest.title()),
                () -> assertEquals(crateHistoryResult.getBody().genre(), HistoryDtoRequest.genre())
        );
    }


    @Test
    void updateHistory() {

        HistoryDtoRequestUpdate HistoryDtoRequest = new HistoryDtoRequestUpdate("Historia del monte embrujado2",
                "Encuantro sercano con almas en pena2", GenreLiterary.NOVELA, "http://portada2.jpg");

        String json = """
                {
                 "title": "Historia del monte embrujado2",
                    "synopsis": "Encuantro cercano con almas en pena2",
                    "genre": "NOVELA",
                    "img": "http://portada2.jpg"
                }
                            
                """;
        HttpEntity<String> request = new HttpEntity<>(json, headers);
        ResponseEntity<HistoryDtoResponse> crateHistoryResult = testRestTemplate.exchange("/api/v1/history/28", HttpMethod.PUT, request, HistoryDtoResponse.class);
        System.out.println("updateHistory = " + crateHistoryResult);
        System.out.println("HistoryDtoRequest = " + json);

        assertAll(
                () -> assertEquals(HttpStatus.OK, crateHistoryResult.getStatusCode()),
                () -> assertEquals(200, crateHistoryResult.getStatusCode().value()),
                () -> assertEquals(crateHistoryResult.getBody().title(), HistoryDtoRequest.title()),
                () -> assertEquals(crateHistoryResult.getBody().genre(), HistoryDtoRequest.genre()),
                () -> assertEquals(crateHistoryResult.getBody().id(), 28)
        );
    }


    @Test
    void deleteHistory() {

        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<String> crateHistoryResult = testRestTemplate.exchange("/api/v1/history/4", HttpMethod.DELETE, request, String.class);
        System.out.println("updateHistory = " + crateHistoryResult);


        assertAll(
                () -> assertEquals(HttpStatus.ACCEPTED, crateHistoryResult.getStatusCode()),
                () -> assertEquals(202, crateHistoryResult.getStatusCode().value()),
                () -> assertEquals(crateHistoryResult.getBody(), "Delete ok")

        );
    }


    @Test
    void gethHistoryByIdAndtheirChapters() {

        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<HistoryDtoResponse> crateHistoryResult = testRestTemplate.exchange("/api/v1/history/28", HttpMethod.GET, request, HistoryDtoResponse.class);
        System.out.println("updateHistory = " + crateHistoryResult);


        assertAll(
                () -> assertEquals(HttpStatus.OK, crateHistoryResult.getStatusCode()),
                () -> assertEquals(200, crateHistoryResult.getStatusCode().value()),
                () -> assertEquals(crateHistoryResult.getBody().id(),28)

        );
    }


    @Test
    void gethHistoryByUserId() throws JsonProcessingException {

        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<JsonNode> result = testRestTemplate.exchange("/api/v1/history/user/1", HttpMethod.GET, request, JsonNode.class);

        JsonUtil.toJsonPrint("List<history> historybyuser",result);


        assertAll(
                () -> assertEquals(HttpStatus.OK, result.getStatusCode()),
                () -> assertEquals(200, result.getStatusCode().value()),
                () -> assertTrue(!result.getBody().isEmpty())

        );
    }



}