all: run

clean:
	rm -f out/Solver.jar out/Worker.jar

out/Solver.jar: out/parcs.jar src/Solver.java src/BFJob.java
	@javac -cp out/parcs.jar src/Solver.java src/BFJob.java
	@jar cf out/Solver.jar src/Solver.class src/BFJob.class
	@rm -f src/Solver.class src/BFJob.class

out/Worker.jar: out/parcs.jar src/BruteforceWorker.java src/BFJob.java
	@javac -cp out/parcs.jar src/BruteforceWorker.java src/BFJob.java
	@jar cf out/Worker.jar src/BruteforceWorker.class src/BFJob.class
	@rm -f src/BruteforceWorker.class src/BFJob.class

build: out/Solver.jar out/Worker.jar

run: out/Solver.jar out/Worker.jar
	@cd out && java -cp 'parcs.jar:Solver.jar' Solver
