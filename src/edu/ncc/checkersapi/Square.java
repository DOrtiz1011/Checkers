// http://en.wikipedia.org/wiki/English_draughts

package edu.ncc.checkersapi;

public class Square
{
   // Is the square playable? Half the squares on the checker board are not
   // playable.

   private boolean playable = false; // default this to false until the board is
                                     // initialized

   public boolean isPlayable()
   {
      return playable;
   }

   protected void setPlayable(boolean playable)
   {
      this.playable = playable;
   }

   // Each playable square has a position number. This is used for recording
   // moves.

   private int Position;

   public int getPosition()
   {
      int positionReturn = 0;

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
}
