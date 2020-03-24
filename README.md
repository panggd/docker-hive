# docker-hive

## Overview
This is a learner project to understand how to ingest a semi-structured data and query with Hive.

1. Run the init-hive script to deploy a docker container of Hadoop cluster server and Hive
2. Build the Java based Hive application to a Jar file
3. Docker cp the Jar file and CSV dataset to Hive container
4. Run Jar to process CSV dataset
5. Read results

## Tech stack
- Docker
- Hadoop
- Hive
- Java
- Gradle

## data
This folder consists of a CSV dataset that describes the total attendance group by medical institutions and year.

## hive
This folder consists of a Hive application that will process the CSV dataset to return the total attendance group by medical institutions.

## init-hive shell script
This is a script that will git clone the Hive docker GitHub project, deploy a docker container of Hive.

## Prerequsites

### Download and install Docker. Follow the below guides.
https://docs.docker.com/install


## How to run

### Start your docker daemon
This is really depend on your OS. For my case, it is just starting the Docker app.

### Deploy Hive container
This will deploy the docker container holding  Hive.
```bash
./init-hive.sh
```

### Build the Hive application
Use your favorite IDE and build the jar in the hive folder.

### Copy the Jar and dataset into the Hadoop + Hive container
```bash
# Go to data folder
docker cp hospital-and-outpatient-attendances.csv \
<hive_server_container_id>:/opt/hospital-and-outpatient-attendances.csv

# Go to hive folder
docker cp hive.jar <hive_server_container_id>:/opt/hive.jar
```

### Process the dataset and enjoy the output results
```bash
# Get into the Hive container
docker exec -it <hive_server_container_id> bash

# Process the dataset
java -cp hive.jar HiveApplication /opt/hospital-and-outpatient-attendances.csv
```

## Housekeeping
Here are some housekeeping tips if you are on a low memory resource machine like me.

```bash
# This is to have a clean state of your docker environment
docker stop $(docker ps -a -q) && \
docker system prune -a
```

## TODO
1. Create and integrate a REST API
3. Extract the output result to the REST API