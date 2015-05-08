#!/bin/bash
my_dir="$(dirname "$0")"
allFolders=(NB20D05 NB20D20 NB25D10 NB25D25 NB30D15 NB35D05 NB35D20 NB20D10 NB20D25 NB25D15 NB30D05 NB30D20 NB35D10 NB35D25 NB20D15 NB25D05 NB25D20 NB30D10 NB30D25 NB35D15)
for folder in "${allFolders[@]}"
do
	cd ${folder}
	echo ${folder}
	../scorefile.sh 
	cd ..
done
