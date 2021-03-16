package com.leodelmiro.proposal.travelnotice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.leodelmiro.proposal.block.CardBlockResponse;
import com.leodelmiro.proposal.cards.CardsClient;
import feign.FeignException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
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

    @MockBean
    private CardsClient cardsClient;

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
        when(cardsClient.notices(eq("5209-1622-1164-6666"), any())).thenReturn(new TravelNoticesApiResponse("CRIADO"));

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

    @Test
    @DisplayName("deveria retornar 422 quando der erro na API externa")
    @WithMockUser
    void shouldReturn422WhenExternalApiError() throws Exception {
        when(cardsClient.notices(eq("2"), any())).thenThrow(FeignException.class);

        mockMvc.perform(post("/api/cards/2/travelnotices")
                .header(HttpHeaders.USER_AGENT, "User-Agent")
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isUnprocessableEntity());

        TravelNotice result = entityManager.find(TravelNotice.class, 1L);
        Assertions.assertNull(result);
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