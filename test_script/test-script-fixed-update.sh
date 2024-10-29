#!/bin/bash

case $1 in
	"1" )	echo CoarseGrainedListIntSet
			OUTPUT="CoarseGrainedListBasedSet"
	;;
	"2" )	echo HandOverHandListIntSet
			OUTPUT="HandsOverHands"
	;;
	"3" )	echo LazyLinkedListSortedSet
			OUTPUT="LazyLinkedListSortedSet"
	;;
	*)		echo "Specify algorithm"
			exit 0
esac

echo "Who I am: $OUTPUT on `uname -n`"
echo "started on" `date`



for j in 100 1000 10000
do
	for i in 1 4 6 8 10 12
	do
		echo "→ $OUTPUT	$j	$i"

		java -cp BenchBin contention.benchmark.Test -b linkedlists.lockbased.$OUTPUT -d 2000 -t $i -u 10 -i $j -r $((2*$j)) -W 0 | grep Throughput
		sleep 60
	done 
done

echo "finished on" `date`
echo "DONE \o/"

