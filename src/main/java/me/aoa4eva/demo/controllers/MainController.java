package me.aoa4eva.demo.controllers;

import me.aoa4eva.demo.models.Actor;
import me.aoa4eva.demo.models.Movie;
import me.aoa4eva.demo.repository.ActorRepository;
import me.aoa4eva.demo.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

//        movie.removeactor(a);
//        a.removeMovie(movie);


        //try remove a movie, cannot remove either side directly, it make more sense just remove a movie from one actor
        //rather than remove the whole movie.



//        movieRepository.delete(2L);
//        actorRepository.delete(1L);


    }


    //try remove relationship from both sides
//    @PostMapping("/remove")
//    public @ResponseBody String remove(@ModelAttribute("movie")Movie movie, @ModelAttribute("actorb")Actor b,
//                                       @ModelAttribute("movie2")Movie movie2, @ModelAttribute("actora")Actor a)
//    {
////        movie.removeactor(a);
////        a.removeMovie(movie);
//        movieRepository.delete(movie);
//
////        b.removeMovie(movie2);
////        movie.removeactor(b);
////        actorRepository.save(b);
//
//        return "testing remove";
//    }

    //only to remove relationship
    @GetMapping("/remove")
    public @ResponseBody String getmremove()
    {

        Actor actor1 = actorRepository.findOne(1L);
        Movie movie1=movieRepository.findOne(1L);
        System.out.println(actor1.getName());
        System.out.println(movie1.getTitle());
        System.out.println("Step1--------");


        //has to remove relations from both sides, otherwise it won't work
        actor1.removeMovie(movie1);
        movie1.removeActor(actor1);

        System.out.println(actor1.getMovies().toString());
        System.out.println(movie1.getCast().toString());

        System.out.println("000000");


        //have to save either side in the repo again to really delete the relation!!!!!
        actorRepository.save(actor1);
//        movieRepository.save(movie1);
//

//
//
//        movieRepository.save(movieRepository.findOne(1L));
//        actorRepository.save(actorRepository.findOne(1L));

//        movieRepository.delete(movieRepository.findOne(1L));


//        b.removeMovie(movie2);
//        movie.removeactor(b);
//        actorRepository.save(b);

        return "testing remove";
    }


    // remove the owner side from repository
    @GetMapping("/removeMovie")
    public @ResponseBody String removeMovie()
    {
        Movie movie1 = movieRepository.findOne(1L);
        System.out.println("lllllllllll" + movie1.getTitle());

//        try {

        for (Actor actor: movie1.getCast())
        {
            System.out.println("&&&&&&&"+actor.getId());
           actor.removeMovie(movie1);
           System.out.println("!!!!!deleting a move from actor");
//           movie1.removeActor(actor);
//           System.out.println("+++++++deleting an actor from movie");
//           movieRepository.save(movie1);
//           System.out.println("MMMMMMM");
//           actorRepository.save(actor);
            System.out.println("AAAAAAAAA");
        }


//    }catch (Exception e)
//        {
//            System.out.println("no movie to delete");
//        }

        System.out.println("lllllllllll" + movie1.getTitle());
        movieRepository.delete(movie1);
        System.out.println("--------trying to deleting from the movie repo");
     return "movie 1 deleted";}

     @GetMapping("/removeActor")
    public @ResponseBody String removeactor()
     {
         Actor actor = actorRepository.findOne(1L);
         for (Movie m : actor.getMovies())
         {
             System.out.println("movie found asscoiated with actor" + m.getTitle());
             m.removeActor(actor);
             System.out.println("Movie removed from actor");

         }
         System.out.println("movies relation deleted");
         actorRepository.delete(actor);
         System.out.println("actor removed from db");

         return "deleting actor!";

     }


}
