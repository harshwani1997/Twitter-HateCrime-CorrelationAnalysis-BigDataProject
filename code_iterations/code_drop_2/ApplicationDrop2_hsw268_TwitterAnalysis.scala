
//PLEASE NOTE: ALL THE COMMENTED CODE IN THIS FILE WAS INCLUDED IN THE ETL CODE and APPLICATION_DROPCODE1 CODE, BUT HAS BEEN PUT HERE AS THE SEQUENCE HAS TO BE MENTIONED AND MAINTAINED
//THANK YOU

import java.util.Properties

import edu.stanford.nlp.ling.CoreAnnotations
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations
import edu.stanford.nlp.pipeline.{Annotation, StanfordCoreNLP}
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations
import org.apache.spark.sql.SQLContext

import scala.collection.mutable.ListBuffer

//import org.apache.spark.sql.SQLContext
import org.apache.spark.sql.functions._
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.convert.wrapAll._

object BdadProject {

  //Setting up the pipeline for sentiments
  private val tweet_properties = new Properties()
  tweet_properties.setProperty("annotators", "tokenize, ssplit, parse, sentiment")
  private val sentiment_setup: StanfordCoreNLP = new StanfordCoreNLP(tweet_properties)

  def main(args: Array[String]): Unit = {
    System.setProperty("hadoop.home.dir", "C:\\winutils\\")
    val appName = "BdadProject"
    //val conf = new SparkConf().setMaster("local[2]").setAppName(appName)
    val conf = new SparkConf().setMaster("yarn").setAppName(appName)
    val sc = new SparkContext(conf)
    sc.setLogLevel("ERROR")
    val sqlCtx = new SQLContext(sc)
    import sqlCtx.implicits._

    // val obama = sc.textFile("project_testing/obama.txt")
    // val obama1 = obama.map(line => (line.substring(1,line.length-1)))
    // val obama2 = obama1.map(line => (line.substring(0,10), line.substring(11)))  //(Date, Tweet)
    // val obama3 = obama2.map(tuple => tuple._2)
    // val obama4 = obama3.map(line => (line,getSentiment(line)))  //(tweet,sentiment)
    // val obama5 = obama2.map(tuple => (tuple._2,tuple._1))  //(tweet,date)

    // val obama6 = obama5.toDF()
    // val obama7 = obama4.toDF()

    // val obama8 = obama6.withColumn("id", monotonically_increasing_id)
    // val obama9 = obama7.withColumn("id", monotonically_increasing_id)
    // val obama10 = obama8.join(obama9, "id")

    // val names = Seq("id","Tokenized_Tweet","Date","Tweet2","sentiment")

    // val obama11 = obama10.toDF(names: _*)
    // val obama12 = obama11.sort(obama11("id").asc)
    // val obama13 = obama12.select("Date","sentiment")   //Final schema on which we will analyse

    // val wholeObama = obama12.select("Date","Tokenized_Tweet","sentiment")
    // wholeObama.rdd.saveAsTextFile("results/wholeObama")
    // val obama14 = obama13.rdd.map(_.mkString(","))
    // val obama15 = obama14.map(line => (line.split(","))).map(tuple => (tuple(0), tuple(1))).groupByKey
    // val obama16 = obama15.sortByKey(ascending = true)

    val obama17 = obama16.map(tuple => (tuple._1, getCounts(tuple._2))) // (String, List[Any]) => (Date, [positive:a, negative:b, neutral:c])
    val obama18 = obama17.map(tuple => (tuple._1, getPercentages(tuple._2)))
    
    obama17.saveAsTextFile("results/obamaCount")
    obama18.saveAsTextFile("results/obamaPercent")

    // //project_testing is a folder in dumbo which has the dataset realdonaldtrump.json
    // val donald = sqlCtx.jsonFile("project_testing/realdonaldtrump.json")

    // //Selecting required columns    
    // val donald2 = donald.select("created_at", "text")

    // //Renameing column names
    // val donald3 = donald2.withColumnRenamed("created_at","Date").withColumnRenamed("text","Tweet")

    // //Converting dataframe to rdd
    // val donald3rdd = donald3.rdd

    // //Transforming RDD[Row] -> RDD[String] to perfosrm string manipulations
    // val donald4rdd = donald3rdd.map(_.mkString(","))

    // //converting the RDD[String] into a RDD[String,String] that is a tuple
    // val donald5rdd = donald4rdd.map(line => (line.toString.substring(0,30), line.toString.substring(31)))

    // //Manipulation date column just to get required info (which is the month and the year)
    // val donald6rdd = donald5rdd.map(tuple => (tuple._1.split(" ")(5) + " " + tuple._1.split(" ")(1), tuple._2))

    // //Applying the preprocess function to every rdd line(tweet) to keep only the significant entities
    // val donald7rdd = donald6rdd.map(tuple => (tuple._1, preprocess(tuple._2)))

    // //Loading the list of exhaustive stopwords into a file   
    // var stopWords : ListBuffer[String] = new ListBuffer()
    // val stop = sc.textFile("project_testing/stopwords").map(a => stopWords+=a)
    // val stopWords_final = "URL"::"ATUSER"::stopWords.toList

    // //Removing the stopwords from the data
    // val donald8rdd = donald7rdd.map(tuple => (tuple._1, removeSW(tuple._2, stopWords_final)))


    // //Analysis of tweets pertinent to muslims also has been done
    // //the pipeline remains the same, just have to replace donald4rdd on line 18 with finalMus and rest of the code remains same
    // val mus = donald4rdd.filter(line => line.contains("muslim"))
    // val mus1 = donald4rdd.filter(line => line.contains("muslims"))
    // val mus2 = donald4rdd.filter(line => line.contains("Muslim"))
    // val mus3 = donald4rdd.filter(line => line.contains("Muslims"))
    // val finalMus = mus.union(mus1).union(mus2).union(mus3)
    // /////

    //The above commented code is included in etl code
    
     //Building the test dataset for sentiment
    // val test = donald8rdd.map(tuple => tuple._2)

    // val test_data = test.map(line => line.mkString(" "))

    // val test_data1 = test_data.map(line => (line, getSentiment(line)))  //(tweet, sentiment)
   
    // //Now merge the test_data1 => (tweet, sentiment) and donald8Join1 => (Date, List(tweet)) => (Date, tweet) => (tweet, Date)

    // val donaldJoin = donald8rdd.map(tuple => (tuple._1, tuple._2.mkString(" ")))    //(Date, tweet)

    // val donaldJoin1 = donaldJoin.map(tuple => (tuple._2, tuple._1))  //(tweet, Date)

    // //Converting to dataframes
    // val test_data1df = test_data1.toDF   //(tweet, sentiment)
    // val donaldJoin1df = donaldJoin1.toDF  //(tweet, Date)

    // //Here we are assigning indexes so that we can sort the dataframes later
    // val result1df = test_data1df.withColumn("id", monotonically_increasing_id)
    // val donaldJoin2df = donaldJoin1df.withColumn("id", monotonically_increasing_id)

    // //Joining the two dataframes on the basis of id
    // val df3 = donaldJoin2df.join(result1df, "id")

    // val names1 = Seq("id","Tokenized_Tweet","Date","Tweet2","sentiment")

    // //Columns are assigned these column names(IN names1) and it is sorted on the basis of id
    // val df3renamed = df3.toDF(names1: _*)
    // val df4 = df3renamed.sort(df3renamed("id").asc)
    // val df5 = df4.select("Date","sentiment")   //Final schema on which we will analyse

    // val rdd6 = df5.rdd.map(_.mkString(","))

    // //Applying groupByKey to aggregate all the tweets for each month and sorting it 
    // val rdd7 = rdd6.map(line => (line.split(","))).map(tuple => (tuple(0), tuple(1))).groupByKey
    // val rdd8 = rdd7.sortByKey(ascending = true)

    //Applying getCounts and getPercentages functions to perform the analysis
    val trumpCount = rdd8.map(tuple => (tuple._1, getCounts(tuple._2))) // (String, List[Any]) => (Date, [positive:a, negative:b, neutral:c])
    val trumpPercentage = rdd9.map(tuple => (tuple._1, getPercentages(tuple._2)))
    trumpCount.saveAsTextFile("results/trumpCount")
    trumpPercentage.saveAsTextFile("results/trumpPercent")
    

  }

