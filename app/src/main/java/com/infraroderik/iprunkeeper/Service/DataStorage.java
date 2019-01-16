package com.infraroderik.iprunkeeper.Service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.util.Log;

import com.infraroderik.iprunkeeper.DataModel.Segment;
import com.infraroderik.iprunkeeper.DataModel.Traject;

import java.util.ArrayList;
import java.util.List;

public class DataStorage extends SQLiteOpenHelper
{
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "ROUTE";


    public DataStorage(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(DatabaseQuery.CREATE_TABLE_TRAJECT);
        db.execSQL(DatabaseQuery.CREATE_TABLE_SEGMENT);

        onConfigure(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS '" + DatabaseQuery.TABLE_HEADER_SEGMENT + "'");
        db.execSQL("DROP TABLE IF EXISTS '" + DatabaseQuery.TABLE_HEADER_TRAJECT + "'");

        onCreate(db);
    }


    @Override
    public void onConfigure(SQLiteDatabase db)
    {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    /**
     * It will search through the database with the two parameters and will return the monument.
     * If it cannot find a monument it will throw a new Error message.
     * @param monumentName the monument name of the searched monument
     * @param constructionyear the construction year of the searched monument
     * @return The monument from the database
    public Monument retrieveMonument(@NonNull String monumentName, @NonNull int constructionyear) {
        String query = "SELECT * FROM MONUMENT WHERE MONUMENT.ConstructionYear = " + constructionyear +" AND MONUMENT.MonumentName = \"" + monumentName + "\" LIMIT 1;";
        Log.d("QUERYMONUMENT", query);

        Cursor cursor = this.getReadableDatabase().rawQuery(query, null);
        if(cursor!= null){
            cursor.moveToFirst();
        }

        if(cursor.getCount() > 0) {
            Monument monument = new Monument.Builder()
                    .name(cursor.getString(cursor.getColumnIndex(DatabaseQuery.COL_MONUMENT_MONUMENTNAME)))
                    .description(cursor.getString(cursor.getColumnIndex(DatabaseQuery.COL_MONUMENT_DESCRIPTION)))
                    .creator(cursor.getString(cursor.getColumnIndex(DatabaseQuery.COL_MONUMENT_CREATOR)))
                    .soundURL(cursor.getString(cursor.getColumnIndex(DatabaseQuery.COL_MONUMENT_SOUNDFILEURL)))
                    .imageURL(cursor.getString(cursor.getColumnIndex(DatabaseQuery.COL_MONUMENT_IMAGEURL)))
                    .latitude(cursor.getDouble(cursor.getColumnIndex(DatabaseQuery.COL_MONUMENT_LATITUDE)))
                    .longitude(cursor.getDouble(cursor.getColumnIndex(DatabaseQuery.COL_MONUMENT_LONGITUDE)))
                    .constructionYear(cursor.getInt(cursor.getColumnIndex(DatabaseQuery.COL_MONUMENT_CONSTRUCTIONYEAR)))
                    .build();

            if(cursor.getInt(cursor.getColumnIndex(DatabaseQuery.COL_MONUMENT_ISVISITED)) == 1) {
                monument.setVisited(true);
            } else
            {
                monument.setVisited(false);
            }

            cursor.close();
            return monument;
        }
        else {
            cursor.close();
            throw new Error("MONUMENT COULD NOT BE FOUND BECAUSE EITHER IT DOES NOT EXIST OR INVALID PARAMETERS!");
        }
    }
    */

    /**
     * This method will return a list of all the trajects in the database
     * @return A list of all the monuments in the database
     */
    public List<Traject> retrieveAllTrajects() {
        List<Traject> trajects = new ArrayList<>();
        String query = "SELECT * FROM TRAJECT";
        Log.d("QUERYALLTRAJECT", query);

        Cursor cursor = this.getReadableDatabase().rawQuery(query, null);
        if(cursor != null) {
            cursor.moveToFirst();
        }

        if(cursor.getCount() > 0 ) {
            do {
                Traject traject = new Traject.Builder()
                        .type(cursor.getInt(cursor.getColumnIndex(DatabaseQuery.COL_TRAJECT_TYPE)))
                        .startDateTime(cursor.getLong(cursor.getColumnIndex(DatabaseQuery.COL_TRAJECT_STARTDATETIME)))
                        .endDateTime(cursor.getLong(cursor.getColumnIndex(DatabaseQuery.COL_TRAJECT_ENDDATETIME)))
                        .segmentList(retrieveSegmentsOfTraject(cursor.getInt(cursor.getColumnIndex(DatabaseQuery.COL_TRAJECT_ID))))
                        .build();
                trajects.add(traject);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return trajects;
    }

    public ArrayList<Segment> retrieveSegmentsOfTraject(int trajectID) {
        Log.d("TEST", trajectID+"");
        ArrayList<Segment> segmentList = new ArrayList<>();
        String query = "SELECT * FROM SEGMENT JOIN TRAJECT ON SEGMENT.trajectID = TRAJECT.ID WHERE SEGMENT.trajectID =" + trajectID + ";";
        Log.d("QUERYSEGMENT", query);
        Cursor cursor = this.getReadableDatabase().rawQuery(query, null);

        if(cursor != null) {
            cursor.moveToFirst();

        } else {
            throw new Error("CURSOR IS NULL");
        }

        if(cursor.getCount() > 0 ) {
            do {
                Segment segment = new Segment.Builder()
                        .time(cursor.getLong(cursor.getColumnIndex(DatabaseQuery.COL_SEGMENT_TIME)))
                        .startPointLat(cursor.getDouble(cursor.getColumnIndex(DatabaseQuery.COL_SEGMENT_STARTPOINTLAT)))
                        .startPointLong(cursor.getDouble(cursor.getColumnIndex(DatabaseQuery.COL_SEGMENT_STARTPOINTLONG)))
                        .endPointLat(cursor.getDouble(cursor.getColumnIndex(DatabaseQuery.COL_SEGMENT_ENDPOINTLAT)))
                        .endPointLong(cursor.getDouble(cursor.getColumnIndex(DatabaseQuery.COL_SEGMENT_ENDPOINTLONG)))
                        .build();
                segmentList.add(segment);
            }while (cursor.moveToNext());

            return segmentList;
        } else {
            cursor.close();
            Log.d("QUERY", "TRAJECT COULD NOT BE FOUND BECAUSE EITHER IT DOES NOT EXIST OR INVALID PARAMETERS!");
            return segmentList;
        }
    }

    public void addSegment(@NonNull Segment segment, int trajectID) {
        ContentValues values = new ContentValues();
        values.put(DatabaseQuery.COL_SEGMENT_TIME, segment.getTime());
        values.put(DatabaseQuery.COL_SEGMENT_STARTPOINTLAT, segment.getStartPointLat());
        values.put(DatabaseQuery.COL_SEGMENT_STARTPOINTLONG, segment.getStartPointLong());
        values.put(DatabaseQuery.COL_SEGMENT_ENDPOINTLAT, segment.getEndPointLat());
        values.put(DatabaseQuery.COL_SEGMENT_ENDPOINTLONG, segment.getEndPointLong());
        if(trajectID != -1)
            values.put(DatabaseQuery.COL_SEGMENT_TRAJECTID, trajectID);


        SQLiteDatabase db = getWritableDatabase();
        long i = db.insert(DatabaseQuery.TABLE_HEADER_SEGMENT, null, values);
        Log.i("ALISTANMO", String.valueOf(i));
        Log.i("INSERTEDDB", "INSERTED SEGMENT IN DB");
    }


    public void addTraject(@NonNull Traject traject) {
        ContentValues values = new ContentValues();
        values.put(DatabaseQuery.COL_TRAJECT_TYPE, traject.getType());
        values.put(DatabaseQuery.COL_TRAJECT_STARTDATETIME, traject.getStartDateTime());
        values.put(DatabaseQuery.COL_TRAJECT_ENDDATETIME, traject.getEndDateTime());


        getWritableDatabase().insert(DatabaseQuery.TABLE_HEADER_TRAJECT, null, values);
        values.clear();
        Log.i("INSERTEDDB", "INSERTED TRAJECT IN DB");
        int trajectID = getTPrimaryKey(traject);
        int index = 0;
        getWritableDatabase().beginTransaction();
        try {

            for (Segment segment : traject.getSegmentList())
            {
                addSegment(segment, trajectID);
            }
            getWritableDatabase().setTransactionSuccessful();
        } finally {
            getWritableDatabase().endTransaction();
        }
    }

    /*
    public List<Route> retrieveAllRoutes() {
        List<Route> routes = new ArrayList<>();
        String query = "SELECT * FROM ROUTE;";
        Log.d("QUERYALLROUTE", query);

        Cursor cursor = this.getReadableDatabase().rawQuery(query, null);
        if(cursor != null)
        {
            cursor.moveToFirst();
        }
        if(cursor.getCount() > 0 ) {
            do {
                Route route = new Route.Builder()
                        .name(cursor.getString(cursor.getColumnIndex(DatabaseQuery.COL_ROUTE_ROUTENAME)))
                        .description(cursor.getString(cursor.getColumnIndex(DatabaseQuery.COL_ROUTE_DESCRIPTION)))
                        .length(cursor.getDouble(cursor.getColumnIndex(DatabaseQuery.COL_ROUTE_LENGTH)))
                        .build();
                if(cursor.getInt(cursor.getColumnIndex(DatabaseQuery.COL_ROUTE_FINISHED)) == 1) {
                    route.setFinished(true);
                } else
                {
                    route.setFinished(false);
                }
                route.setMonumentList(retrieveMonumentsOnRoute(getRPrimaryKey(route)));
                routes.add(route);
            }while (cursor.moveToNext());

        }
        cursor.close();
        return routes;
    }
    */



    /**
     * It will search through the database with the one parameters and will return the route.
     * If it cannot find a route it will throw a new Error message.
     * @param routename The name of the route
     * @return The route
    public Route retrieveRoute(String routename) {

        String query = "SELECT * FROM ROUTE WHERE ROUTE.RouteName = \"" + routename +"\" LIMIT 1;";
        Log.d("QUERYROUTE", query);

        Cursor cursor = this.getReadableDatabase().rawQuery(query, null);
        if(cursor!= null){
            cursor.moveToFirst();
        }

        if(cursor.getCount() > 0) {

            Route route = new Route.Builder()
                    .name(cursor.getString(cursor.getColumnIndex(DatabaseQuery.COL_ROUTE_ROUTENAME)))
                    .description(cursor.getString(cursor.getColumnIndex(DatabaseQuery.COL_ROUTE_DESCRIPTION)))
                    .length(cursor.getDouble(cursor.getColumnIndex(DatabaseQuery.COL_ROUTE_LENGTH)))
                    .build();
            if(cursor.getInt(cursor.getColumnIndex(DatabaseQuery.COL_ROUTE_FINISHED)) == 1) {
                route.setFinished(true);
            } else
            {
                route.setFinished(false);
            }

            route.setMonumentList(retrieveMonumentsOnRoute(cursor.getInt(cursor.getColumnIndex(DatabaseQuery.COL_MAIN_ROUTEID))));
            cursor.close();
            return route;
        }
        else {
            cursor.close();
            throw new Error("ROUTE COULD NOT BE FOUND BECAUSE EITHER IT DOES NOT EXIST OR INVALID PARAMETERS!");
        }
    }
    */

    /**
     * Retrieve the primary key id of the traject.
     * @param traject For it to check on a traject.
     * @return The id number of the a specific traject, if it cannot find a ID it will throw a error;
     */
    public int getTPrimaryKey(Traject traject) {
        String query = "SELECT id FROM TRAJECT WHERE startDateTime = \"" + traject.getStartDateTime() + "\";";
        Log.d("QueryPrimaryKeyT", query);

        Cursor cursor = getReadableDatabase().rawQuery(query, null);
        if(cursor != null)
        {
            cursor.moveToFirst();
            if(cursor.getCount() > 0 ){

                int id = cursor.getInt(cursor.getColumnIndex(DatabaseQuery.COL_TRAJECT_ID));
                cursor.close();
                return id;
            } else {
                throw new Error("COUNT IS NOT BIGGER THEN 0");
            }
        }

        else {
            throw new Error("CURSOR IS NULL");
        }
    }


    /**
     * Retrieve the primary key id of the monument.
     * @param monument For it to check on a monument.
     * @return The id number of the a specific monument, if it cannot find a ID it will throw a error;
    public int getMPrimaryKey(Monument monument) {
        String query = "SELECT id FROM MONUMENT WHERE MonumentName = \"" + monument.getName() + "\" AND Longitude =" + monument.getLongitude() + " AND Latitude =" +  monument.getLatitude() + ";";
        Log.d("QueryPrimaryKeyM", query);

        Cursor cursor = getReadableDatabase().rawQuery(query, null);


        if(cursor != null) {
            cursor.moveToFirst();
            if(cursor.getCount() > 0 ) {
                int id = cursor.getInt(cursor.getColumnIndex(DatabaseQuery.COL_MONUMENT_ID));
                cursor.close();
                return id;
            }
            else {
                cursor.close();
                throw new Error("MONUMENT COULD NOT BE FOUND BECAUSE EITHER IT DOES NOT EXIST OR INVALID PARAMETERS!");
            }
        } else {
            throw new Error("CURSOR IS NULL");
        }

    }
    */

    /*
    public int getMonumentCount()
    {
        String query = "SELECT COUNT(*) FROM" + DatabaseQuery.TABLE_HEADER_MONUMENT +";";
        Log.d("QueryMonumentCount", query);

        Cursor cursor = getReadableDatabase().rawQuery(query, null);
        if(cursor != null) {
            cursor.moveToFirst();
            if(cursor.getCount() > 0 ) {
                int numRows = (int) DatabaseUtils.longForQuery(getReadableDatabase(), query, null);
                cursor.close();
                return numRows;
            }
            else {
                cursor.close();
                throw new Error("COULD NOT RETURN THE SIZE OF MONUMENT");
            }
        } else {
            throw new Error("CURSOR IS NULL");
        }

    }
    */

/*
    public void updateMonument(Monument monument) {

        int id = getMPrimaryKey(monument);
        ContentValues values = new ContentValues();
        values.put(DatabaseQuery.COL_MONUMENT_MONUMENTNAME, monument.getName());
        values.put(DatabaseQuery.COL_MONUMENT_DESCRIPTION, monument.getDescription());
        values.put(DatabaseQuery.COL_MONUMENT_CREATOR, monument.getCreator());
        values.put(DatabaseQuery.COL_MONUMENT_SOUNDFILEURL, monument.getSoundURL());
        values.put(DatabaseQuery.COL_MONUMENT_IMAGEURL, monument.getImageURL());
        values.put(DatabaseQuery.COL_MONUMENT_LATITUDE, monument.getLatitude());
        values.put(DatabaseQuery.COL_MONUMENT_LONGITUDE, monument.getLongitude());
        values.put(DatabaseQuery.COL_MONUMENT_CONSTRUCTIONYEAR, monument.getConstructionYear());

        if(monument.isVisited()) {
            values.put(DatabaseQuery.COL_MONUMENT_ISVISITED, 1);
        } else {
            values.put(DatabaseQuery.COL_MONUMENT_ISVISITED, 0);
        }

        getWritableDatabase().update(DatabaseQuery.TABLE_HEADER_MONUMENT, values ,"id="+id,null);
    }
    */

    /*
    public void updateRoute(Route route) {

        int id = getRPrimaryKey(route);
        ContentValues values = new ContentValues();
        values.put(DatabaseQuery.COL_ROUTE_ROUTENAME, route.getName());
        values.put(DatabaseQuery.COL_ROUTE_DESCRIPTION, route.getDescription());
        values.put(DatabaseQuery.COL_ROUTE_LENGTH, route.getLength());

        if(route.isFinished()) {
            values.put(DatabaseQuery.COL_ROUTE_FINISHED, 1);
        } else {
            values.put(DatabaseQuery.COL_ROUTE_FINISHED, 0);
        }
        getWritableDatabase().update(DatabaseQuery.TABLE_HEADER_ROUTE, values ,"id="+id,null);
    }
    */
}