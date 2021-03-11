package com.leodelmiro.proposal.proposal;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leodelmiro.proposal.builders.NewProposalRequestBuilder;
import com.leodelmiro.proposal.builders.ProposalRequesterAddressRequestBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
public class ProposalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    ProposalRepository proposalRepository;

    String localhost = "http://localhost/api";

    @Test
    @DisplayName("deve retornar 201 quando tudo Ok e Location com o caminho")
    @WithMockUser
    void shouldReturn201() throws Exception {
        NewProposalRequest request = new NewProposalRequestBuilder().defaultValues().build();
        String jsonBody = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/api/proposals")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));

        Proposal result = proposalRepository.findByDocument(request.getDocument()).get();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(request.getName(), result.getName());
        Assertions.assertEquals(request.getDocument(), result.getDocument());
        Assertions.assertEquals(request.getEmail(), result.getEmail());
        Assertions.assertEquals(request.getAddress().getAddress(), result.getAddress().getAddress());
        Assertions.assertEquals(request.getAddress().getNumber(), result.getAddress().getNumber());
        Assertions.assertEquals(request.getAddress().getCep(), result.getAddress().getCep());
        Assertions.assertEquals(request.getSalary(), result.getSalary());
    }

    @Test
    @DisplayName("deve retornar 400 quando documento for inválido")
    @WithMockUser
    void shouldReturn400WhenDocumentIsNotCPFOrCNPJValid() throws Exception {
        RequesterAddressRequest address = new ProposalRequesterAddressRequestBuilder().defaultValues().build();
        NewProposalRequest request = new NewProposalRequestBuilder().withDocument("").withEmail("teste@teste.com")
                .withName("Testador").withAddress(address).withSalary(BigDecimal.TEN).build();
        String jsonBody = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/api/proposals")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody))
                .andExpect(status().isBadRequest());

        Optional<Proposal> result = proposalRepository.findByDocument(request.getDocument());
        Assertions.assertTrue(result.isEmpty());
    }


    @ParameterizedTest
    @CsvSource({"test", ","})
    @DisplayName("deve retornar 400 quando documento for inválido ou nulo")
    @WithMockUser
    void shouldReturn400WhenEmailInvalid(String email) throws Exception {
        RequesterAddressRequest address = new ProposalRequesterAddressRequestBuilder().defaultValues().build();
        NewProposalRequest request = new NewProposalRequestBuilder().withDocument("404.761.395-97").withEmail(email)
                .withName("Testador").withAddress(address).withSalary(BigDecimal.TEN).build();
        String jsonBody = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/api/proposals")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody))
                .andExpect(status().isBadRequest());

        Optional<Proposal> result = proposalRepository.findByDocument(request.getDocument());
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("deve retornar 422 quando documento já possuir uma proposta")
    @WithMockUser
    void shouldReturn422WhenDocumentAlreadyExists() throws Exception {
        RequesterAddressRequest address = new ProposalRequesterAddressRequestBuilder().defaultValues().build();
        NewProposalRequest request = new NewProposalRequestBuilder().withDocument("455.239.470-32").withEmail("teste@teste.com")
                .withName("Testador").withAddress(address).withSalary(BigDecimal.TEN).build();
        String jsonBody = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/api/proposals")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody))
                .andExpect(status().isUnprocessableEntity());
    }


    @Test
    @DisplayName("deve retornar 400 quando nome for vazio")
    @WithMockUser
    void shouldReturn400WhenNameBlank() throws Exception {
        RequesterAddressRequest address = new ProposalRequesterAddressRequestBuilder().defaultValues().build();
        NewProposalRequest request = new NewProposalRequestBuilder().withDocument("404.761.395-97").withEmail("teste@teste.com")
                .withName(" ").withAddress(address).withSalary(BigDecimal.TEN).build();
        String jsonBody = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/api/proposals")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody))
                .andExpect(status().isBadRequest());

        Optional<Proposal> result = proposalRepository.findByDocument(request.getDocument());
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("deve retornar 400 quando endereco for nulo")
    @WithMockUser
    void shouldReturn400WhenAddressNull() throws Exception {
        NewProposalRequest request = new NewProposalRequestBuilder().withDocument("404.761.395-97").withEmail("teste@teste.com")
                .withName("Testado").withAddress(null).withSalary(BigDecimal.TEN).build();
        String jsonBody = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/api/proposals")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody))
                .andExpect(status().isBadRequest());

        Optional<Proposal> result = proposalRepository.findByDocument(request.getDocument());
        Assertions.assertTrue(result.isEmpty());
    }

    @ParameterizedTest
    @CsvSource({",", "-2000"})
    @DisplayName("deve retornar 400 quando salario for nulo ou negativo")
    @WithMockUser
    void shouldReturn400WhenSalaryNullOrNegative(BigDecimal salary) throws Exception {
        RequesterAddressRequest address = new ProposalRequesterAddressRequestBuilder().defaultValues().build();
        NewProposalRequest request = new NewProposalRequestBuilder().withDocument("404.761.395-97").withEmail("teste@teste.com")
                .withName("Testado").withAddress(address).withSalary(salary).build();
        String jsonBody = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/api/proposals")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody))
                .andExpect(status().isBadRequest());

        Optional<Proposal> result = proposalRepository.findByDocument(request.getDocument());
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("deve retornar 200 e proposta quando encontrada")
    @WithMockUser
    void shouldReturn200AndProposalResponseWhenProposalFound() throws Exception {

        mockMvc.perform(get("/api/proposals/0")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("name").exists());
    }

    @Test
    @DisplayName("deve retornar 404 quando proposta não encontrada")
    @WithMockUser
    void shouldReturn404WhenProposalNotFound() throws Exception {

        mockMvc.perform(get("/api/proposals/100"))
                .andExpect(status().isNotFound());
    }
}
