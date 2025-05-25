package br.ufpb.ecosystem.controllers;

import br.ufpb.ecosystem.dtos.BibliographicSourceDTO;
import br.ufpb.ecosystem.security.SecurityConfig;
import br.ufpb.ecosystem.services.BibliographicSourceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/biblioteca")
@Tag(name = "Biblioteca", description = ("Controlador responsável por gerenciar os dados dos trabalhos acadêmicos, permitindo operações de cadastro, consulta, atualização e exclusão."))
@SecurityRequirement(name = SecurityConfig.SECURITY)
public class BibliographicSourceController {

    private final BibliographicSourceService bibliographicSourceService;

    @Autowired
    public BibliographicSourceController(BibliographicSourceService bibliographicSourceService) {
        this.bibliographicSourceService = bibliographicSourceService;
    }

//    @PostMapping("/cadastrar")
//    @Operation(
//            summary = "Cadastrar dados via arquivo CSV",
//            description = "Realiza o upload de um arquivo CSV contendo os dados dos trabalhos acadêmicos. Os dados são extraídos e salvos no banco de dados."
//    )
//    @ApiResponse(responseCode = "201", description = "Dados extraídos e salvos com sucesso.")
//    @ApiResponse(responseCode = "400", description = "Arquivo CSV vazio ou em formato inválido.")
//    @ApiResponse(responseCode = "500", description = "Erro interno no servidor ao processar o arquivo.")
//    public ResponseEntity<List<BibliotecaDto>> uploadCsv(@RequestParam("file") MultipartFile file) {
//        List<BibliotecaDto> trabalhos = bibliotecaService.processarCsv(file);
//        return ResponseEntity.status(HttpStatus.CREATED).body(trabalhos);
//    }

    @PostMapping("/inserir")
    @Operation(
            summary = "Inserir dados manualmente",
            description = "Permite o cadastro manual de um trabalho acadêmico, recebendo os dados no corpo da requisição."
    )
    @ApiResponse(responseCode = "200", description = "Dados inseridos e salvos com sucesso.")
    @ApiResponse(responseCode = "400", description = "Dados inválidos ou incompletos.")
    @ApiResponse(responseCode = "500", description = "Erro interno no servidor ao inserir os dados.")
    public ResponseEntity<BibliographicSourceDTO> inserir(@RequestBody BibliographicSourceDTO bibliographicSourceDto) {
        BibliographicSourceDTO novoTrabalho = bibliographicSourceService.inserir(bibliographicSourceDto);
        return ResponseEntity.ok(novoTrabalho);
    }

    @GetMapping
    @Operation(
            summary = "Listar todos os trabalhos",
            description = "Retorna uma lista com todos os trabalhos cadastrados no sistema."
    )
    @ApiResponse(responseCode = "200", description = "Lista de trabalhos obtida com sucesso.")
    @ApiResponse(responseCode = "204", description = "Nenhum trabalho encontrado.")
    @ApiResponse(responseCode = "500", description = "Erro interno no servidor ao buscar os dados.")
    public ResponseEntity<List<BibliographicSourceDTO>> listarTodos() {
        List<BibliographicSourceDTO> trabalhos = bibliographicSourceService.listarTodos();
        if (trabalhos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(trabalhos);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar trabalho por ID",
            description = "Busca um trabalho específico pelo seu identificador único."
    )
    @ApiResponse(responseCode = "200", description = "Trabalho encontrado com sucesso.")
    @ApiResponse(responseCode = "404", description = "Trabalho com o ID especificado não foi encontrado.")
    @ApiResponse(responseCode = "500", description = "Erro interno no servidor ao buscar o trabalho.")
    public ResponseEntity<BibliographicSourceDTO> buscarPorId(@PathVariable String id) {
        Optional<BibliographicSourceDTO> trabalho = bibliographicSourceService.buscarPorId(id);
        return trabalho.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Atualizar trabalho por completo",
            description = "Atualiza todos os campos de um trabalho existente com base no ID informado."
    )
    @ApiResponse(responseCode = "200", description = "Trabalho atualizado com sucesso.")
    @ApiResponse(responseCode = "404", description = "Trabalho com o ID especificado não foi encontrado.")
    @ApiResponse(responseCode = "500", description = "Erro interno no servidor ao atualizar os dados.")
    public ResponseEntity<BibliographicSourceDTO> atualizar(@PathVariable String id, @RequestBody BibliographicSourceDTO bibliographicSourceDto) {
        BibliographicSourceDTO atualizado = bibliographicSourceService.atualizar(id, bibliographicSourceDto);
        return ResponseEntity.status(HttpStatus.OK).body(atualizado);
    }

    @PatchMapping("/{id}")
    @Operation(
            summary = "Atualizar trabalho parcialmente",
            description = "Atualiza parcialmente os campos de um trabalho, permitindo modificar apenas os dados informados no corpo da requisição."
    )
    @ApiResponse(responseCode = "200", description = "Trabalho atualizado com sucesso.")
    @ApiResponse(responseCode = "404", description = "Trabalho com o ID especificado não foi encontrado.")
    @ApiResponse(responseCode = "500", description = "Erro interno no servidor ao atualizar os dados.")
    public ResponseEntity<BibliographicSourceDTO> atualizarParcialmente(@PathVariable String id, @RequestBody BibliographicSourceDTO bibliographicSourceDto) {
        BibliographicSourceDTO atualizado = bibliographicSourceService.atualizarParcialmente(id, bibliographicSourceDto);
        return ResponseEntity.status(HttpStatus.OK).body(atualizado);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Deletar trabalho por ID",
            description = "Remove um trabalho acadêmico com base no identificador fornecido."
    )
    @ApiResponse(responseCode = "204", description = "Trabalho deletado com sucesso.")
    @ApiResponse(responseCode = "404", description = "Trabalho com o ID especificado não foi encontrado.")
    @ApiResponse(responseCode = "500", description = "Erro interno no servidor ao excluir o trabalho.")
    public ResponseEntity<Void> deletar(@PathVariable String id) {
        bibliographicSourceService.deletar(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}