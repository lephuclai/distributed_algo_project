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



#for j in 0 10 100
#do
	for i in 1 4 6 8 10 12
	do
		#echo "→ $OUTPUT	$i	$j"
		echo "→ $OUTPUT	$i"
		#java -cp bin contention.benchmark.Test -b linkedlists.lockbased.$OUTPUT -d 2000 -t $i -u $j -i 1000 -r 2000 -W 0 | grep Throughput
		java -cp BenchBin contention.benchmark.Test -b linkedlists.lockbased.$OUTPUT -d 2000 -t $i -u 10 -i 1000 -r 2000 -W 0 | grep Throughput
		sleep 60
	done 
#done





echo "finished on" `date`
echo "DONE \o/"

