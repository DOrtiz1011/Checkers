// http://en.wikipedia.org/wiki/English_draughts

package edu.ncc.checkersapi;

import edu.ncc.checkersapi.Square.*;
//import edu.ncc.checkersapi.MoveLog.*;
//import edu.ncc.checkersapi.Stats.*;

public class CheckerBoard
{
   private int numLightMen      = 0; // Number of Light Pieces
   private int numLightKings    = 0; // Number of Light Kings
   private int numLightCaptured = 0; // Number of Captured Light Pieces
   private int numDarkMen       = 0; // Number of Dark Pieces
   private int numDarkKings     = 0; // Number of Dark Kings
   private int numDarkCaptured  = 0; // Number of Captured Dark Pieces

   // --------------------------------------------------------------------------------------------------------------

   private void sanityCheck()
   {
      // needs to throw an exception

      if ((numLightMen + numLightKings + numLightCaptured) != 12)
      {
         System.out.println("SANITY CHECK FAILED FOR LIGHT PIECES!!");
      }

      if ((numDarkMen + numDarkKings + numDarkCaptured) != 12)
      {
         System.out.println("SANITY CHECK FAILED FOR DARK PIECES!!");
      }
   }

   // --------------------------------------------------------------------------------------------------------------

   public int getNumLightCaptured()
   {
      return numLightCaptured;
   }

   // --------------------------------------------------------------------------------------------------------------

   public void incrementNumLightCaptured()
   {
      numLightCaptured++;
      sanityCheck();
   }

   // --------------------------------------------------------------------------------------------------------------

   public int getNumDarkCaptured()
   {
      return numDarkCaptured;
   }

   // --------------------------------------------------------------------------------------------------------------

   public void incrementNumDarkCaptured()
   {
      numDarkCaptured++;
      sanityCheck();
   }

   // --------------------------------------------------------------------------------------------------------------

   public int getNumLightMen()
   {
      return numLightMen;
   }

   public void setNumLightMen(int numLightMen)
   {
      this.numLightMen = numLightMen;
      sanityCheck();
   }

   // --------------------------------------------------------------------------------------------------------------

   public int getNumLightKings()
   {
      return numLightKings;
   }

   public void setNumLightKings(int numLightKings)
   {
      this.numLightKings = numLightKings;
      sanityCheck();
   }

   // --------------------------------------------------------------------------------------------------------------

   public int getNumDarkMen()
   {
      return numDarkMen;
   }

   public void setNumDarkMen(int numDarkMen)
   {
      this.numDarkMen = numDarkMen;
      sanityCheck();
   }

   // --------------------------------------------------------------------------------------------------------------

   public int getNumDarkKings()
   {
      return numDarkKings;
   }

   public void setNumDarkKings(int numDarkKings)
   {
      this.numDarkKings = numDarkKings;
      sanityCheck();
   }

   // --------------------------------------------------------------------------------------------------------------

   public enum PlayerTurn
   {
      LightsTurn, DarksTurn
   }

   private PlayerTurn playerTurn = PlayerTurn.DarksTurn;

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

   // --------------------------------------------------------------------------------------------------------------

   private Square selectedSquare = null;

   public void setSelectedSquare(Square square)
   {
      if (square.isPlayable())
      {
         if (playerTurn == PlayerTurn.DarksTurn && (square.getSquareContents() == SquareContents.DarkMan || square.getSquareContents() == SquareContents.DarkKing))
         {
            selectedSquare = square;
         }
         else if (playerTurn == PlayerTurn.LightsTurn && (square.getSquareContents() == SquareContents.LightMan || square.getSquareContents() == SquareContents.LightKing))
         {
            selectedSquare = square;
         }
      }
   }

   public Square getSelectedSquare()
   {
      return selectedSquare;
   }

   public Square[][] Squares;

   // Setup the board for a new game
   // Default constructor returns a board setup for a new game.
   // --------------------------------------------------------------------------------------------------------------

