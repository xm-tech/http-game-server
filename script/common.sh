#!/bin/bash

die(){
    echo -e "\033[31;40m error: $1. aborting \033[0m"
    exit 1
}

show(){
    echo -e "\033[32;40m $1 \033[0m"    
}

getip(){
	echo $(ifconfig|grep "inet addr:"|grep -v "127.0.0.1"|grep -v "inet addr:10."|awk -F':' '{print $2}'|awk '{print $1}'|sed 's/ //g')
}
