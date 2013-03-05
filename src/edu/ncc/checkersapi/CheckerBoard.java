
package edu.ncc.checkersapi;

import edu.ncc.checkersapi.Square.*;

public class CheckerBoard
{
   private int numLightMen;
   private int numLightKings;
   private int numDarkMen;
   private int numDarkKings;

   public int getNumLightMen()
   {
      return numLightMen;
   }

   public void setNumLightMen(int numLightMen)
   {
      this.numLightMen = numLightMen;
   }

   public int getNumLightKings()
   {
      return numLightKings;
   }

   public void setNumLightKings(int numLightKings)
   {
      this.numLightKings = numLightKings;
   }

   public int getNumDarkMen()
   {
      return numDarkMen;
   }

   public void setNumDarkMen(int numDarkMen)
   {
      this.numDarkMen = numDarkMen;
   }

   public int getNumDarkKings()
   {
      return numDarkKings;
   }

   public void setNumDarkKings(int numDarkKings)
   {
      this.numDarkKings = numDarkKings;
   }

   public PlayerTurn getPlayerTurn()
   {
      return playerTurn;
   }

   public void setPlayerTurn(PlayerTurn playerTurn)
   {
      this.playerTurn = playerTurn;
   }

   public enum PlayerTurn
   {
      LightsTurn, DarksTurn
   }

   public PlayerTurn playerTurn;

   public Square[][] Board;

   // Setup the board for a new game
   // Default constructor returns a board setup for a new game.

   public CheckerBoard()
   {
      int positionIndex = 1;

      numLightMen = 12;
      numLightKings = 0;
      numDarkMen = 12;
      numDarkKings = 0;

      playerTurn = PlayerTurn.DarksTurn;

      Board = new Square[8][8];   // This initializes the pointer for the entire board

      for (int row = 0; row < 8; row++)
      {
         for (int col = 0; col < 8; col++)
         {
            Board[row][col] = new Square();   // Each individual square needs to be intialized.

            if ((row + col) % 2 == 0)
            {
               // this is a non-playable square
               Board[row][col].setPlayable(false);
            }
            else
            {
               Board[row][col].setPlayable(true);
               Board[row][col].setPosition(positionIndex);

               if (positionIndex < 13)
               {
                  Board[row][col].setSquareContents(SquareContents.DarkMan);
               }
               else if (positionIndex > 20)
               {
                  Board[row][col].setSquareContents(SquareContents.LightMan);
               }
			   else
			   {
			      Board[row][col].setSquareContents(SquareContents.Empty);
			   }

               positionIndex++;
            }
         }
      }
   }
}
