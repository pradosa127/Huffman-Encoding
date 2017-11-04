JFLAGS = -g
JC = javac

.SUFFIXES: .java .class

.java.class:
		$(JC) $(JFLAGS) $*.java

CLASSES = \
		Encoder.java\
		Decoder.java

default: classes

classes: $(CLASSES:.java=.class)

clean:
		$(RM) *.class