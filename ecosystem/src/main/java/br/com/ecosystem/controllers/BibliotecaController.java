package br.com.ecosystem.controllers;

import br.com.ecosystem.dtos.BibliotecaDto;
import br.com.ecosystem.services.BibliotecaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/biblioteca")
public class BibliotecaController {

    private final BibliotecaService bibliotecaService;

    @Autowired
    public BibliotecaController(BibliotecaService bibliotecaService) {
        this.bibliotecaService = bibliotecaService;
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<List<BibliotecaDto>> uploadCsv(@RequestParam("file") MultipartFile file) {
        List<BibliotecaDto> trabalhos = bibliotecaService.processarCsv(file);
        return ResponseEntity.status(HttpStatus.CREATED).body(trabalhos);
    }

    @GetMapping
    public ResponseEntity<List<BibliotecaDto>> listarTodos() {
        List<BibliotecaDto> trabalhos = bibliotecaService.listarTodos();
        if (trabalhos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(trabalhos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BibliotecaDto> buscarPorId(@PathVariable String id) {
        Optional<BibliotecaDto> trabalho = bibliotecaService.buscarPorId(id);
        return trabalho.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<BibliotecaDto> atualizar(@PathVariable String id, @RequestBody BibliotecaDto bibliotecaDto) {
        BibliotecaDto atualizado = bibliotecaService.atualizar(id, bibliotecaDto);
        return ResponseEntity.status(HttpStatus.OK).body(atualizado);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<BibliotecaDto> atualizarParcialmente(@PathVariable String id, @RequestBody BibliotecaDto bibliotecaDto) {
        BibliotecaDto atualizado = bibliotecaService.atualizarParcialmente(id, bibliotecaDto);
        return ResponseEntity.status(HttpStatus.OK).body(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable String id) {
        bibliotecaService.deletar(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}