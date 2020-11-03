#!/usr/bin/env bash

./gradlew build
docker-compose up --build --force-recreate
