package org.tester;

import java.util.Map;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception {
        AudioClientTest test = new AudioClientTest();
        Map<String,Long> stat = test.getRoundTripStat();
        System.out.println("Done");
        for (String key : stat.keySet()) {
            System.out.println("Client count : "+key+" milisecond that took while request : "+stat.get(key));
        }

        System.out.println("Writing to a csv file...");
        CSVWriter writer = new CSVWriter(stat);

        writer.writeToCSV();

    }
}
