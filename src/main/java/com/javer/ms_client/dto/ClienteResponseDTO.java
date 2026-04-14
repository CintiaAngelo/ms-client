package com.javer.ms_client.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClienteResponseDTO {

    private Long id;
    private String nome;
    private Long telefone;
    private Boolean correntista;
    private Float scoreCredito;
    private Float saldoCc;
}