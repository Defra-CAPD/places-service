#/bin/bash

function outputBuildComment() {
 echo "************************************"
 echo ""
 echo " " $1
 echo ""
 echo "************************************"
}

function run_assembly() {
	outputBuildComment "Building assembly"
	./sbt assembly

	outputBuildComment "Running assembly"
	java -jar places-service/target/places-service.jar server configuration/local/placesServiceConfiguration.yml
}

function run_dev() {
	outputBuildComment "Running dev"
	./sbt "project places-service" "run server configuration/local/placesServiceConfiguration.yml"
}

REPO_DIR="$( cd "$( dirname "${BASH_SOURCE:-$0}" )" && pwd )"
cd $REPO_DIR


if [ "$1" = "-a" ]; then
	run_assembly
else
	run_dev
fi