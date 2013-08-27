package de.bruns.example.lucene;

import java.util.Date;

public class AkwResultDocument {

    private String bezeichnung;
    private String kuerzel;
    private String typ;
    private String betreiber;
    private String standort;
    private String bundesland;
    private String status;
    private long ausserBetrieb;
    private double latitude;
    private double longitude;
    private String wikipediaUrl;

    public AkwResultDocument(
            String bezeichnung,
            String kuerzel,
            String typ,
            String betreiber,
            String standort,
            String bundesland,
            String status,
            long ausserBetrieb,
            double latitude,
            double longitude, 
            String wikipediaUrl) {
        this.bezeichnung = bezeichnung;
        this.kuerzel = kuerzel;
        this.typ = typ;
        this.betreiber = betreiber;
        this.standort = standort;
        this.bundesland = bundesland;
        this.status = status;
        this.ausserBetrieb = ausserBetrieb;
        this.latitude = latitude;
        this.longitude = longitude;
        this.wikipediaUrl = wikipediaUrl;
    }

    public String getStatus() {
        return status;
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

    public double getLatitude() {
        return latitude;
    }

    public String getBezeichnung() {
        return bezeichnung;
    }

    public String getTyp() {
        return typ;
    }

    public long getAusserBetrieb() {
        return ausserBetrieb;
    }

    public Date getAusserBetriebDate() {
        return new Date(ausserBetrieb);
    }

    public String getWikipediaUrl() {
        return wikipediaUrl;
    }
}
