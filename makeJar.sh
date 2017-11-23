#!/bin/sh
mvn clean install
jar mcfv manifest-headers diceroller.jar -C target/classes/ .
