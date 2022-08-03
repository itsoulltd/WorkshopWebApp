# App running configuration

## Building and run
    #Before run 
    Maven (panel) > RiderWebApp > Lifecycle > install
    #
    Build the project
    ~>$ mvn clean package -DskipTests -Pproduction
    Run RiderWebService api as SpringBoot-Server
    ~>$ cd RiderWebService && mvn clean spring-boot:run
    Run on Docker
    ~>$ open -a Docker
    ~>$ docker compose -f docker-compose.yaml up -d --build
    Stop Docker
    ~>$ docker compose -f docker-compose.yaml down
    
### Access RiderWebApp on 
    http://localhost
### Access RiderWebService api on 
    http://localhost:8080/swagger-ui.html


