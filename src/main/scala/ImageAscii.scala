

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

  //Convert Image to string
  def convertToAscii(imgDir: String, compressionFactor: Int,invertFlag:Boolean ): String = {
    try {
      val img = ImageIO.read(new File(imgDir)) // Read image file
      this.image = img
      this.sb = new StringBuilder((image.getWidth + 1) * image.getHeight)
      this.SCALE_FACTOR = compressionFactor

    }
    catch {
      case ex: IOException => println(ex.getMessage)
    }

    readImage(image.getHeight, image.getWidth, image) // read RGB image to an array
    to_grayscale(image.getHeight, image.getWidth) // convert to greyscale
     gs = scale(image.getHeight, image.getWidth) //compress image to SCALE_FACTOR

    if(invertFlag){
      invert() // if inverted is true
    }

    val str = to_ascii_string(gs,invertFlag) // convert to ascii string
    str
  }

  //convert RGB array to grayscale
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
  //read RGB image to an array
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

  //overwrite to output file
  def write(str: String, outFile: String): Unit = {
    try{
      val fw = new FileWriter(outFile, false)
      fw.write(str)
      fw.close()
    }
    catch {
      case ex: IOException => println(ex.getMessage)
    }

  }

  //convert from Int to Ascii,return an ascii string
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

  // scale/compress the the SCALE_FACTOR
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
        scaled(h)(w) = sum / (SCALE_FACTOR * SCALE_FACTOR)
      }}

       scaled
    }

  //inverts the image
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