// http://en.wikipedia.org/wiki/English_draughts

package edu.ncc.checkersapi;

import edu.ncc.checkersapi.Square.*;

public class CheckerBoard
{
   private int numLightMen;   // Number of Light Pieces
   
   public int getNumLightMen()
   {
      return numLightMen;
   }

   public void setNumLightMen(int numLightMen)
   {
      this.numLightMen = numLightMen;
   }
   
   //--------------------------------------------------------------------------------------------------------------

   private int numLightKings;   // Number of Light Kings
   
   public int getNumLightKings()
   {
      return numLightKings;
   }

   public void setNumLightKings(int numLightKings)
   {
      this.numLightKings = numLightKings;
   }
   
   //--------------------------------------------------------------------------------------------------------------
   
   private int numDarkMen;    // Number of Dark Pieces
      
   public int getNumDarkMen()
   {
      return numDarkMen;
   }

   public void setNumDarkMen(int numDarkMen)
   {
      this.numDarkMen = numDarkMen;
   }

   //--------------------------------------------------------------------------------------------------------------
   
   private int numDarkKings;   // Number of Dark Kings
   
   public int getNumDarkKings()
   {
      return numDarkKings;
   }

   public void setNumDarkKings(int numDarkKings)
   {
      this.numDarkKings = numDarkKings;
   }

   //--------------------------------------------------------------------------------------------------------------

   public enum PlayerTurn
   {
      LightsTurn, DarksTurn
   }

   private PlayerTurn playerTurn;
   
   public PlayerTurn getPlayerTurn()
   {
      return playerTurn;
   }

   public void switchPlayerTurn()
   {
      if (playerTurn == PlayerTurn.LightsTurn)
      {
         playerTurn = PlayerTurn.DarksTurn;
      }
      else if (playerTurn == PlayerTurn.DarksTurn)
      {
         playerTurn = PlayerTurn.LightsTurn;
      }
   }

   public Square[][] Squares;

   // Setup the board for a new game
   // Default constructor returns a board setup for a new game.
   //--------------------------------------------------------------------------------------------------------------
   
   public CheckerBoard()
   {
      int positionIndex = 1;

      numLightMen   = 12;
      numLightKings = 0;
      numDarkMen    = 12;
      numDarkKings  = 0;
      playerTurn    = PlayerTurn.DarksTurn;   // Dark Player goes first
      Squares       = new Square[8][8];       // This initializes the pointer for the entire board

      // Populates the 8x8 board
      for (int row = 0; row < 8; row++)
      {
         for (int col = 0; col < 8; col++)
         {
            Squares[row][col] = new Square();   // Each individual square needs to be initialized.

            if ((row + col) % 2 != 0)
            {
               // The square is marked as playable and assigned a position index (See image here: http://en.wikipedia.org/wiki/File:Draughts_Notation.svg)
               Squares[row][col].setPlayable(true);
               Squares[row][col].setPosition(positionIndex);
               
               setSquareContents(Squares[row][col], positionIndex);
               setSquareEdgeType(Squares[row][col], positionIndex);
               
               findValidMoves(Squares[row][col]);

               positionIndex++;
            }
         }
      }
   }

   //--------------------------------------------------------------------------------------------------------------
   
   private void setSquareContents(Square square, int positionIndex)
   {
      if (positionIndex < 13)        // Populates the top 3 rows of the board with dark pieces
      {
         square.setSquareContents(SquareContents.DarkMan);
      }
      else if (positionIndex > 20)   // Populates the bottom 3 rows of the board with light pieces
      {
         square.setSquareContents(SquareContents.LightMan);
      }
      else                           // Leaves the middle 2 rows empty
      {
         square.setSquareContents(SquareContents.Empty);
      }
   }
   
   //--------------------------------------------------------------------------------------------------------------
   
   private void setSquareEdgeType(Square square, int positionIndex)
   {
      // set the SquareEdgeType for the square
      if (positionIndex < 4)
      {
         square.setSquareEdgeType(SquareEdgeType.TopEdge);
      }
      else if (positionIndex > 29)
      {
         square.setSquareEdgeType(SquareEdgeType.BottomEdge);
      }
      else if (positionIndex == 5 || positionIndex == 13 || positionIndex == 21)
      {
         square.setSquareEdgeType(SquareEdgeType.LeftEdge);
      }
      else if (positionIndex == 12 || positionIndex == 20 || positionIndex == 28)
      {
         square.setSquareEdgeType(SquareEdgeType.RightEdge);
      }
      else if (positionIndex == 4)
      {
         square.setSquareEdgeType(SquareEdgeType.TopRightCorner);
      }
      else if (positionIndex == 29)
      {
         square.setSquareEdgeType(SquareEdgeType.BottomLeftCorner);
      }
      else
      {
         square.setSquareEdgeType(SquareEdgeType.NonEdge);
      }
   }
   
   private void findValidMoves(Square square){
      int[] tempMoves = {-1,-1,-1,-1};
      int direction = 0;
      int offset = 0;
      boolean isKing = false;
      if (square.isPlayable()){
         switch (square.getSquareContents()){
            case LightMan:
               direction = -1;
               break;
            case DarkMan:
               direction = 1;
               break;
            case DarkKing:
               direction = 1;
               isKing = true;
               break;
            case LightKing:
               direction = -1;
               isKing = true;
               break;
         }
         
         switch (square.getSquareEdgeType()){
            case NonEdge:
               if (square.getPosition()/4 % 2 == 0) offset = 5;
               else offset = 3;
               tempMoves[0] = square.getPosition() + (4 * direction);
               tempMoves[1] = square.getPosition() + (offset * direction);
               if (isKing){
                  direction *= -1;
                  if (offset == 3) offset = 5;
                  else offset = 3;
                  tempMoves[2] = square.getPosition() + (4 * direction);
                  tempMoves[3] = square.getPosition() + (offset * direction);
               }
               break;
            case LeftEdge: case RightEdge:
               tempMoves[0] = square.getPosition() + (4 * direction);
               if (isKing) tempMoves[1] = square.getPosition() + (4 * direction * -1);
               break;
            case BottomLeftCorner:
               tempMoves[0] = 25;
               break;
            case TopRightCorner:
               tempMoves[0] = 8;
               break;
            case TopEdge:
               tempMoves[0] = square.getPosition() + 4;
               tempMoves[1] = square.getPosition() + 5;
               break;
            case BottomEdge:
               tempMoves[0] = square.getPosition() - 4;
               tempMoves[1] = square.getPosition() - 5;
               break;
         }
      }
      square.setValidMoves(tempMoves);
   }
}
