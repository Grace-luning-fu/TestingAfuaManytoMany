package me.aoa4eva.demo.models;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Actor {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    String name;
    String realname;

    // the class not being mapped is the owner class. In this case, Movie is owner class/side
    // and actor is the inverse side
    @ManyToMany(mappedBy="cast", cascade = CascadeType.ALL, fetch=FetchType.EAGER )
    private Set<Movie> movies;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public Set<Movie> getMovies() {
        return movies;
    }

    public void setMovies(Set<Movie> movies) {
        this.movies = movies;
    }


    //try add movie from actor side and see if it will work for actor
    public Actor() {
        this.movies = new HashSet<Movie>();
    }

    public void addMovie(Movie a)
    {
        this.movies.add(a);
    }

    public void removeMovie(Movie e){ this.movies.remove(e);}

}
