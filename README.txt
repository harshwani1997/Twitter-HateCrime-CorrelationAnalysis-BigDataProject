------------------------------------------------------------------------------------------------
                          ARE PRESIDENTS HELPING US THINK?
------------------------------------------------------------------------------------------------
                                        Contributors
------------------------------------------------------------------------------------------------

Simran Arora     | New York University | simran.arora@nyu.edu
Harsh Shailesh Wani | New York University | hsw268@nyu.edu

------------------------------------------------------------------------------------------------
                                         DATA SOURCES
------------------------------------------------------------------------------------------------


United States Hate Crime Data (1991-2018)  |  https://crime-data-explorer.fr.cloud.gov/downloads-and-docs
Tweets from the White House Twitter account during the Obama Administration  |  https://data.world/socialmediadata/obama-white-house-social-%20media-obama%20whitehouse-tweets
Donald Trump Tweet Dataset from @realdonaldtrump twitter account  | https://dataverse.harvard.edu/dataset.xhtml?persistentId=doi%3A10.7910%2FDVN%2FKJEBIL

See data_ingest/Data_Ingest.pdf for instructions on how to store files in HDFS.


- Describe your directories and files, how to build your code, how to run your code, where to find results of a run.
------------------------------------------------------------------------------------------------
                                    BUILD AND RUN INSTRUCTIONS
------------------------------------------------------------------------------------------------
Note: The project has two parts, hate crime analysis and twitter analysis. For both for all the processes we have provided different files. These both parts are completely independent of each other and are to run as different codes. We then get the results for both and see if there is a correlation.
For the twitter analysis part, all the different scala files are just a part of one huge class "BdadProject" broken into parts

|__ profiling_code/

		This folder contains code that was used to profile datasets.
                For the twitter analysis part of the project, the dataset is "realdonaldtrump.json" whose path is to be input in "val donald"
                For the hate crimes part of the project, the dataset is hate_crime.csv whose path is to be input in "val crimedata"
                
|__ etl_code/

		This folder contains code that was used to clean datasets.
                For the twitter analysis part of the project, the dataset is "realdonaldtrump.json" whose path is to be input in "val donald"
                For the twitter analysis part, the clean preprocessed data à¸£à¸–f the donald trump dataset after this stage is stored in "val donald8rdd". For the Obama dataset the data obama.txt is already clean which is input in ApplicationDrop1_hsw268.scala.
                For the hate crimes part of the project, the dataset is hate_crime.csv whose path is to be input in "val crimedata"
                For the hate crimes, the results is generated in "/user/sa5476/bdad/project/cleandata"
                

|__ code_iterations/

|___|__ iteration1/
                        For twitter analysis part of the project, ApplicationDrop1_hsw268.scala is the first iteration of the twitter sentiment analytic, which runs the data through the NLP pipeline of preprocessing the data to perform sentimental analysis
			
			ApplicationDrop1_hsw268.scala generates the sentiment for all the Trump tweets and obama tweets and aggregates it for every month of every year.
                        About input paths: For the twitter analysis part, there are three input path files, one is used in ApplicationDrop1_hsw268.scala - The dataset obama.txt is input into "val obama"
                                           For the hate crime analysis part, the dataset used is clean_data.csv and is input in "val crimedata" in file "ApplicationDrop1_sa5467_HateCrimeAnalysis.scala"
                      

|___|__ iteration2/

			For twitter analysis part of the project, ApplicationDrop2_hsw268.scala is the second iteration of the twitter sentiment analytic, which performs analysis to generate the counts and the percentages for the positive, negative and neutral sentiments through the years from 2011 and 2019 covering the presidential terms of both Obama and Trump
			
                        We are not joining the tables from our two individual analysis, so you can run both the codes differently. First to create a jar for the twitter analysis part the command is:sbt -mem 2000 "set test in assembly := {}" assembly
