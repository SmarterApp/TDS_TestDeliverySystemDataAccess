#!/usr/bin/env bash
#-----------------------------------------------------------------------------------------------------------------------
# Description:  This script creates the permissions_db database (if it doesn't already exist): After the database is
# created, the SQL required to create the schema will be run.  The SQL scripts create the tables, functions, procedures
# etc. necessary for the databases to run.  Databases will then be populated with sample/seed data.
#
# Author:  Jeff Johnson <jjohnson@fairwaytech.com>
#
# Pre-requisites:  MySQL must be installed on the server where this script is run.
#
# Usage:  ./db-perm-schema-setup.sh (on the server that has MySQL installed and is intended to host the Permissions
# database)
#-----------------------------------------------------------------------------------------------------------------------

# If you are running this script on the server that is intended to host the databases, change HOST to "localhost"
# (without quotes).  Otherwise, use the public IP or hostname.  To use this script against a server remotely, the
# user must have remote access to MySQL.
HOST=localhost
PORT=3306
USER=remoteuser
PW='change*me'

#-----------------------------------------------------------------------------------------------------------------------
# VERIFY DATABASES EXIST BEFORE CREATING SCHEMAS
#-----------------------------------------------------------------------------------------------------------------------
printf 'BUILDING permissions_db DATABASE...\n\n'

mysql --host="$HOST" --port="$PORT" --user="$USER" --password="$PW" -e "CREATE DATABASE IF NOT EXISTS permissions_db"

printf 'DATABASE CREATION COMPLETE\n\n'

printf 'BUILDING permissions_db SCHEMA....\n\n'
#-----------------------------------------------------------------------------------------------------------------------
# CONFIG DATABASE SCHEMA
#-----------------------------------------------------------------------------------------------------------------------
printf 'PERMISSIONS - building database schema....\n'

printf '    PERMISSIONS - executing CreateTablesScript.sql\n'
mysql --host="$HOST" --port="$PORT" --user="$USER" --password="$PW" --database=permissions_db < ../SS_Permissions/Permissions/Database/Scripts/CreateTablesScript.sql
printf '  PERMISSIONS - datbase schema complete.\n\n'

#-----------------------------------------------------------------------------------------------------------------------
# LOAD GENERIC CONFIGURATION DATA
#-----------------------------------------------------------------------------------------------------------------------
printf 'PERMISSIONS - Loading configuration data...\n'

printf '  PERMISSIONS - executing db-perm-seed-data.sql\n'
mysql --host="$HOST" --port="$PORT" --user="$USER" --password="$PW" --database=permissions_db < db-perm-seed-data.sql
printf '  PERMISSIONS - data load complete.\n\n'
printf 'permissions_db DATABASE BUILD COMPLETE'