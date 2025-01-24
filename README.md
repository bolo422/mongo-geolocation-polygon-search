# mongo-geolocation-polygon-search
This is a POC project to study mongo geolocation. The objetive is to allow the user to store polygons based on coordinates and search for specific coordinates (points) to check if they belong to any polygon.

### Configurations
To setup this project you only need a mongo database connection string. Create a .env file at the root of the repository, same place as env.txt and put your mongo connection string with the key MONGO_URI, the value should specify a collection where the polygons are going to be stored

- Java 21
- Mongo Database
- HTML, CSS, JS
