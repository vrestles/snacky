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