package br.com.ecosystem.services;

import br.com.ecosystem.dtos.BibliotecaDto;
import br.com.ecosystem.models.Biblioteca;
import br.com.ecosystem.repositories.BibliotecaRepository;
import com.opencsv.CSVReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BibliotecaService {

    private final BibliotecaRepository repository;


    public  List<BibliotecaDto> carregarCSV(MultipartFile file) {
        List<BibliotecaDto> trabalhos = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8));
             CSVReader csvReader = new CSVReader(reader)) {

            String[] headers = csvReader.readNext(); // Lê os cabeçalhos
            if (headers == null) {
                throw new RuntimeException("Arquivo CSV vazio ou inválido.");
            }

            String[] linha;
            while ((linha = csvReader.readNext()) != null) {
                BibliotecaDto bibliotecaDto = BibliotecaDto.builder()
                        .ano(Integer.parseInt(linha[0]))
                        .autor(linha[1])
                        .codigo(linha[2])
                        .link(linha[3])
                        .linkDrive(linha[4])
                        .midia(linha[5])
                        .referencia(linha[6])
                        .tipo(linha[7])
                        .titulo(linha[8])
                        .build();

                trabalhos.add(bibliotecaDto);
            }

        } catch (Exception e) {
            throw new RuntimeException("Erro ao processar o arquivo CSV: " + e.getMessage());
        }

        return salvarNoBanco(trabalhos);
    }

    private List<BibliotecaDto> salvarNoBanco(List<BibliotecaDto> bibliotecaDtos) {
        List<Biblioteca> trabalhos = bibliotecaDtos.stream()
                .map(dto -> Biblioteca.builder()
                        .ano(dto.getAno())
                        .autor(dto.getAutor())
                        .codigo(dto.getCodigo())
                        .link(dto.getLink())
                        .linkDrive(dto.getLinkDrive())
                        .midia(dto.getMidia())
                        .referencia(dto.getReferencia())
                        .tipo(dto.getTipo())
                        .titulo(dto.getTitulo())
                        .build())
                .toList();

        repository.saveAll(trabalhos);

        return bibliotecaDtos;
    }
}
