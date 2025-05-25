package br.ufpb.ecosystem.services;

import br.ufpb.ecosystem.dtos.AuthorDTO;
import br.ufpb.ecosystem.dtos.BibliographicSourceDTO;
import br.ufpb.ecosystem.entities.Author;
import br.ufpb.ecosystem.entities.BibliographicSource;
import br.ufpb.ecosystem.repositories.BibliographicSourceRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BibliographicSourceService {

    private final BibliographicSourceRepository bibliographicSourceRepository;

    public BibliographicSourceService(BibliographicSourceRepository bibliographicSourceRepository) {
        this.bibliographicSourceRepository = bibliographicSourceRepository;
    }

    //    public List<BibliotecaDto> processarCsv(MultipartFile file) {
//        List<BibliotecaDto> trabalhos = new ArrayList<>();
//
//        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8));
//             CSVReader csvReader = new CSVReader(reader)) {
//
//            String[] headers = csvReader.readNext();
//            if (headers == null) {
//                throw new RuntimeException("Arquivo CSV vazio ou inválido.");
//            }
//
//            String[] linha;
//            while ((linha = csvReader.readNext()) != null) {
//
//                System.out.println("Processando linha: " + Arrays.toString(linha));
//
//                String nomeSobrenome = linha[2] != null && !linha[2].isEmpty() ? linha[2] : null;
//                String nomeFormatado = nomeSobrenome != null ? formatarNomesAutores(nomeSobrenome) : null;
//
//                BibliotecaDto bibliotecaDto = new BibliotecaDto(
//                        linha[0] != null ? linha[0] : null,
//                        linha[1] != null ? linha[1] : null,
//                        nomeFormatado,
//                        parseIntSafe(linha[3]),
//                        linha[4] != null ? linha[4] : null,
//                        linha[5] != null ? linha[5] : null,
//                        linha[6] != null ? linha[6] : null,
//                        linha[7] != null ? linha[7] : null,
//                        linha[8] != null ? linha[8] : null,
//                        linha[9] != null ? linha[9] : null,
//                        linha[10] != null ? linha[10] : null
//                );
//
//                if (bibliotecaDto != null) {
//                    trabalhos.add(bibliotecaDto);
//                } else {
//                    System.out.println("Linha ignorada: " + Arrays.toString(linha));
//                }
//            }
//
//        } catch (Exception e) {
//            throw new RuntimeException("Erro ao processar o arquivo CSV: " + e.getMessage());
//        }
//
//        return salvarNoBanco(trabalhos);
//    }


    private List<Author> converterAutoresDtoParaEntidade(List<AuthorDTO> autoresDto) {
        if (autoresDto == null) return new ArrayList<>();

        return autoresDto.stream()
                .map(dto -> {
                    Author author = new Author();
                    author.setId(dto.getId());
                    author.setNome(dto.getNome());
                    author.setEmail(dto.getEmail());
                    author.setOrcid(dto.getOrcid());
                    author.setAfiliacao(dto.getAfiliacao());
                    return author;
                })
                .collect(Collectors.toList());
    }

    private List<AuthorDTO> converterAutoresParaDto(List<Author> autores) {
        if (autores == null) return new ArrayList<>();

        return autores.stream()
                .map(author -> {
                    AuthorDTO dto = new AuthorDTO();
                    dto.setId(author.getId());
                    dto.setNome(author.getNome());
                    dto.setEmail(author.getEmail());
                    dto.setOrcid(author.getOrcid());
                    dto.setAfiliacao(author.getAfiliacao());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public BibliographicSourceDTO inserir(BibliographicSourceDTO dto) {
        List<Author> autores = converterAutoresDtoParaEntidade(dto.getAutores());

        BibliographicSource trabalho = new BibliographicSource(
                dto.getCodigo(), dto.getTitulo(), autores, dto.getAno(),
                dto.getReferencia(), dto.getLink(), dto.getTipo(), dto.getMidia(),
                dto.getLinkDrive(), dto.getImagem(), dto.getObservacoes(), dto.getPalavraChave(), dto.getResumo()
        );

        BibliographicSource trabalhoSalvo = bibliographicSourceRepository.save(trabalho);

        return new BibliographicSourceDTO(
                trabalhoSalvo.getCodigoDoRevisor(), trabalhoSalvo.getTitulo(), converterAutoresParaDto(trabalhoSalvo.getAutores()), trabalhoSalvo.getAno(),
                trabalhoSalvo.getReferencia(), trabalhoSalvo.getLink(), trabalhoSalvo.getTipo(), trabalhoSalvo.getMidia(),
                trabalhoSalvo.getLinkDrive(), trabalhoSalvo.getImagem(), trabalhoSalvo.getObservacoes(), trabalhoSalvo.getPalavraChave(), trabalhoSalvo.getResumo()
        );
    }

    private List<BibliographicSourceDTO> salvarNoBanco(List<BibliographicSourceDTO> trabalhosDto) {
        List<BibliographicSource> trabalhos = trabalhosDto.stream()
                .map(dto -> new BibliographicSource(
                        dto.getCodigo(), dto.getTitulo(), converterAutoresDtoParaEntidade(dto.getAutores()), dto.getAno(),
                        dto.getReferencia(), dto.getLink(), dto.getTipo(), dto.getMidia(),
                        dto.getLinkDrive(), dto.getImagem(), dto.getObservacoes(), dto.getPalavraChave(), dto.getResumo()
                ))
                .collect(Collectors.toList());

        bibliographicSourceRepository.saveAll(trabalhos);
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

    public int parseIntSafe(String valor) {
        if (valor == null || valor.trim().isEmpty()) {
            return 0;
        }
        return Integer.parseInt(valor);
    }

    public List<BibliographicSourceDTO> listarTodos() {
        return bibliographicSourceRepository.findAll().stream()
                .map(b -> new BibliographicSourceDTO(
                        b.getCodigoDoRevisor(), b.getTitulo(), converterAutoresParaDto(b.getAutores()), b.getAno(), b.getReferencia(),
                        b.getLink(), b.getTipo(), b.getMidia(), b.getLinkDrive(), b.getImagem(), b.getObservacoes(),
                        b.getPalavraChave(), b.getResumo()
                ))
                .collect(Collectors.toList());
    }

    public Optional<BibliographicSourceDTO> buscarPorId(String id) {
        return bibliographicSourceRepository.findById(id)
                .map(b -> new BibliographicSourceDTO(
                        b.getCodigoDoRevisor(), b.getTitulo(), converterAutoresParaDto(b.getAutores()), b.getAno(), b.getReferencia(),
                        b.getLink(), b.getTipo(), b.getMidia(), b.getLinkDrive(), b.getImagem(), b.getObservacoes(),
                        b.getPalavraChave(), b.getResumo()
                ));
    }

    public BibliographicSourceDTO atualizar(String id, BibliographicSourceDTO bibliographicSourceDto) {
        BibliographicSource fonte = bibliographicSourceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registro não encontrado."));

        fonte.setTitulo(bibliographicSourceDto.getTitulo());
        fonte.setAutores(converterAutoresDtoParaEntidade(bibliographicSourceDto.getAutores()));
        fonte.setAno(bibliographicSourceDto.getAno());
        fonte.setReferencia(bibliographicSourceDto.getReferencia());
        fonte.setLink(bibliographicSourceDto.getLink());
        fonte.setTipo(bibliographicSourceDto.getTipo());
        fonte.setMidia(bibliographicSourceDto.getMidia());
        fonte.setLinkDrive(bibliographicSourceDto.getLinkDrive());
        fonte.setImagem(bibliographicSourceDto.getImagem());
        fonte.setObservacoes(bibliographicSourceDto.getObservacoes());

        BibliographicSource atualizado = bibliographicSourceRepository.save(fonte);

        return new BibliographicSourceDTO(
                atualizado.getCodigoDoRevisor(), atualizado.getTitulo(), converterAutoresParaDto(atualizado.getAutores()), atualizado.getAno(),
                atualizado.getReferencia(), atualizado.getLink(), atualizado.getTipo(), atualizado.getMidia(),
                atualizado.getLinkDrive(), atualizado.getImagem(), atualizado.getObservacoes(), atualizado.getPalavraChave(), atualizado.getResumo()
        );
    }

    public BibliographicSourceDTO atualizarParcialmente(String id, BibliographicSourceDTO bibliographicSourceDto) {
        BibliographicSource fonte = bibliographicSourceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registro não encontrado."));

        if (bibliographicSourceDto.getTitulo() != null) fonte.setTitulo(bibliographicSourceDto.getTitulo());
        if (bibliographicSourceDto.getAutores() != null) fonte.setAutores(converterAutoresDtoParaEntidade(bibliographicSourceDto.getAutores()));
        if (Objects.nonNull(bibliographicSourceDto.getAno())) fonte.setAno(bibliographicSourceDto.getAno());
        if (bibliographicSourceDto.getReferencia() != null) fonte.setReferencia(bibliographicSourceDto.getReferencia());
        if (bibliographicSourceDto.getLink() != null) fonte.setLink(bibliographicSourceDto.getLink());
        if (bibliographicSourceDto.getTipo() != null) fonte.setTipo(bibliographicSourceDto.getTipo());
        if (bibliographicSourceDto.getMidia() != null) fonte.setMidia(bibliographicSourceDto.getMidia());
        if (bibliographicSourceDto.getLinkDrive() != null) fonte.setLinkDrive(bibliographicSourceDto.getLinkDrive());
        if (bibliographicSourceDto.getImagem() != null) fonte.setImagem(bibliographicSourceDto.getImagem());
        if (bibliographicSourceDto.getObservacoes() != null) fonte.setObservacoes(bibliographicSourceDto.getObservacoes());

        BibliographicSource atualizado = bibliographicSourceRepository.save(fonte);

        return new BibliographicSourceDTO(
                atualizado.getCodigoDoRevisor(), atualizado.getTitulo(), converterAutoresParaDto(atualizado.getAutores()), atualizado.getAno(),
                atualizado.getReferencia(), atualizado.getLink(), atualizado.getTipo(), atualizado.getMidia(),
                atualizado.getLinkDrive(), atualizado.getImagem(), atualizado.getObservacoes(), atualizado.getPalavraChave(), atualizado.getResumo()
        );
    }

    public void deletar(String id) {
        if (!bibliographicSourceRepository.existsById(id)) {
            throw new RuntimeException("Registro não encontrado.");
        }
        bibliographicSourceRepository.deleteById(id);
    }
}
