package com.infraroderik.iprunkeeper.Service;

public class DatabaseQuery
{
    // TABLE NAMES
    public static final String TABLE_HEADER_SEGMENT = "Segment";
    public static final String TABLE_HEADER_TRAJECT = "Traject";

    // COLUMNS IN SEGMENT
    public static final String COL_SEGMENT_ID = "id";
    public static final String COL_SEGMENT_TIME = "time";
    public static final String COL_SEGMENT_STARTPOINTLAT = "startPointLat";
    public static final String COL_SEGMENT_STARTPOINTLONG = "startPointLong";
    public static final String COL_SEGMENT_ENDPOINTLAT = "endPointLat";
    public static final String COL_SEGMENT_ENDPOINTLONG = "endPointLong";
    public static final String COL_SEGMENT_TRAJECTID = "trajectID";

    // COLUMNS IN TRAJECT
    public static final String COL_TRAJECT_ID = "id";
    public static final String COL_TRAJECT_TYPE = "type";
    public static final String COL_TRAJECT_STARTDATETIME = "startDateTime";
    public static final String COL_TRAJECT_ENDDATETIME = "endDateTime";

    // BUILDING THE DATABASE WITH THESE QUERY'S
    public static final String CREATE_TABLE_SEGMENT = String.format("CREATE TABLE IF NOT EXISTS %s (\n" +
            "    %s INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "    %s INTEGER(50),\n" +
            "    %s VARCHAR(50),\n" +
            "    %s VARCHAR(50),\n" +
            "    %s VARCHAR(50),\n" +
            "    %s VARCHAR(50),\n" +
            "    %s INTEGER(50) NULL,\n" +
                    "\tCONSTRAINT %s\n" +
                    "\t\tFOREIGN KEY (%s)\n" +
                    "\t\tREFERENCES %s (%s)\n" +
            ");", TABLE_HEADER_SEGMENT, COL_SEGMENT_ID, COL_SEGMENT_TIME, COL_SEGMENT_STARTPOINTLAT,
                COL_SEGMENT_STARTPOINTLONG, COL_SEGMENT_ENDPOINTLAT, COL_SEGMENT_ENDPOINTLONG,
                COL_SEGMENT_TRAJECTID, COL_SEGMENT_TRAJECTID, COL_SEGMENT_TRAJECTID,
                TABLE_HEADER_TRAJECT, COL_SEGMENT_ID);

    public static final String CREATE_TABLE_TRAJECT = String.format("CREATE TABLE IF NOT EXISTS %s (\n" +
            "\t%s INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "\t%s INTEGER(50),\n" +
            "    %s INTEGER(50),\n" +
            "    %s INTEGER(50)\n" +
            ");", TABLE_HEADER_TRAJECT, COL_TRAJECT_ID, COL_TRAJECT_TYPE,
                COL_TRAJECT_STARTDATETIME, COL_TRAJECT_ENDDATETIME);

}
