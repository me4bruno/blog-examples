package de.bruns.example.lucene;

import org.apache.lucene.util.Version;

public interface Constants {

    public static final String ATOMKRAFTWERKE_CSV = "/atomkraftwerke_deutschland.csv";

    public static final String INDEX_DIRECTORY = "./AkwIndex";

    public static final String FIELD_BEZEICHNUNG = "bezeichnung";
    public static final String FIELD_KUERZEL = "kuerzel";
    public static final String FIELD_TYP = "typ";
    public static final String FIELD_BETREIBER = "betreiber";
    public static final String FIELD_STANDORT = "standort";
    public static final String FIELD_BUNDESLAND = "bundesland";
    public static final String FIELD_STATUS = "status";
    public static final String FIELD_AUSSER_BETRIEB = "ausser_betrieb";
    public static final String FIELD_LATITUDE = "latitude";
    public static final String FIELD_LONGITUDE = "longitude";
    public static final String FIELD_WIKIPEDIA_URL = "wikipedia_url";

    public static final Version LUCENE_VERSION = Version.LUCENE_41;
}
