import java.util

object KafkaExample {

  case class Test(
                   aa: String,
                   @Fo(o = 1, s = "s1")
                   bb: String,
                   @Fo(o = 0, s = "s2")
                   cc: String,
                   @Fo(o = 1, s = "s2")
                   dd: String
                 );

  case class Test2(
                    cc: String = "",
                    @Fo(o = 1, s = "s2")
                    dd: String = ""
                  );


  def main(args: Array[String]): Unit = {
    RefectUtil.initCache(classOf[Test], classOf[Test2])
    val source1 = "aaa\001bbb".split("\001");
    val source2 = "ccc\002ddd".split("\002");

    //需要对source的长度做校验，不然会直接报错

    val map = new util.HashMap[String, Array[String]]();
    map.put("s1", source1)
    map.put("s2", source2)

    var test= RefectUtil.getObj(classOf[Test], map)
    var test2 = RefectUtil.getObj(classOf[Test2], map)
    print(test)
  }


}
