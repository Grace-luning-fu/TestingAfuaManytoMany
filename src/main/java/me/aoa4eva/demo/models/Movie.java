package me.aoa4eva.demo.models;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Movie {
    @Id@GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String title;
    private long year;
    private String description;


    //Error: Illegal use of mappedBy on both sides of the relationship if try to mappedBy="movies"
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Actor> cast;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getYear() {
        return year;
    }

    public void setYear(long year) {
        this.year = year;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Actor> getCast() {
        return cast;
    }

    public void setCast(Set<Actor> cast) {
        this.cast = cast;
    }


    //the following two methods are what matter most
    public Movie() {
        this.cast = new HashSet<Actor>();
    }

    public void addActor(Actor a)
    {
        this.cast.add(a);
    }


}

