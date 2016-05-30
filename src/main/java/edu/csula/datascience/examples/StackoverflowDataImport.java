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
  * PUT /stackoverflow-data
 {
     "mappings" : {
         "stackoverflow" : {
             "properties" : {
                 "question" : {
                     "type" : "string",
                     "index" : "analyzed"
                 },
                 "tags" : {
                     "type" : "string",
                     "index" : "analyzed"
                 },
                 "name" : {
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
public class StackoverflowDataImport {
    private final static String indexName = "stackoverflow-data";
    private final static String typeName = "stackoverflow";

    public static void main(String[] args) throws URISyntaxException, IOException {
        Node node = nodeBuilder().settings(Settings.builder()
            .put("cluster.name", "elasticsearch")
            .put("path.home", "elasticsearch-data")).node();
        Client client = node.client();

        /**
         *
         *
         * INSERT data to elastic search
         */

        // as usual process to connect to data source, we will need to set up
        // node and client// to read CSV file from the resource folder
        File csv = new File(
            ClassLoader.getSystemResource("Stackoverflow.csv")
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
            	Stackoverflow_Record objrecord = new Stackoverflow_Record(
                    record.get("question_id"),
                    record.get("question"),
                    record.get("tags"),
                    record.get("name"),
                    record.get("viewcount"),
                    record.get("year")
                 );
                System.out.println(record.get("question"));
                bulkProcessor.add(new IndexRequest(indexName, typeName)
                    .source(gson.toJson(objrecord))
                );
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

   
    /* Implemented Stackoverflow Record class to get information for each record */
    static class Stackoverflow_Record {
        private final String question;
        private final String tags;
        private final String name;
        private final String year;
        private final String question_id;
        private final String viewcount;

        public Stackoverflow_Record(String question_id, String question, String tags, String name,String viewcount, String year) {
            this.question_id=question_id;
            this.question = question;
            this.tags = tags;
            this.name = name;
            this.viewcount=viewcount;
            this.year = year;
        }
        
        public String getQuestionId() {
            return question_id;
        }
     
        public String getName() {
            return name;
        }

        public String getquestion() {
            return question;
        }

        public String getviewcount() {
            return viewcount;
        }

        public String gettags() {
            return tags;
        }

    }
}