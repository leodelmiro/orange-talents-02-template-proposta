package com.leodelmiro.proposal.block;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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

    @Test
    @DisplayName("Deveria bloquear cartão e retornar 200")
    @WithMockUser
    void blockCardShouldBlockCardWhenOk() throws Exception {

        mockMvc.perform(post("/api/cards/2/block")
                .header(HttpHeaders.USER_AGENT, "User-Agent")
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
        ).andExpect(status().isNotFound());

        CardBlock result = entityManager.find(CardBlock.class, 1000L);
        Assertions.assertNull(result);
    }

    @Test
    @DisplayName("Deveria retornar 400 quando for passado algum dado invalido")
    @WithMockUser
    void blockCardShouldReturn400WhenAnyInvalidParam() throws Exception {

        mockMvc.perform(post("/api/cards/2/block")
                .header(HttpHeaders.USER_AGENT, "   ")
        ).andExpect(status().isBadRequest());

        CardBlock result = entityManager.find(CardBlock.class, 2L);
        Assertions.assertNull(result);
    }

    @Test
    @DisplayName("Deveria retornar 422 quando cartão já estiver bloqueado")
    @WithMockUser
    void blockCardShouldReturn422WhenCardAlreadyBlocked() throws Exception {

        mockMvc.perform(post("/api/cards/1/block")
                .header(HttpHeaders.USER_AGENT, "User-Agent")
        ).andExpect(status().isUnprocessableEntity());
    }
}