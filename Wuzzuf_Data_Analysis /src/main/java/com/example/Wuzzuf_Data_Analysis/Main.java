package com.example.Wuzzuf_Data_Analysis;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class Main {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(Main.class, args);
            //Read Dataset, getting statistic summrey ,header of dataset and cleaning the data from nulls and duplicates
            String path = "src/main/resources/WJ.csv";
            Wuzzuf_Dao wuzzufDao = new Wuzzuf_Dao(path);
            POJO readDataSet = wuzzufDao.DAO();
            readDataSet.get_stats();
            readDataSet.getSomeData();
            readDataSet.removeNulls();

            //Getting Company Jobs and Draw the pie Chart of it
            wuzzufDao.company_offers();
            POJO companyOffers = wuzzufDao.company_offers();
            Dataset<Row> companyOffersDataset = companyOffers.getDataset();
            System.out.println("===============Company Offers Dataset==============");
            companyOffersDataset.show();

            //Getting most popular JobTitles Jobs and Draw the category chart for it
            POJO mostPopJobs = wuzzufDao.mostPopJobs();
            Dataset<Row> mostPopJobsDataset = mostPopJobs.getDataset();
            System.out.println("===============Most Popular Jobs Dataset==============");
            mostPopJobsDataset.show();

            //Getting most popular areas and Draw the category chart for it
            POJO mostPopAreas = wuzzufDao.mostPopAreas();
            Dataset<Row> mostPopAreasDataset = mostPopAreas.getDataset();
            System.out.println("===============Most Popular Area==============");
            mostPopAreasDataset.show();
            POJO skills = wuzzufDao.mostRepSkills();

            //Getting required skills
            WuzzufAnalysis analysis = new WuzzufAnalysis();
            analysis.mostPopularSkills(skills);

            //Factorize the YearsExp feature ,convert it to numbers in new col and display it
            POJO yExp = wuzzufDao.yearsOfExp();
            POJO newreadDataSet = wuzzufDao.DAO();
            analysis.yearsExp(newreadDataSet, yExp);

    }
}
