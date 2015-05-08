#!/bin/bash
num=10
for i in $(seq 1 $num); 
do 
	echo $i; 
	cp `ls | grep data${i}_ ` ..
done
