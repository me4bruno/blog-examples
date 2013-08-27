package de.bruns.example.lucene;

import org.apache.commons.io.IOUtils;

import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class AkwCsvImporter {

    public List<AkwCsvData> importCsvData() throws ParseException {
        List<AkwCsvData> akwCsvData = new ArrayList<AkwCsvData>();
        InputStream inputStream = null;
        try {
            inputStream = AkwCsvImporter.class.getResource(Constants.ATOMKRAFTWERKE_CSV).openStream();
            List<String> csvLines = IOUtils.readLines(new InputStreamReader(inputStream));
            for(int i=1; i<csvLines.size(); i++) {
                akwCsvData.add(new AkwCsvData(csvLines.get(i)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
        return akwCsvData;
    }

}
