package com.company.gamestore.controller;

import com.company.gamestore.model.Console;
import com.company.gamestore.model.Game;
import com.company.gamestore.repository.ConsoleRepository;
import com.company.gamestore.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;


@Controller
public class GraphQLController {

    @Autowired
    GameRepository gameRepository;
    @Autowired
    ConsoleRepository consoleRepository;

    @QueryMapping
    public List<Game> getAllGames(){
        return gameRepository.findAll();
    }

    @QueryMapping
    public Game getGameById(@Argument int gameId){
        return gameRepository.findById(gameId).orElse(null);
    }

    @QueryMapping
    public  List<Game> getGamesByTitle(@Argument String title){
        return gameRepository.findByTitle(title);
    }

    @QueryMapping
    public List<Game> getGamesByEsrbRating(@Argument String esrbRating) {
        return gameRepository.findByEsrbRating(esrbRating);
    }

    @QueryMapping
    public List<Game> getGamesByStudio(@Argument String studio){
        return gameRepository.findByStudio(studio);
    }

    @QueryMapping
    public List<Console> getAllConsoles(){
        return consoleRepository.findAll();
    }

    @QueryMapping
    public Console getConsoleById(@Argument int consoleId){
        return consoleRepository.findById(consoleId).orElse(null);
    }

    @QueryMapping
    public List<Console> getConsoleByManufacturer(@Argument String manufacturer){
        return consoleRepository.findByManufacturer(manufacturer);
    }

}
