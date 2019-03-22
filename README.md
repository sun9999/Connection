## Data structure
- Use a HashMap to implement an "Adjacency List' to represent the graph.
- The advantages is easy to build and easy to use.
- Please ignore the Node.java which I created it, but never used.

## The searching algorithm
- I have implemented 2 searching methods. Obviously, I finished the 1-way search, 
  and make sure it works, then enhance it to build the Bi-directional search.
- Basically, the searching is implementing the Breadth First Search (BFS) algorithm 
  using a Queue.

## How to run
Here is an sample command to run the program:
```
java -cp Connection-1.0.0-jar-with-dependencies.jar com.ikas.cmd.Connected cities.txt Boston Ypsilanti
```
Or import the project into Eclipse and run in using the IDE.
  
  
Internet References:
- https://www.geeksforgeeks.org/breadth-first-search-or-bfs-for-a-graph/
- https://adrianmejia.com/blog/2018/05/14/data-structures-for-beginners-graphs-time-complexity-tutorial/#Breadth-first-search-BFS-Graph-search
  
Books:
- Cracking the Coding Interview, Gayle Laakmann McDowell
