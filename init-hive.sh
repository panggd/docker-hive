#!/bin/sh
git clone https://github.com/big-data-europe/docker-hive.git
cd docker-hive
docker-compose up -d # -d flag to start as background task