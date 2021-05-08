all: *.java
	javac *.java

clean:
	rm -f *.class

runchess:
	java chess

runcheckers:
	java checkers

runtictactoe:
	java tictactoe
