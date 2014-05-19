package com.tycoon177.twenty.forty.eight;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import com.tycoon177.engine.Game;
import com.tycoon177.engine.Screen;
public class Board extends Game {
	public Board(String title, int width, int height) {
		super(title, width, height);	
	}
	public static void main(String[]a){
		Board board = new Board("2048", 800, 800);
		
		Screen screen = new ScreenView(board);
		board.getFrame().setFocusable(true);
		board.getFrame().addKeyListener(new KeyListener(){
			@Override
			public void keyTyped(KeyEvent e) {}
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_LEFT)ScreenView.move(ScreenView.LEFT);
				else if(e.getKeyCode() == KeyEvent.VK_UP)ScreenView.move(ScreenView.UP);
				else if(e.getKeyCode() == KeyEvent.VK_RIGHT)ScreenView.move(ScreenView.RIGHT);
				else if(e.getKeyCode() == KeyEvent.VK_DOWN)ScreenView.move(ScreenView.DOWN);
			}
			@Override
			public void keyReleased(KeyEvent e) {}
		});
		board.setScreen(screen);
		board.getFrame().setVisible(true);
	}
}