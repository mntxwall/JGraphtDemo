
import java.util

import org.jgrapht.Graphs
import org.jgrapht.alg.clique.PivotBronKerboschCliqueFinder
import org.jgrapht.graph.{DefaultEdge, DefaultUndirectedGraph}

import collection.JavaConverters._

import scala.collection.mutable


object JGraphtDemo extends App{



  val directedGraph = new DefaultUndirectedGraph[String, DefaultEdge](classOf[DefaultEdge])

  Graphs.addAllVertices(directedGraph, new util.ArrayList[String](util.Arrays.asList("A", "B", "C", "D", "E", "F")))

  directedGraph.addEdge("A", "B")
  directedGraph.addEdge("A", "C")
  directedGraph.addEdge("A", "D")
  directedGraph.addEdge("B", "C")
  directedGraph.addEdge("B", "D")
  directedGraph.addEdge("C", "D")
  directedGraph.addEdge("D", "E")
  directedGraph.addEdge("D", "F")

  val clique = new PivotBronKerboschCliqueFinder(directedGraph).iterator()

  //clique.forEach(x => println(x))

  while(clique.hasNext)
    println(clique.next)

  //val hiedge = directedGraph.edgeSet()

  //hiedge.forEach(x => println(x))

  directedGraph.edgesOf("B").forEach(x => println(x))

  println("A degree is "  + directedGraph.degreeOf("A"))
  println("B degree is " + directedGraph.degreeOf("B") )


 // Graphs.getOppositeVertex(directedGraph, e, "B")

 // println("Hello JGraphtDemo")

  //directedGraph.edgesOf("A").forEach(x => println(Graphs.getOppositeVertex(directedGraph, x, "A")))


  println("choose pivot is " + choosePivot(directedGraph.vertexSet().asScala, Set[String]()))

  def choosePivot(P: mutable.Set[String], X: Set[String]):String = {

    var max: Int = -1
    var pivot: String = ""


    val it = P ++ X

    it.foreach(x => {

      var count = 0
      println("debug x is " + x)

      directedGraph.edgesOf(x).forEach(y => {

        println("debug edge is " + y)
        if (it.contains(Graphs.getOppositeVertex(directedGraph, y, x))){
          count += 1
        }

      })

      println("debug count is " + count)
      if (count > max){
        max = count
        pivot = x
      }
    })

    pivot

  }


}
