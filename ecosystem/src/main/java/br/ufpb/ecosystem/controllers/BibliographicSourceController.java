package br.ufpb.ecosystem.controllers;

import br.ufpb.ecosystem.dtos.BibliographicSourceDTO;
import br.ufpb.ecosystem.security.SecurityConfig;
import br.ufpb.ecosystem.services.BibliographicSourceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.ufpb.ecosystem.entities.BibliographicSource;
import br.ufpb.ecosystem.enums.BibliographicSourceEnum.Type;
import br.ufpb.ecosystem.enums.BibliographicSourceEnum.Media;
import br.ufpb.ecosystem.services.BibliographicSourceService;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/library")
@Tag(name = "Library", description = "Controller responsible for managing academic work data, allowing operations such as registration, retrieval, update, and deletion.")
@SecurityRequirement(name = SecurityConfig.SECURITY)
public class BibliographicSourceController {

    private final BibliographicSourceService bibliographicSourceService;

    @Autowired
    public BibliographicSourceController(BibliographicSourceService bibliographicSourceService) {
        this.bibliographicSourceService = bibliographicSourceService;
    }

    @PostMapping("/add")
    @Operation(
            summary = "Insert data manually",
            description = "Allows manual registration of academic work by receiving the data in the request body."
    )
    @ApiResponse(responseCode = "200", description = "Data successfully inserted and saved.")
    @ApiResponse(responseCode = "400", description = "Invalid or incomplete data.")
    @ApiResponse(responseCode = "500", description = "Internal server error while inserting data.")
    public ResponseEntity<BibliographicSourceDTO> insert(@RequestBody BibliographicSourceDTO bibliographicSourceDto) {
        BibliographicSourceDTO newEntry = bibliographicSourceService.insert(bibliographicSourceDto);
        return ResponseEntity.ok(newEntry);
    }

    @GetMapping
    @Operation(
            summary = "List all academic works",
            description = "Returns a list of all academic works registered in the system."
    )
    @ApiResponse(responseCode = "200", description = "List of academic works successfully retrieved.")
    @ApiResponse(responseCode = "204", description = "No academic works found.")
    @ApiResponse(responseCode = "500", description = "Internal server error while retrieving data.")
    public ResponseEntity<Page<BibliographicSourceDTO>> findAll(
            @RequestParam (defaultValue = "0") int page,
            @RequestParam (defaultValue = "10") int itens) {
        Page<BibliographicSourceDTO> works = bibliographicSourceService.findAll(page, itens);
        if (works.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(works);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Find academic work by ID",
            description = "Retrieves a specific academic work by its unique identifier."
    )
    @ApiResponse(responseCode = "200", description = "Academic work successfully found.")
    @ApiResponse(responseCode = "404", description = "Academic work with the specified ID was not found.")
    @ApiResponse(responseCode = "500", description = "Internal server error while retrieving the academic work.")
    public ResponseEntity<BibliographicSourceDTO> findById(@PathVariable Long id) {
        Optional<BibliographicSourceDTO> work = bibliographicSourceService.findById(id);
        return work.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Fully update academic work",
            description = "Updates all fields of an existing academic work based on the provided ID."
    )
    @ApiResponse(responseCode = "200", description = "Academic work successfully updated.")
    @ApiResponse(responseCode = "404", description = "Academic work with the specified ID was not found.")
    @ApiResponse(responseCode = "500", description = "Internal server error while updating the academic work.")
    public ResponseEntity<BibliographicSourceDTO> update(@PathVariable Long id, @RequestBody BibliographicSourceDTO bibliographicSourceDto) {
        BibliographicSourceDTO updated = bibliographicSourceService.update(id, bibliographicSourceDto);
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }

    @PatchMapping("/{id}")
    @Operation(
            summary = "Partially update academic work",
            description = "Partially updates an academic work by modifying only the fields provided in the request body."
    )
    @ApiResponse(responseCode = "200", description = "Academic work successfully updated.")
    @ApiResponse(responseCode = "404", description = "Academic work with the specified ID was not found.")
    @ApiResponse(responseCode = "500", description = "Internal server error while updating the academic work.")
    public ResponseEntity<BibliographicSourceDTO> partialUpdate(@PathVariable Long id, @RequestBody BibliographicSourceDTO bibliographicSourceDto) {
        BibliographicSourceDTO updated = bibliographicSourceService.partialUpdate(id, bibliographicSourceDto);
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete academic work by ID",
            description = "Removes an academic work based on the provided identifier."
    )
    @ApiResponse(responseCode = "204", description = "Academic work successfully deleted.")
    @ApiResponse(responseCode = "404", description = "Academic work with the specified ID was not found.")
    @ApiResponse(responseCode = "500", description = "Internal server error while deleting the academic work.")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        bibliographicSourceService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/search")
    public List<BibliographicSource> search(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Type type,
            @RequestParam(required = false) Media media
    ) {
        return bibliographicSourceService.search(title, author, year, type, media);
    }

}
