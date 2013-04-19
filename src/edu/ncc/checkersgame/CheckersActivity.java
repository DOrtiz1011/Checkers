
package edu.ncc.checkersgame;

import edu.ncc.checkersapi.CheckerBoard;
import edu.ncc.checkersapi.Square;
import edu.ncc.checkersapi.Square.SquareContents;
import edu.ncc.checkersgame.R;
import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class CheckersActivity extends Activity implements OnClickListener
{
   private ImageButton  imageButtons[][];
   private CheckerBoard checkerBoard;

   // private final String CHECKER_BOARD = "checkerBoard";

   @Override
   protected void onCreate(Bundle savedInstanceState)
   {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.checkers_activity);

      checkerBoard = new CheckerBoard();

      createButtons();
      drawBoard();
   }

   // Creates the buttons for the board. Sets the appropriate buttons to be clickable.
   protected void createButtons()
   {
      imageButtons = new ImageButton[8][8];
      int idIndex = R.id.imageButton00;

      for (int row = 0; row < 8; row++)
      {
         for (int col = 0; col < 8; col++)
         {
            imageButtons[row][col] = (ImageButton) findViewById(idIndex);
            imageButtons[row][col].setOnClickListener(this);

            // Stores the correct square object in the button. There may be a better way to do this.
            imageButtons[row][col].setTag(checkerBoard.Squares[row][col]);
            idIndex++;
         }
      }
   }

   // Checks state of the board for pieces and draws them in the appropriate position on the board.
   protected void drawBoard()
   {
      for (int row = 0; row < 8; row++)
      {
         for (int col = 0; col < 8; col++)
         {
            colorButton(checkerBoard.Squares[row][col]);
         }
      }

      colorAvailableMoves();
   }

   @Override
   public boolean onCreateOptionsMenu(Menu menu)
   {
      // Inflate the menu; this adds items to the action bar if it is present.
      getMenuInflater().inflate(R.menu.checkers_activity, menu);
      return true;
   }

   @Override
   public void onClick(View arg0)
   {
      Square selectedSquare = (Square) arg0.getTag();

      selectedSquare.printValidMoves();

      if (!checkerBoard.equals(null))
      {
         // highlight the clicked square and available moves
         if (checkerBoard.getSelectedSquare() == null)
         {
            checkerBoard.setSelectedSquare(selectedSquare);
         }
         else
         {
            Square temp = checkerBoard.getSelectedSquare();
            for (int i = 0; i < 4; i++)
            {
               if (selectedSquare.equals(temp.getValidMoves()[i]))
               {
                  checkerBoard.movePiece(temp, selectedSquare);
                  drawBoard();
                  break;
               }
            }
         }
         drawBoard();
      }
   }

   private void colorAvailableMoves()
   {
      Square selectedSquare = checkerBoard.getSelectedSquare();

      if (selectedSquare != null)
      {
         Square[] availableMoves = selectedSquare.getValidMoves();

         for (int i = 0; i < availableMoves.length; i++)
         {
            if (availableMoves[i] != null)
            {
               int row = availableMoves[i].getRow();
               int col = availableMoves[i].getCol();

               imageButtons[row][col].setBackgroundColor(Color.MAGENTA);
            }
         }
      }
   }

   private void colorButton(Square square)
   {
      int row = square.getRow();
      int col = square.getCol();

      if (square == checkerBoard.getSelectedSquare() && square.getSquareContents() != SquareContents.Empty)
      {
         imageButtons[row][col].setBackgroundColor(Color.GREEN);
      }
      else if (square.getSquareContents() == SquareContents.LightMan)
      {
         imageButtons[row][col].setBackgroundColor(Color.BLUE);
         imageButtons[row][col].setImageResource(R.drawable.light_man);
      }
      else if (square.getSquareContents() == SquareContents.DarkMan)
      {
         imageButtons[row][col].setBackgroundColor(Color.BLUE);
         imageButtons[row][col].setImageResource(R.drawable.dark_man);
      }
      else if (square.getSquareContents() == SquareContents.LightKing)
      {
         imageButtons[row][col].setBackgroundColor(Color.BLUE);
      }
      else if (square.getSquareContents() == SquareContents.DarkKing)
      {
         imageButtons[row][col].setBackgroundColor(Color.BLUE);
      }
      else if (square.getSquareContents() == SquareContents.Empty && square.isPlayable())
      {
         imageButtons[row][col].setBackgroundColor(Color.BLUE);
         imageButtons[row][col].setImageResource(0);
      }
      else if (square.getSquareContents() == SquareContents.Empty && !square.isPlayable())
      {
         imageButtons[row][col].setBackgroundColor(Color.LTGRAY);
         imageButtons[row][col].setImageResource(0);
      }
   }

   public void onSavedInstanceState(Bundle savedInstanceState)
   {
      super.onSaveInstanceState(savedInstanceState);
   }

   public void onRestoreInstanceState(Bundle savedInstanceState)
   {
      super.onRestoreInstanceState(savedInstanceState);
   }
}
