package de.bruns.example.lucene;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AkwCsvData {

    private String status;
    private Date ausserBetrieb;
    private String kuerzel;
    private String betreiber;
    private double longitude;
    private String bundesland;
    private String standort;
    private String imageUrl;
    private String wikipediaUrl;
    private double latitude;
    private String bezeichnung;
    private String typ;

    public AkwCsvData(String csvLine) throws ParseException {
        String[] tokens = csvLine.split(",");
        status =  tokens[0];
        ausserBetrieb =  new SimpleDateFormat("yyyy-mm-dd").parse(tokens[1]);
        kuerzel =  tokens[2];
        betreiber =  tokens[3];
        longitude =  Double.parseDouble(tokens[4]);
        bundesland =  tokens[5];
        standort =  tokens[6];
        imageUrl =  tokens[7];
        wikipediaUrl =  tokens[8];
        latitude =  Double.parseDouble(tokens[9]);
        bezeichnung =  tokens[10];
        typ =  tokens[11];
    }

    public String getStatus() {
        return status;
    }

    public Date getAusserBetrieb() {
        return ausserBetrieb;
    }

    public String getKuerzel() {
        return kuerzel;
    }

    public String getBetreiber() {
        return betreiber;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getBundesland() {
        return bundesland;
    }

    public String getStandort() {
        return standort;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getWikipediaUrl() {
        return wikipediaUrl;
    }

    public double getLatitude() {
        return latitude;
    }

    public String getBezeichnung() {
        return bezeichnung;
    }

    public String getTyp() {
        return typ;
    }
}
