package com.datalex.taf.ui.data;

import com.datalex.taf.core.loggers.TAFLogger;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

/**
 * CSV Data reader class
 *
 * @author Aleksandar Vulovic
 */
public class CSVDataHelper {

    private CSVDataHelper() {
        throw new UnsupportedOperationException();
    }

    /**
     * Read CSV File
     *
     * @param csvFile - CSV file name without extension
     * @return Data List
     * @throws IOException if error occurs
     */
    public static List<Map<String, String>> readDataFromCSVFile(String csvFile) throws IOException {
        if (csvFile.endsWith(".csv")) {
            csvFile = csvFile.replace(".csv", "");
        }
        List<List<String>> listOfLines = new ArrayList<>();
        List<String> keys;
        Map<String, String> data = new LinkedHashMap<>();
        List<Map<String, String>> allData = new ArrayList<>();

        try (Stream<String> lines = Files.lines(Paths.get("./src/test/resources/CSVData/" + csvFile + ".csv"), StandardCharsets.UTF_8)) {
            for (String line : (Iterable<String>) lines::iterator) {
                listOfLines.add(Arrays.asList(line.split(",")));
            }
            keys = listOfLines.get(0);
            for (int j = 1; j < listOfLines.size(); j++) {
                for (int i = 0; i < keys.size(); i++) {
                    data.put(keys.get(i).trim(), listOfLines.get(j).get(i).trim());
                }
                allData.add(new HashMap<>(data));
                data.clear();
            }
        } finally {
            return allData;
        }
    }

    /**
     * Convert Data List to Object for DataProvider
     *
     * @param allData - Data List from method readDataFromCSVFile
     * @return Object for DataProvider
     */
    public static Object[][] convertDataToObject(List<Map<String, String>> allData) {
        List<Object> testData = new ArrayList<>();
        int i = 1;
        for (Map<String, String> anAllData : allData) {
            TestData testRow = new TestData();
            for (Map.Entry<String, String> entry : anAllData.entrySet()) {
                i++;
                switch (entry.getKey().toLowerCase()) {
                    case "triptype":
                        testRow.setTripType(entry.getValue());
                        break;
                    case "outboundpos":
                        testRow.setOutboundPOS(entry.getValue());
                        break;
                    case "inputfrom":
                        testRow.setInputFrom(entry.getValue());
                        break;
                    case "inputto":
                        testRow.setInputTo(entry.getValue());
                        break;
                    case "departon":
                        testRow.setDepartOn(entry.getValue());
                        break;
                    case "returnon":
                        testRow.setReturnOn(entry.getValue());
                        break;
                    case "frommc1":
                        testRow.setFromMC1(entry.getValue());
                        break;
                    case "tomc1":
                        testRow.setToMC1(entry.getValue());
                        break;
                    case "frommc2":
                        testRow.setFromMC2(entry.getValue());
                        break;
                    case "tomc2":
                        testRow.setToMC2(entry.getValue());
                        break;
                    case "frommc3":
                        testRow.setFromMC3(entry.getValue());
                        break;
                    case "tomc3":
                        testRow.setToMC3(entry.getValue());
                        break;
                    case "departonmc3":
                        testRow.setDepartOnMC3(entry.getValue());
                        break;
                    case "adt":
                        testRow.setAdt(entry.getValue());
                        break;
                    case "chd":
                        testRow.setChd(entry.getValue());
                        break;
                    case "inf":
                        testRow.setInf(entry.getValue());
                        break;
                    case "cabinclass":
                        testRow.setCabinClass(entry.getValue());
                        break;
                    case "farefamily":
                        testRow.setFareFamily(entry.getValue());
                        break;
                    case "insurancesummarypage":
                        testRow.setInsuranceSummaryPage(entry.getValue());
                        break;
                    case "baggage":
                        testRow.setBaggage(entry.getValue());
                        break;
                    case "delayedpayment":
                        testRow.setDelayedPayment(entry.getValue());
                        break;
                    case "meals":
                        testRow.setMeals(entry.getValue());
                        break;
                    case "seats":
                        testRow.setSeats(entry.getValue());
                        break;
                    case "pwd":
                        testRow.setPwd(entry.getValue());
                        break;
                    case "paymenttype":
                        testRow.setPaymentType(entry.getValue());
                        break;
                    case "loop profile":
                        testRow.setLoopProfile(entry.getValue());
                        break;
                    case "frequentflierprogram":
                        testRow.setFrequentFlierProgram(entry.getValue());
                        break;
                    case "age":
                        testRow.setAge(entry.getValue());
                        break;
                    case "insuranceconomynfirmationpage":
                        testRow.setInsuranceEconomyInformationPage(entry.getValue());
                        break;
                    case "baggageconomynfirmationpage":
                        testRow.setBaggageEconomyInformationPage(entry.getValue());
                        break;
                    default:
                        TAFLogger.info("Unsupported field in test data; field name:" + entry.getKey() + " value:" + entry.getValue());
                }
            }
            testData.add(testRow);
        }
        Object[][] testNgData = new Object[testData.size()][1];
        for (i = 0; i < testData.size(); i++) {
            testNgData[i][0] = testData.get(i);
        }
        return testNgData;
    }
}
