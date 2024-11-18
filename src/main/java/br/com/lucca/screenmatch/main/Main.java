package br.com.lucca.screenmatch.main;

import br.com.lucca.screenmatch.model.DadosSerie;
import br.com.lucca.screenmatch.model.DadosTemporada;
import br.com.lucca.screenmatch.service.ConsumoApi;
import br.com.lucca.screenmatch.service.ConverteDados;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
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
    }
}
