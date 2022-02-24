package entities;

import java.io.Serializable;
import javax.persistence.*;


@Entity
@NamedQueries({
@NamedQuery(name = "Movie.deleteAllRows", query = "DELETE from Movie"),
@NamedQuery(name = "Movie.getAll", query = "select m from Movie m"),
@NamedQuery(name = "Movie.getByTitle", query = "select m from Movie m where m.title =:title")})
public class Movie implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int year;
    private String title;
    private String director;
    private String[] actors;
    
    public Movie() {
    }

    public Movie(int year, String title, String director, String[] actors) {
        this.year = year;
        this.title = title;
        this.director = director;
        this.actors = actors;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String[] getActors() {
        return actors;
    }

    public void setActors(String[] actors) {
        this.actors = actors;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    

    
    
    

   
}
