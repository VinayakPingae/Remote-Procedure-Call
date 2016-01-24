JFLAGS = -g
JC = javac
JVM= java
JAR=".:/home/yaoliu/src_code/local/libthrift-1.0.0.jar:/home/yaoliu/src_code/local/slf4j-log4j12-1.5.8.jar:/home/yaoliu/src_code/local/slf4j-api-1.5.8.jar:/home/yaoliu/src_code/local/log4j-1.2.14.jar" 
FILE=
.SUFFIXES: .java .class
.java.class:
	$(JC) -classpath $(JAR) $(JFLAGS) $*.java
CLASSES = \
	FileStore.java \
	JavaFileHandler.java \
	JavaClient.java \
	JavaServer.java \
	RFile.java \
	RFileMetadata.java \
	Status.java \
	StatusReport.java \
	SystemException.java
    
MAIN = JavaServer

default: classes

classes: $(CLASSES:.java=.class)

run: $(MAIN).class
	$(JVM) -cp .:libthrift-0.9.1.jar:logback-access-1.1.2.jar:logback-classic-1.1.2.jar:logback-core-1.1.2.jar:slf4j-api-1.7.7.jar  $(MAIN)

clean:
	$(RM) *.class
