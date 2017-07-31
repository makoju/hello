package com.datalex.taf.ui.data;

import com.opencsv.bean.CsvToBeanBuilder;
import lombok.extern.log4j.Log4j2;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.List;

/**
 * CSV Data reader class
 *
 * @author Aleksandar Vulovic
 */
@Log4j2
public class DataHelper {

    /**
     * Private constructor
     */
    private DataHelper() {
        throw new UnsupportedOperationException();
    }

    /**
     * Map CSV to Object
     *
     * @param csvFile - CSV file name without extension
     * @return Object collection
     * @throws FileNotFoundException if file is not found
     */
    public static Object[][] mapDataFromCSVToObject(String csvFile) throws FileNotFoundException {
        String renamedCSVFile = csvFile;
        if (csvFile.endsWith(".csv")) {
            renamedCSVFile = csvFile.replace(".csv", "");
        }
        Reader file = new FileReader("./src/test/resources/Data/" + renamedCSVFile + ".csv");
        List<TestData> testData = new CsvToBeanBuilder(file)
                .withType(TestData.class).build().parse();

        Object[][] testNgData = new Object[testData.size()][1];
        for (int i = 0; i < testData.size(); i++) {
            testNgData[i][0] = testData.get(i);
        }
        return testNgData;
    }
}