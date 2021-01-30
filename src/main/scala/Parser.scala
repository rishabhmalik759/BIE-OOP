
import scala.util.matching.Regex

object Parser{
  val usage = """
Usage: scala-image-ascii [-c] [-f file] [-w output] ...
Where: -c   Compression factor by default 1
       -i   Invert picture by default false
       -o   Output to console by default false
       -f   Set input file to path
       -w   Set output file to path,appends to existing file

"""
  //Local variables
  val unknown: Regex = "(^-[^\\s])".r
  var inputFile:String=""
  var outputFile:String=""
  var compressionFactor:Int = 1
  var invert:Boolean =false
  var console:Boolean =false

  //reset Config params
  def reset():Unit={
     inputFile=""
     outputFile=""
     compressionFactor = 1
     invert=false
     console=true
  }

  //check for options and save the properties
  val pf: PartialFunction[List[String], List[String]] = {
    case "-f" :: (arg: String) :: tail => inputFile = arg; tail
    case "-w" :: (arg: String) :: tail => outputFile = arg; tail
    case "-c" :: (arg: String) :: tail => compressionFactor = arg.toInt; tail
    case "-i" :: (arg: String) :: tail => invert = arg.toBoolean; tail
    case "-o" :: (arg: String) :: tail => console = arg.toBoolean; tail
    case "-help" :: tail => die("unknown argument "  + "\n" + usage)
    case "help" :: tail => die("unknown argument " + "\n" + usage)
    case unknown(bad) :: tail => die("unknown argument " + bad + "\n" + usage)
  }

  //reset all values,parse all args
  def parser(args: List[String]): Config ={
    reset()
    if(args.startsWith(Seq("scala-image-ascii"))){
      val res = parseArgs(args)
      if(res == Nil){
        die("unknown argument "+ "\n" + usage)

      }
      return Config(invert,0,compressionFactor, console,inputFile, outputFile)
    }

    die("unknown argument "+ "\n" + usage)

  }

  //recursive function for parsing args using partial function pf
  def parseArgs(args: List[String]): List[String] = args match {
    case Nil => Nil
    case _ => if (pf isDefinedAt args) parseArgs(pf(args)) else args.head :: parseArgs(args.tail)
  }
  //kills the process if Invalid arguments are entered,and prints the usage
  def die(msg: String = usage) = {
    println(msg)
    sys.exit(1)
  }

}