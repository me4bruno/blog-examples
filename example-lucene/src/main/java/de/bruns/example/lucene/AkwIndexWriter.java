package de.bruns.example.lucene;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.de.GermanAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.DoubleField;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.LongField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class AkwIndexWriter implements Constants {

    public void writeIndex(List<AkwCsvData> akws) throws IOException {
         Analyzer analyzer = new GermanAnalyzer(LUCENE_VERSION);

         IndexWriterConfig config = new IndexWriterConfig(LUCENE_VERSION, analyzer);
         config.setOpenMode(IndexWriterConfig.OpenMode.CREATE);

         Directory indexDirectory = FSDirectory.open(new File(INDEX_DIRECTORY));
         IndexWriter w = new IndexWriter(indexDirectory, config);

         for(AkwCsvData akw : akws) {
             Document doc = new Document();
             doc.add(new TextField(FIELD_BEZEICHNUNG, akw.getBezeichnung(), Field.Store.YES));
             doc.add(new StringField(FIELD_KUERZEL, akw.getKuerzel(), Field.Store.YES));
             doc.add(new StringField(FIELD_TYP, akw.getTyp(), Field.Store.YES));
             doc.add(new StringField(FIELD_BETREIBER, akw.getBetreiber(), Field.Store.YES));
             doc.add(new StringField(FIELD_STANDORT, akw.getStandort(), Field.Store.YES));
             doc.add(new StringField(FIELD_BUNDESLAND, akw.getBundesland(), Field.Store.YES));
             doc.add(new TextField(FIELD_STATUS, akw.getStatus(), Field.Store.YES));
             doc.add(new LongField(FIELD_AUSSER_BETRIEB, akw.getAusserBetrieb().getTime(), Field.Store.YES));
             doc.add(new DoubleField(FIELD_LATITUDE, akw.getLatitude(), Field.Store.YES));
             doc.add(new DoubleField(FIELD_LONGITUDE, akw.getLongitude(), Field.Store.YES));
             doc.add(new StringField(FIELD_WIKIPEDIA_URL, akw.getWikipediaUrl(), Field.Store.YES));
             w.addDocument(doc);
         }

         w.close();
     }
}
