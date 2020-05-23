
//project_testing is a folder in dumbo which has the dataset realdonaldtrump.json
val donald = sqlCtx.jsonFile("project_testing/realdonaldtrump.json")

//Selecting required columns    
val donald2 = donald.select("created_at", "text")

//Renameing column names
val donald3 = donald2.withColumnRenamed("created_at","Date").withColumnRenamed("text","Tweet")

//Converting dataframe to rdd
val donald3rdd = donald3.rdd

//Transforming RDD[Row] -> RDD[String] to perfosrm string manipulations
val donald4rdd = donald3rdd.map(_.mkString(","))

//converting the RDD[String] into a RDD[String,String] that is a tuple
val donald5rdd = donald4rdd.map(line => (line.toString.substring(0,30), line.toString.substring(31)))

 //Manipulation date column just to get required info (which is the month and the year)
val donald6rdd = donald5rdd.map(tuple => (tuple._1.split(" ")(5) + " " + tuple._1.split(" ")(1), tuple._2))

//Applying the preprocess function to every rdd line(tweet) to keep only the significant entities
val donald7rdd = donald6rdd.map(tuple => (tuple._1, preprocess(tuple._2)))

//Loading the list of exhaustive stopwords into a file   
var stopWords : ListBuffer[String] = new ListBuffer()
val stop = sc.textFile("project_testing/stopwords").map(a => stopWords+=a)
val stopWords_final = "URL"::"ATUSER"::stopWords.toList

//Removing the stopwords from the data
val donald8rdd = donald7rdd.map(tuple => (tuple._1, removeSW(tuple._2, stopWords_final)))

//donald8rdd is the rdd which is then used further for purposes


//Analysis of tweets pertinent to muslims also has been done
//the pipeline remains the same, just have to replace donald4rdd on line 18 with finalMus and rest of the code remains same
val mus = donald4rdd.filter(line => line.contains("muslim"))
val mus1 = donald4rdd.filter(line => line.contains("muslims"))
val mus2 = donald4rdd.filter(line => line.contains("Muslim"))
val mus3 = donald4rdd.filter(line => line.contains("Muslims"))
val finalMus = mus.union(mus1).union(mus2).union(mus3)


//This function just keeps the relevant information from the tweet text to perform future analytics on it
def preprocess(tweet: String): String = {
tweet.replaceAll("((www\\.[^\\s]+)|(https?://[^\\s]+))","URL").replaceAll("@[^\\s]+","ATUSER").replaceAll("#[^\\s]+","").replaceAll("[^A-Za-z0-9 ]+","").replaceAll("  "," ")
}

// This function removes the stopwords from the tweet text tokens
def removeSW(tweet: String, stopWords: List[String]): List[String] = {
      tweet.split(" ").filter(!stopWords.contains(_)).toList
}


//Please Note: the dataset for obama was preprocessed in a similar fashion,but faced some difficulties. So seperately preprocessed the obama dataset on scala shell and reloaded 
//the obama.txt dataset into the application and used it for the further preprocess.
