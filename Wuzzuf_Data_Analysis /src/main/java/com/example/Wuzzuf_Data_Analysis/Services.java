package com.example.Wuzzuf_Data_Analysis;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.*;

@Service
public class Services {
    private final RestTemplate restTemplate;

    public Services(RestTemplateBuilder restTemplateBuilder) {
        restTemplate = restTemplateBuilder.build();
    }

//    public Details getUserDetails(String name) {
//        return restTemplate.getForObject("/{name}/details",
//                Details.class, name);
//    }

    String path ="src/main/resources/WJ.csv";
    Wuzzuf_Dao wuzzufDao =new Wuzzuf_Dao(path);
    POJO readDataSet= wuzzufDao.DAO();
    POJO companyOffers = wuzzufDao.company_offers();
    Dataset<Row> companyOffersDataset = companyOffers.getDataset();
    POJO mostPopJobs = wuzzufDao.mostPopJobs();
    Dataset<Row> mostPopJobsDataset = mostPopJobs.getDataset();
    POJO mostPopAreas = wuzzufDao.mostPopAreas();
    Dataset<Row> mostPopAreasDataset = mostPopAreas.getDataset();
    Drawing draw =new Drawing();

    //It just works with Data that has strings only
    public List<Map> ConvertToJson(List<String> rowLists){
        ArrayList JsonRows = new ArrayList();
        for  (int lineIdx=0; lineIdx < rowLists.size(); lineIdx++){
            String val = rowLists.get(lineIdx);
            val = val.substring(1, val.length()-1);           //remove curly brackets
            String[] keyValuePairs = val.split("\",\"");              //split the string to creat key-value pairs
            Map<String,String> map = new LinkedHashMap<>();

            for(String pair : keyValuePairs)                        //iterate over the pairs
            {
                String[] entry = pair.split("\":\"");                   //split the pairs to get key and value
                map.put(entry[0].trim().replace("\"",""), entry[1].trim().replace("\"",""));          //add them to the hashmap and trim whitespaces and remove the increases notatins
            }
            JsonRows.add(map);
            }
        return JsonRows;
    }

    //It just works with Data that has Conatin numirc value or filtered data
    public List<Map> ConvertNumToJson(List<String> rowLists){
        ArrayList JsonRows = new ArrayList();
        for  (int lineIdx=0; lineIdx < rowLists.size(); lineIdx++){
            String val = rowLists.get(lineIdx);
            val = val.substring(1, val.length()-1);           //remove curly brackets
            String[] keyValuePairs = val.split("\",\"");              //split the string to creat key-value pairs
            Map<String,String> map = new LinkedHashMap<>();

            for(String pair : keyValuePairs)                        //iterate over the pairs
            {
                String[] entry = pair.split(":");                   //split the pairs to get key and value
                map.put(entry[0].trim().replace("\"",""), entry[1].trim().replace("\"",""));          //add them to the hashmap and trim whitespaces and remove the increases notatins
            }
            JsonRows.add(map);
        }
        return JsonRows;
    }

    // This Function is for getting reqired numbers of rows from Spark Dataset
    public List<Map> requiredRows(Dataset<Row> data,int numOfRecords){
        List<String> StringRows = new LinkedList<String>();
        int nums = 0;
        for (String row : data.toJSON().collectAsList()) {
            if (nums < numOfRecords) {
                StringRows.add(row);
                nums++;
            }
        }
        return ConvertToJson(StringRows);
    };

    //These function for getting the statistics summry of data
    public  List<Map> getStats(){
        List<String> StatisticsSummry = readDataSet.getDataset().describe().toJSON().collectAsList();
        return ConvertToJson(StatisticsSummry);
    }

    //These function for getting the Structure of data
    public  List<Map> getSchema(){
        List<String> Sch = new LinkedList<>();
        String Schema = readDataSet.getDataset().schema().toString();

        List<String> records = new LinkedList<>();
        List<Map> recordsMap = new LinkedList<>();
        String[] keyValuePairs = Schema.replace("StructType(","").split("StructField");

        for(String pair : keyValuePairs)
        {
            pair = pair.replace(",true),","").replace(",true)","").replaceAll("[( )]","");
            records.add(pair);
        }
        records = records.subList(1,records.size());
        for(String pair : records)
        {
            Map<String,String> map = new LinkedHashMap<>();
            String[] entry = pair.split(",");
            map.put(entry[0].trim(), entry[1].trim());
            recordsMap.add(map);
        }
        return recordsMap;
    }

    //These function for clean data from nulls
    public List<Map> cleanData() {
        readDataSet.removeNulls();
        Dataset<Row> data = readDataSet.getDataset();

        //We get part from data to make sure that we removed null and duplicates  from it
        List<Map> CleanedData = requiredRows(data,50);

        return CleanedData;
    }

    public  List<Map> getHeader(){
        Dataset<Row> data= readDataSet.getDataset();
        List<Map> header = requiredRows(data,50);
        return header;
    }

    public  List<Map> getCompanyOffers(){
        List<String> rowLists = companyOffersDataset.toJSON().collectAsList();
        return ConvertNumToJson(rowLists);
    }

    public  List<Map> getMostPopJobs(){
        List<String> rowLists = mostPopJobsDataset.toJSON().collectAsList();
        return ConvertNumToJson(rowLists.subList(0,100));
    }

    public  List<Map> getMostPopAreas(){
        List<String> rowLists = mostPopAreasDataset.toJSON().collectAsList();
        return ConvertNumToJson(rowLists.subList(0,100));
    }

    public  Map<String,Integer> mostRepSkills(){
        POJO skills= wuzzufDao.mostRepSkills();
        WuzzufAnalysis wa=new WuzzufAnalysis();
        return wa.mostPopularSkills(skills);
    }

    public  List<Map> dataAfterFectorize(){
        WuzzufAnalysis wuzzufAnalysis=new WuzzufAnalysis();
        POJO yExp=wuzzufDao.yearsOfExp();
        POJO newreadDataSet=wuzzufDao.DAO();

        return ConvertNumToJson(wuzzufAnalysis.yearsExp(newreadDataSet,yExp).toJSON().collectAsList());
    }



    public  void PieChartPath() throws IOException {
        draw.pieChart(companyOffersDataset,"companyOffers.jpg");
    }

    public  void jobsCategoryChart() throws IOException {
        draw.categoryCharts(mostPopJobsDataset," most popular JobTitles","job names","jobs count","jobs.jpg");
    }

    public  void areaCategoryChart() throws IOException {
        draw.categoryCharts(mostPopAreasDataset," most popular Areas","Area names","Areas count","Areas.jpg");
    }
}
