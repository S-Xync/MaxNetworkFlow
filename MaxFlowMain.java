/*
* Name : SaiKumar Immadi
* Roll No : 1501058
*/

/*
  Algorithms Programming Assignment
  Maximum Network Flow
  Ford-Fulkerson Algorithm for finding Maximum Flow in a Network
*/

/*Program Starts from Line 168*/

/*
  Read the Comments carefully.
  Four examples are being included with this program.
  You can also try it with your own examples but the
  representation of your examples should be in the below given format.
*/

// First Input : no. of vertices
// Second Input : no. of edges
// Edge : (vertex 1 , vertex 2, flow from vertex 1 to vertex 2)
// e : (u, v, f)


//  Copy and Paste any of the below given representations and paste it as input.

// Example : 1
// Source : 0 | Sink : 5
// vertices --> 6 | edges --> 10
// Maximum Flow : 23

//Adjacency Matrix of Example : 1
//Representation for this example is given below the Matrix
/*
  {
  {0, 16, 13, 0, 0, 0},
  {0, 0, 10, 12, 0, 0},
  {0, 4, 0, 0, 14, 0},
  {0, 0, 9, 0, 0, 20},
  {0, 0, 0, 7, 0, 4},
  {0, 0, 0, 0, 0, 0}
  }
*/

// Representation of Example : 1

/*

  6 10
  0 1 16
  0 2 13
  1 2 10
  1 3 12
  2 1 4
  2 4 14
  3 2 9
  3 5 20
  4 3 7
  4 5 4

*/

// Example : 2
// Source : 0 | Sink : 6
// vertices --> 7 | edges --> 11
// Maximum Flow : 5

//Adjacency Matrix of Example : 2
//Representation for this example is given below the Matrix

/*
  {
  {0, 3, 0, 3, 0, 0, 0},
  {0, 0, 4, 0, 0, 0, 0},
  {3, 0, 0, 1, 2, 0, 0},
  {0, 0, 0, 0, 2, 6, 0},
  {0, 1, 0, 0, 0, 0, 1},
  {0, 0, 0, 0, 0, 0, 9},
  {0, 0, 0, 0, 0, 0, 0}
  }
*/

// Representation of Example : 2

/*

  7 11
  0 1 3
  0 3 3
  1 2 4
  2 0 3
  2 3 1
  2 4 2
  3 4 2
  3 5 6
  4 1 1
  4 6 1
  5 6 9

*/

// Example : 3
// Source : 0 | Sink : 5
// vertices --> 6 | edges --> 9
// Maximum Flow : 19

//Adjacency Matrix of Example : 3
//Representation for this example is given below the Matrix

/*
  {
  {0, 10, 10, 0, 0, 0},
  {0, 0, 2, 4, 8, 0},
  {0, 0, 0, 0, 9, 0},
  {0, 0, 0, 0, 0, 10},
  {0, 0, 0, 6, 0, 10},
  {0, 0, 0, 0, 0, 0}
  }
*/

// Representation of Example : 3

/*

  6 9
  0 1 10
  0 2 10
  1 2 2
  1 3 4
  1 4 8
  2 4 9
  3 5 10
  4 3 6
  4 5 10

*/

// Example : 4
// Source : 0 | Sink : 3
// vertices --> 4 | edges --> 5
// Maximum Flow : 3

//Adjacency Matrix of Example : 4
//Representation for this example is given below the Matrix

/*
  {
  {0, 3, 1, 0},
  {0, 0, 1, 1},
  {0, 0, 0, 3},
  {0, 0, 0, 0}
  }
*/

// Representation of Example : 4

/*

  4 5
  0 1 3
  1 2 1
  0 2 1
  1 3 1
  2 3 3

*/

//Program Starts here

import java.util.*;
import java.lang.*;
import java.util.LinkedList;

class MaxFlow
{
    static int V; //Number of vertices in graph

    public MaxFlow(int v) {
        this.V = v;
    }

    /* Returns true if there is a path from source 's' to sink
    't' in residual graph. Also fills parent[] to store the
    path */
    boolean bfs(int rGraph[][], int s, int t, int parent[])
    {
        // Create a visited array and mark all vertices as not
        // visited
        boolean visited[] = new boolean[V];
        for(int i=0; i<V; ++i)
            visited[i]=false;

        // Create a queue, enqueue source vertex and mark
        // source vertex as visited
        LinkedList<Integer> queue = new LinkedList<Integer>();
        queue.add(s);
        visited[s] = true;
        parent[s]=-1;

        // Standard BFS Loop
        while (queue.size()!=0)
        {
            int u = queue.poll();

            for (int v=0; v<V; v++)
            {
                if (visited[v]==false && rGraph[u][v] > 0)
                {
                    queue.add(v);
                    parent[v] = u;
                    visited[v] = true;
                }
            }
        }

        // If we reached sink in BFS starting from source, then
        // return true, else false
        return (visited[t] == true);
    }

    // Returns tne maximum flow from s to t in the given graph
    int fordFulkerson(int graph[][], int s, int t)
    {
        int u, v;

        // Create a residual graph and fill the residual graph
        // with given capacities in the original graph as
        // residual capacities in residual graph

        // Residual graph where rGraph[i][j] indicates
        // residual capacity of edge from i to j (if there
        // is an edge. If rGraph[i][j] is 0, then there is
        // not)
        int rGraph[][] = new int[V][V];

        for (u = 0; u < V; u++)
            for (v = 0; v < V; v++)
                rGraph[u][v] = graph[u][v];

        // This array is filled by BFS and to store path
        int parent[] = new int[V];

        int max_flow = 0; // There is no flow initially

        // Augment the flow while tere is path from source
        // to sink
        while (bfs(rGraph, s, t, parent))
        {
            // Find minimum residual capacity of the edhes
            // along the path filled by BFS. Or we can say
            // find the maximum flow through the path found.
            int path_flow = Integer.MAX_VALUE;
            for (v=t; v!=s; v=parent[v])
            {
                u = parent[v];
                path_flow = Math.min(path_flow, rGraph[u][v]);
            }

            // update residual capacities of the edges and
            // reverse edges along the path
            for (v=t; v != s; v=parent[v])
            {
                u = parent[v];
                rGraph[u][v] -= path_flow;
                rGraph[v][u] += path_flow;
            }

            // Add path flow to overall flow
            max_flow += path_flow;
        }

        // Return the overall flow
        return max_flow;
    }
}


public class MaxFlowMain {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int v = scanner.nextInt(); // v = number of vertices
        int e = scanner.nextInt(); // e = number of edges
        int graph[][] = new int[v][v];
        for (int i = 0; i < v; i++) {
            for (int j = 0; j < v; j++) {
                graph[i][j] = 0;
            }
        }

        for (int i = 0; i < e; i++) {
            int start = scanner.nextInt();
            int end = scanner.nextInt();
            graph[start][end]=scanner.nextInt();
        }
        int source = 0;
        int sink = v-1;

        MaxFlow m = new MaxFlow(v);

        System.out.println("The Maximum Flow of Network : "+m.fordFulkerson(graph, source, sink));

    }
}
