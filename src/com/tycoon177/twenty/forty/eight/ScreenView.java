package com.tycoon177.twenty.forty.eight;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;

import com.tycoon177.engine.Game;
import com.tycoon177.engine.Screen;

@SuppressWarnings("serial")
public class ScreenView extends Screen {
    public static int LEFT = 0, DOWN = 1, RIGHT = 2, UP = 3;

    public ScreenView(Game game) {
	super(game);
    }

    public static int[][] grid;

    @Override
    public void onDraw(Graphics g2) {
	Graphics2D g = (Graphics2D) g2;
	g.fillRect(200, 0, 5, 800);
	g.fillRect(400, 0, 5, 800);
	g.fillRect(600, 0, 5, 800);
	g.fillRect(0, 200, 800, 5);
	g.fillRect(0, 400, 800, 5);
	g.fillRect(0, 600, 800, 5); //Draw the grid
	g.setFont(new Font("default", Font.BOLD, 16)); //Set a bold font and then draw the numbers
	drawNums(g);

    }

    private void drawNums(Graphics2D g) {
	for (int i = 0; i < 4; i++)
	    for (int j = 0; j < 4; j++)
		if (grid[i][j] > 0) {
		    g.setColor(getColor(grid[i][j]));
		    g.fillRect(7 + i * 200, 5 + j * 200, 190, 190);//Fill the background with a color of choice, relating to the value
		    g.setColor(Color.black);
		    g.drawString(grid[i][j] + "", i * 200 + 100, j * 200 + 100);
		}

    }

    private Color getColor(int i) { //Switch statement found here http://java-articles.info/articles/?p=516 because it was unnecessary to find the colors again
	Color color;
	switch (i) {
	case 2:
	case 4:
	    color = Color.WHITE;
	    break;
	case 8:
	    color = new Color(255, 255, 170);
	    break;
	case 16:
	    color = new Color(255, 255, 128);
	    break;
	case 32:
	    color = new Color(255, 255, 85);
	    break;
	case 64:
	    color = new Color(255, 255, 43);
	    break;
	case 128:
	    color = new Color(255, 255, 0);
	    break;
	case 256:
	    color = new Color(213, 213, 0);
	    break;
	case 512:
	    color = new Color(170, 170, 0);
	    break;
	case 1024:
	    color = new Color(128, 128, 0);
	    break;
	case 2048:
	    color = new Color(85, 85, 0);
	    break;
	default:
	    color = new Color(43, 43, 0);
	    break;
	}
	return color;
    }

    @Override
    public void onCreate() {
	grid = new int[4][4];
	this.setFocusable(true);
	grid[new Random(System.nanoTime()).nextInt(4)][new Random(
		System.nanoTime()).nextInt(4)] = 2; //Place the first integer, a two, onto the board
    }

    public static void move(int direction) {
	int[][] movedTiles = new int[grid.length][grid[0].length];
	int[][] newBoard = grid.clone();
	for (int i = 0; i < direction; i++)
	    newBoard = rotate(newBoard); //Rotate the grid so that the direction in which it is being moved is on the left
	for (int i = 0; i < grid.length; i++)
	    for (int j = 0; j < grid[0].length; j++)
		if (newBoard[i][j] != 0)
		    movedTiles[i][j] = 1;
	boolean moved = false; //A flag to know if any piece has moved. If it has, another number will be added later.
	for (int n = 0; n < 4; n++) //Make sure the tiles can travel the entire board
	    for (int i = 1; i < 4; i++) 
		for (int j = 0; j < 4; j++) {
		    if (newBoard[i][j] != 0
			    && (newBoard[i - 1][j] == 0 || newBoard[i - 1][j] == newBoard[i][j])) { //Make sure the tile is able to move
			if (newBoard[i - 1][j] == newBoard[i][j] && movedTiles[i][j] == 1
				&& movedTiles[i - 1][j] == 1) { //Handles movement and combining of tiles
			    newBoard[i - 1][j] *= 2;
			    newBoard[i][j] = 0;
			    movedTiles[i - 1][j] = 2;
			    moved = true;
			} else if (newBoard[i - 1][j] == 0) { //Handles plain movement of a tile
			    newBoard[i - 1][j] = newBoard[i][j];
			    movedTiles[i - 1][j] = movedTiles[i][j];
			    movedTiles[i][j] = 0;
			    newBoard[i][j] = 0;
			    moved = true;
			}
		    }
		}
	if(!moved)return; //Don't waste time on any of this if nothing changed
	Random r = new Random();
	boolean tilePlaced = false;
	while (!tilePlaced && moved) { 
	    int x = r.nextInt(4), y = r.nextInt(4);
	    if (newBoard[x][y] == 0) {
		newBoard[x][y] = r.nextInt(100) > 75 ? 4 : 2;
		tilePlaced = true;
	    }
	}
	for (int i = 0; i < 4 - direction; i++)
	    newBoard = rotate(newBoard); //Rotate the grid back to its original orientation
	grid = newBoard.clone(); //set the grid to the new, edited version.
    }

    public static int[][] rotate(int[][] grid) { //Rotates an NxM array and returns it in the fasion of an MxN array
	int[][] rotated = new int[grid[0].length][grid.length]; //Create a new array of the same size to store the rotated values
	for (int i = 0; i < grid.length; i++)
	    for (int j = 0; j < grid[0].length; j++) {
		rotated[i][j] = grid[j][grid.length - 1 - i]; 
	    }
	return rotated;
    }

    @Override
    public void onTick(double updateTime) {} //Required because of the abstract implementation in the game engine.
}