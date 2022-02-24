package rest;

import dtos.MovieDTO;
import entities.Movie;
import utils.EMF_Creator;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import io.restassured.parsing.Parser;
import java.net.URI;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
//Uncomment the line below, to temporarily disable this test
//@Disabled

public class MovieResourceTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";
    private static Movie movie1, movie2;

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;

    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    @BeforeAll
    public static void setUpClass() {
        //This method must be called before you request the EntityManagerFactory
        EMF_Creator.startREST_TestWithDB();
        emf = EMF_Creator.createEntityManagerFactoryForTest();

        httpServer = startServer();
        //Setup RestAssured
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;
    }

    @AfterAll
    public static void closeTestServer() {
        //System.in.read();

        //Don't forget this, if you called its counterpart in @BeforeAll
        EMF_Creator.endREST_TestWithDB();
        httpServer.shutdownNow();
    }

    // Setup the DataBase (used by the test-server and this test) in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the EntityClass used below to use YOUR OWN (renamed) Entity class
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        movie1 =  new Movie(1992,"The last of the Mohicans", "Michael Mann", new String[]{"Daniel Day-Lewis","Madeleine Stowe","Russell Means","Eric Schweig"});
        movie2 =  new Movie(1985,"The Goonies", "Richard Donner", new String[]{"Sean Astin","Josh Brolin","Jeff Cohen","Corey Feldman"});
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Movie.deleteAllRows").executeUpdate();
            em.persist(movie1);
            em.persist(movie2);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Test
    public void testServerIsUp() {
        System.out.println("Testing is server UP");
        given().when().get("/movie").then().statusCode(200);
    }

    //This test assumes the database contains two rows
    @Test
    public void testGetAllMoviesStatusCode() {
                given()
                .contentType("application/json")
                .when()
                .get("/movie/all")
                .then().statusCode(200);

    }

    @Test
    public void testGetAllMoviesLog() {
        given()
                .contentType("application/json")
                .when()
                .get("/movie/all")
                .then().log().body().statusCode(200);

    }

    @Test
    public void getMovieById(){
        given().contentType("application/json")
                .get("/movie/{id}", movie1.getId())
                .then()
                .assertThat()
                .statusCode(200)
                .body("id", equalTo(new Long(movie1.getId()).intValue()))
                .body("year", equalTo(movie1.getYear()))
                .body("title", equalTo(movie1.getTitle()));

    }

    @Test
    public void testGetMovieByTitle(){
        given().contentType("application/json").when()
                .get("/movie/title/{title}", movie1.getTitle())
                .then()
                .assertThat()
                .statusCode(200)
                .body("title", equalTo(movie1.getTitle()));
    }



}
