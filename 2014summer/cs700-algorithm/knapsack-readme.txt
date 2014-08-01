CS700 project: Knapsack
Student: Haijun Su

Given a set of items, each with a weight and a value, determine the number 
of each item to include in a collection so that the total weight is less 
than or equal to a given limit and the total value is as large as possible.

This solution can read the maximum weight of a container and items from an
input file. The first valid line is the maximum weight value and each other
valid line is one item.

Output file stores the solution result.

Compile: javac -cp . Knapsack.java

Usage: java -cp . Knapsack <input file> <output file>

       java -cp . -DisDebug=true Knapsack <input file> <output file>

Example:
       java -cp . Knapsack knapsack-input.txt knapsack-output.txt

       java -cp . -DisDebug=true Knapsack knapsack-input.txt knapsack-output.txt