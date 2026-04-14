package com.javer.ms_client.service;

import com.javer.ms_client.client.StorageClient;
import com.javer.ms_client.dto.ClienteRequestDTO;
import com.javer.ms_client.dto.ClienteResponseDTO;
import com.javer.ms_client.dto.ScoreCreditoResponseDTO;
import com.javer.ms_client.exception.ClienteNotFoundException;
import com.javer.ms_client.service.ClienteServiceImpl;
import feign.FeignException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteServiceImplTest {

    @Mock
    private StorageClient storageClient;

    @InjectMocks
    private ClienteServiceImpl clienteService;

    private ClienteResponseDTO responseDTO;
    private ClienteRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        responseDTO = ClienteResponseDTO.builder()
                .id(1L)
                .nome("Joao Silva")
                .telefone(11999998888L)
                .correntista(true)
                .scoreCredito(750.0f)
                .saldoCc(2500.0f)
                .build();

        requestDTO = ClienteRequestDTO.builder()
                .nome("Joao Silva")
                .telefone(11999998888L)
                .correntista(true)
                .scoreCredito(750.0f)
                .saldoCc(2500.0f)
                .build();
    }

    @Test
    void criar_deveRetornarResponseDTO_quandoChamadaFeign() {
        when(storageClient.criar(any(ClienteRequestDTO.class))).thenReturn(responseDTO);

        ClienteResponseDTO response = clienteService.criar(requestDTO);

        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getNome()).isEqualTo("Joao Silva");
        verify(storageClient, times(1)).criar(any(ClienteRequestDTO.class));
    }

    @Test
    void buscarPorId_deveRetornarCliente_quandoIdExiste() {
        when(storageClient.buscarPorId(1L)).thenReturn(responseDTO);

        ClienteResponseDTO response = clienteService.buscarPorId(1L);

        assertThat(response.getId()).isEqualTo(1L);
        verify(storageClient, times(1)).buscarPorId(1L);
    }

    @Test
    void buscarPorId_deveLancarClienteNotFoundException_quandoFeignRetorna404() {
        when(storageClient.buscarPorId(99L))
                .thenThrow(FeignException.NotFound.class);

        assertThatThrownBy(() -> clienteService.buscarPorId(99L))
                .isInstanceOf(ClienteNotFoundException.class)
                .hasMessageContaining("99");
    }

    @Test
    void listarTodos_deveRetornarLista() {
        when(storageClient.listarTodos()).thenReturn(List.of(responseDTO));

        List<ClienteResponseDTO> response = clienteService.listarTodos();

        assertThat(response).hasSize(1);
        assertThat(response.get(0).getNome()).isEqualTo("Joao Silva");
        verify(storageClient, times(1)).listarTodos();
    }

    @Test
    void atualizar_deveRetornarClienteAtualizado_quandoIdExiste() {
        when(storageClient.atualizar(eq(1L), any(ClienteRequestDTO.class)))
                .thenReturn(responseDTO);

        ClienteResponseDTO response = clienteService.atualizar(1L, requestDTO);

        assertThat(response.getId()).isEqualTo(1L);
        verify(storageClient, times(1)).atualizar(eq(1L), any(ClienteRequestDTO.class));
    }

    @Test
    void atualizar_deveLancarClienteNotFoundException_quandoFeignRetorna404() {
        when(storageClient.atualizar(eq(99L), any(ClienteRequestDTO.class)))
                .thenThrow(FeignException.NotFound.class);

        assertThatThrownBy(() -> clienteService.atualizar(99L, requestDTO))
                .isInstanceOf(ClienteNotFoundException.class)
                .hasMessageContaining("99");
    }

    @Test
    void deletar_deveChamarStorageClient_quandoIdExiste() {
        doNothing().when(storageClient).deletar(1L);

        assertThatCode(() -> clienteService.deletar(1L))
                .doesNotThrowAnyException();

        verify(storageClient, times(1)).deletar(1L);
    }

    @Test
    void deletar_deveLancarClienteNotFoundException_quandoFeignRetorna404() {
        doThrow(FeignException.NotFound.class).when(storageClient).deletar(99L);

        assertThatThrownBy(() -> clienteService.deletar(99L))
                .isInstanceOf(ClienteNotFoundException.class)
                .hasMessageContaining("99");
    }

    @Test
    void calcularScoreCredito_deveRetornarScoreCorreto_quandoIdExiste() {
        when(storageClient.buscarPorId(1L)).thenReturn(responseDTO);

        ScoreCreditoResponseDTO response = clienteService.calcularScoreCredito(1L);

        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getNome()).isEqualTo("Joao Silva");
        assertThat(response.getSaldoCc()).isEqualTo(2500.0f);
        assertThat(response.getScoreCalculado()).isEqualTo(250.0f);
    }

    @Test
    void calcularScoreCredito_deveLancarClienteNotFoundException_quandoFeignRetorna404() {
        when(storageClient.buscarPorId(99L))
                .thenThrow(FeignException.NotFound.class);

        assertThatThrownBy(() -> clienteService.calcularScoreCredito(99L))
                .isInstanceOf(ClienteNotFoundException.class)
                .hasMessageContaining("99");
    }
}
