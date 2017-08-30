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
        Actor a = new Actor();
        a.setName("Sandra Bullock");
        a.setRealname("Sandra Mae Bullowski");
        actorRepository.save(a);

        Movie movie = new Movie();
        movie.setTitle("Emoji Movie");
        movie.setYear(2017);
        movie.setDescription("About Emojis...");

        movie.addActor(a);
        movieRepository.save(movie);


        //add a new movie and connect to actor a
        Set<Actor> castlist = new HashSet<>();
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
        b.getMovies().add(movie);

        actorRepository.save(b);



        return "results added";


    }


}
