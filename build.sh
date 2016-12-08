#!/bin/bash

JAVA_HOME=$JAVA_HOME_1_6
export JAVA_HOME
PATH=$JAVA_HOME/bin:$PATH

export MAVEN_HOME=$MAVEN_3_0_4
PATH=$MAVEN_HOME:$PATH
export PATH

mvn clean
mvn -U -Ponline deploy -Dmaven.test.skip=true
mkdir output
cp target/*.jar output
