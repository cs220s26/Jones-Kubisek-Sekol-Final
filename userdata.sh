#!/bin/bash
dnf update -y
dnf install -y redis6
yum install -y git
git clone https://github.com/cs220s26/Jones-Kubisek-Sekol-Final.git
yum install -y maven-amazon-corretto21
cd /Jones-Kubisek-Sekol-Final
mvn package 
TODO: .service file information
