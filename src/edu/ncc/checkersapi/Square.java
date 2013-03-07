package edu.ncc.checkersapi;

public class Square
{
   // Is the square playable? Half the squares on the checker board are not playable.
   //--------------------------------------------------------------------------------------------------------------

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
   //--------------------------------------------------------------------------------------------------------------

   private int Position = -1;

   public int getPosition()
   {
      int positionReturn = -1;

      if (playable)
      {
         positionReturn = Position;
      }

      return positionReturn;
   }

   protected void setPosition(int position)
   {
      if (playable)
      {
         Position = position;
      }
   }

   // What is in the square? This is the standard notation for the game pieces.
   //--------------------------------------------------------------------------------------------------------------

   public enum SquareContents
   {
      Empty, LightMan, LightKing, DarkMan, DarkKing
   }

   private SquareContents squareContents;

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
   //--------------------------------------------------------------------------------------------------------------
   
   public enum SquareEdgeType
   {
      NonEdge, TopEdge, BottomEdge, LeftEdge, RightEdge, TopRightCorner, BottomLeftCorner
   }
   
   private SquareEdgeType squareEdgeType;

   public SquareEdgeType getSquareEdgeType()
   {
      SquareEdgeType squareEdgeTypeReturn = SquareEdgeType.NonEdge;

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
}
