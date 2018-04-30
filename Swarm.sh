eval `java -jar Swarm.jar $1 $2 $3 $4 $5 $6 $7`
pid=$!
wait $pid

eval `ffmpeg -r 30 -i frame%05d.pgm -vcodec libx264 -pix_fmt yuv420p -an e$1_p$2_i$3_v$4_c$5_s$6_f$7.mp4`
pid=$!
wait $pid

eval `rm frame*.pgm`
