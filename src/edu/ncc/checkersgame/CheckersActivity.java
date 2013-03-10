package edu.ncc.checkersgame;

import edu.ncc.checkersapi.CheckerBoard;
import edu.ncc.checkersapi.Square;
import edu.ncc.checkersapi.Square.SquareContents;
import edu.ncc.checkersgame.R;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
//import android.widget.ImageButton;


public class CheckersActivity extends Activity implements OnClickListener {
	
	private Button buttons[][];
	private CheckerBoard theBoard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkers_activity);
        
        
        theBoard = new CheckerBoard();
        buttons = new Button [8][8];
        int idIndex = R.id.unplayable_1;
        
        for(int row=0; row<8; row++)
        	for(int col=0; col<8; col++){
        		if((row % 2) == 0){
        			buttons[row][col] = (Button)findViewById(idIndex);
        		}
        		else{
        			buttons[row][col] = (Button)findViewById(idIndex);
        			buttons[row][col].setOnClickListener(this);
        		}
        		idIndex++;
        	}
        
        for(int row=0; row<8; row++)
        {
        	for(int col=0; col<8; col++)
        	{
        		if(theBoard.Squares[row][col].getSquareContents() != SquareContents.Empty)
        		{
               buttons[row][col].setTag(theBoard.Squares[row][col]);
        			if(theBoard.Squares[row][col].getSquareContents() == SquareContents.LightMan)
        				buttons[row][col].setText(R.string.light);
        			else
        				if(theBoard.Squares[row][col].getSquareContents() == SquareContents.DarkMan)
        					buttons[row][col].setText(R.string.dark);
        				else
        					if(theBoard.Squares[row][col].getSquareContents() == SquareContents.LightKing)
        						buttons[row][col].setText(R.string.light_king);
        					else
        						if(theBoard.Squares[row][col].getSquareContents() == SquareContents.DarkKing)
        							buttons[row][col].setText(R.string.dark_king);
        		}
        		
        			
        			
        	}
        }
        			
        	
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.checkers_activity, menu);
        return true;
    }

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
	   Square temp = (Square) arg0.getTag();
	   System.out.println("Valid Moves for Square #" + temp.getPosition() + ":");
	   for (int i = 0; i < 4; i++){
	      System.out.println(temp.getValidMoves()[i]);
	   }
	}
    
}
