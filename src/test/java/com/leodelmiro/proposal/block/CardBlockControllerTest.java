package com.leodelmiro.proposal.block;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.leodelmiro.proposal.cards.CardsClient;
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
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
class CardBlockControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CardsClient cardsClient;

    private CardBlockRequest request;
    private String jsonBody;

    @BeforeEach
    void init() throws JsonProcessingException {
        CardBlockRequest request = new CardBlockRequest("Teste");
        jsonBody = objectMapper.writeValueAsString(request);
    }

    @Test
    @DisplayName("Deveria bloquear cartão e retornar 200")
    @WithMockUser
    void blockCardShouldBlockCardWhenOk() throws Exception {
        when(cardsClient.blockCard(eq("2"), any())).thenReturn(new CardBlockResponse("BLOQUEADO"));

        mockMvc.perform(post("/api/cards/2/block")
                .header(HttpHeaders.USER_AGENT, "User-Agent")
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());

        CardBlock result = entityManager.find(CardBlock.class, 2L);
        Assertions.assertNotNull(result);
        Assertions.assertEquals("User-Agent", ReflectionTestUtils.getField(result, "userAgent"));
    }

    @Test
    @DisplayName("Deveria retornar 404 quando cartão não encontrado")
    @WithMockUser
    void blockCardShouldReturn404WhenCardNotFound() throws Exception {

        mockMvc.perform(post("/api/cards/1000/block")
                .header(HttpHeaders.USER_AGENT, "User-Agent")
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound());


        CardBlock result = entityManager.find(CardBlock.class, 1000L);
        Assertions.assertNull(result);
    }

    @ParameterizedTest
    @DisplayName("Deveria retornar 400 quando for passado algum dado invalido")
    @WithMockUser
    @CsvSource({"User-agent, ''", "'' , {'sistemaResponsavel': 'Teste'}"})
    void blockCardShouldReturn400WhenAnyInvalidParam(String userAgent, String jsonBody) throws Exception {

        mockMvc.perform(post("/api/cards/2/block")
                .header(HttpHeaders.USER_AGENT, userAgent)
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());


        CardBlock result = entityManager.find(CardBlock.class, 2L);
        Assertions.assertNull(result);
    }

    @Test
    @DisplayName("Deveria retornar 422 quando cartão já estiver bloqueado")
    @WithMockUser
    void blockCardShouldReturn422WhenCardAlreadyBlocked() throws Exception {

        mockMvc.perform(post("/api/cards/2/block")
                .header(HttpHeaders.USER_AGENT, "User-Agent")
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isUnprocessableEntity());

    }
}