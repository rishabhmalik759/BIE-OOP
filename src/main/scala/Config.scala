

case class Config(var invert: Boolean = false,
                  var lightnessModifier: Int = 0,
                  var compressionFactor: Int = 1,
                  var file: String = null,
                  var out: String = null)