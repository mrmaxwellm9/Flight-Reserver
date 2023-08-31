#!/bin/bash
cd /Users/Maxwell/Desktop/JavaProjects
java -cp "FlightReserver_lib/sqlite-jdbc-3.43.0.0.jar" --module-path "FlightReserver_lib" --add-modules javafx.controls,javafx.fxml,javafx.graphics,java.sql -jar FlightReserver.jar