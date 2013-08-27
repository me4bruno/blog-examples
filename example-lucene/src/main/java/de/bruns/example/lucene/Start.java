package de.bruns.example.lucene;

import java.text.DateFormat;
import java.util.List;
import java.util.Locale;

import org.apache.lucene.search.SortField;
import org.apache.lucene.search.SortField.Type;

public class Start {

    public static void main(String[] args) throws Exception {
        List<AkwCsvData> akws = new AkwCsvImporter().importCsvData();

        System.out.println("****** Folgende " + akws.size() + " AKWs importiert: ******");
        for(AkwCsvData akw : akws) {
            System.out.println(akw.getBezeichnung() + ": " + akw.getStatus() + " (" + akw.getStandort() + ", " + akw.getBundesland() + ")");
        }

        System.out.println("****** Schreibe Index ******");
        AkwIndexWriter akwIndexWriter = new AkwIndexWriter();
        System.out.println("Index at: " + Constants.INDEX_DIRECTORY);
        akwIndexWriter.writeIndex(akws);

        AkwIndexReader akwIndexReader = new AkwIndexReader();

        SortField sortBezeichnung = new SortField(Constants.FIELD_BEZEICHNUNG, Type.STRING);
        List<AkwResultDocument> result = akwIndexReader.searchByField(Constants.FIELD_BEZEICHNUNG, "Karlsruhe", sortBezeichnung);
        System.out.println("****** Lese Index mit Abfrage: 'Bezeichnung:Karlsruhe' => " + result.size() + " ******");
        for (AkwResultDocument akw : result) {
            System.out.println(akw.getBezeichnung() + " -> " + akw.getStatus());
        }

        result = akwIndexReader.searchByField(Constants.FIELD_STATUS, "Rückbau");
        System.out.println("****** Lese Index mit Abfrage: 'Status:Rückbau' => " + result.size() + " ******");
        for (AkwResultDocument akw : result) {
            System.out.println(akw.getBezeichnung() + " -> " + akw.getStatus());
        }

        result = akwIndexReader.searchByField(Constants.FIELD_STATUS, "Im");
        System.out.println("****** Lese Index mit Abfrage: 'Status:Im' => " + result.size() + " ******");
        for (AkwResultDocument akw : result) {
            System.out.println(akw.getBezeichnung() + " -> " + akw.getStatus());
        }
        
        SortField sortLatitude = new SortField(Constants.FIELD_LATITUDE, Type.DOUBLE);
        result = akwIndexReader.searchByDoubleRange(Constants.FIELD_LATITUDE, new Double(53), null, sortLatitude);
        System.out.println("****** AKWs nödlich von Bremen sortiert nach Breitengrad => " + result.size() + " ******");
        for (AkwResultDocument akw : result) {
            System.out.println(akw.getBezeichnung() + " (" + akw.getBundesland() + ") -> " + akw.getLatitude());
        }

        SortField sortAusserBetrieb = new SortField(Constants.FIELD_AUSSER_BETRIEB, Type.LONG);;
        DateFormat dateInstance = DateFormat.getDateInstance(DateFormat.SHORT, Locale.GERMAN);
        Long startDate = Long.valueOf(dateInstance.parse("01.01.2015").getTime());
        Long endDate = Long.valueOf(dateInstance.parse("31.12.2020").getTime());
        result = akwIndexReader.searchByLongRange(Constants.FIELD_AUSSER_BETRIEB, startDate, endDate, sortAusserBetrieb);
        System.out.println("****** AKWs die zwischen 2015 und 2020 stillgelegt werden sortiert nach Datum => " + result.size() + " ******");
        for (AkwResultDocument akw : result) {
            System.out.println(akw.getBezeichnung() + " (" + akw.getBundesland() + ") -> " + dateInstance.format(akw.getAusserBetriebDate()));
        }
    }
}
