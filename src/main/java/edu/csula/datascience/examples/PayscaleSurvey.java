package edu.csula.datascience.examples;

import com.google.gson.Gson;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.elasticsearch.action.bulk.*;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.node.Node;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;

import static org.elasticsearch.node.NodeBuilder.nodeBuilder;

/**
 * Mappings for Payscale Survey Data
 * PUT /salary-data
 {
     "mappings" : {
         "salary" : {
             "properties" : {
               "programming_languages" : {
                     "type" : "string",
                     "index" : "analyzed"
                 },
                 "average_salary" : {
                     "type" : "string",
                     "index" : "analyzed"
                 },
                 "advertised_jobs" : {
                     "type" : "string",
                     "index" : "analyzed"
                 },
                 "year": {
                     "type": "date"
                 }
             }
         }
     }
 }
 
 */
public class PayscaleSurvey {
    private final static String indexName = "salary-data";
    private final static String typeName = "salary";

    public static void main(String[] args) throws URISyntaxException, IOException {
        Node node = nodeBuilder().settings(Settings.builder()
            .put("cluster.name", "elasticsearch")
            .put("path.home", "elasticsearch-data")).node();
        Client client = node.client();
        // node and client// to read CSV file from the resource folder
        File csv = new File(
            ClassLoader.getSystemResource("AvgSalary.csv")
                .toURI()
        );

        // create bulk processor
        BulkProcessor bulkProcessor = BulkProcessor.builder(
            client,
            new BulkProcessor.Listener() {
                @Override
                public void beforeBulk(long executionId,
                                       BulkRequest request) {
                }

                @Override
                public void afterBulk(long executionId,
                                      BulkRequest request,
                                      BulkResponse response) {
                }

                @Override
                public void afterBulk(long executionId,
                                      BulkRequest request,
                                      Throwable failure) {
                    System.out.println("Facing error while importing data to elastic search");
                    failure.printStackTrace();
                }
            })
            .setBulkActions(10000)
            .setBulkSize(new ByteSizeValue(1, ByteSizeUnit.GB))
            .setFlushInterval(TimeValue.timeValueSeconds(5))
            .setConcurrentRequests(1)
            .setBackoffPolicy(
                BackoffPolicy.exponentialBackoff(TimeValue.timeValueMillis(100), 3))
            .build();

        // Gson library for sending json to elastic search
        Gson gson = new Gson();

        try {
            // after reading the csv file, we will use CSVParser to parse through
            // the csv files
            CSVParser parser = CSVParser.parse(
                csv,
                Charset.defaultCharset(),
                CSVFormat.EXCEL.withHeader()
            );

            // for each record, we will insert data into Elastic Search
            parser.forEach(record -> {
            	Payscale_Survey salary = new Payscale_Survey(
                    record.get("Programming Languages"),
                    record.get("Average Salary"),
                    record.get("Advertised jobs in 2015"),
                    record.get("Year")
                   
                );
                System.out.println(record.get("Programming Languages"));
                bulkProcessor.add(new IndexRequest(indexName, typeName)
                    .source(gson.toJson(salary))
                );
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

  /* To get information about payscale data based on the survey */  
    static class Payscale_Survey {
      
    	private final String programming_languages;
        private final String average_salary;
        private final String advertised_jobs;
        private final String year;
       
        public Payscale_Survey(String programming_languages, String average_salary, String advertised_jobs,String Year) {
           this.programming_languages=programming_languages;
           this.average_salary = average_salary;
           this.advertised_jobs = advertised_jobs;
           this.year = Year;
        }
        public String getPrgLang() {
            return programming_languages;
        }
 
        public String getAvgSalary() {
            return average_salary;
        }

        public String getjobs() {
            return advertised_jobs;
        }

        public String getyear() {
            return year;
        }


        
    }
}
