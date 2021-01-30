import Parser.parser


object Main {

  val outputImage = Map("output" ->writeImage _)
  val parse = Map("jpeg" ->readImage _ ,"jpg" ->readImage _,"png" ->readImage _,"gif" ->readImage _)

  // write Image to the destination file
  def writeImage(output:Any,destination:String,converter:ImageAscii,consoleFlag: Boolean):Unit={
    if(consoleFlag){
      def printToConsole(): Unit = print(output)
      printToConsole()
    }

    converter.write(output.toString,destination)
   print("File written to destination"+destination)
  }

  // read Image from the input file
  def readImage(inputFile:String,compression:Int,invert:Boolean,converter:ImageAscii):String={
    val output = converter.convertToAscii(inputFile,compression,invert)
    output

  }
  // get input file extension
  def getExtension(str: String): String ={
    val result = str.split("\\.").last
    result
  }

   def main(args:Array[String]): Unit = {
    val config:Config  = parser(args.toList)
    val getExt:String = getExtension(config.file)


      if(config!=null){
        val converter = new ImageAscii()
        try{
          val output = parse(getExt)(config.file,config.compressionFactor,config.invert,converter)
          outputImage("output")(output,config.out,converter,config.console)
        }
        catch {
          case ex: Any => println(ex.getMessage)

        }
      }
    trait Output {
      def print(s: String): Unit = println(s)
    }

  }
}

//run --image ../images/test-image.jpg --rotate +90 --scale 0.25 --invert --output-console

//write to a file -w
