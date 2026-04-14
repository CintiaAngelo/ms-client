package com.javer.ms_client.service;

import com.javer.ms_client.client.StorageClient;
import com.javer.ms_client.dto.ClienteRequestDTO;
import com.javer.ms_client.dto.ClienteResponseDTO;
import com.javer.ms_client.dto.ScoreCreditoResponseDTO;
import com.javer.ms_client.service.ClienteService;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.javer.ms_client.exception.ClienteNotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteService {

    private final StorageClient storageClient;

    @Override
    public ClienteResponseDTO criar(ClienteRequestDTO dto) {
        return storageClient.criar(dto);
    }

    @Override
    public ClienteResponseDTO buscarPorId(Long id) {
        try {
            return storageClient.buscarPorId(id);
        } catch (FeignException.NotFound e) {
            throw new ClienteNotFoundException(id);
        }
    }

    @Override
    public List<ClienteResponseDTO> listarTodos() {
        return storageClient.listarTodos();
    }

    @Override
    public ClienteResponseDTO atualizar(Long id, ClienteRequestDTO dto) {
        try {
            return storageClient.atualizar(id, dto);
        } catch (FeignException.NotFound e) {
            throw new ClienteNotFoundException(id);
        }
    }

    @Override
    public void deletar(Long id) {
        try {
            storageClient.deletar(id);
        } catch (FeignException.NotFound e) {
            throw new ClienteNotFoundException(id);
        }
    }

    @Override
    public ScoreCreditoResponseDTO calcularScoreCredito(Long id) {
        try {
            ClienteResponseDTO cliente = storageClient.buscarPorId(id);
            Float scoreCalculado = cliente.getSaldoCc() * 0.1f;

            return ScoreCreditoResponseDTO.builder()
                    .id(cliente.getId())
                    .nome(cliente.getNome())
                    .saldoCc(cliente.getSaldoCc())
                    .scoreCalculado(scoreCalculado)
                    .build();
        } catch (FeignException.NotFound e) {
            throw new ClienteNotFoundException(id);
        }
    }
}
