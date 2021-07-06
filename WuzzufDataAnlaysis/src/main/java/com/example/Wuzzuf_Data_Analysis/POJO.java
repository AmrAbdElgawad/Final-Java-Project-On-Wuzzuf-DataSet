package com.example.Wuzzuf_Data_Analysis;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

public final  class POJO {
    private Dataset<Row> dataset;
    POJO(Dataset<Row> df){
        this.dataset = df;
    }

        public  void get_stats(){
            System.out.println("==========Preliminary Statistic==========");
            System.out.println("Total passenger count: " + dataset.count());
            Dataset<Row> describe = dataset.describe();
            describe.show();
            System.out.println("===============DataSet Schema==============");
            describe.printSchema();
        }

         public void getSomeData(){
             System.out.println("===============Head of the Data Set==============");
             dataset.show(20);
         }

    public void removeNulls(){
        long before  = dataset.count();
        System.out.println("===============Removing null values and updating data set==============");
        System.out.println("Total passenger count before removing nan values: " + before);
        dataset=dataset.na().drop();
        long after = dataset.count();
        System.out.println("Total passenger count After removing nan values: " + after);
        if (before==after){ System.out.println("Data is clean and doesnot contain nulls"); }
        System.out.println("===============Removing Duplicates and updating data set ==============");
        dataset=dataset.dropDuplicates();
        long afterdup = dataset.count();
        System.out.println("Total passenger count After removing Duplicates: " + afterdup);

    }

        public Dataset<Row> getDataset() {
            return dataset;
        }
}
