
import Parser.parser
import org.scalatest.{FunSuite, Matchers}


class ParserTest extends FunSuite with Matchers{
  test("Parser") {
    val config = Config(true, 0, 2, true, "src/test/resources/Images/hello.png", "src/test/resources/compress.txt")
    val args : List[String]= List("scala-image-ascii" ,"-c", "2", "-i","true" ,"-f", "src/test/resources/Images/hello.png","-w" ,"src/test/resources/compress.txt","-o", "true")
    val parsedConfig = parser(args)
    parsedConfig.shouldBe(config)
  }
  test("ParserDefault") {
    val config = Config(false, 0, 1, false, "src/test/resources/Images/hello.png", "src/test/resources/compress.txt")
    val args : List[String]= List("scala-image-ascii" ,"-f", "src/test/resources/Images/hello.png","-w" ,"src/test/resources/compress.txt")
    val parsedConfig = parser(args)
    parsedConfig.shouldBe(config)
  }



  }
