/**
 * Imports
 */
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JSplitPane;
import javax.swing.JButton;


public class UIMain {
	/**
	 * Variables
	 */
	static JFrame frame;
	private static JPanel panelGame;
	private static Game mainGame;
	private static JSplitPane splitPane;
	private static JButton[][] buttons;
	
	
	/**
	 * Create the application.
	 */
	public UIMain() {
		initialize();
	}

	
	/**
	 * Initialize the contents of the frame.
	 */
	private static void initialize() {
		//Frame setup
		frame = new JFrame();
		frame.setBounds(150, 150, 600, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new GridLayout(1, 0, 0, 0));
		
		//Split pane
		splitPane = new JSplitPane();
		splitPane.setResizeWeight(0.2);
		frame.getContentPane().add(splitPane);
		
		//Game panel
		panelGame = new JPanel();
		panelGame.setPreferredSize(new Dimension(400, 400));
		splitPane.setRightComponent(panelGame);
		panelGame.setLayout(new GridLayout(1, 0, 0, 0));
		
		//Button Controls Panel
		JPanel panelControls = new JPanel();
		splitPane.setLeftComponent(panelControls);
		panelControls.setLayout(new GridLayout(3, 0, 0, 0));
		
		//Solve the game button
		JButton btnSolve = new JButton("Solve");
		btnSolve.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int results = 0;
				UpdateGameBoard(mainGame.getRows(),mainGame.getCols());
				do {
					results = mainGame.SolveMaze();
					if(results == 2) {
						results = 2;
					}
					MoveGame(mainGame.getPrevX(),mainGame.getPrevY(),mainGame.getIncrX(),mainGame.getIncrY(),results);
				} while (results == 1 || results == 3);
			}
		});
		panelControls.add(btnSolve);
		
		//Create Game Button
		JButton btnCreateGame = new JButton("Create New Game");
		btnCreateGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mainGame.CreateMaze("MazeTemplate.xlsx");
				mainGame.InitializeMaze();
				UpdateGameBoard(mainGame.getRows(),mainGame.getCols());
			}
		});
		panelControls.add(btnCreateGame);	
		
		//Reload the game button
		JButton btnReloadGame = new JButton("Reload Game");
		btnReloadGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {			
				mainGame.CreateMaze("MazeTemplate.xlsx");
				mainGame.InitializeMaze();
				UpdateGameBoard(mainGame.getRows(),mainGame.getCols());
			}
		});
		panelControls.add(btnReloadGame);
		frame.setVisible(true);
	}
	
	
	/**
	 * Changes the colors of game pieces around the board
	 */
	public static void MoveGame(int oldLocationX, int oldLocationY, int newLocationX, int newLocationY, int results) {
		MazeSpace[][] maze = mainGame.getMazeSpaces();
		if(results == 1) {
        	buttons[oldLocationY][oldLocationX].setBackground(Color.green);
		}else if(results == 2) {
			buttons[newLocationY][newLocationX].setBackground(Color.blue);
		}else if(results == 3) {
			buttons[oldLocationY][oldLocationX].setBackground(Color.orange);
		}else {
			buttons[newLocationY][newLocationX].setBackground(Color.white);
		}
		panelGame.invalidate();
	}
	
	
	/**
	 * Initialize the contents of the frame.
	 */
	public static void UpdateGameBoard(int rows, int cols) {
		panelGame = new JPanel();
		panelGame.setPreferredSize(new Dimension(400, 400));
		splitPane.setRightComponent(panelGame);
		panelGame.setLayout(new GridLayout(1, 0, 0, 0));
		panelGame.setLayout(new GridLayout(rows, cols, 0, 0));
		MazeSpace[][] maze = mainGame.getMazeSpaces();
		buttons = new JButton[rows][cols];
		for (int i = 0; i < cols; i++) {
			for (int j = 0; j < rows; j++) {
				buttons[i][j] = new JButton();
				if(maze[i][j].getWallSpace()) {
					buttons[i][j].setBackground(Color.red);
				}else if(maze[i][j].getOpenSpace()) {
					buttons[i][j].setBackground(Color.white);
				}else if(maze[i][j].getStartSpace()) {
					buttons[i][j].setBackground(Color.green);
				}else if(maze[i][j].getEndSpace()) {
					buttons[i][j].setBackground(Color.blue);
				}else {
					buttons[i][j].setBackground(Color.black);
				}
				panelGame.add(buttons[i][j]).setLocation(i, j);
			}
		}   
		panelGame.revalidate();	
	}
	
	
	/**
	 * Main 
	 */
	public static void main(String[] args) {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						initialize();
						mainGame = new Game();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
	}
	/**
	 * @param args
	 */

}
