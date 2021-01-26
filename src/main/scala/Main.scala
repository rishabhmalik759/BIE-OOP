import Parser.parser


object Main {



  def main(args:Array[String]): Unit = {


      val config  = parser(args.toList)
      if(config!=null){
        val converter = new ImageAscii()
        converter.convertToAscii(config.file,config.out,config.compressionFactor,config.invert)
      }


  }
}

//run --image ../images/test-image.jpg --rotate +90 --scale 0.25 --invert --output-console

//write to a file -w
