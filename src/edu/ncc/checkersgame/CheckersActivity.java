
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
         for (int col = 0; col < 8; col++)
         {
            buttons[row][col] = (Button) findViewById(idIndex);
            buttons[row][col].setOnClickListener(this);

            // Stores the correct square object in the button. There may be a better way to do this.
            buttons[row][col].setTag(theBoard.Squares[row][col]);
            idIndex++;
         }
   }

   // Checks state of the board for pieces and draws them in the appropriate position on the board.
   protected void drawBoard()
   {
      for (int row = 0; row < 8; row++)
      {
         for (int col = 0; col < 8; col++)
         {
            if (theBoard.Squares[row][col].getSquareContents() == SquareContents.LightMan)
            {
               buttons[row][col].setText(R.string.light);
               buttons[row][col].setBackgroundColor(Color.LTGRAY);
               buttons[row][col].setTextColor(Color.BLACK);
            }
            else if (theBoard.Squares[row][col].getSquareContents() == SquareContents.DarkMan)
            {
               buttons[row][col].setText(R.string.dark);
               buttons[row][col].setBackgroundColor(Color.DKGRAY);
               buttons[row][col].setTextColor(Color.WHITE);
            }
            else if (theBoard.Squares[row][col].getSquareContents() == SquareContents.LightKing)
            {
               buttons[row][col].setText(R.string.light_king);
               buttons[row][col].setBackgroundColor(Color.LTGRAY);
               buttons[row][col].setTextColor(Color.BLACK);
            }
            else if (theBoard.Squares[row][col].getSquareContents() == SquareContents.DarkKing)
            {
               buttons[row][col].setText(R.string.dark_king);
               buttons[row][col].setBackgroundColor(Color.DKGRAY);
               buttons[row][col].setTextColor(Color.WHITE);
            }
            else if (theBoard.Squares[row][col].getSquareContents() == SquareContents.Empty && theBoard.Squares[row][col].isPlayable())
            {
               buttons[row][col].setText("");
               buttons[row][col].setBackgroundColor(Color.BLUE);
            }
            else if (theBoard.Squares[row][col].getSquareContents() == SquareContents.Empty && !theBoard.Squares[row][col].isPlayable())
            {
               buttons[row][col].setText("");
               buttons[row][col].setBackgroundColor(Color.WHITE);
            }
         }
      }
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
      Square temp = (Square) arg0.getTag();
      
      if (temp.isPlayable())
      {
         System.out.println("Valid Moves for Square #" + temp.getPosition() + ":");

         for (int i = 0; i < 4; i++)
         {
            System.out.println(temp.getValidMoves()[i]);
         }
      }
   }
}
