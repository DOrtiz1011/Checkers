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

               positionIndex++;
            }
         }
      }

      for (int row = 0; row < 8; row++){
         for (int col = 0; col < 8; col++){
            findValidMoves(Squares[row][col]);
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

   //--------------------------------------------------------------------------------------------------------------
   //Finds the valid moves for a given square. Doesn't take jumps into consideration yet.
   private void findValidMoves(Square square){
      int[] tempMoves = {-1,-1,-1,-1};    //Array that holds the position index of up to 4 valid moves. -1 for no move
      Square[] nextSquares = {null, null, null, null};
      int direction = 0;      //Direction the pieces are moving. 1 for going down, -1 for going up.
      int offset = 0;         //Number of position indexes away the valid move is. Will either be 3 or 5, depending on the row.
      boolean isKing = false; //Is the piece in this square a king?

      if (square.isPlayable()){
         switch (square.getSquareContents()){

            case LightMan: //Light pieces will travel up the board
               direction = -1;
               break;
            case DarkMan:  //Dark pieces will travel down
               direction = 1;
               break;
            case DarkKing: //Direction doesn't really matter for Kings
               isKing = true;
               break;
            case LightKing:
               isKing = true;
               break;
         }
         if (square.getSquareContents() != SquareContents.Empty){
            switch (square.getSquareEdgeType()){

               case NonEdge:
                  //Calculates the row number. If the square is in an even numbered row the offset will be 5
                  if (square.getPosition()/4 % 2 == 0) offset = 5;
                  else offset = 3;  //If the square is in an odd numbered row the offset will be 3

                  //Multiply by direction (1 or -1) to determine whether to add or subtract the offsets
                  tempMoves[0] = square.getPosition() + (4 * direction);   //One of the offsets will always be 4 regardless of row number
                  tempMoves[1] = square.getPosition() + (offset * direction);

                  if (isKing){   //If the piece is a King, also find moves in the opposite direction
                     direction *= -1;

                     if (offset == 3) offset = 5;  //When going the other direction, the offset needs to be flipped between 5 and 3
                     else offset = 3;

                     tempMoves[2] = square.getPosition() + (4 * direction);
                     tempMoves[3] = square.getPosition() + (offset * direction);
                  }
                  break;

                  //If the square is on an edge, there is a maximum of 2 valid moves.
               case LeftEdge: case RightEdge:
                  tempMoves[0] = square.getPosition() + (4 * direction);
                  if (isKing) tempMoves[1] = square.getPosition() + (4 * direction * -1);
                  break;

                  //Squares in the corners only have one possible move.
               case BottomLeftCorner:
                  tempMoves[0] = 25;
                  break;

               case TopRightCorner:
                  tempMoves[0] = 8;
                  break;

                  //Squares on the top or bottom edges only have 2 possible moves.
               case TopEdge:
                  tempMoves[0] = square.getPosition() + 4;
                  tempMoves[1] = square.getPosition() + 5;
                  break;
               case BottomEdge:
                  tempMoves[0] = square.getPosition() - 4;
                  tempMoves[1] = square.getPosition() - 5;
                  break;
            }

            for (int i = 0; i < 4; i++){
               if (tempMoves[i] != -1){
                  int tempRow = (tempMoves[i]-1)/4;   //Finds the row of the next possible square
                  int tempCol = -1;
                  
                  //Finds the column of the next possible square
                  if (tempRow % 2 == 0) tempCol = (tempMoves[i] - (tempRow * 4)) * 2 - 1;
                  else tempCol = ((tempMoves[i] - (tempRow * 4)) - 1) * 2;
                  
                  //Adds that square to an array of possible squares
                  nextSquares[i] = this.Squares[tempRow][tempCol];
               }
            }
         }
      }
      square.setValidMoves(tempMoves);    //Saves the array of valid moves in the square object.
      square.setNextSquares(nextSquares); //Saves the array of squares in the square object.
   }
}
