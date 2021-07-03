#!/usr/bin/env bash

set -e

gw() {
  if ! command -v gradle &> /dev/null; then
    ./gradlew --parallel "$@"
  else
    gradle --parallel "$@"
  fi
}

if [ -z "$1" ]; then
  gw -t build -x test > spring-build.log &
  gw bootRun > spring-run.log
else
  gw "$@"
fi
