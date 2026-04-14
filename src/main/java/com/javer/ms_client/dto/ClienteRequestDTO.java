package com.javer.ms_client.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClienteRequestDTO {

    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    @NotNull(message = "Telefone é obrigatório")
    private Long telefone;

    @NotNull(message = "Correntista é obrigatório")
    private Boolean correntista;

    @NotNull(message = "Score de crédito é obrigatório")
    private Float scoreCredito;

    @NotNull(message = "Saldo CC é obrigatório")
    private Float saldoCc;
}