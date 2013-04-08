
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
import android.widget.Button;

//import android.widget.ImageButton;

public class CheckersActivity extends Activity implements OnClickListener
{
   private Button       buttons[][];
   private CheckerBoard theBoard;
   //private final String CHECKER_BOARD = "checkerBoard";

   @Override
   protected void onCreate(Bundle savedInstanceState)
   {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.checkers_activity);

      theBoard = new CheckerBoard();

      createButtons();
      drawBoard();
   }

   // Creates the buttons for the board. Sets the appropriate buttons to be clickable.
   protected void createButtons()
   {
      buttons = new Button[8][8];
      int idIndex = R.id.unplayable_1;

      for (int row = 0; row < 8; row++)
      {
         for (int col = 0; col < 8; col++)
         {
            buttons[row][col] = (Button) findViewById(idIndex);
            buttons[row][col].setOnClickListener(this);

            // Stores the correct square object in the button. There may be a better way to do this.
            buttons[row][col].setTag(theBoard.Squares[row][col]);
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
            colorButton(theBoard.Squares[row][col]);
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
      // Test for valid moves. Prints the valid moves into logcat when button is clicked.
      Square selectedSquare = (Square) arg0.getTag();

      if (selectedSquare.isPlayable() && selectedSquare.getSquareContents() != SquareContents.Empty)
      {
         System.out.println("Valid Moves for Square #" + selectedSquare.getPosition() + ":");

         for (int i = 0; i < 4; i++)
         {
            System.out.println(selectedSquare.getValidMoves()[i]);
         }

         // highlight the clicked square and available moves
         theBoard.setSelectedSquare(selectedSquare);
         drawBoard();
      }
   }

   private void colorAvailableMoves()
   {
      Square selectedSquare = theBoard.getSelectedSquare();

      if (selectedSquare != null)
      {
         Square[] availableMoves = selectedSquare.getNextSquares();

         for (int i = 0; i < availableMoves.length; i++)
         {
            if (availableMoves[i] != null)
            {
               int row = availableMoves[i].getRow();
               int col = availableMoves[i].getCol();

               buttons[row][col].setBackgroundColor(Color.MAGENTA);
               buttons[row][col].setTextColor(Color.BLACK);
            }
         }
      }
   }

   private void colorButton(Square square)
   {
      int row = square.getRow();
      int col = square.getCol();

      if (square.getSquareContents() == SquareContents.LightMan)
      {
         buttons[row][col].setText(R.string.light);
         buttons[row][col].setBackgroundColor(Color.LTGRAY);
         buttons[row][col].setTextColor(Color.BLACK);
      }
      else if (square.getSquareContents() == SquareContents.DarkMan)
      {
         buttons[row][col].setText(R.string.dark);
         buttons[row][col].setBackgroundColor(Color.DKGRAY);
         buttons[row][col].setTextColor(Color.WHITE);
      }
      else if (square.getSquareContents() == SquareContents.LightKing)
      {
         buttons[row][col].setText(R.string.light_king);
         buttons[row][col].setBackgroundColor(Color.LTGRAY);
         buttons[row][col].setTextColor(Color.BLACK);
      }
      else if (square.getSquareContents() == SquareContents.DarkKing)
      {
         buttons[row][col].setText(R.string.dark_king);
         buttons[row][col].setBackgroundColor(Color.DKGRAY);
         buttons[row][col].setTextColor(Color.WHITE);
      }
      else if (square.getSquareContents() == SquareContents.Empty && square.isPlayable())
      {
         buttons[row][col].setText("");
         buttons[row][col].setBackgroundColor(Color.BLUE);
      }
      else if (square.getSquareContents() == SquareContents.Empty && !square.isPlayable())
      {
         buttons[row][col].setText("");
         buttons[row][col].setBackgroundColor(Color.WHITE);
      }

      if (square == theBoard.getSelectedSquare() && square.getSquareContents() != SquareContents.Empty)
      {
         buttons[row][col].setBackgroundColor(Color.GREEN);
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
