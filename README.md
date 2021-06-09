# Waypoints
Create RPG-style fast travel waypoints for your server in minecraft! To prevent player abuse,
an exp cost penalty is enforced. The further the distance traveled, the higher the exp cost.

## Main Features
- Waypoints can be created using any Sign, with the following conditions:
    - The first line of text should be [Waypoint]
    - The second line of text should be the a unique name to be given to the waypoint
- Right clicking on a created waypoint brings up a menu in which you can select an existing waypoint to fast travel to.
- Waypoints are accessible only in the world it is created in (you can't fast travel to a nether waypoint in the nether, etc.)
- Database connection is required for storing the list of waypoints
- Exp cost penalty and the max distance for free travelling is configurable

## Dev Dependencies
Dependencies can be found in the `pom.xml`.
- [Java 8](https://www.oracle.com/java/technologies/javase/javase-jdk8-downloads.html) - high-level, class-based, object-oriented programming language
- [Maven](https://maven.apache.org/download.cgi) - build automation tool primarily used for Java projects
- [Spigot 1.16.5](https://www.spigotmc.org/wiki/spigot-maven/) - A Minecraft Server API
- [MySQL](https://mvnrepository.com/artifact/mysql/mysql-connector-java) - Database connection driver
- [Project Lombok](https://projectlombok.org/) - Java library tool used to reduce boilerplate code for mode/data objects

## Compilation
- Compilation is handled by Maven using this command:
  > mvn clean-install
- Once successfully compiled, the jar file can be found in the `/target/` folder.


## Contributions
Want to contribute to this project? Please contact 
