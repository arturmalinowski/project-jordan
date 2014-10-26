package net.wazim.jordan.persistence;

import com.mongodb.*;
import net.wazim.jordan.domain.BluRay;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Boolean.getBoolean;

public class MongoBluRayDatabase implements BluRayDatabase {

    private final DBCollection allBluRays;

    public MongoBluRayDatabase() {
        MongoClient mongoClient = null;
        try {
            mongoClient = new MongoClient("localhost");
        } catch (UnknownHostException e) {
            System.out.println("Cannot connect to Mongo " + e.toString());
        }
        DB blurayDb = mongoClient.getDB("blurays");
        blurayDb.dropDatabase();
        allBluRays = blurayDb.getCollection("myBluRays");
    }

    @Override
    public BluRay findBluRayByName(String name) {
        DBObject retrievedObject = allBluRays.findOne(new BasicDBObject("name", name));

        double priceNew = Double.parseDouble(retrievedObject.get("priceNew").toString());
        double priceUsed = Double.parseDouble(retrievedObject.get("priceUsed").toString());

        return new BluRay(
                retrievedObject.get("name").toString(),
                priceNew,
                priceUsed,
                retrievedObject.get("url").toString(),
                getBoolean(retrievedObject.get("isOwned").toString()));
    }

    @Override
    public BluRay getFirstBluRay() {
        return null;
    }

    @Override
    public List<BluRay> getAllBluRays() {
        ArrayList<BluRay> myBluRays = new ArrayList<BluRay>();
        DBCursor dbObjects = allBluRays.find();
        for (DBObject dbObject : dbObjects) {
            double priceNew = Double.parseDouble(dbObject.get("priceNew").toString());
            double priceUsed = Double.parseDouble(dbObject.get("priceUsed").toString());
            myBluRays.add(new BluRay(dbObject.get("name").toString(), priceNew, priceUsed, dbObject.get("url").toString(), getBoolean(dbObject.get("isOwned").toString())));
        }
        return myBluRays;
    }

    @Override
    public List<BluRay> getAllOwnedBluRays() {
        return null;
    }

    @Override
    public List<BluRay> getAllUnownedBluRays() {
        return null;
    }

    @Override
    public void saveBluRay(BluRay bluRay) {
        Map<String, String> bluRayMap = new HashMap<String, String>();
        bluRayMap.put("name", bluRay.getName());
        bluRayMap.put("priceNew", Double.toString(bluRay.getPriceNew()));
        bluRayMap.put("priceUsed", Double.toString(bluRay.getPriceUsed()));
        bluRayMap.put("url", bluRay.getUrl());
        bluRayMap.put("isOwned", String.valueOf(bluRay.getIsOwned()));
        allBluRays.save(new BasicDBObject(bluRayMap));
    }

    @Override
    public void clearDownDatabase() {
        DBCursor dbObjects = allBluRays.find();
        for (DBObject dbObject : dbObjects) {
            allBluRays.remove(dbObject);
        }
    }
}
