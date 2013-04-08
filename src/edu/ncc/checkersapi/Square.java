
package edu.ncc.checkersapi;

public class Square
{
   // Is the square playable? Half the squares on the checker board are not playable.
   // --------------------------------------------------------------------------------------------------------------

   private boolean playable = false; // default this to false until the board is initialized

   public boolean isPlayable()
   {
      return playable;
   }

   protected void setPlayable(boolean playable)
   {
      this.playable = playable;
   }

   // Each playable square has a position number. This is used for recording moves. Default value is -1
   // --------------------------------------------------------------------------------------------------------------

   private int position = -1;

   public int getPosition()
   {
      int positionReturn = -1;

      if (playable)
      {
         positionReturn = position;
      }

      return positionReturn;
   }

   protected void setPosition(int position)
   {
      if (playable)
      {
         this.position = position;
      }
   }

   // What is in the square? This is the standard notation for the game pieces.
   // --------------------------------------------------------------------------------------------------------------

   public enum SquareContents
   {
      Empty, LightMan, LightKing, DarkMan, DarkKing
   }

   private SquareContents squareContents = SquareContents.Empty;

   public SquareContents getSquareContents()
   {
      SquareContents squareContentsReturn = SquareContents.Empty;

      if (playable)
      {
         squareContentsReturn = squareContents;
      }

      return squareContentsReturn;
   }

   public void setSquareContents(SquareContents squareContents)
   {
      if (playable)
      {
         this.squareContents = squareContents;
      }
   }

   // Is the square on the edge of the board. This will be used to simplify game logic.
   // --------------------------------------------------------------------------------------------------------------

   public enum SquareEdgeType
   {
      NonEdge, TopEdge, BottomEdge, LeftEdge, RightEdge, TopRightCorner, BottomLeftCorner, NonPlayable
   }

   private SquareEdgeType squareEdgeType = SquareEdgeType.NonPlayable;

   public SquareEdgeType getSquareEdgeType()
   {
      SquareEdgeType squareEdgeTypeReturn = SquareEdgeType.NonPlayable;

      if (playable)
      {
         squareEdgeTypeReturn = squareEdgeType;
      }

      return squareEdgeTypeReturn;
   }

   protected void setSquareEdgeType(SquareEdgeType squareEdgeType)
   {
      if (playable)
      {
         this.squareEdgeType = squareEdgeType;
      }
   }

   //
   // --------------------------------------------------------------------------------------------------------------

   private int[] validMoves = {-1, -1, -1, -1};    // Array that holds position indexes of valid moves. -1 for no valid moves.

   public int[] getValidMoves()
   {
      return validMoves;
   }

   public void setValidMoves(int[] validMoves)
   {
      this.validMoves = validMoves;
   }

   //
   // --------------------------------------------------------------------------------------------------------------

   private Square[] nextSquares = {null, null, null, null}; // Array that holds the actual squares that can be moved into.

   public Square[] getNextSquares()
   {
      return nextSquares;
   }

   public void setNextSquares(Square[] nextSquares)
   {
      this.nextSquares = nextSquares;
   }

   //
   // --------------------------------------------------------------------------------------------------------------

   private int row = 0;

   protected void setRow(int row)
   {
      this.row = row;
   }

   public int getRow()
   {
      return this.row;
   }

   //
   // --------------------------------------------------------------------------------------------------------------

   private int col = 0;

   protected void setCol(int col)
   {
      this.col = col;
   }

   public int getCol()
   {
      return this.col;
   }

   // Square number: Top left corner square is 0, increments sequentially ending with the bottom right square being 63
   // Unplayable squares have numbers too.
   private int number = 0;

   public int getNumber()
   {
      return number;
   }

   protected void setNumber(int number)
   {
      this.number = number;
   }

}
