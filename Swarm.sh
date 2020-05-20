#!/bin/bash

##############################################################################
# MIT License
# 
# Copyright (c) 2020 Gurudev Ball-Khalsa
# 
# Permission is hereby granted, free of charge, to any person obtaining a copy
# of this software and associated documentation files (the "Software"), to deal
# in the Software without restriction, including without limitation the rights
# to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
# copies of the Software, and to permit persons to whom the Software is
# furnished to do so, subject to the following conditions:
# 
# The above copyright notice and this permission notice shall be included in all
# copies or substantial portions of the Software.
# 
# THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
# IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
# FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
# AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
# LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
# OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
# SOFTWARE.
##############################################################################

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

