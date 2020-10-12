/**
 * 
 */

/**
 * @author Martin Kucharek
 *
 */
public class MazeSpace {
	/**
	 * 
	 */
	private Boolean openSpace;
	private Boolean wallSpace;
	private Boolean endSpace;
	private Boolean startSpace;
	private Boolean alreadyTried;
	private int x;
	private int y;
	
	/**
	 * (0 for blocked, 1 for able to walk through, 2 for start, and 3 for end)
	 */
	public MazeSpace(int spaceType, int y, int x) {
		this.setAlreadyTried(false);
		this.x = x;
		this.y = y;		
		if(spaceType == 0) {
			this.wallSpace = true;
			this.openSpace = false;
			this.endSpace = false;
			this.startSpace = false;
		}else if(spaceType == 1){
			this.wallSpace = false;
			this.openSpace = true;
			this.endSpace = false;
			this.startSpace = false;
		}else if(spaceType == 2){
			this.wallSpace = false;
			this.openSpace = false;
			this.endSpace = false;
			this.startSpace = true;
		}else if(spaceType == 3){
			this.wallSpace = false;
			this.openSpace = false;
			this.endSpace = true;
			this.startSpace = false;
		}else {
			this.wallSpace = false;
			this.openSpace = false;
			this.endSpace = false;
			this.startSpace = false;
		}
	}


	/**
	 * @return the openSpace
	 */
	public Boolean getOpenSpace() {
		return openSpace;
	}


	/**
	 * @param openSpace the openSpace to set
	 */
	public void setOpenSpace(Boolean openSpace) {
		this.openSpace = openSpace;
	}


	/**
	 * @return the wallSpace
	 */
	public Boolean getWallSpace() {
		return wallSpace;
	}


	/**
	 * @param wallSpace the wallSpace to set
	 */
	public void setWallSpace(Boolean wallSpace) {
		this.wallSpace = wallSpace;
	}


	/**
	 * @return the endSpace
	 */
	public Boolean getEndSpace() {
		return endSpace;
	}


	/**
	 * @param endSpace the endSpace to set
	 */
	public void setEndSpace(Boolean endSpace) {
		this.endSpace = endSpace;
	}


	/**
	 * @return the startSpace
	 */
	public Boolean getStartSpace() {
		return startSpace;
	}


	/**
	 * @param startSpace the startSpace to set
	 */
	public void setStartSpace(Boolean startSpace) {
		this.startSpace = startSpace;
	}


	public Boolean getAlreadyTried() {
		return alreadyTried;
	}


	public void setAlreadyTried(Boolean alreadyTried) {
		this.alreadyTried = alreadyTried;
	}


	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}


	/**
	 * @param x the x to set
	 */
	public void setX(int x) {
		this.x = x;
	}


	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}


	/**
	 * @param y the y to set
	 */
	public void setY(int y) {
		this.y = y;
	}
	
	

}
