# Waypoints
![GitHub workflow status](https://img.shields.io/github/workflow/status/lunarpatriots/Waypoints/Build/develop?logo=github)
[![GitHub last commit](https://img.shields.io/github/last-commit/lunarpatriots/Waypoints.svg?logo=github)](https://github.com/lunarpatriots/Waypoints/commits/develop)
[![GitHub code size in bytes](https://img.shields.io/github/languages/code-size/lunarpatriots/Waypoints.svg?logo=github)](https://github.com/lunarpatriots/Waypoints)
[![GitHub contributors](https://img.shields.io/github/contributors/lunarpatriots/Waypoints.svg?logo=github)](https://github.com/lunarpatriots/Waypoints/graphs/contributors)
[![License](https://img.shields.io/github/license/lunarpatriots/Waypoints.svg?logo=github)](https://github.com/lunarpatriots/Waypoints/blob/develop/LICENSE)

Create RPG-style fast travel waypoints for your server in minecraft! To prevent player abuse,
an exp cost penalty is enforced. The further the distance traveled, the higher the exp cost.

## Main Features
- Waypoints can be created using any Sign, with the following conditions:
    - The first line of text should be `[Waypoint]`
    - The second line of text should be the unique name to be given to the waypoint (ex. Home)
    - Left-click on the newly created waypoint while holding a compass to activate it
- Right-clicking on an activated waypoint brings up a menu in which you can select another existing waypoint to fast travel to.
- Waypoints are accessible only in the world it is created in (you can't fast travel to a nether waypoint in the nether, etc.)
- An option to not penalize players who use the waypoint to travel within a short distance is also available
- Exp cost per block, and the max distance for free travelling is configurable
- Uses database to store waypoint data

## Project Dependencies
Dependencies can be found in the `pom.xml`.
- [Java 8](https://www.oracle.com/java/technologies/javase/javase-jdk8-downloads.html) - A high-level, class-based, object-oriented programming language
- [Maven](https://maven.apache.org/download.cgi) - Build automation tool primarily used for Java projects
- [Spigot 1.16.5](https://www.spigotmc.org/wiki/spigot-maven/) - A Minecraft Server API
- [Lombok](https://projectlombok.org/) - Java library tool used to reduce boilerplate code for model/data objects
- [SQLite](https://www.sqlite.org/about.html) - Library that implements a self-contained, zero-configuration, transactional SQL database engine

## Compilation
- Compilation is handled by Maven by running the command `mvn clean install`
- Once successfully compiled, the jar file can be found in the `/target/` folder.

