
import scopt.OptionParser


object Parser {

  val parser: OptionParser[Config] = new scopt.OptionParser[Config]("scala-ascii-art") {
    head("scala-image-ascii")

    opt[Boolean]('i', "invert").valueName("<bool>").action((x, c) => {
      c.copy(invert = x)
    })

    opt[Int]('c', "compression").required().valueName("<compression factor>").action((x, c) => {
      c.copy(compressionFactor = x)
    })

    opt[Int]('l', "lightness").valueName("<lightness modifier>").action((x, c) => {
      c.copy(lightnessModifier = x)
    })

    opt[String]('w', "write").valueName("<output file>").action((x, c) => {
      c.copy(out=x)
    })

    help("help").text("prints this usage text")

    arg[String]("<file>") unbounded() optional() action { (x, c) =>
      c.copy(file = x) } text("optional unbounded args" )

  }

}
