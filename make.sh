#!/usr/bin/env bash
dir_name=`dirname "${BASH_SOURCE-$0}"`
pwd=`pwd`
current_dir=$pwd/$dir_name

profile="local"

mvn -f pom.xml clean package -P$profile -U

cp spring-boot-application/target/spring-boot-application.jar bin/spring-boot-application.jar