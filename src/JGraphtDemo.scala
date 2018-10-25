
import java.util

import org.jgrapht.Graphs
import org.jgrapht.alg.clique.PivotBronKerboschCliqueFinder
import org.jgrapht.graph.{DefaultEdge, DefaultUndirectedGraph}

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

 // Graphs.getOppositeVertex(directedGraph, e, "B")

 // println("Hello JGraphtDemo")

}
