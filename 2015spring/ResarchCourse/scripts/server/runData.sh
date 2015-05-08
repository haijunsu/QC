#!/bin/bash
rm ilpTime.txt
rm astarTime.txt
rm fileList.txt
#ln -s ~/ilp/gobnilp/gobnilp.set
ln -s ~/ilp/gobnilp/gobnilp.set
for files in *.pss; do 
    if [ -f "$files" ]; then
        # do something
	#date +"%D %T.%N"
	echo `date +"%D %T.%N,"` $files >> fileList.txt
	gobnilp -fpss $files >> ilpTime.txt
#	astar $files >> astarTime.txt
	astar-decomposition.py -t 1000 $files -p "${files}.top1.pg.part.2.pd" >> astarTime.txt
    fi
done
rm gobnilp.set
grep "Solving Time" ilpTime.txt |awk '{print $5}'>a.txt

#awk '{if (sub(/^Found/,"")) printf "%s", $0; else print $0}' astarTime.txt |grep solution: | awk '{print $3}'|awk '{if (sub(/s$/,"")) printf "%s\n", $0; else print $0}'>b.txt
grep "Total wall time:" astarTime.txt| awk '{print $4}'|awk '{if (sub(/s$/,"")) printf "%s\n", $0; else print $0}'>b.txt

paste -d, fileList.txt a.txt b.txt>c.txt
