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

# number of Networks
nNBs=50
# total records of data file
records=(500 1000)

# generate data
genData() {
    Degree=$maxDegree
    Arc=$maxArcs
    if [ ${Degree} -lt 10 ]; then
        Degree="0"${maxDegree}
    fi
    if [ ${Arc} -lt 100 ]; then
        Arc="0"${maxArcs}
    fi
    fName="NB${nNodes}D${Degree}A${Arc}I${maxIW}V${maxVal}data"
    echo $fName
    java -cp ../classes:../libs/embayes.jar:../libs/colt.jar BNGenerator -format java -nNodes $nNodes -maxDegree $maxDegree -maxArcs $maxArcs -maxIW $maxIW -maxVal $maxVal -nBNs $nNBs -fName $fName

    javac -cp ../classes:../libs/embayes.jar:../libs/colt.jar *.java -d ../classes
    rm *.java
    for i in "${records[@]}"
    do
        java -cp ../classes:../libs/embayes.jar:../libs/colt.jar ParseBayesNet $fName $nNBs $i
    done    
}

# setting
Nodes=(20 25 30 35)
Degrees=(5 10 15 20 25)
Arcs=(50 100 150 200 250)
IWs=(2 5 8)
Values=(2 3)

for i in "${Nodes[@]}"
do
    nNodes=$i
    for j in "${Degrees[@]}"
    do
        maxDegree=$j
        for k in "${Arcs[@]}"
        do
            maxArcs=$k
            for m in "${IWs[@]}"
            do
                maxIW=$m
                for n in "${Values[@]}"
                do
                    maxVal=$n
                    genData
                done
            done
        done
    done
done
