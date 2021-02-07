import scala.annotation.tailrec
import scala.io.Source

object GC {
  def main(args: Array[String]) {
    val result = {
      for (line <- splitFile("rosalind_gc.txt")) yield (line._1, countGC(line._2))
    } maxBy (_._2)

    println(result._1)
    println(result._2)
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

  def countGC(dna: String): Double = {
    val res = rec_count(dna)
    ((res('C') + res('G')).toDouble / (res('C') + res('G') + res('A') + res('T'))) * 100
  }

  def rec_count(dna: String): Map[Char, Int] = {
    @tailrec def inner(dna: List[Char], counts: Map[Char, Int]): Map[Char, Int] = dna match {
      case Nil => counts
      case elem :: tail => inner(tail, counts + (elem -> (counts(elem) + 1)))
    }

    inner(dna.toList, Map(
      'A' -> 0,
      'C' -> 0,
      'G' -> 0,
      'T' -> 0,
    ))
  }
}
