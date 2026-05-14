mkdir -p out

find src -name "*.java" > sources.txt

javac -d out @sources.txt
java -cp out main.Main

rm sources.txt
