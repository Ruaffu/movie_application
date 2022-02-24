package facades;

import dtos.MovieDTO;
import entities.Movie;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

//Uncomment the line below, to temporarily disable this test
//@Disabled
public class MovieFacadeTest {

    private static EntityManagerFactory emf;
    private static MovieFacade facade;
    Movie mv1, mv2;

    public MovieFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
       emf = EMF_Creator.createEntityManagerFactoryForTest();
       facade = MovieFacade.getFacadeExample(emf);
    }

    @AfterAll
    public static void tearDownClass() {
//        Clean up database after test is done or use a persistence unit with drop-and-create to start up clean on every test
    }

    // Setup the DataBase in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the code below to use YOUR OWN entity class
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Movie.deleteAllRows").executeUpdate();
            mv1 = new Movie(1945,"yo",new String[]{"larry","curly","moe","leon"});
            mv2 = new Movie(1946,"yoyo",new String[]{"larry","curly","moe","leon"});
            em.persist(mv1);
            em.persist(mv2);

            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @AfterEach
    public void tearDown() {
//        Remove any data after each test was run
    }

    // TODO: Delete or change this method 
    @Test
    public void testAFacadeMethod() throws Exception {
        assertEquals(2, facade.getRenameMeCount(), "Expects two rows in the database");
    }

    @Test
    public void testGetAllMovies(){
        int expected = 2;
        int actual = facade.getAllMovies().size();
        assertEquals(expected, actual);
    }

    @Test
    public void testGetMovieById(){
        String expected = mv1.getTitle();
        String actual = facade.getMovieById(mv1.getId()).getTitle();
        assertEquals(expected, actual);
    }
    

}
