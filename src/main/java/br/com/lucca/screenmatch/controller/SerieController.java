package br.com.lucca.screenmatch.controller;

import br.com.lucca.screenmatch.dto.EpisodioDTO;
import br.com.lucca.screenmatch.dto.SerieDTO;
import br.com.lucca.screenmatch.service.SerieServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/series")
public class SerieController {

    @Autowired
    private SerieServices services;

    @GetMapping
    public List<SerieDTO> obterSeries() {
        return services.obterTodasAsSeries();
    }

    @GetMapping("/top5")
    public List<SerieDTO> obterTop5Series() {
        return services.obterTop5Series();
    }

    @GetMapping("/lancamentos")
    public List<SerieDTO> obterLancamentos() {
        return services.obterLancamentos();
    }

    @GetMapping("/{id}")
    public SerieDTO obterPorId(@PathVariable Long id) { // PathVariable indica que o caminho ta vindo lá do front
        return services.obterPorId(id);
    }

    @GetMapping("/{id}/temporadas/todas")
    public List<EpisodioDTO> obterTodasTemporadas(@PathVariable Long id) {
        return services.obterTodasTemporadas(id);
    }
}
