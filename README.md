# Single Player Battleship
This is a single player battleship-like game made with Java Swing. 

## Rules
### Generation
- 5 ships are generated onto the 10x10 grid at random
- Ships will generate as these sizes: 2, 3, 3, 4, 5
- Ships can generate next to each other, but will not overlap
- Ships generate horizontally and vertically

### Gameplay
- Click a board tile to attempt a hit
- Tile will indicate a hit (red "X") or a miss (yellow "M")
- 5 consecutive misses = 1 strike
- 3 strikes ends the game in a loss
- Sink all 5 ships without 3 strikes to win!


## How to Run
**Option 1: Using IDE**
- Open project in IDE
- Run `Main.java`

**Option 2: Command Line**
```
cd src
javac *.java
java Main
```
