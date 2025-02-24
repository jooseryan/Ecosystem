package br.com.ecosystem.services;

import br.com.ecosystem.dtos.BibliotecaDto;
import br.com.ecosystem.models.Biblioteca;
import br.com.ecosystem.repositories.BibliotecaRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.opencsv.CSVReader;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
public class BibliotecaService {

    private final BibliotecaRepository bibliotecaRepository;

    public BibliotecaService(BibliotecaRepository bibliotecaRepository) {
        this.bibliotecaRepository = bibliotecaRepository;
    }

    public List<BibliotecaDto> processarCsv(MultipartFile file) {
        List<BibliotecaDto> trabalhos = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8));
             CSVReader csvReader = new CSVReader(reader)) {

            String[] headers = csvReader.readNext();
            if (headers == null) {
                throw new RuntimeException("Arquivo CSV vazio ou inválido.");
            }

            String[] linha;
            while ((linha = csvReader.readNext()) != null) {

                String nomeSobrenome = linha[2];
                String nomeFormatado = formatarNomesAutores(nomeSobrenome);

                BibliotecaDto bibliotecaDto = new BibliotecaDto(
                        linha[0],
                        linha[1],
                        nomeFormatado,
                        Integer.parseInt(linha[3]),
                        linha[4],
                        linha[5],
                        linha[6],
                        linha[7],
                        linha[8],
                        linha[9]
                );
                trabalhos.add(bibliotecaDto);
            }

        } catch (Exception e) {
            throw new RuntimeException("Erro ao processar o arquivo CSV: " + e.getMessage());
        }

        return salvarNoBanco(trabalhos);
    }

    private List<BibliotecaDto> salvarNoBanco(List<BibliotecaDto> trabalhosDto) {
        List<Biblioteca> trabalhos = new ArrayList<>();
        for (BibliotecaDto dto : trabalhosDto) {
            trabalhos.add(new Biblioteca(
                    dto.getCodigo(), dto.getTitulo(), dto.getAutor(), dto.getAno(),
                    dto.getReferencia(), dto.getLink(), dto.getTipo(), dto.getMidia(),
                    dto.getImagem(), dto.getObservacoes()
            ));
        }
        bibliotecaRepository.saveAll(trabalhos);
        return trabalhosDto;
    }

    public String formatarNomesAutores(String autores) {

        String[] listaAutores = autores.split(";");
        List<String> nomesFormatados = new ArrayList<>();

        for (String autor : listaAutores) {
            String[] partes = autor.trim().split(",");
            if (partes.length == 2) {
                nomesFormatados.add(partes[1].trim() + " " + partes[0].trim());
            } else {
                nomesFormatados.add(autor.trim());
            }
        }
        return String.join(", ", nomesFormatados);
    }
}
