# Final-Java-Project-On-Wuzzuf-DataSet
this repository contains our Final project in Java Course During iTi Ai pro Track as we created Exploratory Data analysis on wuzzuf Dataset using Spark core and spark SQL then deployed our job in Spring boot web service ,making some visualization Using Xchart and display All outputs in the webservice 
Team Members:
* Amr Eid Mohamed
* Ahmed Abd-elSattar Mahmoud 
* Nayrouz Hamdy ElGammal
project Functionality:
1-To read dataset From csv and convert it to spark Dataframe.
2-Display structure and summary os data on console and using webservice.
3-removing nulls and duplicated values;
4-count the number of job offers made by each company.
5-create a pieChart of the number of joboffers made by top companies and export it as image to diaplay on browser.
6-finding the most popular job titles.
7- drawing category chart represents the frequency of each job title in the dataset.
8- find the most popular ares
9-create category chart using our generalized method changing parameters to draw the frequency of each area in the dataset.
10-print each skill and the frequency of it to get the most common skills .
11-(Bonus Question):to factorize the YearsEXP column to numbers the add the new column to the dataset


project structure:
the project contains 7 classes and the main class
1-Wuzzuf_dao class : is responsible for reading dataset and apply Queries on data set it's constructor takes dataset path and each method in it return an object of the POJO Class
2-POJO class:is responsible for getting Special infor about datasets collected from Wuzzuf_Dao as it's constructor takes Dataset<Row> and have different methods with different or no return types as neede .
3-WuzzufAnalysis:is used for tasks that needs many operations and advanced preprocessing and data collection that dao methods can't cover it do two tasks
    getting the most repeated skills    and   factorize the year of experince column to integer column then add it to the dataset
4-Drawing Class: is used For drwaing Charts using xchart ,it has two methods one for drawing piecharts and another for categoryCharts 
  Both methods are implemented in a Generalized form and Can be used to draw Pie-category charts for any set of data,colors of each slice in piechart and for barsin category chart is selected randomly in each run by our getRandomColors method that returns color object wih random values of (R,G,B).
  Takes a POJO object as input and produces images of charts that can be displayed on browser using web service
5-Services class for spring Boot that contain methods to do the different tasks with each method have suitable return object format to be printed using web service on web browser
6-Controller: is a controller for spring Boot That have a mapping for each service on specific path that executed on calling this path
7- WuzzufControllerTest: which is our Tester Client Class that makes sure that data shown on the web and comes from the request is same as the output from our Project.
  
  
  Environment:
  path to spring webService:  http://127.0.0.1:8081/path Nname from controller     EX:http://127.0.0.1:8081/CompanyOffers to view total job offers of each company
  Java intellij Ultimate Edition
  Java 1.8
  and Json formater plugin for chrome browser To view json in human readable format
  
  
