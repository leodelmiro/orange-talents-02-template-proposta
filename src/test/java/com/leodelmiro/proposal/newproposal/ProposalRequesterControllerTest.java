package com.leodelmiro.proposal.newproposal;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leodelmiro.builders.NewProposalRequesterRequestBuilder;
import com.leodelmiro.builders.RequesterAddressRequestBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
public class ProposalRequesterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @PersistenceContext
    private EntityManager entityManager;

    @InjectMocks
    private ProposalController proposalController;

    String localhost = "http://localhost";

    @Test
    @DisplayName("deve retornar 201 quando tudo Ok e Location com o caminho")
    void shouldReturn201() throws Exception {
        NewProposalRequesterRequest request = new NewProposalRequesterRequestBuilder().defaultValues().build();
        String jsonBody = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/proposals")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(header().string("Location", localhost + "/proposals/1"));
    }

    @Test
    @DisplayName("deve retornar 400 quando algum dado for inválido")
    void shouldReturn400() throws Exception {
        NewProposalRequesterRequest request = new NewProposalRequesterRequestBuilder().build();
        String jsonBody = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/proposals")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("deve retornar 400 quando documento for inválido")
    void shouldReturn400WhenDocumentIsNotCPFOrCNPJValid() throws Exception {
        RequesterAddressRequest address = new RequesterAddressRequestBuilder().defaultValues().build();
        NewProposalRequesterRequest request = new NewProposalRequesterRequestBuilder().withDocument("").withEmail("teste@teste.com")
                .withName("Testador").withAddress(address).withSalary(BigDecimal.TEN).build();
        String jsonBody = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/proposals")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody))
                .andExpect(status().isBadRequest());
    }


    @ParameterizedTest
    @CsvSource({"test", ","})
    @DisplayName("deve retornar 400 quando documento for inválido ou nulo")
    void shouldReturn400WhenEmailInvalid(String email) throws Exception {
        RequesterAddressRequest address = new RequesterAddressRequestBuilder().defaultValues().build();
        NewProposalRequesterRequest request = new NewProposalRequesterRequestBuilder().withDocument("404.761.395-97").withEmail(email)
                .withName("Testador").withAddress(address).withSalary(BigDecimal.TEN).build();
        String jsonBody = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/proposals")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("deve retornar 422 quando documento já possuir uma proposta")
    void shouldReturn422WhenDocumentAlreadyExists() throws Exception {
        RequesterAddressRequest address = new RequesterAddressRequestBuilder().defaultValues().build();
        NewProposalRequesterRequest request = new NewProposalRequesterRequestBuilder().withDocument("404.761.395-97").withEmail("teste@teste.com")
                .withName("Testador").withAddress(address).withSalary(BigDecimal.TEN).build();
        String jsonBody = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/proposals")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody));

        mockMvc.perform(post("/proposals")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody))
                .andExpect(status().isUnprocessableEntity());
    }


    @Test
    @DisplayName("deve retornar 400 quando nome for vazio")
    void shouldReturn400WhenNameBlank() throws Exception {
        RequesterAddressRequest address = new RequesterAddressRequestBuilder().defaultValues().build();
        NewProposalRequesterRequest request = new NewProposalRequesterRequestBuilder().withDocument("404.761.395-97").withEmail("teste@teste.com")
                .withName(" ").withAddress(address).withSalary(BigDecimal.TEN).build();
        String jsonBody = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/proposals")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("deve retornar 400 quando endereco for nulo")
    void shouldReturn400WhenAddressNull() throws Exception {
        NewProposalRequesterRequest request = new NewProposalRequesterRequestBuilder().withDocument("404.761.395-97").withEmail("teste@teste.com")
                .withName("Testado").withAddress(null).withSalary(BigDecimal.TEN).build();
        String jsonBody = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/proposals")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody))
                .andExpect(status().isBadRequest());
    }

    @ParameterizedTest
    @CsvSource({",", "-2000"})
    @DisplayName("deve retornar 400 quando salario for nulo ou negativo")
    void shouldReturn400WhenSalaryNullOrNegative(BigDecimal salary) throws Exception {
        RequesterAddressRequest address = new RequesterAddressRequestBuilder().defaultValues().build();
        NewProposalRequesterRequest request = new NewProposalRequesterRequestBuilder().withDocument("404.761.395-97").withEmail("teste@teste.com")
                .withName("Testado").withAddress(address).withSalary(salary).build();
        String jsonBody = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/proposals")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody))
                .andExpect(status().isBadRequest());
    }
}
