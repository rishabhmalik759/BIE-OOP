import java.awt.{Font, Graphics}
import java.awt.image.BufferedImage
import java.io.{File, FileWriter, IOException}
import javax.imageio.ImageIO
import scala.util.Random


class ImageAscii{

  var image:BufferedImage = null
  var pixVal:Double = 0.0
  var sb:StringBuilder=null
  val base = "$@B%8&MW#*oahkbdpqwmZ0OQLCJUYXzcvunxrjft/\\\\(|)1{}][?-_+~i!Il:;,\"\\^`"
  var graphics:Graphics = null

  def convertToAscii(imgDir: String,outDir:String): Unit = {
    try  {
      val img = ImageIO.read(new File(imgDir))
      this.image = img
       this.graphics = image.getGraphics
      this.sb  = new StringBuilder((image.getWidth + 1) * image.getHeight)
      graphics.setFont(new Font("SansSerif", Font.BOLD, 24))
    }
    catch {
      case ex: IOException => ex.printStackTrace()
    }

    var y=0
    var x=0
    for( y <- 0 until image.getHeight()){
      var strbl = new StringBuilder();

      for( x <- 0 until image.getWidth()){
      val temp = image.getRGB(x,y)
        strbl.append(strChar(temp));


    }

      if(strbl.toString().trim.nonEmpty){
        sb.append(strbl.toString())

        sb.append("\n")
      }

  }

    write(sb.toString(),outDir)
  }

  def strChar(g: Double): String = {
    var rand = new Random()
    val random = rand.nextInt(base.length)

    var str = " "
    if (g >= 230) str = " "
    else if (g >= 160) str = "\\"
    else if (g >= 130) str = "^"
    else if (g >= 100) str = "&"
    else if (g >= 70) str = "8"
    else if (g >= 50) str = "#"
    else if (g == -1) str = "R"
    else str = base.charAt(random)+""
    str

  }

 def write(str: String,outFile:String):Unit ={
   val fw = new FileWriter(outFile, true) ;
   fw.write(str) ;
   fw.close()
 }


}
