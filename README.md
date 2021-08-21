# GraphDB Java Example
The GraphDB Java example show how you can use Java code to connect to a GraphDB server, manage GraphDB databases and query them with native library.
All GraphDB programming examples are provided as a Maven project.

## Index
- [About project](#about-project)
- [Prerequisites to run](#prerequisites-to-run)
- [Running the example](#running-the-example)

## About project
This example project demonstrates how to connect to a GraphDB server using [RDF4J Java framework](https://rdf4j.org/javadoc/latest/) and execute queries using SPARQL.
This example is provided by the application when we download it.

## Prerequisites to run

* Java development environment (e.g., IntelliJ IDEA, etc.)

## Running the example

In this repository have tow examples: 
- [HelloWorld](src/main/java/com/ontotext/graphdb/example/app/hello/HelloWorld.java)
- [FamilyRelationsApp](src/main/java/com/ontotext/graphdb/example/app/semantica/FamilyRelationsApp.java)

> To run any of these examples, run the main method.

These examples use an embedded database, but you can replace it with a remote one. To do this you need to download and install some version of GraphDB (Docker, desktop etc), to download this go to [graphdb site](https://graphdb.ontotext.com/).

For more information you can show the [GraphDB Quick Start Guide](https://graphdb.ontotext.com/documentation/free/quick-start-guide.html).
