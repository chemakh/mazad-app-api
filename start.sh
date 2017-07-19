rm -rf /home/ubuntu/workspace/bin
rm -rf /home/ubuntu/workspace/log
rm -rf /home/ubuntu/workspace/lib
mkdir /home/ubuntu/workspace/bin
mkdir /home/ubuntu/workspace/log
mkdir /home/ubuntu/workspace/lib
chmod +x start.sh
chmod +x stop-debug.sh
chmod +x debug.sh
touch /home/ubuntu/workspace/log/mazad-service.log
mvn clean install -Dmaven.test.skip=true
cp mazad_config/target/mazadConfig.jar /home/ubuntu/workspace/lib/
cp mazad_service/target/mazadService.jar /home/ubuntu/workspace/lib/
cp mazad_oauth/target/mazadAuth.jar /home/ubuntu/workspace/lib/
sudo systemctl restart mazadConfig.service
sudo systemctl restart mazadService.service
sudo systemctl restart mazadAuth.service

#{   "address" : {     "country" : "Saudia",     "streetAddress1" : "Rue di",     "location" : {       "lat" : 0,       "lon" : 0     },     "city" : "City",     "postalCode" : "566"   },   "password" : "test",   "mobileNumber" : "7568568",   "firstname" : "Mejri",   "lastname" : "Hedi",   "sex" : "M",
# "email" : "lchemakh@starit.fr" }