import java.util

import org.apache.spark.sql.SparkSession


/**
  * Created by jevon.liu on 2018/11/1.
  */
object Test {

  case class ModelTest(@Fo(o = 0, s = "value1", f = "name")
                       name: String,
                       @Fo(o = 1, s = "value1", f = "value")
                       value: String,
                       @Fo(o = 0, s = "value2", f = "age")
                       age: String,
                       @Fo(o = 2, s = "value2", f = "sex")
                       sex: String
                      )

  def main(args: Array[String]): Unit = {


    val spark = SparkSession.builder().master("local").appName("case class test").getOrCreate()
    val sqlContext = spark.sqlContext;
    import spark.implicits._
    spark.sparkContext.textFile("D:\\test1.txt")
      .map(x => {
        (x.split("&")(0).split(" "), x.split("&")(1).split(" "))
      }).map(x => {
      val map2 = new util.HashMap[String, Array[String]]();
      map2.put("value1", x._1)
      map2.put("value2", x._2)
      println(x._1(0), x._2(0))
      val last = RefectUtil.getObj(classOf[ModelTest], map2)
      println(last.age)
      last
    }).toDF().createOrReplaceTempView("test")
    sqlContext.sql("select * from test where age>21").show(10)
  }


}