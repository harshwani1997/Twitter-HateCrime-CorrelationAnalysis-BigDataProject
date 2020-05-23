
//(TRUMP DATASET)
val donald = sqlCtx.jsonFile("project_testing/realdonaldtrump.json")

//Selecting required columns    
val donald2 = donald.select("created_at", "text")

//Renameing column names
val donald3 = donald2.withColumnRenamed("created_at","Date").withColumnRenamed("text","Tweet")

//Converting dataframe to rdd
val donald3rdd = donald3.rdd

//Transforming RDD[Row] -> RDD[String] to perform string manipulations
val donald4rdd = donald3rdd.map(_.mkString(","))

//The above three code lines are from the cleaning part, just to get into a form on which  we can some profiling
// Getting number of records for trump data
donald3rdd.count
//res7: Long = 40241 

// Initially the orginal dataset has more than 25 column fields
donald.columns
//res6: Array[String] = Array(contributors, coordinates, created_at, entities, extended_entities, favorite_count, favorited, geo, id, id_str, in_reply_to_screen_name, in_reply_to_status_id, in_reply_to_status_id_str, in_reply_to_user_id, in_reply_to_user_id_str, is_quote_status, lang, place, possibly_sensitive, quoted_status, quoted_status_id, quoted_status_id_str, retrieved_utc, retweet_count, retweeted, retweeted_status, scopes, source, text, truncated, user, withheld_copyright, withheld_in_countries, withheld_scope)

////After dropping columsn and making the data relevant it has only two column fields
donald2.columns
//res7: Array[String] = Array(created_at, text)


//Getting the lengths of tweets made out of trump over time
val lengths = donald4rdd.map(line => line.split(",")).map(line => line(1).length)
//117
//131
//116
//103
//109
//107
//64
//104
//115
//27
//..

//Getting the maximum length of the tweet by trump
val minlen_tweet = lengths.distinct.sortBy(x => x.toDouble,true,1)
minlen_tweet.take(1).foreach(println)
//1

//Getting the maximum length of the tweet by trump
val maxlen_tweet = lengths.distinct.sortBy(x => x.toDouble,false,1)
maxlen_tweet.take(1).foreach(println)
//152



