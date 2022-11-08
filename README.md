# Complete simple webapp

Goal: Deploy to Azure a webapp that can communinicate with SQL Server

## Goal 1: Deploy SOMETHING to Azure

* Run a simple web server with Jetty
* Package with shade plugin (NB: Not complete)
* Deploy with Azure plugin
* Add some logging (in theory)

## Goal 2: Make JPA work

* Add Jersey and JPA
* Fix shade packaging
* Setup Azure SQL Server
* application.properties

## Goal 3: Make React work

* npm create vite@latest
* frontend-plugin to build React
* Fix Jetty to server from src/main/resources without locking
