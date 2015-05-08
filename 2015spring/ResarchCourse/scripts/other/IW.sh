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

. myfunction.sh

# IW values
IWs=(3 4 5 6 7 8 9 10)

iws() {
    for i in "${IWs[@]}"
    do
        maxIW=$i
        fName="I"$i"NB20a"
        echo $fName
        genData
    done
    for i in "${IWs[@]}"
    do
        maxIW=$i
        totalRecords=1000
        fName="I"$i"NB20b"
        echo $fName
        genData
    done
}
iws
# Generate data with maxIW=3
# maxIW=3
# fName=I3NB20a
# genData
# Generate data with maxIW=3
# maxIW=3
# totalRecords=1000
# fName=I3NB20b
