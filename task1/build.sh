#!/bin/bash
find -name "*.java" > sources.txt
javac -cp "log4j.jar" @sources.txt
