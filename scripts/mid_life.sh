#!/bin/bash

REDIS="redis-cli"

# ---- STATE CONTROL ----
set_state() {
  local state="$1"
  $REDIS HSET jeopardy:state state "$state"
  echo "State set to $state"
}

# ---- ACTIVE QUESTION ----
activate_question() {
  local cat="$1"
  local q="$2"
  local answer="$3"
  local value="$4"

  $REDIS HSET jeopardy:active \
    category "$cat" \
    question "$q" \
    answer "$answer" \
    value "$value" \
    active "true"

  echo "Activated question C$cat Q$q"
}

deactivate_question() {
  $REDIS HSET jeopardy:active active "false"
  echo "Question deactivated"
}

# ---- PLAYER MODIFICATIONS ----
add_score() {
  local player="$1"
  local delta="$2"

  key="jeopardy:players:$player"

  current=$($REDIS HGET "$key" score)
  current=${current:-0}

  new_score=$((current + delta))

  $REDIS HSET "$key" score "$new_score"

  echo "$player score updated: $current -> $new_score"
}

set_answered() {
  local player="$1"
  local val="$2" # true/false

  $REDIS HSET "jeopardy:players:$player" answered "$val"
  echo "$player answered set to $val"
}

# ---- BOARD MODIFICATIONS ----
mark_answered() {
  local cat="$1"
  local q="$2"

  key="jeopardy:board:category:$cat:q:$q"
  $REDIS HSET "$key" answered "true"

  echo "Marked C$cat Q$q as answered"
}

edit_question() {
  local cat="$1"
  local q="$2"
  local new_question="$3"

  key="jeopardy:board:category:$cat:q:$q"
  $REDIS HSET "$key" question "$new_question"

  echo "Updated question C$cat Q$q"
}

edit_answer() {
  local cat="$1"
  local q="$2"
  local new_answer="$3"

  key="jeopardy:board:category:$cat:q:$q"
  $REDIS HSET "$key" answer "$new_answer"

  echo "Updated answer C$cat Q$q"
}

# ---- SAFE RESETS (NON-DESTRUCTIVE) ----
reset_players_answered() {
  for key in $($REDIS KEYS "jeopardy:players:*"); do
    $REDIS HSET "$key" answered "false"
  done
  echo "Reset all players answered flags"
}

# ---- DEBUG ----
show_active() {
  echo "Active Question:"
  $REDIS HGETALL jeopardy:active
}

show_players() {
  for key in $($REDIS KEYS "jeopardy:players:*"); do
    echo "$key"
    $REDIS HGETALL "$key"
    echo ""
  done
}

# ---- COMMAND ROUTER ----
case "$1" in
  state) set_state "$2" ;;
  activate) activate_question "$2" "$3" "$4" "$5" ;;
  deactivate) deactivate_question ;;
  addscore) add_score "$2" "$3" ;;
  answered) set_answered "$2" "$3" ;;
  mark) mark_answered "$2" "$3" ;;
  editq) edit_question "$2" "$3" "$4" ;;
  edita) edit_answer "$2" "$3" "$4" ;;
  resetbuzz) reset_players_answered ;;
  showactive) show_active ;;
  showplayers) show_players ;;
  *)
    echo "Usage:"
    echo "  $0 state PLAYING"
    echo "  $0 activate <cat> <q> <answer> <value>"
    echo "  $0 addscore <player> <points>"
    echo "  $0 answered <player> true|false"
    echo "  $0 mark <cat> <q>"
    echo "  $0 editq <cat> <q> \"new question\""
    echo "  $0 edita <cat> <q> \"new answer\""
    echo "  $0 resetbuzz"
    echo "  $0 showactive"
    echo "  $0 showplayers"
    ;;
esac
