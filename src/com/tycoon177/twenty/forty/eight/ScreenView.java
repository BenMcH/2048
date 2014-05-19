package com.tycoon177.twenty.forty.eight;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Arrays;
import java.util.Random;

import com.tycoon177.engine.Game;
import com.tycoon177.engine.Screen;

public class ScreenView extends Screen {
    public static int LEFT = 0, DOWN = 1, RIGHT = 2, UP = 3;

    public ScreenView(Game game) {
	super(game);
	// TODO Auto-generated constructor stub
    }

    public static int[][] sq;
    /**
			 * 
			 */
    private static final long serialVersionUID = -4989947253742922676L;

    @Override
    public void onDraw(Graphics g2) {
	Graphics2D g = (Graphics2D) g2;
	g.fillRect(200, 0, 5, 800);
	g.fillRect(400, 0, 5, 800);
	g.fillRect(600, 0, 5, 800);
	g.fillRect(0, 200, 800, 5);
	g.fillRect(0, 400, 800, 5);
	g.fillRect(0, 600, 800, 5);
	g.setFont(new Font("default", Font.BOLD, 16));
	drawNums(g);

    }

    private void drawNums(Graphics2D g) {
	for (int i = 0; i < 4; i++)
	    for (int j = 0; j < 4; j++)
		if (sq[i][j] > 0) {
		    g.setColor(getColor(sq[i][j]));
		    g.fillRect(7 + i * 200, 5 + j * 200, 190, 190);
		    g.setColor(Color.black);
		    g.drawString(sq[i][j] + "", i * 200 + 100, j * 200 + 100);
		}

    }

    private Color getColor(int i) { //Switch statement found here http://java-articles.info/articles/?p=516 because it was unnecessary to find the colors again
	Color color;
	switch (i) {
	case 2:
	    color = Color.WHITE;
	    break;
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
	sq = new int[4][4];
	this.setFocusable(true);
	sq[new Random(System.currentTimeMillis()).nextInt(4)][new Random(
		System.currentTimeMillis()).nextInt(4)] = 2;
    }

    public static void move(int direction) {
	// 0->left 1->down 2->right 3->up
	int[][] bits = new int[sq.length][sq[0].length];
	int[][] a1 = sq.clone();
	for (int i = 0; i < direction; i++)
	    a1 = rotateDirection(a1);
	for (int i = 0; i < sq.length; i++)
	    for (int j = 0; j < sq[0].length; j++)
		if (a1[i][j] != 0)
		    bits[i][j] = 1;
	boolean moved = false;
	for (int n = 0; n < 4; n++)
	    for (int i = 1; i < 4; i++)
		for (int j = 0; j < 4; j++) {
		    if (a1[i][j] != 0
			    && (a1[i - 1][j] == 0 || a1[i - 1][j] == a1[i][j])) {

			if (a1[i - 1][j] == a1[i][j] && bits[i][j] == 1
				&& bits[i - 1][j] == 1) {
			    a1[i - 1][j] *= 2;
			    a1[i][j] = 0;
			    bits[i - 1][j] = 2;
			    moved = true;
			} else if (a1[i - 1][j] == 0) {
			    a1[i - 1][j] = a1[i][j];
			    bits[i - 1][j] = bits[i][j];
			    bits[i][j] = 0;
			    a1[i][j] = 0;
			    moved = true;
			}
		    }
		}
	if(!moved)return; //Don't waste time on any of this if nothing changed
	Random r = new Random();
	boolean a = false;
	while (!a && moved) {
	    int a2 = r.nextInt(4), b = r.nextInt(4);
	    if (a1[a2][b] == 0) {
		a1[a2][b] = r.nextInt(100) > 75 ? 4 : 2;
		a = true;
	    }
	}
	for (int i = 0; i < 4 - direction; i++)
	    a1 = rotateDirection(a1);
	sq = a1.clone();
    }

    public static int[][] rotateDirection(int[][] grid) {
	int[][] rotated = new int[grid.length][grid[0].length];
	for (int i = 0; i < grid.length; i++)
	    for (int j = 0; j < grid[0].length; j++) {
		rotated[i][j] = grid[j][grid.length - 1 - i];
	    }
	return rotated;
    }

    @Override
    public void onTick(double updateTime) {
    }
}