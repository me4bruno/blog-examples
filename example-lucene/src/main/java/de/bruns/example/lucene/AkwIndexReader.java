package de.bruns.example.lucene;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.de.GermanAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class AkwIndexReader implements Constants {

    private static final int MAX_RESULTS = 100;

    public List<AkwResultDocument> searchByField(String field, String value) throws IOException, ParseException {
        Analyzer analyzer = new GermanAnalyzer(LUCENE_VERSION);
        Query query = new QueryParser(LUCENE_VERSION, field, analyzer).parse(value);
        return doSearch(query, null);
    }

    public List<AkwResultDocument> searchByField(String field, String value, SortField sortField) throws IOException, ParseException {
        Analyzer analyzer = new GermanAnalyzer(LUCENE_VERSION);
        Query query = new QueryParser(LUCENE_VERSION, field, analyzer).parse(value);
        return doSearch(query, sortField);
    }
    
    public List<AkwResultDocument> searchByDoubleRange(String field, Double min, Double max) throws IOException, ParseException {
        Query query = NumericRangeQuery.newDoubleRange(field, min, max, true, true);
        return doSearch(query, null);
    }

    public List<AkwResultDocument> searchByDoubleRange(String field, Double min, Double max, SortField sortField) throws IOException, ParseException {
        Query query = NumericRangeQuery.newDoubleRange(field, min, max, true, true);
        return doSearch(query, sortField);
    }

    public List<AkwResultDocument> searchByLongRange(String field, Long min, Long max, SortField sortField) throws IOException {
        Query query = NumericRangeQuery.newLongRange(field, min, max, true, true);
        return doSearch(query, sortField);
    }
    
    private List<AkwResultDocument> doSearch(Query query, SortField sortField) throws IOException {
        List<AkwResultDocument> akws = new ArrayList<AkwResultDocument>();
        Directory indexDirectory = FSDirectory.open(new File("./AkwIndex"));
        IndexReader indexReader = null;
        try {
            indexReader = DirectoryReader.open(indexDirectory);
            IndexSearcher searcher = new IndexSearcher(indexReader);

            TopDocs topDocs = null;
            if (sortField != null) {
                topDocs = searcher.search(query, MAX_RESULTS, new Sort(sortField));
            } else {
                topDocs = searcher.search(query, MAX_RESULTS);
            }

            ScoreDoc[] hits = topDocs.scoreDocs;
            for (ScoreDoc scoreDoc : hits) {
                Document d = searcher.doc(scoreDoc.doc);
                akws.add(new AkwResultDocument(
                        d.getField(Constants.FIELD_BEZEICHNUNG).stringValue(),
                        d.getField(Constants.FIELD_KUERZEL).stringValue(),
                        d.getField(Constants.FIELD_TYP).stringValue(),
                        d.getField(Constants.FIELD_BETREIBER).stringValue(),
                        d.getField(Constants.FIELD_STANDORT).stringValue(),
                        d.getField(Constants.FIELD_BUNDESLAND).stringValue(),
                        d.getField(Constants.FIELD_STATUS).stringValue(),
                        d.getField(Constants.FIELD_AUSSER_BETRIEB).numericValue().longValue(),
                        d.getField(Constants.FIELD_LATITUDE).numericValue().doubleValue(),
                        d.getField(Constants.FIELD_LONGITUDE).numericValue().doubleValue(),
                        d.getField(Constants.FIELD_WIKIPEDIA_URL).stringValue()
                        ));
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw(e);
        } finally {
            indexReader.close();
        }
        return akws;
    }

}
