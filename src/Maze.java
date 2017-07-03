import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Random;

public class Maze
{
  private int dimensionX;
  private int dimensionY;
  int gridDimensionX;
  int gridDimensionY;
  boolean[][] grid;
  private Maze.Cell[][] cells;
  private Random random = new Random();
  
  public Maze(int aDimension)
  {
    this(aDimension, aDimension);
    draw();
  }
  
  public Maze(int xDimension, int yDimension)
  {
    this.dimensionX = xDimension;
    this.dimensionY = yDimension;
    this.gridDimensionX = (xDimension * 4 + 1);
    this.gridDimensionY = (yDimension * 2 + 1);
    this.grid = new boolean[this.gridDimensionX][this.gridDimensionY];
    init();
    generateMaze();
  }
  
  private void init()
  {
    this.cells = new Maze.Cell[this.dimensionX][this.dimensionY];
    for (int x = 0; x < this.dimensionX; x++) {
      for (int y = 0; y < this.dimensionY; y++) {
        this.cells[x][y] = new Maze.Cell(x, y, false);
      }
    }
  }
  
  private class Cell
  {
    int x;
    int y;
    ArrayList<Cell> neighbors = new ArrayList();
    boolean inPath = false;
    boolean wall = true;
    boolean open = true;
    
    Cell(Maze maze, int x, int y)
    {
      this(x, y, true);
    }
    
    Cell(int x, int y, boolean isWall)
    {
      this.x = x;
      this.y = y;
      this.wall = isWall;
    }
    
    void addNeighbor(Cell other)
    {
      if (!this.neighbors.contains(other)) {
        this.neighbors.add(other);
      }
      if (!other.neighbors.contains(this)) {
        other.neighbors.add(this);
      }
    }
    
    boolean isCellBelowNeighbor()
    {
      return this.neighbors.contains(new Cell(Maze.this, this.x, this.y + 1));
    }
    
    boolean isCellRightNeighbor()
    {
      return this.neighbors.contains(new Cell(Maze.this, this.x + 1, this.y));
    }
    
    public String toString()
    {
      return String.format("Cell(%s, %s)", new Object[] { Integer.valueOf(this.x), Integer.valueOf(this.y) });
    }
    
    public boolean equals(Object other)
    {
      if (!(other instanceof Cell)) {
        return false;
      }
      Cell otherCell = (Cell)other;
      return (this.x == otherCell.x) && (this.y == otherCell.y);
    }
    
    public int hashCode()
    {
      return this.x + this.y * 256;
    }
  }
  
  private void generateMaze()
  {
    generateMaze(0, 0);
  }
  
  private void generateMaze(int x, int y)
  {
    generateMaze(getCell(x, y));
  }
  
  private void generateMaze(Maze.Cell startAt)
  {
    if (startAt == null) {
      return;
    }
    startAt.open = false;
    ArrayList<Maze.Cell> cells = new ArrayList();
    cells.add(startAt);
    while (!cells.isEmpty())
    {
      Maze.Cell cell;
      if (this.random.nextInt(10) == 0) {
        cell = (Maze.Cell)cells.remove(this.random.nextInt(cells.size()));
      } else {
        cell = (Maze.Cell)cells.remove(cells.size() - 1);
      }
      ArrayList<Maze.Cell> neighbors = new ArrayList();
      
      Maze.Cell[] potentialNeighbors = {
        getCell(cell.x + 1, cell.y), 
        getCell(cell.x, cell.y + 1), 
        getCell(cell.x - 1, cell.y), 
        getCell(cell.x, cell.y - 1) };
      Maze.Cell[] arrayOfCell1;
      int j = (arrayOfCell1 = potentialNeighbors).length;
      for (int i = 0; i < j; i++)
      {
        Maze.Cell other = arrayOfCell1[i];
        if ((other != null) && (!other.wall) && (other.open)) {
          neighbors.add(other);
        }
      }
      if (!neighbors.isEmpty())
      {
        Maze.Cell selected = (Maze.Cell)neighbors.get(this.random.nextInt(neighbors.size()));
        
        selected.open = false;
        cell.addNeighbor(selected);
        cells.add(cell);
        cells.add(selected);
      }
    }
  }
  
  public Maze.Cell getCell(int x, int y)
  {
    try
    {
      return this.cells[x][y];
    }
    catch (ArrayIndexOutOfBoundsException e) {}
    return null;
  }
  
  public void updateGrid()
  {
    boolean wallChar = true;boolean cellChar = false;
    for (int x = 0; x < this.gridDimensionX; x++) {
      for (int y = 0; y < this.gridDimensionY; y++) {
        this.grid[x][y] = cellChar;
      }
    }
    for (int x = 0; x < this.gridDimensionX; x++) {
      for (int y = 0; y < this.gridDimensionY; y++) {
        if ((x % 4 == 0) || (y % 2 == 0)) {
          this.grid[x][y] = wallChar;
        }
      }
    }
    for (int x = 0; x < this.dimensionX; x++) {
      for (int y = 0; y < this.dimensionY; y++)
      {
        Maze.Cell current = getCell(x, y);
        int gridX = x * 4 + 2;
        int gridY = y * 2 + 1;
        if (current.inPath)
        {
          if ((current.isCellBelowNeighbor()) && 
            (!getCell(x, y + 1).inPath)) {
            this.grid[gridX][(gridY + 1)] = cellChar;
          }
          if ((current.isCellRightNeighbor()) && 
            (!getCell(x + 1, y).inPath))
          {
            this.grid[(gridX + 2)][gridY] = cellChar;
            this.grid[(gridX + 1)][gridY] = cellChar;
            this.grid[(gridX + 3)][gridY] = cellChar;
          }
        }
        else
        {
          this.grid[gridX][gridY] = cellChar;
          if (current.isCellBelowNeighbor()) {
            this.grid[gridX][(gridY + 1)] = cellChar;
          }
          if (current.isCellRightNeighbor())
          {
            this.grid[(gridX + 2)][gridY] = cellChar;
            this.grid[(gridX + 1)][gridY] = cellChar;
            this.grid[(gridX + 3)][gridY] = cellChar;
          }
        }
      }
    }
  }
  
  public void draw()
  {
    System.out.print(this);
  }
  
  public String toString()
  {
    updateGrid();
	return "";
  }
}