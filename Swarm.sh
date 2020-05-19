#!/bin/bash

# takes 1 cmd arg: params.txt
# launches Swarm.jar with all parameters

mkdir raw

while IFS='' read -r line || [[ -n "$line" ]]; do 
	
	p=($line)
	echo "Running Swarm with e=${p[0]} p=${p[1]} i=${p[2]} v=${p[3]} c=${p[4]} s=${p[5]} f=${p[6]}"

	java -jar Swarm.jar ${p[0]} ${p[1]} ${p[2]} ${p[3]} ${p[4]} ${p[5]} ${p[6]}
	
	ffmpeg -y -hide_banner -v quiet -stats -r 30 -i frame%05d.pgm -vcodec libx264 -pix_fmt yuv420p -an raw/e${p[0]}_p${p[1]}_i${p[2]}_v${p[3]}_c${p[4]}_s${p[5]}_f${p[6]}.mp4 < /dev/null
	
	rm frame*.pgm

done < "$1"

