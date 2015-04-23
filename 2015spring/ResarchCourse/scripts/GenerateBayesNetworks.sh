#!/bin/bash
# Available command options:
# Structure of generated graphs:[-structure singly] or [-structure multi]
# Number of nodes:[-nNodes value]
# Max degree for each node: [-maxDegree value]
# Maximum number of incoming arcs for each node: [-maxInDegree value]
# Maximum number of outgoing arcs for each node: [-maxOutDegree value]
# Maximum total number of arcs: [-maxArcs value]
# Maximum induced-width allowed: [-maxIW value]
# Maximum number of value for each node: [-maxVal value]
# This option fix the number of values to the maximum value: [-fixed_maxVal]
# Number of generated graphs: [-nBNs value]
# Number of transitions between samples (default value is 6*nNodes*nNodes): [-nTransitions value]
# Saved file format (xml is the default format): [-format xml] or [-format java]
# Name of saved file: [-fName name]

#Default parameter value
default() {
    nNode=20
    maxDegree=10
    maxArcs=100
    maxIW=2
    maxVal=2
    nNBs=20
    fName=NB20a

    totalRecords=500
}
genData() {
    java -cp ../classes:../libs/embayes.jar:../libs/colt.jar BNGenerator -format java -nNodes $nNode -maxDegree $maxDegree -maxArcs $maxArcs -maxIW $maxIW -maxVal $maxVal -nBNs $nNBs -fName $fName

    javac -cp ../classes:../libs/embayes.jar:../libs/colt.jar *.java -d ../classes
    rm *.java
    java -cp ../classes:../libs/embayes.jar:../libs/colt.jar ParseBayesNet $fName $nNBs $totalRecords
    default
}

# Generate data with default setting
default
genData

#generate 1000 records
totalRecords=1000
fName=NB20b
genData

# Generate data with nNode=25
nNode=25
fName=NB25a
genData
# Generate data with nNode=25
nNode=25
totalRecords=1000
fName=NB25b
genData

# Generate data with nNode=30
nNode=30
fName=NB30a
genData

# Generate data with nNode=30
nNode=30
totalRecords=1000
fName=NB30b
genData

# Generate data with nNode=35
nNode=35
fName=NB35a
genData

# Generate data with nNode=35
nNode=35
totalRecords=1000
fName=NB35b
genData

# Generate data with nNode=40
nNode=40
fName=NB40a
genData

# Generate data with nNode=40
nNode=40
totalRecords=1000
fName=NB40b
genData
