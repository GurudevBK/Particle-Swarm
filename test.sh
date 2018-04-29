eval `javac *.java`
pid=$!
wait $pid

eval `java Driver $1 $2 $3 $4 $5 $6 $7`
pid=$!
wait $pid

eval `ffmpeg -r 30 -i frame%05d.pgm -vcodec libx264 -pix_fmt yuv420p -an e$1_p$2_i$3_c$4_s$5_v$6_f$7.mp4`
pid=$!
wait $pid

eval `rm frame*.pgm`
