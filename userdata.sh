#!/bin/bash
dnf update -y
dnf install -y redis6 git maven-amazon-corretto21

cd /home/ec2-user
git clone https://github.com/cs220s26/Jones-Kubisek-Sekol-Final.git
cd /home/ec2-user/Jones-Kubisek-Sekol-Final
mvn package

systemctl start redis6
systemctl enable redis6 

sudo cp jeopardy_bot.service /etc/systemd/system/tunetailorbot.service
sudo systemctl daemon-reload
sudo systemctl enable jeopardy_bot
