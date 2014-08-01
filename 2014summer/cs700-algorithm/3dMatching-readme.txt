CS700 project: 3-dimensional matching
Student: Haijun Su

Let X, Y, and Z be finite, disjoint sets, and let T be a subset of X ¡Á Y ¡Á Z. 
That is, T consists of triples (x, y, z) such that x in X,y in Y, and z in Z. 
Now M is subset of T is a 3-dimensional matching if the following holds: for any 
two distinct triples (x1, y1, z1) in M and (x2, y2, z2) in M, we have x1 <> x2, 
y1 <> y2, and z1<> z2.

This solution can accept three sets from an input file. One set is one line
and members are separated by a blank.

Output file stores the solution result.

Compile: javac -cp . ThreeDimensionalMatching.java

Usage: java -cp . ThreeDimensionalMatching <input file> <output file>

       java -cp . -DisDebug=true ThreeDimensionalMatching <input file> <output file>

Example:
       java -cp . ThreeDimensionalMatching 3dMatching-input.txt 3dMatching-output.txt

       java -cp . -DisDebug=true ThreeDimensionalMatching 3dMatching-input.txt 3dMatching-output.txt
