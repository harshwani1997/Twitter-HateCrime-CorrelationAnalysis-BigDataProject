//At command line, type:
spark-shell --packages com.databricks:spark-csv_2.10:1.5.0
//In Scala Shell, type:
import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf
import scala.collection.mutable.ArrayBuffer
import org.apache.spark.sql.SQLContext
val sqlContext = new SQLContext(sc)
import sqlContext._
import sqlContext.implicits._

val df = sqlContext.load("com.databricks.spark.csv", Map("path" -> "bdad/project/hate_crime.csv"))
df.schema
df.show


val dftag = df.select("C1", "C7", "C18", "C21", "C24")

dftag.show(10)


val single_bias = dftag.filter(!($"C24".contains(";"))).filter(!($"C24".contains("BIAS_DESC"))).withColumnRenamed("C1","Year").withColumnRenamed("C7","State").withColumnRenamed("C18","Offender_race").withColumnRenamed("C21","Offense_name").withColumnRenamed("C24","Bias").withColumn("Total",col("Year")* 0+1)

val analysis = single_bias.groupBy("Year","Bias").agg(sum("Total").as("Total_cases"))

analysis.show()
analysis.write.format("com.databricks.spark.csv").save("bdad/project/analysis.csv")

