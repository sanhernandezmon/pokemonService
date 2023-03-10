# POKEMON SERVICE

### Getting started

This project has 2 main atributes, Users and Pokemons
for storing pruposes we will be using postgresql

Java version = 19

#### How to run
whyle the proyect is on docker-hub, you can run locally or in any cloud provider
the docker-compose.yml file, for this prupose I recommend an IDE for clicking the run button


Once both, Database and service are running you must run BaseDatos.sql on a postgres query console for db initialization

### How to use

The first step is going to be a register process, there you will be given 
an uuid in case of success, in other case an error will be returned

once you have register as user, you will have to log in the application,
if you successfully log in, A JWT will be given

Now with the JWT token you can perform the CRUD operations on the Pokemons data
by adding the token as Authentication header


in addition there are another endpoint called cats, there you will be given a fun 
fact about cats, this fact is actually from a public api and requested by an internal client


for more clarity we recommend to use postman and import test.json file, there you can manage the authentication token as an evniroment variable

