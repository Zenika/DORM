package com.zenika.dorm.maven.test.grinder;

import au.com.bytecode.opencsv.CSVWriter;
import org.apache.commons.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class GrinderResultToCSV {

    private static ExecutorService executorService = Executors.newFixedThreadPool(10);

    private static final Logger LOG = LoggerFactory.getLogger(GrinderResultToCSV.class);

    private static final String[] columns;

    static {
        columns = new String[]{
                "Timed test", "Errors", "Response status", "Response length", "Response errors", "Connect time", "First byte time"
        };
    }

    public static void write(GrinderStatistics result, String filePath) {
        executorService.execute(new Writer(result, filePath));
    }

    private static class Writer implements Runnable {

        private GrinderStatistics statistics;
        private String filePath;

        public Writer(GrinderStatistics result, String filePath) {
            this.statistics = result;
            this.filePath = filePath;
        }

        @Override
        public void run() {
            boolean alreadyExist = new File(filePath).exists();
            try {
                CSVWriter csvWriter = new CSVWriter(new FileWriter(filePath, true), ',');
                if (!alreadyExist) {
                    csvWriter.writeNext(columns);
                }
                csvWriter.writeNext(statistics.toArray());
                csvWriter.close();
            } catch (IOException e) {
                LOG.error("IO error", e);
                throw new IllegalStateException("IO error !", e);
            }
        }
    }
}
