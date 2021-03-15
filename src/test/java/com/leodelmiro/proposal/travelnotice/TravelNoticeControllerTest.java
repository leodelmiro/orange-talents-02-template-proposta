package com.leodelmiro.proposal.travelnotice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@ActiveProfiles("test")
class TravelNoticeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @PersistenceContext
    private EntityManager entityManager;

    private String jsonBody;

    @BeforeEach
    void init() throws JsonProcessingException {
        TravelNoticeRequest request = new TravelNoticeRequest("Teste", LocalDate.MAX);
        jsonBody = objectMapper.writeValueAsString(request);
    }

    @Test
    @DisplayName("deveria retornar 200 quando tudo Ok")
    @WithMockUser
    void shouldReturn200WhenOk() throws Exception {
        mockMvc.perform(post("/api/cards/2/travelnotices")
                .header(HttpHeaders.USER_AGENT, "User-Agent")
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());

        TravelNotice result = entityManager.find(TravelNotice.class, 1L);
        Assertions.assertNotNull(result);
        Assertions.assertEquals("Teste", result.getDestiny());
        Assertions.assertEquals("User-Agent", result.getUserAgent());
        Assertions.assertEquals(LocalDate.MAX, result.getEndDate());
    }

    @ParameterizedTest
    @DisplayName("deveria retornar 400 quando algum dado for inválido ou não informado")
    @WithMockUser
    @CsvSource({", 2999-03-15, User-Agent", "Brasil, , User-Agent", "Brasil, 2999-03-15, ''"})
    void shouldReturn400WhenAnyInvalidData(String destiny, LocalDate endDate, String userAgent) throws Exception {
        TravelNoticeRequest request = new TravelNoticeRequest(destiny, endDate);
        String jsonContent = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/api/cards/2/travelnotices")
                .header(HttpHeaders.USER_AGENT, userAgent)
                .content(jsonContent)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());

        TravelNotice result = entityManager.find(TravelNotice.class, 1L);
        Assertions.assertNull(result);
    }

    @Test
    @DisplayName("deveria retornar 404 quando cartão não encontrado")
    @WithMockUser
    void shouldReturn404WhenCardNotFound() throws Exception {
        mockMvc.perform(post("/api/cards/1000/travelnotices")
                .header(HttpHeaders.USER_AGENT, "User-Agent")
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound());

        TravelNotice result = entityManager.find(TravelNotice.class, 1000L);
        Assertions.assertNull(result);
    }

}