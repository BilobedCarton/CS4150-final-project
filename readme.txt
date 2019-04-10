****** README ******
JAR is in out/artifacts/CS4150FinalProject_jar/CS4150FinalProject.jar

Usage guide:
wasd - standard movement
r - resets game and generates new map
z - debug mode - displays the target locations of mobs (where they would like to move towards if they move) and prints the steps in the tree

Currently we have some very simple trees working without combat. The melee enemies move to engage the player and the ranged enemies move so that they can see the player.
Gameplay is somewhat similar to Crypt of the NecroDancer in that every move the player makes means the enemies make a move too (but not in real time).
They currently ignore the floor effects - but this should be a small update to their pathing strategies.
We have the structure for adding more types of enemies and strategies.
We are working on create squad/group based tactics for the enemies. i.e. certain types will serve a different role in combat
We are also working on implementing the actions the player has as well.

Using IntelliJ as a Java Development Client - so we can implement classes and models in Java and render using processing.
Here is a guide to using Processing in Eclipse (or IntelliJ, or any JAVA IDE): https://happycoding.io/tutorials/java/processing-in-java

Upload content through git hub. 
When committing, put name at the beginning of the commit message. Diverse file structure is nice!.
Check issues for stuff that needs to be done.
