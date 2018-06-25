#!/bin/bash

source /root/common.sh

game_conf(){
	\cp -rf ${server_template_dir} ${server_setup_dir}

	game_conf_file=${server_setup_dir}/webapps/demo/WEB-INF/classes/conf/config.properties
	sed -i "s/jedis.Port=60001/jedis.Port=${redis_port}/" ${game_conf_file}
	sed -i "s/serverid=1/serverid=${sid}/" ${game_conf_file}

	datasource_conf_file=${server_setup_dir}/webapps/demo/META-INF/context.xml
	sed -i "s/demo_1/${db_name}/" ${datasource_conf_file}

	tomcat_conf_file=${server_setup_dir}/tomcat/conf/server.xml
	sed -i "s/10001/${http_port}/" ${tomcat_conf_file}
	sed -i "s/${sname_template}/${sname}/" ${tomcat_conf_file}

	tomcat_startup_file=${server_setup_dir}/tomcat/startup.sh
	sed -i "s/${sname_template}/${sname}/g" ${tomcat_startup_file}
	# FIXME 此处根据需求改动
	sed -i "s/512m/1536m/g" ${tomcat_startup_file}

	tomcat_shutdown_file=${server_setup_dir}/tomcat/shutdown.sh
	sed -i "s/${sname_template}/${sname}/g" ${tomcat_shutdown_file}

	tomcat_tlog_file=${server_setup_dir}/tomcat/tlog
	sed -i "s/${sname_template}/${sname}/" ${tomcat_tlog_file}

	rm -f ${server_setup_dir}/tomcat/pid

	show "game_conf ok"
}

mysql_conf(){
	/usr/local/mysql/bin/mysql -u${db_user} -p${db_passwd} -e "create database ${db_name}; use ${db_name}; source ${db_template_file}; "
	/usr/local/mysql/bin/mysql -u${db_user} -p${db_passwd} -e "GRANT ALL ON ${db_name}.* to ppgames@'%' IDENTIFIED BY 'Dfdfd#45@3!';FLUSH PRIVILEGES;"

    show "mysql_conf ok"
}

redis_conf(){
	\cp -f ${redis_template_file} ${redis_conf_file}

	sed -i "s/60001/${redis_port}/" ${redis_conf_file}
	sed -i "s/redis_1/redis_${sid}/" ${redis_conf_file}
	# /usr/local/bin/memcached -d -m 1024 -u root -p ${mem_port} -P /tmp/memcached_${1}.pid
	show "redis_conf ok"
}

# TODO check sid

show "begin"

sid=$1
sname="s${sid}"
sname_template="s1"

http_port=$[10000+${sid}]

redis_port=$[60000+${sid}]
redis_conf_file="/data/redis/conf/redis_${sid}.conf"
redis_template_file="/data/redis/conf/redis_1.conf"

db_name="demo_${sid}"
db_user="root"
db_passwd='F_#$9er#!@'

db_template_file="/root/sync/db/demo_1.sql"

# 只有1台物理机
server_template_dir="/root/sync/${sname_template}"
server_setup_dir="/data/server/${sname}"


#statements
game_conf || die "game_conf fail"
mysql_conf || die "mysql_conf fail"
redis_conf || die "redis_conf fail"
show "deploy ${sid} ok"
