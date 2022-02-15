import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Stack;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Game {
	/**
	 * Variables
	 */
	private int cols;
	private int rows;
	private static MazeSpace[][] mazeSpaces = new MazeSpace[0][0];
	private static Stack<MazeSpace> trackingStack = new Stack<MazeSpace>(); 
	private int[][] startEndPoint;
	private static int incrX;
	private static int incrY;
	private static int prevX;
	private static int prevY;
	private static int startX;
	private static int startY;
	private static int endX;
	private static int endY;
	
	
	/**
	 * Creates the maze
	 * (0 for blocked, 1 for able to walk through, 2 for start, and 3 for end)
	 * 
	 * Returns the maze 2d int array
	 */		
	public void CreateMaze(String fileName) {
		try {
			Workbook workbook = createXLSFile(fileName);
			Sheet sheet = workbook.getSheet("Sheet1");
			int rowCount = sheet.getLastRowNum()-sheet.getFirstRowNum();
			Row row = sheet.getRow(1);
			int[][] maze = new int[rowCount+1][row.getLastCellNum()];
			cols = row.getLastCellNum();
			rows = rowCount + 1;
			for (int i = 0; i < rowCount+1; i++) {
				row = sheet.getRow(i);
				for (int j = 0; j < row.getLastCellNum(); j++) {
					final DataFormatter df = new DataFormatter();
					final Cell cell = row.getCell(j);
					String valueAsString = df.formatCellValue(cell);
					maze[i][j] = Integer.parseInt(valueAsString);
				}
			} 
		    mazeSpaces = new MazeSpace[maze.length][maze[0].length];
			for (int i = 0; i < maze.length; i++) {
				for (int j = 0; j < maze[0].length; j++) {
					mazeSpaces[i][j] = new MazeSpace(maze[i][j],i,j);
				}
			}   	
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	private Workbook createXLSFile(String fileName) throws FileNotFoundException, IOException {
		String filePath = System.getProperty("user.dir")+"\\src";
		File file =    new File(filePath+"\\"+fileName);
		FileInputStream inputStream = new FileInputStream(file);
		Workbook workbook = null;
		String fileExtensionName = fileName.substring(fileName.indexOf("."));
		if(fileExtensionName.equals(".xlsx")){
			workbook = new XSSFWorkbook(inputStream);
		}else if(fileExtensionName.equals(".xls")){
			workbook = new HSSFWorkbook(inputStream);
		}
		return workbook;
	}
	

	/**
	 * Finds start and end point
	 * 
	 * Returns xy in an array
	 */
	public static int[][] FindStartEndPoint(MazeSpace[][] maze){
		int[][] xy = {
				{0,0},
				{0,0}
				};
		for (int i = 0; i < maze.length; i++) {
			for (int j = 0; j < maze[0].length; j++) {
				if(maze[i][j].getStartSpace() == true) {
					 xy[0][1]= i;
					 xy[0][0]= j;
				}else if(maze[i][j].getEndSpace() == true) {
					 xy[1][1]= i;
					 xy[1][0]= j;
				}
			}
		}                      	
		return xy;
	}

	
	/**
	 * Initialize the maze variables (could of just called new in the reload and create buttons)
	 */
	public void InitializeMaze() {
		startEndPoint = FindStartEndPoint(mazeSpaces);
		incrX = startEndPoint[0][0];
		incrY = startEndPoint[0][1];
		prevX = -1;
		prevY = -1;
		startX = startEndPoint[0][0];
		startY = startEndPoint[0][1];
		endX = startEndPoint[1][0];
		endY = startEndPoint[1][1];
		mazeSpaces[incrY][incrX].setAlreadyTried(true);
	}

	
	/**
	 * Solves the maze
	 * 
	 *  Returns the solved results (0 for not solvable, 1 for still solving, 2 for solved, 3 for back tracking)
	 * 
	 */
	public static int SolveMaze() {
			//Check to see if solved already
			if(incrX == endX && incrY == endY) {
				return 2;
			}
			if(prevX == incrX && prevY == incrY) {
				if(trackingStack.isEmpty() != true) {
					MazeSpace backTrack =  trackingStack.pop();
					incrX = backTrack.getX();
					incrY = backTrack.getY();
					return 3;
				}
			}else {
				prevX = incrX;
				prevY = incrY;
			}
			//Look Up
			if(incrY != 0) {
				if(mazeSpaces[incrY - 1][incrX].getWallSpace() != true && mazeSpaces[incrY - 1][incrX].getAlreadyTried() != true) {
					incrY -= 1;
					mazeSpaces[incrY][incrX].setAlreadyTried(true);
					trackingStack.push(mazeSpaces[incrY][incrX]);
					return 1;
				}
			}
			//Look Down
			if(incrY != (mazeSpaces.length-1)) {
				if(mazeSpaces[incrY + 1][incrX].getWallSpace() != true && mazeSpaces[incrY + 1][incrX].getAlreadyTried() != true) {
					incrY += 1;
					mazeSpaces[incrY][incrX].setAlreadyTried(true);
					trackingStack.push(mazeSpaces[incrY][incrX]);
					return 1;
				}
			}
			//Look Left
			if(incrX != 0) {
				if(mazeSpaces[incrY][incrX - 1].getWallSpace() != true && mazeSpaces[incrY][incrX - 1].getAlreadyTried() != true) {
					incrX -= 1;
					mazeSpaces[incrY][incrX].setAlreadyTried(true);
					trackingStack.push(mazeSpaces[incrY][incrX]);
					return 1;
				}
			}
			//Look Right
			if(incrX != (mazeSpaces[0].length-1)) {
				if(mazeSpaces[incrY][incrX + 1].getWallSpace() != true && mazeSpaces[incrY][incrX + 1].getAlreadyTried() != true) {
					incrX += 1;
					mazeSpaces[incrY][incrX].setAlreadyTried(true);
					trackingStack.push(mazeSpaces[incrY][incrX]);
					return 1;
				}
			}	
			
			//Check to see if nothing left to move
			if(incrX == startX && incrY == startY && trackingStack.isEmpty()) {
				return 0;
			}
			
			if(trackingStack.isEmpty()) {
				return 0;
			}
			return 1;	
	}
	

	/**
	 * @return the prevX
	 */
	public static int getPrevX() {
		return prevX;
	}

	/**
	 * @param prevX the prevX to set
	 */
	public static void setPrevX(int prevX) {
		Game.prevX = prevX;
	}

	/**
	 * @return the prevY
	 */
	public int getPrevY() {
		return prevY;
	}

	/**
	 * @param prevY the prevY to set
	 */
	public void setPrevY(int prevY) {
		Game.prevY = prevY;
	}

	/**
	 * @return the incrX
	 */
	public int getIncrX() {
		return incrX;
	}

	/**
	 * @param incrX the incrX to set
	 */
	public void setIncrX(int incrX) {
		Game.incrX = incrX;
	}

	/**
	 * @return the incrY
	 */
	public int getIncrY() {
		return incrY;
	}

	/**
	 * @param incrY the incrY to set
	 */
	public void setIncrY(int incrY) {
		Game.incrY = incrY;
	}

	/**
	 * @return the cols
	 */
	public int getCols() {
		return cols;
	}

	/**
	 * @param cols the cols to set
	 */
	public void setCols(int cols) {
		cols = cols;
	}

	/**
	 * @return the rows
	 */
	public int getRows() {
		return rows;
	}

	/**
	 * @param rows the rows to set
	 */
	public void setRows(int rows) {
		rows = rows;
	}

	/**
	 * @return the mazeSpaces
	 */
	public static MazeSpace[][] getMazeSpaces() {
		return mazeSpaces;
	}
}
