package com.company.gamestore.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.company.gamestore.model.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class GameRepositoryTest {
    @Autowired
    GameRepository GameRepo;

    Game game;
    @BeforeEach
    public void setUp() throws Exception{
        GameRepo.deleteAll();
        game = new Game();
        game.setTitle("The Legend of Adventure");
        game.setEsrbRating("E10+");
        game.setDescription("Embark on an epic journey to save the mystical land from darkness.");
        game.setPrice(49.99);
        game.setStudio("QuestWorks Studios");
        game.setQuantity(100);

    }

    //Create game Test
    @Test
    public void shouldCreateGame(){
        game = GameRepo.save(game);

        Optional<Game> game1 = GameRepo.findById(game.getGameId());
        assertEquals(game1.get(), game);
    }


    //Read game byID Test
    @Test
    public void shouldGetGameById(){
        GameRepo.save(game);
        Optional<Game> game1 = GameRepo.findById(game.getGameId());
        assertEquals(game1.get(), game);
    }



    //Read All game Test


    //Update Test
    @Test
    public void shouldUpdateGame(){
        game = GameRepo.save(game);

        game.setTitle("FiFa 23");
        game.setEsrbRating("E10");
        game.setDescription("Embark on Epic");

// Update in DB
        GameRepo.save(game);
        Optional<Game> game1 = GameRepo.findById(game.getGameId());
        assertEquals(game1.get(), game);
    }



    //Delete Test
    @Test
    public void shouldDeleteAGame(){
        game = GameRepo.save(game);
        Optional<Game> game1 = GameRepo.findById(game.getGameId());
        assertEquals(game1.get(), game);

        GameRepo.deleteById(game.getGameId());

        game1 = GameRepo.findById(game.getGameId());
        assertFalse(game1.isPresent());
    }


    //get By Studio Test
    @Test
    public void shouldGetGameByStudio(){
        GameRepo.save(game);
        game.setGameId(15);
        GameRepo.save(game);

        List<Game> games = GameRepo.findByStudio(game.getStudio());
        assertEquals(games.size(), 2);
    }


    //get By ESRB Test
    @Test
    public void shouldGetGameByESRB(){
        GameRepo.save(game);
        game.setGameId(15);
        GameRepo.save(game);

        List<Game> games = GameRepo.findByEsrbRating(game.getEsrbRating());
        assertEquals(games.size(), 2);
    }


    //get By Title Test
    @Test
    public void shouldGetGameByTitle(){
        GameRepo.save(game);
        game.setGameId(15);
        GameRepo.save(game);

        List<Game> games = GameRepo.findByTitle(game.getTitle());
        assertEquals(games.size(), 2);
    }


}