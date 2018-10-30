
import java.util

import org.jgrapht.Graphs
import org.jgrapht.alg.clique.PivotBronKerboschCliqueFinder
import org.jgrapht.graph.{DefaultEdge, DefaultUndirectedGraph}

import collection.JavaConverters._

import scala.collection.mutable


object JGraphtDemo extends App{



  val udirectedGraph: DefaultUndirectedGraph[String, DefaultEdge] = new DefaultUndirectedGraph[String, DefaultEdge](classOf[DefaultEdge])

  Graphs.addAllVertices(udirectedGraph, new util.ArrayList[String](util.Arrays.asList("A", "B", "C", "D", "E", "F")))

  udirectedGraph.addEdge("A", "B")
  udirectedGraph.addEdge("A", "C")
  udirectedGraph.addEdge("A", "D")
  udirectedGraph.addEdge("B", "C")
  udirectedGraph.addEdge("B", "D")
  udirectedGraph.addEdge("C", "D")
  udirectedGraph.addEdge("D", "E")
  udirectedGraph.addEdge("D", "F")
/*
  val clique = new PivotBronKerboschCliqueFinder(udirectedGraph).iterator()

  //clique.forEach(x => println(x))

  while(clique.hasNext)
    println(clique.next)
*/
  //val hiedge = udirectedGraph.edgeSet()

  //hiedge.forEach(x => println(x))

  udirectedGraph.edgesOf("B").forEach(x => println(x))

  println("A degree is "  + udirectedGraph.degreeOf("A"))
  println("B degree is " + udirectedGraph.degreeOf("B") )


  //the graph edge set
/*
  udirectedGraph.edgeSet.forEach(edge => {

   // println("Source vertext is " + udirectedGraph.getEdgeSource(edge))
    //println("Target vertext is " + udirectedGraph.getEdgeTarget(edge))

    val edgeVertextSet: Set[String] = Set(udirectedGraph.getEdgeSource(edge), udirectedGraph.getEdgeTarget(edge))







  })*/

  val neighborVertexs = mutable.Set[String]()

  val checkConnectedHash = mutable.HashMap[Set[String], Int]()


  udirectedGraph.getAllEdges("C", "D").forEach(x => {

    //get the vertext of the counting edges
    val edgeVertextSet: Set[String] = Set(udirectedGraph.getEdgeSource(x), udirectedGraph.getEdgeTarget(x))

    println("Set is " + edgeVertextSet)

    //get the neighbor vertext of the counting edges
    edgeVertextSet.foreach(edgeVertext => {
      Graphs.neighborSetOf(udirectedGraph, edgeVertext).forEach( nvSetEle => {
        if (!edgeVertextSet.contains(nvSetEle)) {
          //Graphs.getOppositeVertex(udirectedGraph, x, edgeVertext)
          val newvla: Set[String] = edgeVertextSet + nvSetEle
          if(!checkConnectedHash.contains(newvla)){
            checkConnectedHash.put(newvla, 1)
          }
          else {
            checkConnectedHash.apply(newvla) += 1
          }
          //println(checkConnectedHash.apply(x))
          udirectedGraph.containsEdge(edgeVertext, nvSetEle)
          neighborVertexs += nvSetEle
        }
      })
    })

    /*
    neighborVertexs.foreach( nvSetEle /*short for neighbor vertex set*/ => {

      /*check is the nvSetEle connect with counting edge*/


    })*/

  })


  println(checkConnectedHash)

  //aaa.foreach()
 // println(aaa)






  //udirectedGraph.incomingEdgesOf("A").forEach(println)


  //udirectedGraph.outgoingEdgesOf("A").forEach(println)




 // Graphs.getOppositeVertex(udirectedGraph, e, "B")

 // println("Hello JGraphtDemo")

  //udirectedGraph.edgesOf("A").forEach(x => println(Graphs.getOppositeVertex(udirectedGraph, x, "A")))

/*
  println("choose pivot is " + choosePivot(udirectedGraph.vertexSet().asScala, Set[String]()))

  def choosePivot(P: mutable.Set[String], X: Set[String]):String = {

    var max: Int = -1
    var pivot: String = ""


    val it = P ++ X

    it.foreach(x => {

      var count = 0
      println("debug x is " + x)

      udirectedGraph.edgesOf(x).forEach(y => {

        println("debug edge is " + y)
        if (it.contains(Graphs.getOppositeVertex(udirectedGraph, y, x))){
          count += 1
        }2

      })

      println("debug count is " + count)
      if (count > max){
        max = count
        pivot = x
      }
    })

    pivot

  }
  */


  

}
