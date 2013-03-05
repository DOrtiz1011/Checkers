// http://en.wikipedia.org/wiki/English_draughts

package edu.ncc.checkersapi;

import edu.ncc.checkersapi.Square.*;

public class CheckerBoard
{
   private int numLightMen;		//Number of Light Pieces
   private int numLightKings;	//Number of Light Kings
   private int numDarkMen;		//Number of Dark Pieces
   private int numDarkKings;	//Number of Dark Kings

   //Getters & Setters
   //-----------------------------------------------------------------------------------
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

      numLightMen   = 12;
      numLightKings = 0;
      numDarkMen    = 12;
      numDarkKings  = 0;

      playerTurn = PlayerTurn.DarksTurn;	//Dark Player goes first

      Board = new Square[8][8];   // This initializes the pointer for the entire board

      //Populates the 8x8 board
      for (int row = 0; row < 8; row++)
      {
         for (int col = 0; col < 8; col++)
         {
            Board[row][col] = new Square();   // Each individual square needs to be initialized.

            if ((row + col) % 2 == 0)
            {
               // this is a non-playable square
               Board[row][col].setPlayable(false);
            }
            else
            {
               // This square is playable
            	//The square is marked as playable and assigned a position index (See image here: http://en.wikipedia.org/wiki/File:Draughts_Notation.svg)
               Board[row][col].setPlayable(true);
               Board[row][col].setPosition(positionIndex);

               if (positionIndex < 13)		//Populates the top 3 rows of the board with dark pieces
               {
                  Board[row][col].setSquareContents(SquareContents.DarkMan);
               }
               else if (positionIndex > 20)	//Populates the bottom 3 rows of the board with light pieces
               {
                  Board[row][col].setSquareContents(SquareContents.LightMan);
               }
			   else		//Leaves the middle 2 rows empty
			   {
			      Board[row][col].setSquareContents(SquareContents.Empty);
			   }

               // set the edgetype for the square
               if (positionIndex < 4)
               {
                  Board[row][col].setSquareEdgeType(SquareEdgeType.TopEdge);
               }
               else if (positionIndex > 29)
               {
                  Board[row][col].setSquareEdgeType(SquareEdgeType.BottomEdge);
               }
               else if (positionIndex == 5 || positionIndex == 13 || positionIndex == 21)
               {
                  Board[row][col].setSquareEdgeType(SquareEdgeType.LeftEdge);
               }
               else if (positionIndex == 12 || positionIndex == 20 || positionIndex == 28)
               {
                  Board[row][col].setSquareEdgeType(SquareEdgeType.RightEdge);
               }
               else if (positionIndex == 4 || positionIndex == 29)
               {
                  Board[row][col].setSquareEdgeType(SquareEdgeType.Corner);
               }
               else
               {
                  Board[row][col].setSquareEdgeType(SquareEdgeType.NonEdge);
               }

               positionIndex++;
            }
         }
      }
   }
}
