# Build
mvn clean package && docker build -t com.example.javaee8_service/javaee8-api .

# RUN

docker rm -f javaee8-api || true && docker run -d -p 8080:8080 -p 4848:4848 --name javaee8-api com.example.javaee8_service/javaee8-api 