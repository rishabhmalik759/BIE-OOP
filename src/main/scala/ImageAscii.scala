import java.awt.Color
import java.awt.image.BufferedImage
import java.io.{File, FileWriter, IOException}
import javax.imageio.ImageIO
import scala.Array.ofDim


class ImageAscii {

  var image: BufferedImage = null
  var pixVal: Double = 0.0
  var sb: StringBuilder = null
  var array: Array[Array[Array[Int]]] = null
  var gs: Array[Array[Int]] = null
  var SCALE_FACTOR: Int = 0
  var height:Int =0
  var width:Int =0

  def convertToAscii(imgDir: String, outDir: String, compressionFactor: Int,invertFlag:Boolean): Unit = {
    try {
      val img = ImageIO.read(new File(imgDir))
      this.image = img
      this.sb = new StringBuilder((image.getWidth + 1) * image.getHeight)
      this.SCALE_FACTOR = compressionFactor

    }
    catch {
      case ex: IOException => ex.printStackTrace()
    }

    readImage(image.getHeight, image.getWidth, image)
    to_grayscale(image.getHeight, image.getWidth)
     gs = scale(image.getHeight, image.getWidth)

    if(invertFlag){
      invert()
    }

    val str = to_ascii_string(gs,invertFlag)
    write(str, outDir)
  }


  def to_grayscale(height: Int, width: Int): Unit = {
    gs = Array.ofDim[Int](height, width)
    var y = 0
    while ( {
      y < height
    }) {
      var x = 0
      while ( {
        x < width
      }) {
        val r = array(y)(x)(0)
        val g = array(y)(x)(1)
        val b = array(y)(x)(2)
        val avg = (r + g + b).toInt / 3
        gs(y)(x) = avg

        x += 1
      }

      y += 1
    }

  }

  def readImage(height: Int, width: Int, image: BufferedImage): Unit = {
    array = Array.ofDim[Int](height, width, 3)

    var y = 0
    while ( {
      y < height
    }) {
      var x = 0
      while ( {
        x < width
      }) {
        val pixel = image.getRGB(x, y)
        val color = new Color(pixel, true)
        array(y)(x)(0) = color.getRed()
        array(y)(x)(1) = color.getGreen()
        array(y)(x)(2) = color.getBlue()

        x += 1
      }

      y += 1
    }
  }


  def write(str: String, outFile: String): Unit = {
    val fw = new FileWriter(outFile, true);
    fw.write(str);
    fw.close()
  }

  def to_ascii_string(scaledGs:Array[Array[Int]],flag:Boolean): String = {
    val INTENSITY_MAP = "@#$&?^}{><*`'~=+-_,. "
    val INTENSITY_BIN = 255.toInt / INTENSITY_MAP.length
    var im_string = ""
    var a = 1
    if(flag) { a = 0}
    for (x <- scaledGs) {
      for (v <- x) {
        val c = v.toInt / INTENSITY_BIN - a
        if (c >= 0) im_string = im_string + INTENSITY_MAP.charAt(c) + INTENSITY_MAP.charAt(c)
        else im_string = im_string + "  "
      }
      im_string = im_string + "\n"
    }
    im_string
  }

  def scale(height: Int, width: Int): Array[Array[Int]] = {
    val w_scaled = width.asInstanceOf[Int] / SCALE_FACTOR
    val h_scaled = height.asInstanceOf[Int] / SCALE_FACTOR
    val scaled = ofDim[Int](h_scaled, w_scaled)
    for (w <- 0 until w_scaled) {
      for (h <- 0 until h_scaled) { //looping over blocks
        var sum = 0
        var x = w * SCALE_FACTOR
        while ( {
          x < (w + 1) * SCALE_FACTOR
        }) { //looping over the individual coordinates in block
          var y = h * SCALE_FACTOR
          while ( {
            y < (h + 1) * SCALE_FACTOR
          }) { //System.out.println("("+Integer.toString(x)+", "+Integer.toString(y)+")");
            sum = sum + gs(y)(x)

            y += 1
          }
          //end loop 2
          x += 1
        }
        scaled(h)(w) = sum.asInstanceOf[Int] / (SCALE_FACTOR * SCALE_FACTOR)
      }}

       scaled
    }

  def invert(): Unit = {

    height = gs.length
    width=gs(0).length

    var y = 0
    while ( {
      y < height
    }) {
      var x = 0
      while ( {
        x < width
      }) {
        gs(y)(x) = (1 - gs(y)(x))

        x += 1
      }

      y += 1
    }
  }


}