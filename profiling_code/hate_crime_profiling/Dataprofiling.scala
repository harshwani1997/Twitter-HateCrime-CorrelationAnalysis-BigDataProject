val crimedata = "/user/sa5476/bdad/project/hate_crime.csv"

val crime = sc.textFile(crimedata)

val output = crime.map(line => line.split(","))

val yeardata = output.map(array => (array(1),1))

val yearcount = yeardata.reduceByKey((x,y) => x+y)

yearcount.count() //Long = 28