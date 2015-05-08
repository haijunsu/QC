#!/bin/bash
for files in *.csv; do 
    if [ -f "$files" ]; then
        # do something
       #echo $file
       #score $files "$files.pss"
	#NB30D20A150I5V2data5_1000.csv.pss.top1.pg.part.2.pd
	if [ -f "${files}.pss.top1.pg.part.2.pd" ]; then
		echo "file exists"
	else
		#echo $files
       		create-parent-grouping-pd.py $files "$files.pss"
	fi
    fi
done
