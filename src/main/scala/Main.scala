

import Parser.parser

import java.io.File


object Main {



  def main(args:Array[String]): Unit = {



    parser.parse(args,Config()) match {
      case Some(config) =>
        val d = new File(config.file)
        if(!d.exists()){
            println("File")
        }
      println
      val obj = new ImageAscii
       obj.convertToAscii(config.file,config.out)

      case None =>
      println("No input received")
    }

  }
}

//run --image ../images/test-image.jpg --rotate +90 --scale 0.25 --invert --output-console

//write to a file -w