  //to get counts for every date
  def getCounts(sentiments: Iterable[String]): String =
    {
      var positive:Int = 0
      var negative:Int = 0
      var neutral:Int = 0
      for(s<-sentiments)
        {
          if(s=="positive")
          {
            positive = positive + 1
          }
          else if(s=="negative")
          {
            negative = negative + 1
          }
          else if(s=="neutral")
          {
            neutral = neutral + 1
          }
        }
      positive + " " + negative + " " + neutral
    }

  // def preprocess(tweet: String): String = {
  //   tweet.replaceAll("((www\\.[^\\s]+)|(https?://[^\\s]+))","URL").replaceAll("@[^\\s]+","ATUSER").replaceAll("#[^\\s]+","").replaceAll("[^A-Za-z0-9 ]+","").replaceAll("  "," ")
  // }

  // def removeSW(tweet: String, stopWords: List[String]): List[String] = {
  //   tweet.split(" ").filter(!stopWords.contains(_)).toList
  // }

  // def getSentiment(tweet: String): String = {
  //   if (Option(tweet).isDefined && !tweet.trim.isEmpty) {
  //     val annotation: Annotation = sentiment_setup.process(tweet)
  //     val (_, sentiment) =
  //       annotation.get(classOf[CoreAnnotations.SentencesAnnotation])
  //         .map { line => (line, line.get(classOf[SentimentCoreAnnotations.SentimentAnnotatedTree])) }
  //         .map { case (line, line_property) => (line.toString, getSentimentLabel(RNNCoreAnnotations.getPredictedClass(line_property))) }
  //         .maxBy { case (line, _) => line.length }
  //     sentiment
  //   } else {
  //     //throw new IllegalArgumentException("Text should not empty or null")
  //     "neutral"
  //   }
  // }

  // def getSentimentLabel(sentiment: Int): String = {
  //   if(sentiment==3 || sentiment==4)
  //     {
  //       "positive"
  //     }
  //   else if(sentiment==0 || sentiment==1) {
  //     "negative"
  //   }
  //   else
  //   {
  //     "neutral"
  //   }
  // }

  //get percentages of sentiments for each date
  def getPercentages(sentimentCounts: String): String =
  {
    var positive:String = sentimentCounts.split("")(0)
    var negative:String = sentimentCounts.split("")(1)
    var neutral:String = sentimentCounts.split("")(2)

    var positiveCounts = positive.split(":")(1).toInt
    var negativeCounts = negative.split(":")(1).toInt
    var neutralCounts = neutral.split(":")(1).toInt

    var totalCount:Int = positiveCounts + negativeCounts + neutralCounts

    var pospercent = positiveCounts.toDouble/totalCount.toDouble
    var negpercent = negativeCounts.toDouble/totalCount.toDouble
    var neutralpercent = neutralCounts.toDouble/totalCount.toDouble
    pospercent + " " + negpercent + " " + neutralpercent
  }
}