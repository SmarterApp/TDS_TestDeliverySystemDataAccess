#!/usr/bin/env bash
#-----------------------------------------------------------------------------------------------------------------------
# Description:  This script creates the following databases (if they don't already exist):
#
#   * configs
#   * itembank
#   * session
#   * archive
#
# After the schemas are created, the SQL required to create the objects for each schema will be run.  The SQL
# scripts create the tables, functions, procedures etc. necessary for the schemas to run.  Schemas will then be
# populated with sample/seed data.
#
# NOTE:  For the itembank seed data, these SQL scripts were created by Fairway:
#
#   * db-itembank-config.sql
#   * db-itembank-load-tests.sql
#   * db-itembank-item-update.sql
#
# The scripts cited above are part of the tds-build repo.
#
# Finally, any specified patches/updates are applied to the schemas as appropriate.

#
# Pre-requisites:  
# 
#   * The MySQL server intended to host the TDS databases must be accessible by the computer that will
#     run this script.  The MySQL server must have `log_bin_trust_function_creators` set to `true`
#   * The TDS_TestDeliverySystemDataAccess must be at the same directory level as TDS_Build.  For example:
#
#       /home/ubuntu/TDS_Build
#       /home/ubuntu/TDS_TestDeliverySystemDataAccess
#
# Usage:  ./db-schema-setup.sh (on the server that has MySQL installed and is intended to host the TDS databases)
#-----------------------------------------------------------------------------------------------------------------------

# If you are running this script on the server that is intended to host the databases, change HOST to "localhost"
# (without quotes).  Otherwise, use the public IP or hostname.  To use this script against a server remotely, the
# user must have remote access to MySQL.
HOST=[the host name or IP address of the database server that will host the TDS databases]
PORT=[the port on which the database server is listening]
USER=[the user name with sufficient privileges to create schemas and objects within those schemas]
PW=[the password for the MySQL user account cited above]

#-----------------------------------------------------------------------------------------------------------------------
# VERIFY DATABASES EXIST BEFORE CREATING SCHEMAS
#-----------------------------------------------------------------------------------------------------------------------
printf 'BUILDING DATABASES...\n\n'

DATABASES=(
    "configs"
    "itembank"
    "session"
    "archive"
)

for db in "${DATABASES[@]}"; do
    printf "Creating $db database...\n"
    mysql --host="$HOST" --port="$PORT" --user="$USER" --password="$PW" -e "CREATE DATABASE IF NOT EXISTS $db CHARACTER SET utf8 COLLATE utf8_unicode_ci"
    printf "  $db database created.\n"
done

printf 'DATABASE CREATION COMPLETE\n\n'

printf 'BUILDING DATABASE SCHEMAS....\n\n'
#-----------------------------------------------------------------------------------------------------------------------
# CONFIG DATABASE SCHEMA
#-----------------------------------------------------------------------------------------------------------------------
printf 'CONFIGS - building database schema....\n'
printf '    CONFIGS - executing create_tables.sql\n'
mysql --host="$HOST" --port="$PORT" --user="$USER" --password="$PW" --database=configs < ../../tds-dll-schemas/src/main/resources/sql/MYSQL/configs/create_tables.sql
printf '    CONFIGS - executing create_constraints.sql\n'
mysql --host="$HOST" --port="$PORT" --user="$USER" --password="$PW" --database=configs < ../../tds-dll-schemas/src/main/resources/sql/MYSQL/configs/create_constraints.sql
printf '    CONFIGS - executing create_indices.sql\n'
mysql --host="$HOST" --port="$PORT" --user="$USER" --password="$PW" --database=configs < ../../tds-dll-schemas/src/main/resources/sql/MYSQL/configs/create_indices.sql

