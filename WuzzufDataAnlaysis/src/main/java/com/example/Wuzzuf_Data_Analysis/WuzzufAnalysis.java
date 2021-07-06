package com.example.Wuzzuf_Data_Analysis;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.Metadata;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;

import java.util.*;
import java.util.function.Function;

import static java.util.stream.Collectors.*;

public class WuzzufAnalysis
{
public Map<String, Integer> mostPopularSkills(POJO skills){
    Dataset<Row> sk_dataset = skills.getDataset();
    List<Row> skillSet = sk_dataset.collectAsList();
    List<String> allSkils=new ArrayList<String>();
    String skill;
    for (Row row : skillSet){
        skill= row.get(0).toString();
        String[] subs = skill.split(",");
        for(String word :subs){
            allSkils.add(word);
        }
    }
    //count frequency of each word and put results in map
    Map<String, Integer> mapAllSkills =
            allSkils.stream().collect(groupingBy(Function.identity(), summingInt(e -> 1)));
    //Sort the map descending
    Map<String, Integer> sorted_skillset = mapAllSkills
            .entrySet()
            .stream()
            .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
            //to return only first 50 element
            .limit(100)
            .collect(
                    toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                            LinkedHashMap::new));
    int idx=0;

    System.out.println("===============Most Repaeated Skills==============");
    for (Map.Entry<String, Integer> entry : sorted_skillset.entrySet()) {
        System.out.println(entry.getKey()+" : "+entry.getValue());
        if (idx==20){break;}
        idx++;
    }
        return (sorted_skillset);
}

    public Dataset<Row> yearsExp(POJO totalData,POJO yExp){
        SparkSession sparkSession = SparkSession.builder()
                .appName("Spark CSV Analysis Demo")
                .master("local[3]")
                .getOrCreate();
        sparkSession.sparkContext().setLogLevel("ERROR");
        Dataset<Row> yExp_dataset = yExp.getDataset();
        Dataset<Row> totalDataset = totalData.getDataset();
        System.out.println("===============Year of Experience==============");
        yExp_dataset.show();
        totalDataset.show();
        List<Row> yExp_list = yExp_dataset.collectAsList();
        List<Row> totaldata = totalDataset.collectAsList();

        List<Integer> new_years_experience=new ArrayList<>();
        for (Row row_Exp:yExp_list) {
            String row_str = row_Exp.toString();
            String[] splitted_str = row_str.split(" ");
            String first_word = splitted_str[0];
            first_word=first_word.substring(1);
            first_word=first_word.replaceAll("null","0");
            String[] get_min = first_word.split("-");
            first_word=get_min[0];
            first_word=first_word.replaceAll("[^0-9]", "");
            new_years_experience.add(Integer.parseInt(first_word));
        }
        //crete struct type
        StructType schema = new StructType(new StructField[] {
                new StructField("Title", DataTypes.StringType, true, Metadata.empty()),
                new StructField("Company", DataTypes.StringType, true, Metadata.empty()),
                new StructField("Location", DataTypes.StringType, true, Metadata.empty()),
                new StructField("Type", DataTypes.StringType, true, Metadata.empty()),
                new StructField("Level", DataTypes.StringType, true, Metadata.empty()),
                new StructField("YearExp", DataTypes.StringType, true, Metadata.empty()),
                new StructField("Country", DataTypes.StringType, true, Metadata.empty()),
                new StructField("Skills", DataTypes.StringType, true, Metadata.empty()),
                new StructField("NumberOfYearsExperience", DataTypes.IntegerType, true, Metadata.empty())

        });
        List<Row> rowList =new ArrayList<Row>() ;
        int idx=0;
        for (Row r:totaldata) {
            Row row = RowFactory.create(r.get(0),r.get(1),r.get(2),r.get(3),r.get(4),r.get(5),r.get(6),r.get(7), new_years_experience.get(idx));
            rowList.add(row);
            idx++;
        }
        System.out.println("===============Data Set After Adding Factorized column of Years of experience Column==============");
        Dataset<Row> data = sparkSession.createDataFrame(rowList, schema);
        data.show();
        //return only the first 200 elemnt from data set to show it
        return data.limit(200);
    }
}
