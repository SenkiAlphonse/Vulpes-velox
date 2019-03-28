#!/bin/bash
docker-compose up -d db
sleep 5
docker-compose up app