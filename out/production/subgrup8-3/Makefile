JFLAGS = -g
JC = javac

.SUFFIXES: .java .class

.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
		  ./Domini/Assignacio.java \
		  ./Domini/Atribut.java \
		  ./Domini/Clients.java \
		  ./Domini/Conjunts.java \
		  ./Domini/Controlador.java \
		  ./Dommini/Llista.java \
		  ./Domini/Rang.java \
		  ./Domini/Regles.java \
		  ./Domini/Resultats.java \
		  ./Persistencia/Controlador.java \
		  ./Persistencia/FileW.java \
		  ./Presentacio/Controller.java \
		  ./Presentacio/prova.java

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class
	
run:
	java prova
