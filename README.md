# Waypoints
Create RPG-style fast travel waypoints for your server in minecraft! To prevent player abuse,
an exp cost penalty is enforced. The further the distance traveled, the higher the exp cost.

## Features
- Waypoints can be created using any Sign, with the following conditions:
    - The first line of text should be [Waypoint]
    - The second line of text should be the a unique name to be given to the waypoint
- Right clicking on a created waypoint using a Diamond brings up a menu in which you can select an existing waypoint to fast travel to.
- Exp cost per block is configurable
- A max distance value can also be set wherein fast travelling within that distance will not cost exp
- A database connection is required to store the list of created waypoints
- Waypoints are accessible only in the world it is created in (you can't fast travel to a nether waypoint in the nether, etc.)
