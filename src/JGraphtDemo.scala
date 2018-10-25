
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

  val clique = new PivotBronKerboschCliqueFinder(directedGraph)

  clique.forEach(x => println(x))

  println("Hello JGraphtDemo")

}
