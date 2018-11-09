
import java.util

import org.jgrapht.Graphs
import org.jgrapht.alg.clique.PivotBronKerboschCliqueFinder
import org.jgrapht.graph.{DefaultEdge, DefaultUndirectedGraph}

import collection.JavaConverters._
import scala.collection.mutable
import scala.collection.immutable

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

  println(udirectedGraph.edgeSet())
  

  val cliqueHashMap = mutable.HashMap[Int, mutable.Set[Set[String]]]()

  //cliqueHashMap.put(2, mutable.Set[Set[String]]())

  val edgeVertexSet = mutable.Set[Set[String]]()

//.getAllEdges("C", "D")
  //put each edge vertex in the edge Set
  udirectedGraph.edgeSet().forEach(x => {
    val edgeVertextOne = udirectedGraph.getEdgeSource(x)
    val edgeVertextTwo = udirectedGraph.getEdgeTarget(x)
    val setVertex: Set[String] = Set(edgeVertextOne, edgeVertextTwo)
    //cliqueHashMap.apply(2) += setVertex
    edgeVertexSet += setVertex

  })

 // println(cliqueHashMap.apply(2))

  //var cliqueResult = cliqueHashMap.apply(2)

  var cliqueResult = edgeVertexSet

  val kCliqueGraphHashMap = mutable.HashMap[Int, DefaultUndirectedGraph[Set[String], DefaultEdge]]()

  val kCliqueGraphHashMap2 = mutable.HashMap[Int, Set[Set[String]]]()

  while (cliqueResult.nonEmpty){

    cliqueResult = findClique(K, cliqueResult)

    if (!cliqueHashMap.contains(K))
      cliqueHashMap.put(K, cliqueResult)
    else
      cliqueHashMap.update(K, cliqueResult)

    println(s"The $K-clique is ${cliqueHashMap.apply(K)}")
    K += 1
  }


  /*
  //根据找到的完全子图来计算利用CPM计算结果
  cliqueHashMap.keysIterator.foreach(cliqueHashMapKey => {

    val kCliqueGraph = new DefaultUndirectedGraph[Set[String], DefaultEdge](classOf[DefaultEdge])

    //val edgeCheck = mutable.HashMap[DefaultEdge, Int]()

    if (cliqueHashMap.apply(cliqueHashMapKey).nonEmpty) {

      //formed a new graph and add new vertex
      cliqueHashMap.apply(cliqueHashMapKey).foreach(cliqueSetA => {

        kCliqueGraph.addVertex(cliqueSetA)
      })

      val t = kCliqueGraph.vertexSet().asScala.toSeq
      //println(t(0))

      for (i <- t.indices){
        var j = i + 1
        var s = t.length - 1
        while(j <= s){

          if( !(t(i) == t(j)) ){

            if( !kCliqueGraph.containsEdge(t(i), t(j)) && t(i).intersect(t(j)).size >= cliqueHashMapKey - 1){

              kCliqueGraph.addEdge(t(i), t(j))
            }

          }
          if ( !(t(i) == t(s))){
            if( !kCliqueGraph.containsEdge(t(i), t(s)) && t(i).intersect(t(s)).size >= cliqueHashMapKey - 1){
              kCliqueGraph.addEdge(t(i), t(s))
            }
          }
          j += 1
          s -= 1
        }

      }
    }
    kCliqueGraphHashMap.put(cliqueHashMapKey, kCliqueGraph)

  })// end cliqueHashMapKey

  println(kCliqueGraphHashMap.apply(3).edgeSet())
*/


  val clusterHashMap = mutable.HashMap[Int, mutable.ListBuffer[mutable.Set[String]]]()

  //val clusterSet = mutable.Set[Set[String]]()

  //a new way to get the clique map
  cliqueHashMap.keysIterator.foreach(cliqueIndex => {

    //根据k子图，添加空白List有待添加k子图的分组
    clusterHashMap.put(cliqueIndex, mutable.ListBuffer[mutable.Set[String]]())

    /*
    *算法描述：

    根据cliqueHashMap中的结构，构建clusterHashMap。
    clusterHashMap与cliqueHashMap中不一样的地方在于：
    clusterHashMap用来保存k子图下的分组结果。
    cliqueHashMpa是保存k子图的结果。

    cliqueHashMap中k子图记为m，其中m(i)表示k子图下的第i个元素。
    如果m(i)不存在于clusterHashMap中,将m(i)加入到clusterHashMap中记为c(i)。

    做c(i)与m(i)的交集得出结果s(i,i)，如果s(i,i)集合中元素的个数大于等于k-1，
    那么c(i)和m(i)可以组成一个分组。

    在做分组的过程中会出现c(i)与c(j)和m(i)的交集s(i,i),s(j,i)都会大于等于k-1
    那么说明c(i)和c(j)是连通的，那么就要合并c(i)和c(j)
    *
    *
    * */
    cliqueHashMap.apply(cliqueIndex).foreach(cliqueSetA => {

      val tmpCliqueSetAtoMutable = collection.mutable.Set(cliqueSetA.toArray:_*)
      val clusterSet = clusterHashMap.apply(cliqueIndex)

      var isMerge: Int = 0

  //    println("cliqueSetA is " + cliqueSetA)
   //   println("tmpR is " + tmpR)

      if (clusterSet.isEmpty){

        clusterSet.append(tmpCliqueSetAtoMutable)

      }
      else{
        //用来保存处理过的set，如果还有相同的边，要进行Set合并
        var maxSet = mutable.Set[String]()
        var listIndex: Int = 0

        clusterSet.foreach(clusterIndexSet => {

          if (clusterIndexSet.intersect(cliqueSetA).size >= cliqueIndex - 1){

            if (maxSet.isEmpty){
              listIndex = clusterSet.indexOf(clusterIndexSet)
              clusterIndexSet ++= tmpCliqueSetAtoMutable
              isMerge = 1
              maxSet = clusterIndexSet
            }
            else{
              clusterSet(listIndex) ++= tmpCliqueSetAtoMutable
              clusterSet -= clusterIndexSet
            }
          }
        })
        if (isMerge == 0){
          clusterSet.append(tmpCliqueSetAtoMutable)
        }


      }

    })

  })

  println(clusterHashMap)



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
