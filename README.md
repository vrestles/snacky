# Snacky project

### Building and running the project on Windows
#### Building
```
gradlew clean build
```
#### Running
```
gradlew bootRun
```

Before running the app, run this command in terminal:

```
docker run --rm --name postgres-db -e POSTGRES_USER=admin -e POSTGRES_PASSWORD=qwerty -e POSTGRES_DB=snacky -p 5432:5432 -d postgres:alpine
```
