package com.leodelmiro.proposal.wallet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.leodelmiro.proposal.cards.CardsClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
@AutoConfigureMockMvc
class AssociateWalletControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CardsClient cardsClient;

    @PersistenceContext
    private EntityManager entityManager;

    private WalletRequest request;
    private String jsonBody;


    @BeforeEach
    void init() throws JsonProcessingException {
        request = new WalletRequest("email@email.com", WalletServices.PAYPAL);
        jsonBody = objectMapper.writeValueAsString(request);
    }

    @Test
    @DisplayName("deveria retornar 201 quando tudo estiver Ok")
    @WithMockUser
    void shouldReturn201WhenOk() throws Exception {
        when(cardsClient.walletAssociation(eq("5209-1622-1164-9999"), any())).thenReturn(new WalletResponse("ASSOCIADA", "AAAA-BBBB-CCCC-DDDD"));

        mockMvc.perform(post("/api/cards/3/wallets")
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));

        Wallet result = entityManager.find(Wallet.class, 2L);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(request.getEmail(), ReflectionTestUtils.getField(result, "email"));
        Assertions.assertEquals(request.getWallet(), result.getWalletService());
    }

    @Test
    @DisplayName("deveria retornar 400 quando alguns dado informado for inválido")
    @WithMockUser
    void shouldReturn400WhenAnyInvalidInputData() throws Exception {
        WalletRequest request = new WalletRequest("  ", WalletServices.PAYPAL);
        String jsonContent = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/api/cards/2/wallets")
                .content(jsonContent)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());

        Wallet result = entityManager.find(Wallet.class, 2L);
        Assertions.assertNull(result);

    }

    @Test
    @DisplayName("deveria retornar 404 quando cartão não for encontrado")
    @WithMockUser
    void shouldReturn404WhenCardNotFound() throws Exception {
        mockMvc.perform(post("/api/cards/1000/wallets")
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound());

        Wallet result = entityManager.find(Wallet.class, 1000L);
        Assertions.assertNull(result);
    }

    @Test
    @DisplayName("deveria retornar 422 quando já existir cartão associado a mesma carteira")
    @WithMockUser
    void shouldReturn422WhenAssociateCardToSameWalletService() throws Exception {
        mockMvc.perform(post("/api/cards/2/wallets")
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isUnprocessableEntity());
    }

    @Test
    @DisplayName("deveria retornar 201 quando não for o mesmo tipo de carteira")
    @WithMockUser
    void shouldReturn201WhenAssociateCardToOtherWalletService() throws Exception {
        WalletRequest request = new WalletRequest("email@email.com", WalletServices.SAMSUNG_PAY);
        String jsonContent = objectMapper.writeValueAsString(request);
        when(cardsClient.walletAssociation(eq("5209-1622-1164-9999"), any())).thenReturn(new WalletResponse("ASSOCIADA", "AAAA-BBBB-CCCC-DDDD"));

        mockMvc.perform(post("/api/cards/2/wallets")
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isUnprocessableEntity());
    }

}