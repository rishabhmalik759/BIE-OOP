import org.scalatest.{FunSuite, Matchers}

class ImageASCIIConversion extends FunSuite with Matchers {

  test("Compression-by-2") {
    val config = Config(false, 0, 2, false, "src/test/resources/Images/hello.png", "src/test/resources/compress.txt")
    val obj = new ImageAscii
    val output = obj.convertToAscii(config.file, config.compressionFactor, config.invert)
    var result:String =""
    for (line <- io.Source.fromFile("src/test/resources/compress.txt").getLines) {
      result+=line +"\n"
    }
    output.shouldBe(result)


  }

  test("Invert") {
    val config = Config(true, 0, 2, false, "src/test/resources/Images/immy.png", "src/test/resources/invert.txt")
    val obj = new ImageAscii
    val output = obj.convertToAscii(config.file, config.compressionFactor, config.invert)
    var result:String =""
    for (line <- io.Source.fromFile("src/test/resources/invert.txt").getLines) {
      result+=line +"\n"
    }
    output.shouldBe(result)


  }
}

//read("src/test/resources/ExpectedOutput/compress.txt")