package com.javer.ms_client.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class ClienteRequestDTOTest {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void devePassarValidacao_quandoTodosCamposValidos() {
        ClienteRequestDTO dto = ClienteRequestDTO.builder()
                .nome("Joao Silva")
                .telefone(11999998888L)
                .correntista(true)
                .scoreCredito(750.0f)
                .saldoCc(2500.0f)
                .build();

        Set<ConstraintViolation<ClienteRequestDTO>> violations = validator.validate(dto);

        assertThat(violations).isEmpty();
    }

    @Test
    void deveFalharValidacao_quandoNomeNulo() {
        ClienteRequestDTO dto = ClienteRequestDTO.builder()
                .nome(null)
                .telefone(11999998888L)
                .correntista(true)
                .scoreCredito(750.0f)
                .saldoCc(2500.0f)
                .build();

        Set<ConstraintViolation<ClienteRequestDTO>> violations = validator.validate(dto);

        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("nome"));
    }

    @Test
    void deveFalharValidacao_quandoTodosCamposNulos() {
        ClienteRequestDTO dto = ClienteRequestDTO.builder().build();

        Set<ConstraintViolation<ClienteRequestDTO>> violations = validator.validate(dto);

        assertThat(violations).hasSize(5);
    }
}