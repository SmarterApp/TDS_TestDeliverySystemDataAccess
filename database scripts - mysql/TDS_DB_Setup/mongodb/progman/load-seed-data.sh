#!/usr/bin/env bash
#-----------------------------------------------------------------------------------------------------------------------
# Description: This script will load seed data for all Components into the MongoDB database that supports the ProgMan
# component.  The records will be loaded into the "component" collection of ProgMan's database.
#
# !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! IMPORTANT !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
# Before running this script, verify the following:
#
#   1.  The "component" collection in ProgMan's database is empty
#   2.  The HOST, PORT, USER, PW and DB variables are configured correctly for your environment
#
# !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
#
# Author: Jeff Johnson <jjohnson@fairwaytech.com>
#
# Pre-Requisites:  MongoDB must be installed on the computer that runs this script.  The "component_seed_data.json" file
# must be in the same directory as this script.
#
# Usage: ./load-seed-data.sh
#-----------------------------------------------------------------------------------------------------------------------
HOST=54.201.173.209     # The FQDN or IP address of the MongoDB server hosting the ProgMan database
PORT=27017              # The port on which MongoDB is listening
USER=admin              # The user account with "readWrite" privileges in the ProgMan database
PW=password123          # The password for the user account
DB=progman              # The name of the database containing ProgMan's data

COMPONENT_LENGTH=$(mongo $DB --host $HOST:$PORT -u $USER -p $PW --eval "db.component.count()" | tail -1)

if [ "$COMPONENT_LENGTH" != 0 ]; then
    printf "The $DB.component collection is not empty, meaning Components have already been added to the database.  Exiting.\n"
    exit 1
fi;

mongoimport -v -h $HOST:$PORT -d $DB -u $USER -p $PW -c component --file component_seed_data.json
