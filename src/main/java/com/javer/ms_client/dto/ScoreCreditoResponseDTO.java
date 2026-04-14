package com.javer.ms_client.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScoreCreditoResponseDTO {

    private Long id;
    private String nome;
    private Float saldoCc;
    private Float scoreCalculado;
}
