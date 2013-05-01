// http://en.wikipedia.org/wiki/English_draughts

package edu.ncc.checkersapi;

import java.io.Serializable;

import edu.ncc.checkersapi.Square.SquareContents;
import edu.ncc.checkersapi.Square.SquareEdgeType;

public class CheckerBoard implements Serializable
{
   private static final long serialVersionUID = 1L;
   private int               numLightMen      = 0; // Number of Light Pieces
   private int               numLightKings    = 0; // Number of Light Kings
   private int               numLightCaptured = 0; // Number of Captured Light Pieces
   private int               numDarkMen       = 0; // Number of Dark Pieces
   private int               numDarkKings     = 0; // Number of Dark Kings
   private int               numDarkCaptured  = 0; // Number of Captured Dark Pieces

   // --------------------------------------------------------------------------------------------------------------

   public enum GameState
   {
      InPlay, DarkWins, LightWins, Draw
   }

   private GameState gameState = GameState.InPlay;

   public GameState getGameState()
   {
      return gameState;
   }

   // --------------------------------------------------------------------------------------------------------------

   public GameState checkWinner()
   {
      if (numLightCaptured == 12)
      {
         gameState = GameState.DarkWins;
      }
      else if (numDarkCaptured == 12)
      {
         gameState = GameState.LightWins;
      }

      // need a case here for a draw if no moves a available

      return gameState;
   }

   // --------------------------------------------------------------------------------------------------------------

