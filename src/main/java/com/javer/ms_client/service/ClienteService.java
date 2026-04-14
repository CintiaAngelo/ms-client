package com.javer.ms_client.service;

import com.javer.ms_client.dto.ClienteRequestDTO;
import com.javer.ms_client.dto.ClienteResponseDTO;
import com.javer.ms_client.dto.ScoreCreditoResponseDTO;

import java.util.List;

public interface ClienteService {
    ClienteResponseDTO criar(ClienteRequestDTO dto);
    ClienteResponseDTO buscarPorId(Long id);
    List<ClienteResponseDTO> listarTodos();
    ClienteResponseDTO atualizar(Long id, ClienteRequestDTO dto);
    void deletar(Long id);
    ScoreCreditoResponseDTO calcularScoreCredito(Long id);
}