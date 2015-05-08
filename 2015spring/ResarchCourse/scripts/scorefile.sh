#!/bin/bash
for files in *.csv; do 
    if [ -f "$files" ]; then
        # do something
       #echo $file
       #score $files "$files.pss"
	if [ -f "${files}.pss" ]; then
		echo "file exists"
	else
		#echo $files
       		score $files "$files.pss"
	fi
    fi
done
