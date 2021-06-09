# Waypoints
Create RPG-style fast travel waypoints for your server in minecraft! To prevent player abuse,
an exp cost penalty is enforced. The further the distance traveled, the higher the exp cost.

## Main Features
- Waypoints can be created using any Sign, with the following conditions:
    - The first line of text should be `[Waypoint]`
    - The second line of text should be the unique name to be given to the waypoint (ex. Home)
    - Right-click on the newly created waypoint using a compass to activate it.
    - Activated waypoints have their first lines colored green
- Right-clicking on an activated waypoint brings up a menu in which you can select another existing waypoint to fast travel to.
- Waypoints are accessible only in the world it is created in (you can't fast travel to a nether waypoint in the nether, etc.)
- An option to not penalize players who use the waypoint to travel within a short distance is also available
- Exp cost per block and the max distance for free travelling is configurable
- Database connection is required to handle saving and retrieval of created waypoints

## Dev Dependencies
Dependencies can be found in the `pom.xml`.
- [Java 8](https://www.oracle.com/java/technologies/javase/javase-jdk8-downloads.html) - high-level, class-based, object-oriented programming language
- [Maven](https://maven.apache.org/download.cgi) - build automation tool primarily used for Java projects
- [Spigot 1.16.5](https://www.spigotmc.org/wiki/spigot-maven/) - A Minecraft Server API
- [MySQL](https://mvnrepository.com/artifact/mysql/mysql-connector-java) - Database connection driver
- [Lombok](https://projectlombok.org/) - Java library tool used to reduce boilerplate code for mode/data objects

## Compilation
- Compilation is handled by Maven by running the command below:
  > mvn clean install
- Once successfully compiled, the jar file can be found in the `/target/` folder.

## Contributions
Want to contribute to this project? You can contact me at [lunarpatriots@gmail.com](mailto:lunarpatriots@gmail.com).
