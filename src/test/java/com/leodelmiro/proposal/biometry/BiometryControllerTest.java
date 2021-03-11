package com.leodelmiro.proposal.biometry;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class BiometryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    String localhost = "http://localhost/api";

    @Test
    @DisplayName("deve retornar 201 quando tudo Ok e Location com o caminho")
    @WithMockUser
    void shouldReturn201() throws Exception {
        NewBiometryRequest request = new NewBiometryRequest("ZmluZ2VycHJpbnQ=");
        String jsonBody = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/api/biometrics/0")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(header().string("Location", localhost + "/biometrics/0/1"));
    }

    @Test
    @DisplayName("deve retornar 400 quando algum dado for inválido")
    @WithMockUser
    void shouldReturn400() throws Exception {
        NewBiometryRequest request = new NewBiometryRequest(" ");
        String jsonBody = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/api/biometrics/0")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("deve retornar 400 quando não for Base64")
    @WithMockUser
    void shouldReturn400WhenNotBase64Fingerprint() throws Exception {
        NewBiometryRequest request = new NewBiometryRequest(";;;;;;;;;;;;");
        String jsonBody = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/api/biometrics/0")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody))
                .andExpect(status().isBadRequest());
    }


    @Test
    @DisplayName("deve retornar 404 quando cartão informado não for encontrado")
    @WithMockUser
    void shouldReturn404WhenCardNotFound() throws Exception {
        NewBiometryRequest request = new NewBiometryRequest("ZmluZ2VycHJpbnQ=");
        String jsonBody = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/api/biometrics/100")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody))
                .andExpect(status().isNotFound());
    }



}
