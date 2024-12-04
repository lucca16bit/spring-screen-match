package br.com.lucca.screenmatch.main;

import br.com.lucca.screenmatch.model.DadosEpisodio;
import br.com.lucca.screenmatch.model.DadosSerie;
import br.com.lucca.screenmatch.model.DadosTemporada;
import br.com.lucca.screenmatch.model.Episodio;
import br.com.lucca.screenmatch.service.ConsumoApi;
import br.com.lucca.screenmatch.service.ConverteDados;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Streams {
    private Scanner sc = new Scanner(System.in);
    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();
    private final String URL = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=59fc3cd2";

    public void exibeMenu() {
        System.out.println("Digite o nome da Série:");
        var nomeSerie = sc.nextLine();
        consumo = new ConsumoApi();
        var json =  consumo.obterDados(URL + nomeSerie.replace(" ", "+") + API_KEY);
        DadosSerie dados = conversor.obterDados(json, DadosSerie.class);

        System.out.println(dados);

        List<DadosTemporada> temporadas = new ArrayList<>();

		for (int i = 1; i<=dados.totalTemporadas(); i++) {
			json = consumo.obterDados(URL + nomeSerie.replace(" ", "+") + "&season=" + i + API_KEY);
			DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
			temporadas.add(dadosTemporada);
		}
        temporadas.forEach(System.out::println);

        // opçao 1
//        for(int i = 0; i < dados.totalTemporadas(); i++) {
//            List<DadosEpisodio> episodioTemporada = temporadas.get(i).episodios();
//            for (int j = 0; j < episodioTemporada.size(); j++) {
//                System.out.println(episodioTemporada.get(j).titulo());
//            }
//        }

        // opcao 2, funçoes lambda
        temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));

        List<DadosEpisodio> dadosEpisodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream())
                .collect(Collectors.toList());

        System.out.println("\nTop 5 episódios:");
        dadosEpisodios.stream()
                .filter(e -> !e.avaliacao().equalsIgnoreCase("N/A"))
                .peek(e -> System.out.println("Primeiro filtro: (N/A) " + e))
                .sorted(Comparator.comparing(DadosEpisodio::avaliacao).reversed())
                .peek(e -> System.out.println("Ordenação " + e))
                .limit(5)
                .peek(e -> System.out.println("Limite " + e))
                .map(e -> e.titulo().toUpperCase())
                .peek(e -> System.out.println("Mapeamento " + e))
                .forEach(System.out::println);

        List<Episodio> episodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream()
                        .map(d -> new Episodio(t.numero(), d))
                ).collect(Collectors.toList());
        episodios.forEach(System.out::println);

        System.out.println("digite um trecho do titulo do episodio: ");

        var trechoTitulo = sc.nextLine();
        // o optional está guardando informaçoes CASO ele exista
        Optional<Episodio> episodioBuscado = episodios.stream()
                .filter(e -> e.getTitulo().toUpperCase().contains(trechoTitulo.toUpperCase()))
                .findFirst();
        if (episodioBuscado.isPresent()) {
            System.out.println("Episodio econtrado!");
            System.out.println("Temporada: " + episodioBuscado.get().getTemporada());
        } else {
            System.out.println("Episodio não encontrado :c");
        }

        System.out.println("A partir de que ano você deseja ver os episódios?");
        var ano = sc.nextInt();
        sc.nextLine();

        LocalDate dataBusca = LocalDate.of(ano, 1,1);

        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        episodios.stream()
                .filter(e -> e.getDataLancamento() != null && e.getDataLancamento().isAfter(dataBusca))
                .forEach(e -> System.out.println(
                        "Temporada: " + e.getTemporada() +
                            ", Episódio: " + e.getNumeroEpisodio() +
                            ", Data Lançamento: " + e.getDataLancamento().format(formatador)
                ));

        Map<Integer, Double> avaliacoesPorTemporada = episodios.stream()
                .filter(e -> e.getAvaliacao() > 0.0)
                .collect(Collectors.groupingBy(Episodio::getTemporada, Collectors.averagingDouble(Episodio::getAvaliacao)));
        System.out.println(avaliacoesPorTemporada);

        DoubleSummaryStatistics est = episodios.stream()
                .filter(e -> e.getAvaliacao() > 0.0)
                .collect(Collectors.summarizingDouble(Episodio::getAvaliacao));
        System.out.println("Média: " + est.getAverage());
        System.out.println("Melhor avaliação: " + est.getMax());
        System.out.println("Pior avaliação: " + est.getMin());
        System.out.println("Quantidade: " + est.getCount());
    }
}