   protected String sanityCheck()
   {
      String error = null;

      if ((numLightMen + numLightKings + numLightCaptured) != 12 || (numDarkMen + numDarkKings + numDarkCaptured) != 12)
      {
         error = "SANITY CHECK FAILED!!" + "\nnumLightMen      = " + numLightMen + "\nnumLightKings    = " + numLightKings + "\nnumLightCaptured = " + numLightCaptured + "\nnumDarkMen       = " + numDarkMen + "\nnumDarkKings     = " + numDarkKings + "\nnumDarkCaptured  = " + numDarkCaptured;
      }

      return error;
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
      if (square != null)
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
      else
      {
         selectedSquare = null;
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

   public String findValidMovesForAllSquares()
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

      checkWinner();

      return sanityCheck();
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
      Square[] nextSquares = {null, null, null, null};
      int direction = 1;         // Direction the pieces are moving. 1 for going down, -1 for going up.
      boolean isKing = false;     // Is the piece in this square a king?
      Square tempSquare;
      int currRow;
      int currCol;

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
            currRow = square.getRow();
            currCol = square.getCol();
            switch (square.getSquareEdgeType())
            {
               case NonEdge:
                  // Calculates the row number. If the square is in an even numbered row the offset will be 5
                  nextSquares[0] = Squares[currRow + direction][currCol - 1];
                  nextSquares[1] = Squares[currRow + direction][currCol + 1];

                  if (isKing)
                  {   // If the piece is a King, also find moves in the opposite direction
                     direction *= -1;

                     nextSquares[2] = Squares[currRow + direction][currCol + 1];
                     nextSquares[3] = Squares[currRow + direction][currCol - 1];
                  }

                  break;

               // If the square is on an edge, there is a maximum of 2 valid moves.
               case LeftEdge:
                  nextSquares[0] = Squares[currRow + direction][currCol + 1];

                  if (isKing)
                  {
                     nextSquares[1] = Squares[currRow - direction][currCol + 1];
                  }

                  break;

               case RightEdge:
                  nextSquares[0] = Squares[currRow + direction][currCol - 1];

                  if (isKing)
                  {
                     nextSquares[1] = Squares[currRow - direction][currCol - 1];
                  }

                  break;

               // Squares in the corners only have one possible move.
               case BottomLeftCorner:
                  nextSquares[0] = Squares[6][1];
                  break;

               case TopRightCorner:
                  nextSquares[0] = Squares[1][6];
                  break;

               // Squares on the top or bottom edges only have 2 possible moves.
               case TopEdge:
                  nextSquares[0] = Squares[1][currCol + 1];
                  nextSquares[1] = Squares[1][currCol - 1];
                  break;
               case BottomEdge:
                  nextSquares[0] = Squares[6][currCol + 1];
                  nextSquares[1] = Squares[6][currCol - 1];
                  break;
            }

            // Checks to see if next valid square has a piece in it or not.
            int[] jumpList = {-1, -1, -1, -1};
            for (int i = 0; i < 4; i++)
            {
               if (nextSquares[i] != null)
               {
                  tempSquare = nextSquares[i];   // Square to potentially move to

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
                           nextSquares[i] = null;
                           break;
                        // If the potential square contains a light piece, checks to see if a jump is possible
                        case LightMan:
                        case LightKing:
                           tempSquare = checkForJump(square, tempSquare);

                           // If a jump is possible (tempSquare isn't null)
                           if (tempSquare != null)
                           {
                              square.setJumpAvailable(true);
                              nextSquares[i] = tempSquare;
                              jumpList[i] = tempSquare.getPosition();
                           }
                           else
                           {
                              nextSquares[i] = null;
                           }
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
                              nextSquares[i] = tempSquare;
                              jumpList[i] = tempSquare.getPosition();
                           }
                           else
                           {
                              nextSquares[i] = null;
                           }
                           break;
                        // If the potential square contains another light piece, that square isn't a valid move
                        case LightMan:
                        case LightKing:
                           tempSquare = null;
                           nextSquares[i] = null;
                           break;
                     }

                  }

                  // Adds that square to an array of possible squares
                  nextSquares[i] = tempSquare;
                  square.setJumpList(jumpList);
               }
            }
         }
      }

      square.setValidMoves(nextSquares); // Saves the array of squares in the square object.
   }

   // Checks to see if a jump is possible. Returns the square to jump to if possible, null if not
   private Square checkForJump(Square square, Square tempSquare)
   {
      int tempRow = tempSquare.getRow();
      int tempCol = tempSquare.getCol();
      int row = square.getRow();
      int col = square.getCol();

      int rowDirection = 0;
      if (tempRow - row > 0)
      {
         rowDirection = 1;
      }
      else if (tempRow - row < 0)
      {
         rowDirection = -1;
      }
      else
      {
         rowDirection = 0;
      }

      int colDirection = 0;
      if (tempCol - col > 0)
      {
         colDirection = 1;
      }
      else if (tempCol - col < 0)
      {
         colDirection = -1;
      }
      else
      {
         colDirection = 0;
      }

      if (tempRow + rowDirection < 0 || tempRow + rowDirection > 7)
      {
         rowDirection = 0;
      }
      if (tempCol + colDirection < 0 || tempCol + colDirection > 7)
      {
         colDirection = 0;
      }

      if (rowDirection == 0 || colDirection == 0)
      {
         return null;
      }

      Square checkSquare = Squares[tempRow + rowDirection][tempCol + colDirection];
      if (checkSquare.getSquareContents() == SquareContents.Empty)
      {
         return checkSquare;
      }

      return null;
   }

   public String movePiece(Square sqFrom, Square sqTo)
   {
      SquareContents temp = sqFrom.getSquareContents();
      sqTo.setSquareContents(temp);
      sqFrom.setSquareContents(SquareContents.Empty);

      if (sqTo.getSquareEdgeType() == SquareEdgeType.BottomEdge || sqTo.getSquareEdgeType() == SquareEdgeType.TopEdge)
      {
         temp = sqTo.getSquareContents();

         if (temp == SquareContents.DarkMan)
         {
            temp = SquareContents.DarkKing;
         }
         if (temp == SquareContents.LightMan)
         {
            temp = SquareContents.LightKing;
         }

         sqTo.setSquareContents(temp);
      }

      if (sqFrom.isJumpAvailable())
      {
         int[] jumpList = sqFrom.getJumpList();
         Square deadSquare = null;
         for (int i = 0; i < jumpList.length; i++)
         {
            if (sqTo.getPosition() == jumpList[i])
            {
               int rowDirection = sqTo.getRow() - sqFrom.getRow();
               int colDirection = sqTo.getCol() - sqFrom.getCol();

               if (rowDirection < 0 && colDirection < 0)
               {
                  deadSquare = Squares[sqTo.getRow() + 1][sqTo.getCol() + 1];
               }
               else if (rowDirection < 0 && colDirection > 0)
               {
                  deadSquare = Squares[sqTo.getRow() + 1][sqTo.getCol() - 1];
               }
               else if (rowDirection > 0 && colDirection < 0)
               {
                  deadSquare = Squares[sqTo.getRow() - 1][sqTo.getCol() + 1];
               }
               else if (rowDirection > 0 && colDirection > 0)
               {
                  deadSquare = Squares[sqTo.getRow() - 1][sqTo.getCol() - 1];
               }
               break;
            }
         }
         if (deadSquare != null)
         {
            switch (deadSquare.getSquareContents())
            {
               case DarkMan:
               case DarkKing:
                  numDarkCaptured++;
                  break;
               case LightMan:
               case LightKing:
                  numLightCaptured++;
                  break;
            }
            deadSquare.setSquareContents(SquareContents.Empty);
         }
      }

      setSelectedSquare(null);
      switchPlayerTurn();
      return findValidMovesForAllSquares();
   }

   public void Reset()
   {
      numLightMen = 0;
      numLightKings = 0;
      numLightCaptured = 0;
      numDarkMen = 0;
      numDarkKings = 0;
      numDarkCaptured = 0;
      gameState = GameState.InPlay;
      playerTurn = PlayerTurn.DarksTurn;
      setSelectedSquare(null);

      for (int row = 0; row < 8; row++)
      {
         for (int col = 0; col < 8; col++)
         {
            if (Squares[row][col].isPlayable())
            {
               setSquareContents(Squares[row][col], Squares[row][col].getPosition());
            }
         }
      }

      findValidMovesForAllSquares();
   }
}
