package me.aoa4eva.demo.controllers;

import me.aoa4eva.demo.models.Actor;
import me.aoa4eva.demo.models.Movie;
import me.aoa4eva.demo.repository.ActorRepository;
import me.aoa4eva.demo.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashSet;
import java.util.Set;

@Controller
public class MainController {

    @Autowired
    ActorRepository actorRepository;

    @Autowired
    MovieRepository movieRepository;

    @RequestMapping("/")
    public @ResponseBody String  showIndex()
    {

        // movies is the class not being mapped, which is the owner class!
        Actor a = new Actor();
        a.setName("Sandra Bullock");
        a.setRealname("Sandra Mae Bullowski");
        actorRepository.save(a);



        // create a new movie and add an actor to movie
        Movie movie = new Movie();
        movie.setTitle("Emoji Movie");
        movie.setYear(2017);
        movie.setDescription("About Emojis...");

        //after creating movie, have to add actor to movie to make connection
        movie.addActor(a);
        movieRepository.save(movie);


        //add a new movie 2 and connect to an actor
        Set<Actor> castlist = new HashSet<Actor>();
        castlist.add(a);
        Movie movie2 = new Movie();
        movie2.setTitle("hello2");
        movie2.setCast(castlist);

        //this is a easier way to make it also work
        Movie movie3 = new Movie();
        movie3.setTitle("hello2");


        movie3.getCast().add(a);


        //when save to movieRepo, the relation movies-cast is also created
        movieRepository.save(movie2);
        movieRepository.save(movie3);



        //add a new actor and create a relation with movie, but it doesn't create a relationship this way
        Actor b = new Actor();
        b.setName("Nolan");
        b.setRealname("Sandra Mae Nolan");
//        actorRepository.save(b);

        // just add movie to actor and save actor doesn't create the relationship
        b.getMovies().add(movie3);
        //works this way, only when you also add actor to movie, the relationship gets created
        //without the following statement, the relationship doesn't get created
        movie3.addActor(b);
        actorRepository.save(b);

        //another way to save into database
        b.addMovie(movie2);
        //without the following statement, the relationship doesn't get created, but you don't have to save it to make it work
        movie2.addActor(b);
        actorRepository.save(b);

        //this is what create the relationship, even without save the movie to repo
        movie.addActor(b);


        actorRepository.save(b);

        return "results added";


        //try remove a movie, cannot remove either side directly

//        movieRepository.delete(2L);
//        actorRepository.delete(1L);


    }


}
