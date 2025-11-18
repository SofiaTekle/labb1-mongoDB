package se.iths.sofia.mongodb;


import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.conversions.Bson;
import se.iths.sofia.dao.MovieDAO;
import se.iths.sofia.dao.MovieDAOMongo;

public class Main {
    public static void main(String[] args) {

        String uri = "mongodb+srv://<USERNAME>:<PASSWORD>@cluster0.nx7miq3.mongodb.net/?appName=Cluster0";

        try (MongoClient client = MongoClients.create(uri)) {

            MongoDatabase db = client.getDatabase("moviesdb");
            MongoCollection<Document> collection = db.getCollection("movies");
            MovieDAO movieDAO = new MovieDAOMongo(collection);

            // --- Insert movies ---
            movieDAO.insert("Gladiator", 2000);
            movieDAO.insert("The Shining", 1980);
            movieDAO.insert("The Godfather", 1972);
            movieDAO.insert("Inception", 2010);
            movieDAO.insert("No Country for Old Men", 2007);
            movieDAO.insert("The Social Network", 2010);


            // -- Show all movies ---
            movieDAO.findAll().forEach(System.out::println);


            // -- Update movies ---
            System.out.println("\nUpdating movies with year < 2000");
            Bson filter = Filters.lt("year", 2000);
            Bson update = Updates.set("year", 2000);
            long updatedCount = movieDAO.updateMany(filter, update);
            System.out.println("Movies updated count: " + updatedCount);


            // --- Delete movies ---
            System.out.println("\nDeleting all movies from 2010...");
            Bson deleteFilter = Filters.eq("year", 2010);
            long deletedCount = movieDAO.deleteMany(deleteFilter);
            System.out.println("Movies deleted count: " + deletedCount);


            // --- Find specific movie ---
            System.out.println("\nSearching for 'The Shining'...");
            Document movie = movieDAO.findByTitle("The Shining");
            if (movie != null) {
                System.out.println(movie.toJson());
                System.out.println();
            } else {
                System.out.println("Movie not found");
            }


            // --- Print all movies ---
            System.out.println("\nAll movies: ");
            movieDAO.findAll().forEach(doc -> System.out.println(doc.toJson()));


        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
