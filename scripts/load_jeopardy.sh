#!/bin/bash

REDIS_CLI="redis-cli"

# helper function
add_question() {
  local category="$1"
  local value="$2"
  local clue="$3"
  local answer="$4"
  local question="$5"

  key="jeopardy:${category}:${value}"

  $REDIS_CLI HSET "$key" \
    category "$category" \
    value "$value" \
    clue "$clue" \
    answer "$answer" \
    question "$question"

  # optional indexing
  $REDIS_CLI SADD "category:${category}" "$key"
  $REDIS_CLI SADD "jeopardy:questions" "$key"
}

# ---- DATA ----

add_question "LIVRES_EN_FRANCAIS" 200 \
"Contains both French & Russian elements: 'La guerre et la paix'" \
"War and Peace" \
"what is war and peace"

add_question "LIVRES_EN_FRANCAIS" 400 \
"Pure poetry from a Brooklyn bard: 'Feuilles d'herbe'" \
"Leaves of Grass" \
"what is leaves of grass"

add_question "LIVRES_EN_FRANCAIS" 600 \
"Adventure in Africa: 'Au coeur des ténèbres'" \
"Hearts of Darkness" \
"what is hearts of darkness"

add_question "LIVRES_EN_FRANCAIS" 800 \
"About a quest or quête: 'L'épée dans la pierre'" \
"The Sword in the Stone" \
"what is sword in the stone"


add_question "DISNEY_CHARACTERS" 200 \
"This legendary warrior maiden of China gained a superpower for her 2020 live-action remake" \
"Mulan" \
"who is mulan"

add_question "DISNEY_CHARACTERS" 400 \
"In 'Aladdin' Jafar's sidekick is a sarcastic parrot with this villainous name" \
"Iago" \
"who is iago"

add_question "DISNEY_CHARACTERS" 600 \
"A song about him says 'No one's slick as... No one's quick as...'" \
"Gaston" \
"who is gaston"

add_question "DISNEY_CHARACTERS" 800 \
"He says 'Some people are worth melting for'" \
"Olaf" \
"who is olaf"


add_question "DESSERT" 200 \
"Also called a half moon cookie; Obama called it a 'unity cookie'" \
"Black and White" \
"what is black and white"

add_question "DESSERT" 400 \
"Sicilian pastry tubes filled with sweet ricotta cheese" \
"Cannoli" \
"what is a cannoli"

add_question "DESSERT" 600 \
"Mexican fried dough rolled in cinnamon sugar" \
"Churro" \
"what is a churro"

add_question "DESSERT" 800 \
"Angel food or red velvet on a stick" \
"Cake pop" \
"what is a cake pop"


add_question "U_S_CITIES" 200 \
"New Mexico city known as Hot Air Balloon Capital of the World" \
"Albuquerque" \
"where is albuquerque"

add_question "U_S_CITIES" 400 \
"Dallas suburb named after Washington Irving" \
"Irving" \
"where is irving"

add_question "U_S_CITIES" 600 \
"California city named after poet John Greenleaf Whittier" \
"Whittier" \
"where is whittier"

add_question "U_S_CITIES" 800 \
"'Christmas City' in Pennsylvania" \
"Bethlehem" \
"where is bethlehem"

# ---- DONE ----
echo "Data loaded into Redis."