The command to submit the spark job on dumbo for the twitter_analysis part is : spark2-submit --class BdadProject --deploy-mode cluster --executor-memory 4G --num-executors 3 project/BdadProject-assembly-0.1.jar

                        After combining the code of ApplicationDrop2_hsw268.scala with the previous etl and ApplicationDrop1_hsw268.scala codes you can run the entire code as one BdadProject class and get the results.
                        
                        About results:	For the twitter sentiment analysis part, results for Trump get generated in the hdfs under the results directory as "results/trumpCount" and "results/trumpPercent". For Obama, get generated in the hdfs under the results directory as "results/obamaCount" and "results/obamaPercent"
                                        For the hate crime analysis part, in the file "sa5476_crime_for_each_bias_HateCrimeAnalysis.scala", the results gets generated in "bdad/project/analysis.csv"

                        About input paths: For the twitter analysis part, there are three input path files, 2) and 3) is used in ApplicationDrop2_hsw268.scala
                                                1) The dataset realdonaldtrump.json is input into "val donald"
                                                2) The dataset stopwords is input into "val stop"
                        For the hate crime analysis part, the dataset used is hate_crimes.csv and is input in "val df" in file "sa5476_crime_for_each_bias_HateCrimeAnalysis.scala". In both files "sa5476_bias_count_HateCrimeAnalysis.scala", and "crime_per_year_HateCrimeAnalysis.scala" the data is hate_crimes.csv put in "val crimedata"
                        
                                               
------------------------------------------------------------------------------------------------
                                         FILE MANIFEST
------------------------------------------------------------------------------------------------


Code Drop #2/
|
|__ README.txt
|
|__ code_iterations/
|   |
|   |__ code_drop_1/
|   |   |
|   |   |__ ApplicationDrop1_hsw268_TwitterAnalysis.scala
|   |   |__ ApplicationDrop1_sa5467_HateCrimeAnalysis.scala
|   |   
|   |   
|   |__ code_drop_2/
|       |
|       |__ ApplicationDrop2_hsw268_TwitterAnalysis.scala
|       |__ sa5476_crime_for_each_bias_HateCrimeAnalysis.scala
|       |__ sa5476_bias_count_HateCrimeAnalysis.scala
|       |__ sa5476_crime_for_each_bias_HateCrimeAnalysis.scala
|      
|__ data_ingest/
|   |
|   |__ Data_Ingest_Hate Crime.pdf
|   |__ Dataingestion.scala
|   |__ Data_Ingest_Twitter Analysis.pdf
|   |__ DataIngestion_twitteranalysis.scala
|
|__ etl_code/
|    |
|    |__ hate_crime_cleaning/
|    |   |
|    |   |__ Datacleaning.scala
|    |
|    |__ twitter_cleaning/
|    |   |
|    |   |__ Datacleaning.scala
|
|__ profiling_code/
|    |
|    |__ hate_crime_profiling/
|    |   |
|    |   |__ Dataprofiling.scala
|    |   |__Dataprofiling.pdf
|    |
|    |__ twitter_profiling/
|    |   |
|    |   |__ Dataprofiling.scala
|   
|__ screenshots/ 
    |
    |__ hate_crime_visualisation/
    |   |
    |   |__ 2012-2014.jpg
    |   |
    |   |__ 2016-2018.jpg
    |   |
    |   |__ Anti-Muslim.jpg
    |   |
    |   |__ Anti-Arab.jpg
    |   |
    |   |__ Anti-Hispanic.jpg
    |   |
    |   |__ Anti-Lesbian.jpg
    |   |
    |   |__Bias_each_year.jpg
    |   |
    |   |__ Obama.jpg
    |
    |__ twitter_visualisation/
        |
        |__ Sentiment_Obama.PNG
        |
        |__ Sentiment_Trump.PNG
        |
        |__ bargraph_Trump.PNG
        |
        |__ muslimPieChart.PNG
