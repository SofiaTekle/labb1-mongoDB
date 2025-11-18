package se.iths.sofia.dao;


import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;

public class MovieDAOMongo implements MovieDAO {

    private final MongoCollection<Document> collection;

    public MovieDAOMongo(MongoCollection<Document> collection) {
        this.collection = collection;
    }


    @Override
    public void insert(String title, int year) {
        Document doc = new Document();
        doc.append("title", title);
        doc.append("year", year);
        collection.insertOne(doc);
    }

    @Override
    public List<Document> findAll() {
        FindIterable<Document> movies = collection.find();
        List<Document> movieList = new ArrayList<>();
        // hämtar alla filmer
        for (Document document : movies) {
            movieList.add(document);
        }
        return movieList;
    }

    @Override
    public Document findByTitle(String title) {
        Bson filter = Filters.eq("title", title);
        return collection.find(filter).first();
    }

    @Override
    public long updateYearByTitle(String title, int year) {
//        Bson filter = Filters.eq("title", title);
//        Bson update = Updates.set("year", year);
//        collection.updateOne(filter, update);

        return collection.updateOne(Filters.eq("title", title), Updates.set("year", year))
                .getModifiedCount();
    }

    @Override
    public long updateMany(Bson filter, Bson update) {
        return collection.updateMany(filter, update).getModifiedCount();
    }


    @Override
    public long deleteOneByTitle(String title) {
//        Bson filter = Filters.eq("title", title);
//        collection.deleteOne(filter);
        return collection.deleteOne(Filters.eq("title", title)).getDeletedCount();
    }

    @Override
    public long deleteMany(Bson filter) {
        return collection.deleteMany(filter).getDeletedCount();
    }


}
