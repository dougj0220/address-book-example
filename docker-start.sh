#!/usr/bin/env bash
docker run -p 8080:8080 --add-host="localhost:10.0.2.2" docker/ab-rest:latest