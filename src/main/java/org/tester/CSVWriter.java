package org.tester;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class CSVWriter {
    private Map<String,Long> statData;

    public CSVWriter(Map<String,Long> statData) {
        this.statData = statData;
    }

    public boolean writeToCSV() throws IOException {
        File file = new File("stat_data.csv");
        // if there are already a file exist by this name then it will delete it
        // and then create new one.
        if (file.exists()) {
            file.delete();
        }

        FileWriter writer = new FileWriter(file);

        try {
            // writing the header of csv
            writer.write("client_count,2:1 req,5:1 req,10:1 req\n");

            writer.write("10,"+this.statData.get("10-2_1")+","+this.statData.get("10-5_1")+","+this.statData.get("10-10_1")+"\n");
            writer.write("50,"+this.statData.get("50-2_1")+","+this.statData.get("50-5_1")+","+this.statData.get("50-10_1")+"\n");
            writer.write("100,"+this.statData.get("100-2_1")+","+this.statData.get("100-5_1")+","+this.statData.get("100-10_1")+"\n");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            writer.flush();
        }
    }
}
