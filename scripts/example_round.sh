#!/bin/bash

REDIS="redis6-cli"

$REDIS <<EOF
HSET jeopardy:state state IN_PROGRESS


HINCRBY jeopardy:players:Josh score 1800
HINCRBY jeopardy:players:Nora score 2800
HINCRBY jeopardy:players:Lili score -1600
HINCRBY jeopardy:players:Dr.Coleman score 8000
EOF

echo "Live game state updated."
