#!/usr/bin/bash
echo "Starting DB4O-Server..."
java -cp lib/companydemo-@{version}.jar;lib/db4o-8.0.184.15484-all-java5.jar;lib/mysql-connector-java-5.0.8-bin.jar net.gumbix.dba.companydemo.db4o.DB4OServer