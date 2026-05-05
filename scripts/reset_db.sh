#!/bin/bash

REDIS="redis6-cli"

echo "Clearing all Jeopardy data..."

keys=$($REDIS KEYS "jeopardy:*")

if [ -n "$keys" ]; then
  $REDIS DEL $keys
fi

echo "Reset complete."
