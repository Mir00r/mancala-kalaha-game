package com.bol.mancala;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.net.URI;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author mir00r on 18/2/22
 * @project IntelliJ IDEA
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {
        MancalaApplication.class
}, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
//@MockBean(classes = {
//        Instant.class
//})
public abstract class AbstractTest {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    protected String baseUrl = "http://localhost:";
    protected String ACCESS_TOKEN;

    @LocalServerPort
    protected int port;


    /**
     * Stringifies a pojo
     *
     * @param obj
     * @return
     * @throws JsonProcessingException
     */
    protected String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }

    /**
     * Maps json string to a POJO if possible.
     *
     * @param json
     * @param clazz
     * @param <T>
     * @return
     * @throws IOException
     */
    protected <T> T mapFromJson(String json, Class<T> clazz)
            throws IOException {

        ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules().disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return objectMapper.readValue(json, clazz);
    }

    /**
     * Returns the JsonNode object
     * created from a json string
     *
     * @param text
     * @return
     */
    protected JsonNode getJsonNode(String text) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readTree(text);
    }


    /**
     * simply returns host, port, uri
     *
     * @param uri
     * @return
     */
    protected String url(String uri) {
        return baseUrl + port + uri;
    }

    /**
     * adds access_token as param
     *
     * @param uri
     * @param authentication
     * @return
     */
    protected String url(String uri, String authentication) {
        if (authentication.equals("access_token")) {
            assertNotNull(ACCESS_TOKEN);
            Map<String, String> param = new LinkedHashMap<>(1);
            param.put("access_token", ACCESS_TOKEN);
            return setRequestParams(baseUrl + port + uri, param);
        }
        return null;
    }


    /**
     * set url parameters to uri. parameters are
     * received as Map like POJO.
     * <p>
     * <p>
     * Example
     * ```
     * Map<String, String> param = new HashMap<String, String>
     * param.put("first_name", "John);
     * param.put("last_name", "Wick);
     * ```
     * `www.example.com?first_name=John&last_name=Wick` will be returned
     * after calling `setRequestParams("www.example.com", param)`
     *
     * @param uri
     * @param params
     * @param <K>
     * @param <V>
     * @param <T>
     * @return
     */
    protected <K, V, T extends Map<K, V>> String setRequestParams(String uri, T params) {
        StringBuilder uriBuilder = new StringBuilder(uri + "?");
        for (K key : params.keySet()) {
            uriBuilder.append(key)
                    .append("=")
                    .append(params.get(key))
                    .append("&");
        }
        uri = uriBuilder.deleteCharAt(uriBuilder.lastIndexOf("&")).toString();
        return uri;
    }

    protected RequestEntity<Void> getHttpPostRequest(String endpoint) {
        return RequestEntity.post(URI.create(endpoint))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .header("accept", "*/*")
                .build();
    }
}
