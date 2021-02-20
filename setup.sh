#!/usr/bin/env bash

gw() {
   gradle "$@" || ./gradlew "$@"
}

gw -t build -x test > spring-build.log &
gw bootRun > spring-run.log
