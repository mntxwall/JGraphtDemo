
import java.util

import org.jgrapht.Graphs
import org.jgrapht.alg.clique.PivotBronKerboschCliqueFinder
import org.jgrapht.graph.{DefaultEdge, DefaultUndirectedGraph}

import collection.JavaConverters._

import scala.collection.mutable

/**
  *
  *找出完全图的思路
  * 运用动态规划的原理，
  * 从每条边E出发，把每个顶点的neighbor点找出来，判断neighbor点是否与E相连接，如果有就构成了k=3的完全图
  * 再从k=3的完全图出发，将完全图中的所有neighbor顶点找到，再次判断是否与完全图中的每个点有相连，如果有相连，那么就构成了k=4的完全图。
  */

object JGraphtDemo extends App{


  //the k-clique
  var K: Int = 3
  var maxDegree: Int = 0

  val udirectedGraph: DefaultUndirectedGraph[String, DefaultEdge] = new DefaultUndirectedGraph[String, DefaultEdge](classOf[DefaultEdge])

  Graphs.addAllVertices(udirectedGraph, new util.ArrayList[String](util.Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9")))

  udirectedGraph.addEdge("1", "2")
  udirectedGraph.addEdge("1", "3")
  udirectedGraph.addEdge("1", "4")
  udirectedGraph.addEdge("2", "3")
  udirectedGraph.addEdge("3", "4")
  udirectedGraph.addEdge("4", "5")
  udirectedGraph.addEdge("4", "6")
  udirectedGraph.addEdge("5", "6")
  udirectedGraph.addEdge("5", "7")
  udirectedGraph.addEdge("5", "8")
  udirectedGraph.addEdge("6", "7")
  udirectedGraph.addEdge("6", "8")
  udirectedGraph.addEdge("7", "8")
  udirectedGraph.addEdge("7", "9")


 // println(udirectedGraph.containsEdge("7", "8"))
 // println(udirectedGraph.containsEdge("8", "7"))

/*
  val clique = new PivotBronKerboschCliqueFinder(udirectedGraph).iterator()

  //clique.forEach(x => println(x))

  while(clique.hasNext)
    println(clique.next)
*/
  //val hiedge = udirectedGraph.edgeSet()

  //hiedge.forEach(x => println(x))

//  udirectedGraph.edgesOf("B").forEach(x => println(x))

 // println("A degree is "  + udirectedGraph.degreeOf("A"))
 // println("B degree is " + udirectedGraph.degreeOf("B") )


  //the graph edge set
/*
  udirectedGraph.edgeSet.forEach(edge => {

   // println("Source vertext is " + udirectedGraph.getEdgeSource(edge))
    //println("Target vertext is " + udirectedGraph.getEdgeTarget(edge))

    val edgeVertextSet: Set[String] = Set(udirectedGraph.getEdgeSource(edge), udirectedGraph.getEdgeTarget(edge))
  })*/

  //val neighborVertexs = mutable.Set[String]()

  val cliqueHashMap = mutable.HashMap[Int, mutable.Set[Set[String]]]()

  cliqueHashMap.put(2, mutable.Set[Set[String]]())

//.getAllEdges("C", "D")
  //put each edge vertex in the edge Set
  udirectedGraph.edgeSet().forEach(x => {
    val edgeVertextOne = udirectedGraph.getEdgeSource(x)
    val edgeVertextTwo = udirectedGraph.getEdgeTarget(x)
    val setVertex: Set[String] = Set(edgeVertextOne, edgeVertextTwo)
    cliqueHashMap.apply(2) += setVertex

  })

  println(cliqueHashMap.apply(2))

/*

  udirectedGraph.edgeSet.forEach(countEdge => {

    //get the vertext of the counting edges
   // val edgeVertextSet: Set[String] = Set(udirectedGraph.getEdgeSource(countEdge), udirectedGraph.getEdgeTarget(countEdge))

    val edgeVertextOne = udirectedGraph.getEdgeSource(countEdge)
    val edgeVertextTwo = udirectedGraph.getEdgeTarget(countEdge)
    val edgeVertextSet: Set[String] = Set(edgeVertextOne, edgeVertextTwo)

    //get the max value of degree
    if (udirectedGraph.degreeOf(edgeVertextOne) > maxDegree) maxDegree = udirectedGraph.degreeOf(edgeVertextOne)
    if (udirectedGraph.degreeOf(edgeVertextTwo) > maxDegree) maxDegree = udirectedGraph.degreeOf(edgeVertextTwo)

    println("Set is " + edgeVertextSet)

    //if edgeVertextSet
    //get the neighbor vertext of the counting edges
    if(udirectedGraph.degreeOf(edgeVertextOne) >= K - 1 && udirectedGraph.degreeOf(edgeVertextTwo) >= K - 1){
      edgeVertextSet.foreach(edgeVertext => {
          Graphs.neighborSetOf(udirectedGraph, edgeVertext).forEach( nvSetEle => {

            println("vertext is " + nvSetEle + " and the degree is " + udirectedGraph.degreeOf(nvSetEle))
            if (!edgeVertextSet.contains(nvSetEle) && udirectedGraph.degreeOf(nvSetEle) >= K - 1 ) {
              //Graphs.getOppositeVertex(udirectedGraph, x, edgeVertext)
              val newvla: Set[String] = edgeVertextSet + nvSetEle

              println("Checking vertexs is " + newvla)

              if (!cliqueSet.contains(newvla)){

                if(!checkConnectedHash.contains(newvla)){
                  checkConnectedHash.put(newvla, 0)
                }
                if (udirectedGraph.containsEdge(edgeVertext, nvSetEle)){
                  checkConnectedHash.apply(newvla) += 1
                  print(newvla)
                  println(" value is " + checkConnectedHash.apply(newvla).toString)

                  if (checkConnectedHash.apply(newvla) >= 2){
                    cliqueSet += newvla
                  }
                }
              }


              //println(checkConnectedHash.apply(x))
              //udirectedGraph.containsEdge(edgeVertext, nvSetEle)
              //neighborVertexs += nvSetEle
            }
          })

      })

    }
    checkConnectedHash.clear()

    /*
    neighborVertexs.foreach( nvSetEle /*short for neighbor vertex set*/ => {

      /*check is the nvSetEle connect with counting edge*/


    })*/

  })
  */


  var cliqueResult = cliqueHashMap.apply(2)

  //var cliqueResult = findClique(3, cliqueHashMap.apply(2))

  while (cliqueResult.nonEmpty){

    cliqueResult = findClique(K, cliqueResult)

    if (!cliqueHashMap.contains(K))
      cliqueHashMap.put(K, cliqueResult)
    else
      cliqueHashMap.update(K, cliqueResult)

    println(s"The $K-clique is ${cliqueHashMap.apply(K)}")
    K += 1
  }


  cliqueHashMap.keysIterator.foreach(x => {

    println(x)

  })



  def findClique(kIndex: Int, kClique: mutable.Set[Set[String]]): mutable.Set[Set[String]] = {

    //Storage the result clique
    val cliqueSet = mutable.Set[Set[String]]()

    //Storage vertex counting result
    val checkConnectedHash = mutable.HashMap[Set[String], Int]()

    if (kClique.nonEmpty)
    {
      kClique.foreach(cliqueSetIndex => {

       // println("edge is " + cliqueSetIndex)
        cliqueSetIndex.foreach(cliqueVertextIndex => {
       //   println("edge vertext is " + cliqueVertextIndex)

          if(udirectedGraph.degreeOf(cliqueVertextIndex) >= kIndex - 1){
            Graphs.neighborSetOf(udirectedGraph, cliqueVertextIndex).forEach( nvSetEle => {

          //    println("vertext is " + nvSetEle + " and the degree is " + udirectedGraph.degreeOf(nvSetEle))
              if (!cliqueSetIndex.contains(nvSetEle) && udirectedGraph.degreeOf(nvSetEle) >= kIndex - 1) {
                //Graphs.getOppositeVertex(udirectedGraph, x, edgeVertext)
                val newvla: Set[String] = cliqueSetIndex + nvSetEle

           //     println("Checking vertexs is " + newvla)

                if (!cliqueSet.contains(newvla)){
                  if(!checkConnectedHash.contains(newvla)){
                    checkConnectedHash.put(newvla, 0)
                  }
                  if (udirectedGraph.containsEdge(cliqueVertextIndex, nvSetEle)){
                    checkConnectedHash.apply(newvla) += 1
             //       print(newvla)
              //      println(" value is " + checkConnectedHash.apply(newvla).toString)

                    if (checkConnectedHash.apply(newvla) >= kIndex - 1){
                      cliqueSet += newvla
                    }
                  }
                }
              }
            })

          }

        })
        checkConnectedHash.clear()
      })

    }

    cliqueSet
  }

}
