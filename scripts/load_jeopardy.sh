#!/bin/bash

REDIS_CLI="redis6-cli"

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

add_question "ARTISTS_BY_CONCERT_TOURS" 200 \
"Blond Ambition; Madame X" \
"Madonna" \
"who is madonna"

add_question "ARTISTS_BY_CONCERT_TOURS" 400 \
"Hell Freezes Over; One of These Nights" \
"The Eagles" \
"who are the eagles"

add_question "ARTISTS_BY_CONCERT_TOURS" 600 \
"Music of the Spheres; Viva la Vida" \
"Coldplay" \
"who is coldplay"

add_question "ARTISTS_BY_CONCERT_TOURS" 800 \
"Divide; Mathematics" \
"Ed Sheeran" \
"who is ed sheeran"


add_question "PEANUTS" 200 \
"Charles Schulz loved Beethoven's 'Hammerklavier' sonata--which this tiny virtuoso is playing in an early strip" \
"Schroeder" \
"who is schroeder"

add_question "PEANUTS" 400 \
"Most of the gang visits Lucy's psychiatric booth; this boy certainly has some issues" \
"Pig-Pen" \
"who is pig pen"

add_question "PEANUTS" 600 \
"This Christmas special includes 'Fear not: for behold I bring you tidings of great joy'" \
"A Charlie Brown Christmas" \
"what is a charlie brown christmas"

add_question "PEANUTS" 800 \
"This 1966 Halloween special features the Great Pumpkin" \
"It's the Great Pumpkin, Charlie Brown" \
"what is its the great pumpkin charlie brown"


add_question "YOUR_HOUSE_IS_SMART" 200 \
"iRobot launched this O.G. robovac in 2002" \
"Roomba" \
"what is a roomba"

add_question "YOUR_HOUSE_IS_SMART" 400 \
"Amazon-owned brand of doorbell cameras" \
"Ring" \
"what is ring"

add_question "YOUR_HOUSE_IS_SMART" 600 \
"IoT stands for this" \
"Internet of Things" \
"what is internet of things"

add_question "YOUR_HOUSE_IS_SMART" 800 \
"Laser-based mapping tech used by smart devices" \
"LiDAR" \
"what is lidar"


add_question "HAVE_A_DAY" 200 \
"January 18 celebrates this reference book" \
"Thesaurus" \
"what is thesaurus"

add_question "HAVE_A_DAY" 400 \
"September 19: Talk Like a Pirate Day" \
"Talk Like a Pirate Day" \
"when is talk like a pirate day"

add_question "HAVE_A_DAY" 600 \
"Tater Tot Day & Crepe Day share this date" \
"Groundhog Day" \
"when is groundhog day"

add_question "HAVE_A_DAY" 800 \
"Keyboard key celebrated June 28 & Oct 22" \
"Caps Lock" \
"what is caps lock"


add_question "OFFICIAL_LANGUAGES" 200 \
"In Cuba" \
"Spanish" \
"what is spanish"

add_question "OFFICIAL_LANGUAGES" 400 \
"In Egypt" \
"Arabic" \
"what is arabic"

add_question "OFFICIAL_LANGUAGES" 600 \
"In Benin" \
"French" \
"what is french"

add_question "OFFICIAL_LANGUAGES" 800 \
"In Brunei" \
"Malay" \
"what is malay"


add_question "COMPUTER_LANGUAGES" 200 \
"Beginner's All-purpose Symbolic Instruction Code" \
"BASIC" \
"what is basic"

add_question "COMPUTER_LANGUAGES" 400 \
"Language created by Tim Berners-Lee for web pages" \
"HTML" \
"what is html"

add_question "COMPUTER_LANGUAGES" 600 \
"Language named after a snake used by NASA" \
"Python" \
"what is python"

add_question "COMPUTER_LANGUAGES" 800 \
"Language developed by Bjarne Stroustrup: C++" \
"C++" \
"what is c++"


add_question "TECHNOLOGY" 200 \
"Cars that use dual power systems" \
"Hybrids" \
"what are hybrids"

add_question "TECHNOLOGY" 400 \
"Relational data storehouses" \
"Databases" \
"what are databases"

add_question "TECHNOLOGY" 600 \
"Moore's Law refers to these doubling" \
"Transistors" \
"what are transistors"

add_question "TECHNOLOGY" 800 \
"GUI stands for this" \
"Graphical User Interface" \
"what is a graphical user interface"


add_question "ANIMALS_IN_GERMAN" 200 \
"Schaf" \
"Sheep" \
"what is a sheep"

add_question "ANIMALS_IN_GERMAN" 400 \
"Schwein" \
"Pig" \
"what is a pig"

add_question "ANIMALS_IN_GERMAN" 600 \
"Nilpferd" \
"Hippo" \
"what is a hippo"

add_question "ANIMALS_IN_GERMAN" 800 \
"Kavallerie refers to these animals" \
"Horses" \
"what are horses"


add_question "SITCOMS_BY_CHARACTERS" 200 \
"Michael Scott; Dwight Schrute; Pam Beesly" \
"The Office" \
"what is the office"

add_question "SITCOMS_BY_CHARACTERS" 400 \
"Jackie; Darlene; Dan" \
"The Conners" \
"what is the conners"

add_question "SITCOMS_BY_CHARACTERS" 600 \
"Georgie; Meemaw; young Sheldon" \
"Young Sheldon" \
"what is young sheldon"

add_question "SITCOMS_BY_CHARACTERS" 800 \
"Charlie Kelly; Frank Reynolds; Mac" \
"It's Always Sunny in Philadelphia" \
"what is its always sunny in philadelphia"


add_question "GRAPHY" 200 \
"List of sources" \
"Bibliography" \
"what is a bibliography"

add_question "GRAPHY" 400 \
"Science of mapmaking" \
"Cartography" \
"what is cartography"

add_question "GRAPHY" 600 \
"Art of decorative writing" \
"Calligraphy" \
"what is calligraphy"

add_question "GRAPHY" 800 \
"Film photography category at the Oscars" \
"Cinematography" \
"what is cinematography"


add_question "MOVIE_TITLE_PAIRS" 200 \
"Hawaiian girl adopts an alien" \
"Lilo & Stitch" \
"what is lilo and stitch"

add_question "MOVIE_TITLE_PAIRS" 400 \
"King Arthur surreal quest film" \
"Monty Python and the Holy Grail" \
"what is monty python and the holy grail"

add_question "MOVIE_TITLE_PAIRS" 600 \
"Director of Frankenstein befriends gardener" \
"Gods and Monsters" \
"what is gods and monsters"

add_question "MOVIE_TITLE_PAIRS" 800 \
"Two women go on the run" \
"Thelma & Louise" \
"what is thelma and louise"

# ---- DONE ----
echo "Data loaded into Redis."
