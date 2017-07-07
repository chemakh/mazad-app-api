sudo systemctl stop mazadService.service
java -jar -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5050 -Dspring.profiles.active=prod /home/ubuntu/workspace/lib/mazadService.jar &> /home/ubuntu/workspace/log/mazadService.log &
echo $! > /home/ubuntu/workspace/bin/mazadService.pid
tail -f /home/ubuntu/workspace/log/mazadService.log