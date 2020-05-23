val crimedata = "/user/sa5476/bdad/project/hate_crime.csv"

val crime = sc.textFile(crimedata)

val output = crime.map(line => line.split(","))

val validCols = output.map(array => (array(1),array(7),array(14),array(20),array(23),array(26)))

val finalOutput = validCols.map { a => a.productIterator.mkString(",") }

finalOutput.saveAsTextFile("/user/sa5476/bdad/project/cleandata")