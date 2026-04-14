package com.javer.ms_client.client;

import com.javer.ms_client.dto.ClienteRequestDTO;
import com.javer.ms_client.dto.ClienteResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "ms-storage", url = "${ms-storage.url}")
public interface StorageClient {

    @PostMapping("/clientes")
    ClienteResponseDTO criar(@RequestBody ClienteRequestDTO dto);

    @GetMapping("/clientes/{id}")
    ClienteResponseDTO buscarPorId(@PathVariable Long id);

    @GetMapping("/clientes")
    List<ClienteResponseDTO> listarTodos();

    @PutMapping("/clientes/{id}")
    ClienteResponseDTO atualizar(@PathVariable Long id, @RequestBody ClienteRequestDTO dto);

    @DeleteMapping("/clientes/{id}")
    void deletar(@PathVariable Long id);
}