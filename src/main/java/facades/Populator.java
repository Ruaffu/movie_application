/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import dtos.MovieDTO;
import entities.Movie;

import javax.persistence.EntityManagerFactory;
import utils.EMF_Creator;

/**
 *
 * @author tha
 */
public class Populator {
    public static void populate(){
        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
        MovieFacade mf = MovieFacade.getFacadeExample(emf);
        mf.createMovie(new MovieDTO(1992,"The last of the Mohicans", "Michael Mann", new String[]{"Daniel Day-Lewis","Madeleine Stowe","Russell Means","Eric Schweig"}));
        mf.createMovie(new MovieDTO(1985,"The Goonies", "Richard Donner", new String[]{"Sean Astin","Josh Brolin","Jeff Cohen","Corey Feldman"}));
        mf.createMovie(new MovieDTO(1991,"Hook", "Steven Spielberg", new String[]{"Dustin Hoffman","Robin Williams","Julia Roberts","Bob Hoskins"}));
    }
    
    public static void main(String[] args) {
        populate();
    }
}
