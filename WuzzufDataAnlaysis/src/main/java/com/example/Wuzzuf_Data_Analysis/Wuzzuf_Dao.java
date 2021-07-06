package com.example.Wuzzuf_Data_Analysis;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

//removing duplicates;

public class Wuzzuf_Dao {
    String path;
    SparkSession sparkSession;
     Dataset<Row> datset;

    Wuzzuf_Dao(String s){
        this.path = s;
    }
    public POJO DAO() {

          sparkSession = SparkSession.builder()
                .appName("Spark CSV Analysis Demo")
                .master("local[3]")
                .getOrCreate();
        sparkSession.sparkContext().setLogLevel("ERROR");
         datset= sparkSession.read().option("header",true).csv(path);
        return new POJO(datset);
    }
    // count jobs of ech company and display that is order
    public POJO company_offers(){
        datset.createOrReplaceTempView("wuzzuf");
        Dataset<Row> compOffers = sparkSession.sql ("SELECT   Company, COUNT(Company) AS Company_Count" +
                " FROM wuzzuf GROUP BY Company " +
                "ORDER BY Company_Count DESC");
            return new POJO(compOffers);
    }
    // Find what is the most popular JobTitle
    public POJO mostPopJobs(){
        datset.createOrReplaceTempView("wuzzuf");
        Dataset<Row> jobTitles = sparkSession.sql ("SELECT   Title, COUNT(Title) AS Title_count" +
                " FROM wuzzuf GROUP BY Title " +
                "ORDER BY Title_count DESC");
        return new POJO(jobTitles);
    }
    // Find what is the most popular Areas
    public POJO mostPopAreas(){
        datset.createOrReplaceTempView("wuzzuf");
        Dataset<Row> Areas = sparkSession.sql ("SELECT   Location, COUNT(Location) AS Location_count" +
                " FROM wuzzuf GROUP BY Location " +
                "ORDER BY Location_count DESC");
        return new POJO(Areas);
    }
    // Find the most repeated Skills
    public POJO mostRepSkills() {
        datset.createOrReplaceTempView("wuzzuf");
        Dataset<Row> skills = sparkSession.sql("SELECT Skills from wuzzuf");
        return new POJO(skills);
    }

    public POJO yearsOfExp(){
        datset.createOrReplaceTempView("wuzzuf");
        Dataset<Row> YearsExp = sparkSession.sql("SELECT YearsExp from wuzzuf");
        return new POJO(YearsExp);

    }
}