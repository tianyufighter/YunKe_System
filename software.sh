#!/bin/bash
#这里可替换为你自己的执行程序，其他代码无需更改
SOFTWARE_NAME1=admin-1.0.0.jar
SOFTWARE_NAME2=conversation-1.0.0.jar
SOFTWARE_NAME3=gateway-1.0.0.jar
SOFTWARE_NAME4=normal-1.0.0.jar
SOFTWARE_NAME5=person-management-1.0.0.jar
SOFTWARE_NAME6=share-platform-1.0.0.jar
SOFTWARE_NAME7=technical-archive-1.0.0.jar
SOFTWARE_NAME8=user-service-1.0.0.jar
#使用说明，用来提示输入参数
usage() {
 echo "Usage: sh software.sh [start|stop|restart|status]"
 exit 1
}
 
#检查程序是否在运行
is_exist(){
 pid1=`ps -ef|grep $SOFTWARE_NAME1|grep -v grep|awk '{print $2}' `
 pid2=`ps -ef|grep $SOFTWARE_NAME2|grep -v grep|awk '{print $2}' `
 pid3=`ps -ef|grep $SOFTWARE_NAME3|grep -v grep|awk '{print $2}' `
 pid4=`ps -ef|grep $SOFTWARE_NAME4|grep -v grep|awk '{print $2}' `
 pid5=`ps -ef|grep $SOFTWARE_NAME5|grep -v grep|awk '{print $2}' `
 pid6=`ps -ef|grep $SOFTWARE_NAME6|grep -v grep|awk '{print $2}' `
 pid7=`ps -ef|grep $SOFTWARE_NAME7|grep -v grep|awk '{print $2}' `
 pid8=`ps -ef|grep $SOFTWARE_NAME8|grep -v grep|awk '{print $2}' `
 #如果不存在返回1，存在返回0 
 if [ -z "${pid1}" -a -z "${pid2}" -a -z "${pid3}" -a -z "${pid4}" -a -z "${pid5}" -a -z "${pid6}" -a -z "${pid7}" -a -z "${pid8}" ]; then
 return 1
 else
 return 0
 fi
}

#启动方法
start(){
 is_exist
 if [ $? -eq "0" ]; then
 echo "software is already running."
 else
 nohup java -jar /root/software/yunke/jar/$SOFTWARE_NAME1 > /root/software/yunke/log/${SOFTWARE_NAME1}.file 2>&1 &
 nohup java -jar /root/software/yunke/jar/$SOFTWARE_NAME2 > /root/software/yunke/log/${SOFTWARE_NAME2}.file 2>&1 &
 nohup java -jar /root/software/yunke/jar/$SOFTWARE_NAME3 > /root/software/yunke/log/${SOFTWARE_NAME3}.file 2>&1 &
 nohup java -jar /root/software/yunke/jar/$SOFTWARE_NAME4 > /root/software/yunke/log/${SOFTWARE_NAME4}.file 2>&1 &
 nohup java -jar /root/software/yunke/jar/$SOFTWARE_NAME5 > /root/software/yunke/log/${SOFTWARE_NAME5}.file 2>&1 &
 nohup java -jar /root/software/yunke/jar/$SOFTWARE_NAME6 > /root/software/yunke/log/${SOFTWARE_NAME6}.file 2>&1 &
 nohup java -jar /root/software/yunke/jar/$SOFTWARE_NAME7 > /root/software/yunke/log/${SOFTWARE_NAME7}.file 2>&1 &
 nohup java -jar /root/software/yunke/jar/$SOFTWARE_NAME8 > /root/software/yunke/log/${SOFTWARE_NAME8}.file 2>&1 &
 echo "software start success"
 fi
}

#停止方法
stop(){
 is_exist
 if [ $? -eq "0" ]; then
 kill -9 $pid1
 kill -9 $pid2
 kill -9 $pid3
 kill -9 $pid4
 kill -9 $pid5
 kill -9 $pid6 
 kill -9 $pid7
 kill -9 $pid8
 else
 echo "software is not running"
 fi
}

#输出运行状态
status(){
 is_exist
 if [ $? -eq "0" ]; then
 echo "software is running."
 else
 echo "software is NOT running."
 fi
}

#重启
restart(){
 stop
 start
}

#根据输入参数，选择执行对应方法，不输入则执行使用说明
case "$1" in
 "start")
 start
 ;;
 "stop")
 stop
 ;;
 "status")
 status
 ;;
 "restart")
 restart
 ;;
 *)
 usage
 ;;
esac
