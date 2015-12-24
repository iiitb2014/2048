package Game2048;

import Game2048.Game.Game;
import Game2048.Tile.Tile;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Game2048 extends JPanel {
    private static final Color BG_COLOR = new Color(0xbbada0);
    private static final String FONT_NAME = "Arial";
    private static final int TILE_SIZE = 64;
    private static final int TILES_MARGIN = 16;
    
    final Game game = new Game();
    public Game2048() {
    
        setFocusable(true);
        addKeyListener(new KeyAdapter() {
        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                game.resetGame();
            }
            if (!game.canMove()) {
            	game.setLose(true);
                //myLose = true;
            }
            if (!game.getWin() && !game.getLose()) {
                switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    game.left();
                    break;
                case KeyEvent.VK_RIGHT:
                    game.right();
                    break;
                case KeyEvent.VK_DOWN:
                    game.down();
                    break;
                case KeyEvent.VK_UP:
                    game.up();
                    break;
                }
            }
            if (!game.getWin() && !game.canMove()) {
                game.setLose(true);
            }
            repaint();
        }
        
        });
        game.resetGame();
    }


    
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(BG_COLOR);
        g.fillRect(0, 0, this.getSize().width, this.getSize().height);
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
            	Tile[] tiles = game.getBoard();
                drawTile(g, tiles[x + y * 4], x, y);
            }
        }
    }

    private void drawTile(Graphics g, Tile tile, int x, int y) {
        int value = tile.value;
        int xOffset = offsetCoors(x);
        int yOffset = offsetCoors(y);
        g.setColor(tile.getBackground());
        g.fillRoundRect(xOffset, yOffset, TILE_SIZE, TILE_SIZE, 14, 14);
        g.setColor(tile.getForeground());
        final int size = value < 100 ? 36 : value < 1000 ? 32 : 24;
        final Font font = new Font(FONT_NAME, Font.BOLD, size);
        g.setFont(font);
        String s = String.valueOf(value);
        final FontMetrics fm = getFontMetrics(font);
        final int w = fm.stringWidth(s);
        final int h = -(int) fm.getLineMetrics(s, g).getBaselineOffsets()[2];
        if (value != 0)
            g.drawString(s, xOffset + (TILE_SIZE - w) / 2, yOffset + TILE_SIZE - (TILE_SIZE - h) / 2 - 2);
        if (game.getWin() || game.getLose()) {
            g.setColor(new Color(255, 255, 255, 30));
            g.fillRect(0, 0, getWidth(), getHeight());
            g.setColor(new Color(78, 139, 202));
            g.setFont(new Font(FONT_NAME, Font.BOLD, 48));
            if (game.getWin()) {
                g.drawString("You won!", 68, 150);
            }
            if (game.getLose()) {
                g.drawString("Game over!", 15, 130);
                g.drawString("You lose!", 44, 180);
            }
            if (game.getWin() || game.getLose()) {
                g.setFont(new Font(FONT_NAME, Font.PLAIN, 16));
                g.setColor(new Color(128, 128, 128, 128)); 
                g.drawString("Press ESC to play again", 80, getHeight() - 60);
                }
        }
        g.setFont(new Font(FONT_NAME, Font.PLAIN, 18));
        g.drawString("Score: " + game.myScore, 200, 365);
    }
    
    
    private int offsetCoors(int arg) {
        return arg * (TILES_MARGIN + TILE_SIZE) + TILES_MARGIN;
    }

    public static void main(String[] args) {
        JFrame game = new JFrame();
        game.setTitle("2048 Game");
        game.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        game.setSize(340, 400);
        game.setResizable(false);
        game.add(new Game2048());
        game.setLocationRelativeTo(null);
        game.setVisible(true);
    }
}
