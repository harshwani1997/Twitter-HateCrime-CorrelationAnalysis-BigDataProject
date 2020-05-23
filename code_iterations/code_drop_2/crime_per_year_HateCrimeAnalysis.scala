//import org.apache.spark.sql.SQLContext
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SQLContext

object simran {

  def main(args: Array[String]): Unit = {
    System.setProperty("hadoop.home.dir", "C:\\winutils\\")
    val appName = "BdadProject"
    val conf = new SparkConf().setMaster("local[2]").setAppName(appName)
    //val conf = new SparkConf().setMaster("yarn").setAppName(appName)
    val sc = new SparkContext(conf)
    sc.setLogLevel("ERROR")
    val sqlCtx = new SQLContext(sc)

  val crimedata = sc.textFile("/user/sa5476/bdad/project/hate_crime.csv")

  val output = crimedata.map(line => line.split(","))

    val yeardata = output.map(array => (array(0),1))

    val yearcount = yeardata.reduceByKey((x,y) => x+y)

    yearcount.count() //Long = 28
  }
}