import org.scalatest.{FunSuite, Matchers}

class WriteToFileTest extends FunSuite with Matchers{

  test("Write-to-output-file") {
    val config = Config(true, 0, 2, false, "src/test/resources/Images/immy.png", "src/test/resources/output.txt")
    val obj = new ImageAscii
    val output = obj.convertToAscii(config.file, config.compressionFactor, config.invert)

    obj.write(output,config.out)

    var result:String =""
    for (line <- io.Source.fromFile("src/test/resources/output.txt").getLines) {
      result+=line +"\n"
    }
    assert(!result.isEmpty)


  }

  test("Write-to-output-check-if-identical") {
    val config = Config(true, 0, 2, false, "src/test/resources/Images/immy.png", "src/test/resources/output.txt")
    val obj = new ImageAscii
    val output = obj.convertToAscii(config.file, config.compressionFactor, config.invert)

    obj.write(output,config.out)

    var result:String =""
    for (line <- io.Source.fromFile("src/test/resources/output.txt").getLines) {
      result+=line +"\n"
    }

    var expectedOutput:String =""
    for (line <- io.Source.fromFile("src/test/resources/expected.txt").getLines) {
      expectedOutput+=line +"\n"
    }

    result.shouldBe(expectedOutput)


  }




}
