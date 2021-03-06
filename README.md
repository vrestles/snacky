# Snacky project

#### Building and running the project
Building (Windows)
```
gradlew clean build
```
Running (Windows)
```
gradlew bootRun
```
Building (MacOs)
```
./gradlew clean build
```
Running (MacOs)
```
./gradlew bootRun
```
Environment variables to run the app locally:
```text
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/snacky;
SPRING_DATASOURCE_USERNAME=admin;
SPRING_DATASOURCE_PASSWORD=qwerty
```

#### Before running the app, run this command in terminal:

```
docker run --name snacky-pg-13.3 -p 5432:5432 -e POSTGRES_USER=admin -e POSTGRES_PASSWORD=qwerty -e POSTGRES_DB=snacky -d postgres:13-alpine
```

#### Building Docker Image (MacOs)
```
docker build -t snacky-app -f Dockerfile .
```

#### Running the app and PostgreSQL in Docker (MacOs)
```
docker-compose up -d
```

### Creating the secret for Docker
```
docker swarm init
printf "password" | docker secret create db_password -
```
Output we need to save inside the root folder in file called [db_password.txt](./db_password.txt)
And run the whole project with docker-compose command

### See dependency tree
Windows
```text
gradlew dependencies >> dep-tree
```