   public CheckerBoard()
   {
      int positionIndex = 1;

      Squares = new Square[8][8];   // This initializes the pointer for the entire board

      // Populates the 8x8 board
      for (int row = 0; row < 8; row++)
      {
         for (int col = 0; col < 8; col++)
         {
            Squares[row][col] = new Square();   // Each individual square needs to be initialized.

            // set the coordinates for the square
            Squares[row][col].setRow(row);
            Squares[row][col].setCol(col);

            // assigns square number
            Squares[row][col].setNumber((row * 8) + col);

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

      // Temporarily Adds Extra Pieces to allow for jump testing.
      // Squares[4][3].setSquareContents(SquareContents.DarkMan);
      // Squares[3][6].setSquareContents(SquareContents.LightMan);

      findValidMovesForAllSquares();
   }

   private void countPieces(Square square)
   {
      if (square.getSquareContents() == SquareContents.LightMan)
      {
         numLightMen++;
      }
      else if (square.getSquareContents() == SquareContents.LightKing)
      {
         numLightKings++;
      }
      else if (square.getSquareContents() == SquareContents.DarkMan)
      {
         numDarkMen++;
      }
      else if (square.getSquareContents() == SquareContents.DarkKing)
      {
         numDarkKings++;
      }
   }

   // --------------------------------------------------------------------------------------------------------------

   public void findValidMovesForAllSquares()
   {
      numLightMen = 0;
      numLightKings = 0;
      numDarkMen = 0;
      numDarkKings = 0;

      for (int row = 0; row < 8; row++)
      {
         for (int col = 0; col < 8; col++)
         {
            findValidMovesForSquare(Squares[row][col]);
            countPieces(Squares[row][col]);
         }
      }

      sanityCheck();
   }

   // --------------------------------------------------------------------------------------------------------------

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
      else
      // Leaves the middle 2 rows empty
      {
         square.setSquareContents(SquareContents.Empty);
      }
   }

   // --------------------------------------------------------------------------------------------------------------

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

   // --------------------------------------------------------------------------------------------------------------
   // Finds the valid moves for a given square. Doesn't take jumps into consideration yet.
   private void findValidMovesForSquare(Square square)
   {
      int[] tempMoves = {-1, -1, -1, -1};    // Array that holds the position index of up to 4 valid moves. -1 for no move
      Square[] nextSquares = {null, null, null, null};
      int direction = 0;         // Direction the pieces are moving. 1 for going down, -1 for going up.
      int offset = 0;         // Number of position indexes away the valid move is. Will either be 3 or 5, depending on the row.
      boolean isKing = false;     // Is the piece in this square a king?
      Square tempSquare;

      square.setJumpAvailable(false);   // when sweeping the board to determine moves assume no jump is available until its found

      if (square.isPlayable())
      {
         switch (square.getSquareContents())
         {
            case LightMan: // Light pieces will travel up the board
               direction = -1;
               break;
            case DarkMan:  // Dark pieces will travel down
               direction = 1;
               break;
            case DarkKing: // Direction doesn't really matter for Kings
               isKing = true;
               break;
            case LightKing:
               isKing = true;
               break;
         }

         if (square.getSquareContents() != SquareContents.Empty)
         {
            switch (square.getSquareEdgeType())
            {
               case NonEdge:
                  // Calculates the row number. If the square is in an even numbered row the offset will be 5
                  if (((square.getPosition() - 1) / 4) % 2 == 0)
                  {
                     if (direction == 1)
                        offset = 5;
                     else
                        offset = 3;
                  }
                  else
                  {
                     if (direction == 1)
                        offset = 3;  // If the square is in an odd numbered row the offset will be 3
                     else
                        offset = 5;
                  }

                  // Multiply by direction (1 or -1) to determine whether to add or subtract the offsets
                  tempMoves[0] = square.getPosition() + (4 * direction);   // One of the offsets will always be 4 regardless of row number
                  tempMoves[1] = square.getPosition() + (offset * direction);

                  if (isKing)
                  {   // If the piece is a King, also find moves in the opposite direction
                     direction *= -1;

                     if (offset == 3)
                     {
                        offset = 5;  // When going the other direction, the offset needs to be flipped between 5 and 3
                     }
                     else
                     {
                        offset = 3;
                     }

                     tempMoves[2] = square.getPosition() + (4 * direction);
                     tempMoves[3] = square.getPosition() + (offset * direction);
                  }

                  break;

               // If the square is on an edge, there is a maximum of 2 valid moves.
               case LeftEdge:
               case RightEdge:
                  tempMoves[0] = square.getPosition() + (4 * direction);

                  if (isKing)
                  {
                     tempMoves[1] = square.getPosition() + (4 * direction * -1);
                  }

                  break;

               // Squares in the corners only have one possible move.
               case BottomLeftCorner:
                  tempMoves[0] = 25;
                  break;

               case TopRightCorner:
                  tempMoves[0] = 8;
                  break;

               // Squares on the top or bottom edges only have 2 possible moves.
               case TopEdge:
                  tempMoves[0] = square.getPosition() + 4;
                  tempMoves[1] = square.getPosition() + 5;
                  break;
               case BottomEdge:
                  tempMoves[0] = square.getPosition() - 4;
                  tempMoves[1] = square.getPosition() - 5;
                  break;
            }

            // Checks to see if next valid square has a piece in it or not.
            for (int i = 0; i < 4; i++)
            {
               if (tempMoves[i] != -1)
               {
                  tempSquare = posToSquare(tempMoves[i]);   // Square to potentially move to

                  SquareContents contents = tempSquare.getSquareContents();

                  // If current square contains a dark piece
                  if (square.getSquareContents() == SquareContents.DarkMan || square.getSquareContents() == SquareContents.DarkKing)
                  {
                     switch (contents)
                     {
                     // If the potential square contains another dark piece, that square isn't a valid move
                        case DarkMan:
                        case DarkKing:
                           tempSquare = null;
                           tempMoves[i] = -1;
                           break;
                        // If the potential square contains a light piece, checks to see if a jump is possible
                        case LightMan:
                        case LightKing:
                           tempSquare = checkForJump(square, tempSquare);

                           // If a jump is possible (tempSquare isn't null)
                           if (tempSquare != null)
                           {
                              square.setJumpAvailable(true);
                              tempMoves[i] = tempSquare.getPosition();
                           }
                           else
                              tempMoves[i] = -1;
                           break;
                     }
                  }
                  // If the current square contains a light piece
                  else
                  {
                     switch (contents)
                     {
                     // If the potential square contains a dark piece, checks to see if a jump is possible
                        case DarkMan:
                        case DarkKing:
                           tempSquare = checkForJump(square, tempSquare);
                           if (tempSquare != null)
                           {
                              // jumpAvailable = true;
                              square.setJumpAvailable(true);
                              tempMoves[i] = tempSquare.getPosition();
                           }
                           else
                              tempMoves[i] = -1;
                           break;
                        // If the potential square contains another light piece, that square isn't a valid move
                        case LightMan:
                        case LightKing:
                           tempSquare = null;
                           tempMoves[i] = -1;
                           break;
                     }

                  }

                  // Adds that square to an array of possible squares
                  nextSquares[i] = tempSquare;
               }
            }
         }
      }

      square.setValidMoves(nextSquares); // Saves the array of squares in the square object.
   }

   // Checks to see if a jump is possible. Returns the square to jump to if possible, null if not
   private Square checkForJump(Square square, Square tempSquare)
   {
      int squareNum = square.getNumber();
      int targetNum = tempSquare.getNumber();

      int distance = squareNum - targetNum;

      Square checkSquare = numToSquare(targetNum - distance);
      if (checkSquare.getSquareContents() == SquareContents.Empty)
         return checkSquare;
      return null;
   }

   // Returns the square associated to the given number
   private Square numToSquare(int number)
   {
      int coord[] = {-1, -1};
      coord[0] = number / 8;
      coord[1] = number - (coord[0] * 8);
      return getSquare(coord);
   }

   // Returns the square with the given position
   private Square posToSquare(int position)
   {
      int[] coordinates = {-1, -1};
      coordinates[0] = (position - 1) / 4;   // Finds the row of the next possible square

      // Finds the column of the next possible square
      if (coordinates[0] % 2 == 0)
      {
         coordinates[1] = (position - (coordinates[0] * 4)) * 2 - 1;
      }
      else
      {
         coordinates[1] = (position - ((coordinates[0] * 4)) - 1) * 2;
      }
      return getSquare(coordinates);
   }

   // Returns the square with the given coordinates
   private Square getSquare(int[] coordinates)
   {
      return Squares[coordinates[0]][coordinates[1]];
   }
}