# execute stored procedure scripts
for f in ../../tds-dll-schemas/src/main/resources/sql/MYSQL/configs/StoredProcedures/*
do
    printf "    CONFIGS - stored procedures - executing %s\n" "$f"
    mysql --host="$HOST" --port="$PORT" --user="$USER" --password="$PW" --database=configs < "$f"
done

#execute function scripts
for f in ../../tds-dll-schemas/src/main/resources/sql/MYSQL/configs/Functions/*
do
    printf "    CONFIGS - functions - executing %s\n" "$f"
    mysql --host="$HOST" --port="$PORT" --user="$USER" --password="$PW" --database=configs < "$f"
done
printf '  CONIFGS - datbase schema complete.\n\n'


#-----------------------------------------------------------------------------------------------------------------------
# ITEMBANK DATABASE SCHEMA
#-----------------------------------------------------------------------------------------------------------------------
printf 'ITEMBANK - building database schema...\n'
printf '    ITEMBANK - executing create_tables.sql\n'
mysql --host="$HOST" --port="$PORT" --user="$USER" --password="$PW" --database=itembank < ../../tds-dll-schemas/src/main/resources/sql/MYSQL/itembank/create_tables.sql
printf '    ITEMBANK - executing create_constraints.sql\n'
mysql --host="$HOST" --port="$PORT" --user="$USER" --password="$PW" --database=itembank < ../../tds-dll-schemas/src/main/resources/sql/MYSQL/itembank/create_constraints.sql
printf '    ITEMBANK - executing create_indices.sql\n'
mysql --host="$HOST" --port="$PORT" --user="$USER" --password="$PW" --database=itembank < ../../tds-dll-schemas/src/main/resources/sql/MYSQL/itembank/create_indices.sql

for f in ../../tds-dll-schemas/src/main/resources/sql/MYSQL/itembank/Triggers/*
do
    printf "    ITEMBANK - triggers - executing %s\n" "$f"
    mysql --host="$HOST" --port="$PORT" --user="$USER" --password="$PW" --database=itembank < "$f"
done

for f in ../../tds-dll-schemas/src/main/resources/sql/MYSQL/itembank/StoredProcedures/*
do
    printf "    ITEMBANK - stored procedures - executing %s\n" "$f"
    mysql --host="$HOST" --port="$PORT" --user="$USER" --password="$PW" --database=itembank < "$f"
done

for f in ../../tds-dll-schemas/src/main/resources/sql/MYSQL/itembank/Functions/*
do
    printf "    ITEMBANK - functions - executing %s\n" "$f"
    mysql --host="$HOST" --port="$PORT" --user="$USER" --password="$PW" --database=itembank < "$f"
done
printf '  ITEMBANK - database schema complete\n\n'


#-----------------------------------------------------------------------------------------------------------------------
# SESSION DATABASE SCHEMA
#-----------------------------------------------------------------------------------------------------------------------
printf 'SESSION - building database schema...\n'
printf '    SESSION - executing create_tables.sql\n'
mysql --host="$HOST" --port="$PORT" --user="$USER" --password="$PW" --database=session < ../../tds-dll-schemas/src/main/resources/sql/MYSQL/session/create_tables.sql
printf '    SESSION - executing create_constraints.sql\n'
mysql --host="$HOST" --port="$PORT" --user="$USER" --password="$PW" --database=session < ../../tds-dll-schemas/src/main/resources/sql/MYSQL/session/create_constraints.sql
printf '    SESSION - executing create_indices.sql\n'
mysql --host="$HOST" --port="$PORT" --user="$USER" --password="$PW" --database=session < ../../tds-dll-schemas/src/main/resources/sql/MYSQL/session/create_indices.sql
printf '    SESSION - executing Triggers/triggers.sql\n'
mysql --host="$HOST" --port="$PORT" --user="$USER" --password="$PW" --database=session < ../../tds-dll-schemas/src/main/resources/sql/MYSQL/session/Triggers/triggers.sql

for f in ../../tds-dll-schemas/src/main/resources/sql/MYSQL/session/Triggers/*
do
    printf "    SESSION - triggers - executing %s\n" "$f"
    mysql --host="$HOST" --port="$PORT" --user="$USER" --password="$PW" --database=session < "$f"
done

for f in ../../tds-dll-schemas/src/main/resources/sql/MYSQL/session/Views/*
do
    printf "    SESSION - views - executing %s\n" "$f"
    mysql --host="$HOST" --port="$PORT" --user="$USER" --password="$PW" --database=session < "$f"
done

for f in ../../tds-dll-schemas/src/main/resources/sql/MYSQL/session/StoredProcedures/*
do
    printf "    SESSION - stored procedures - executing %s\n" "$f"
    mysql --host="$HOST" --port="$PORT" --user="$USER" --password="$PW" --database=session < "$f"
done

for f in ../../tds-dll-schemas/src/main/resources/sql/MYSQL/session/Functions/*
do
    printf "    SESSION - stored procedures - executing %s\n" "$f"
    mysql --host="$HOST" --port="$PORT" --user="$USER" --password="$PW" --database=session < "$f"
done

printf "    SESSION - altering testtype column in r_proctorpackage table" "$f"
mysql --host="$HOST" --port="$PORT" --user="$USER" --password="$PW" --database=session -e "ALTER TABLE r_proctorpackage MODIFY testtype VARCHAR(40)"
printf '  SESSION - database schema complete.\n\n'


#-----------------------------------------------------------------------------------------------------------------------
# ARCHIVE DATABASE SCHEMA
#-----------------------------------------------------------------------------------------------------------------------
printf 'ARCHIVE - building database schema...\n'
printf '    ARCHIVE - executing create_tables.sql\n'
mysql --host="$HOST" --port="$PORT" --user="$USER" --password="$PW" --database=archive < ../../tds-dll-schemas/src/main/resources/sql/MYSQL/archive/create_tables.sql
printf '    ARCHIVE - executing create_indices.sql\n'
mysql --host="$HOST" --port="$PORT" --user="$USER" --password="$PW" --database=archive < ../../tds-dll-schemas/src/main/resources/sql/MYSQL/archive/create_indices.sql
printf '  ARCHIVE - database schema complete.\n\n'


#-----------------------------------------------------------------------------------------------------------------------
# LOAD GENERIC CONFIGURATION DATA
#-----------------------------------------------------------------------------------------------------------------------
printf 'DATA - load generic configuration data...\n'
# Load generic configuration data into configs database
for f in ../../tds-dll-schemas/src/main/resources/import/genericsbacconfig/gen*.sql
do
    printf "    DATA[CONFIGS] - generic config data - executing %s\n" "$f"
    mysql --host="$HOST" --port="$PORT" --user="$USER" --password="$PW" --database=configs < "$f"
done

# Load itembank configuration data
printf '    DATA[ITEMBANK] - loading configuration data - executing db-itembank-config.sql\n'
mysql --host="$HOST" --port="$PORT" --user="$USER" --password="$PW" --database=itembank < db-itembank-config.sql

# Load session._externs table for SBAC and SBAC_PT in the Development environment
printf '    DATA[SESSION] - loading seed data - executing db-session-externs-seed-data.sql\n'
mysql --host="$HOST" --port="$PORT" --user="$USER" --password="$PW" --database=session < db-session-externs-seed-data.sql

printf '  DATA - load generic configuration data complete.\n\n'

#-----------------------------------------------------------------------------------------------------------------------
# APPLY PATCHES
#-----------------------------------------------------------------------------------------------------------------------
printf 'PATCHES - applying database patches...\n'
printf '    PATCHES[CONFIGS] - executing configs_update_patch_02252015.sql\n'
mysql --host="$HOST" --port="$PORT" --user="$USER" --password="$PW" --database=configs < ../../tds-dll-schemas/src/main/resources/import/genericsbacconfig/configs_update_patch_02252015.sql
printf '    PATCHES[CONFIGS] - executing sb1277_other accommodation addition.sql\n'
mysql --host="$HOST" --port="$PORT" --user="$USER" --password="$PW" --database=configs < ../../tds-dll-schemas/src/main/resources/import/genericsbacconfig/'sb1277_other accommodation addition.sql'
printf '    PATCHES[CONFIGS] - executing sb1116_appmessages_update.sql\n'
mysql --host="$HOST" --port="$PORT" --user="$USER" --password="$PW" --database=configs < ../../tds-dll-schemas/src/main/resources/import/genericsbacconfig/sb1116_appmessages_update.sql
printf '    PATCHES[CONFIGS] - executing response_duration_update.sql\n'
mysql --host="$HOST" --port="$PORT" --user="$USER" --password="$PW" --database=configs < ../../tds-dll-schemas/src/main/resources/import/genericsbacconfig/response_duration_update.sql
# Quote from Rami: "--I think this file need NOT be executed when building database from scratch, because the table constraints/indexes has been removed from the original create script (src\main\resources\sql\MYSQL\session\create_indexes.sql) to begin with."
# printf '    PATCHES[SESSION] - apply sb1282_student_proctor_package_changes.sql\n'
# mysql --host="$HOST" --port="$PORT" --user="$USER" --password="$PW" --database=session < ../../tds-dll-schemas/src/main/resources/import/sessionupdates/sb1282_student_proctor_package_changes.sql
printf '    PATCHES[CONFIGS] - executing sb1293_modify_NEA_NEDS.sql\n'
mysql --host="$HOST" --port="$PORT" --user="$USER" --password="$PW" --database=configs < ../../tds-dll-schemas/src/main/resources/import/genericsbacconfig/sb1293_modify_NEA_NEDS.sql
printf '    PATCHES[CONFIGS] - executing sb1281_other_accommodation_visibility.sql\n'
mysql --host="$HOST" --port="$PORT" --user="$USER" --password="$PW" --database=configs < ../../tds-dll-schemas/src/main/resources/import/genericsbacconfig/sb1281_other_accommodation_visibility.sql
printf '    PATCHES[CONFIGS] - executing sb1301_translation_combined_values order.sql\n'
mysql --host="$HOST" --port="$PORT" --user="$USER" --password="$PW" --database=configs < ../../tds-dll-schemas/src/main/resources/import/genericsbacconfig/'sb1301_translation_combined_values order.sql'
printf '    PATCHES[CONFIGS] - executing sb1350_appmessageupdate.sql\n'
mysql --host="$HOST" --port="$PORT" --user="$USER" --password="$PW" --database=configs < ../../tds-dll-schemas/src/main/resources/import/genericsbacconfig/'sb1350_appmessageupdate.sql'
printf '    PATCHES[CONFIGS] - executing sb1362_appmessage_update.sql\n'
mysql --host="$HOST" --port="$PORT" --user="$USER" --password="$PW" --database=configs < ../../tds-dll-schemas/src/main/resources/import/genericsbacconfig/'sb1362_appmessage_update.sql'
printf '    PATCHES[CONFIGS] - executing sb1505_tdscoremessageobject_insert.sql\n'
mysql --host="$HOST" --port="$PORT" --user="$USER" --password="$PW" --database=configs < ../../tds-dll-schemas/src/main/resources/import/genericsbacconfig/'sb1505_tdscoremessageobject_insert.sql'
printf '    PATCHES[CONFIGS] - executing sbac_help_message_path_update.sql\n'
mysql --host="$HOST" --port="$PORT" --user="$USER" --password="$PW" --database=configs < ../../tds-dll-schemas/src/main/resources/import/genericsbacconfig/'sbac_help_message_path_update.sql'

printf '    PATCHES[ITEMBANK] - executing sb1396_testgradesreduction.sql\n'
mysql --host="$HOST" --port="$PORT" --user="$USER" --password="$PW" --database=itembank < ../../tds-dll-schemas/src/main/resources/import/genericsbacconfig/'sb1396_testgradesreduction.sql'
printf '    PATCHES[ITEMBANK] - executing db-itembank-item-update.sql\n'
mysql --host="$HOST" --port="$PORT" --user="$USER" --password="$PW" --database=itembank < db-itembank-item-update.sql
printf '    PATCHES[ITEMBANK] - executing db-client_testtool-languagecasefix.sql\n'
mysql --host="$HOST" --port="$PORT" --user="$USER" --password="$PW" --database=itembank < db-client_testtool-languagecasefix.sql

printf '  PATCHES - complete.\n\n'
printf 'BUILDING DATABASE SCHEMAS COMPLETE\n\n'
