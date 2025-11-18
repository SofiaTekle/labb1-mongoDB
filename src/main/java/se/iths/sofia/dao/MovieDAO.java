package se.iths.sofia.dao;


import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.List;

public interface MovieDAO {

    void insert(String title, int year);

    List<Document> findAll();

    Document findByTitle(String title);

    long updateYearByTitle(String title, int year);

    long updateMany(Bson filter, Bson update);

    long deleteOneByTitle(String title);

    long deleteMany(Bson filter);
}