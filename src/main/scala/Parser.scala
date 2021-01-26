object Parser {
  val usage = """
Usage: scala-image-ascii [-c] [-f file] [-w output] ...
Where: -c   Compression factor by default 1
       -i   Invert picture by default false
       -f   Set input file to F
       -w   Set output file to F

"""

  val unknown = "(^-[^\\s])".r
  var config:Config = Config()


  val pf: PartialFunction[List[String], List[String]] = {
    case "-f" :: (arg: String) :: tail => config.file = arg; tail
    case "-w" :: (arg: String) :: tail => config.out = arg; tail
    case "-c" :: (arg: String) :: tail => config.compressionFactor = arg.toInt; tail
    case "-i" :: (arg: String) :: tail => config.invert = arg.toBoolean; tail
    case "-help" :: tail => die("unknown argument "  + "\n" + usage)
    case "help" :: tail => die("unknown argument " + "\n" + usage)
    case unknown(bad) :: tail => die("unknown argument " + bad + "\n" + usage)
  }

  def parser(args: List[String]): Config ={
    if(args.startsWith(Seq("scala-image-ascii"))){
      val res = parseArgs(args)
      if(res == Nil){
        die("unknown argument "+ "\n" + usage)

      }
      return config
    }
    die("unknown argument "+ "\n" + usage)
  }


  def parseArgs(args: List[String]): List[String] = args match {
    case Nil => Nil
    case _ => if (pf isDefinedAt args) parseArgs(pf(args)) else args.head :: parseArgs(args.tail)
  }

  def die(msg: String = usage) = {
    println(msg)
    sys.exit(1)
  }

}