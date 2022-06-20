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
docker run --name snacky-pg-13.3 -p 5432:5432 -e POSTGRES_USER=admin -e POSTGRES_PASSWORD=qwerty -e POSTGRES_DB=snacky -d postgres:13.3
```
