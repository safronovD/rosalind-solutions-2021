import GC.splitFile

import scala.io.Source

object GRPH {
  def main(args: Array[String]) {
    for (lines <- edges("rosalind_grph.txt", 3)) println(lines._1._1 + " " + lines._2._1)
  }

  def edges(filename: String, n: Int) = {
    val dataset = splitFile(filename)

    { for {iline <- dataset; jline <- dataset if (iline != jline) && (iline._2.takeRight(n) == jline._2.take(n))} yield (iline, jline) }
  }

  def splitFile(filename: String): Array[(String, String)] = {
    val lines = Source.fromFile(filename)
      .toArray
      .mkString
      .split('>')
      .filter(_.length != 0)

    {
      for (line <- lines)
        yield (
          line.split('\n')(0)
            .replaceAll("\r", ""),
          line.replaceAll("\r", "")
            .split('\n')
            .drop(1)
            .mkString("")
        )
    }
  }
}
