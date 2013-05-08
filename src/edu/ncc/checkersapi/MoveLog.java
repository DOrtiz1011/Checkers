
package edu.ncc.checkersapi;

public class MoveLog
{

   String  moveLog;
   int     turn;
   boolean newTurn;

   public MoveLog()
   {
      newTurn = true;
      turn = 0;
      moveLog = new String("");

   }

   public void logMove(int oldPos, int newPos, boolean jump)
   {
      if (newTurn)
      {
         turn++;
         moveLog += "\n" + turn + ". ";
         newTurn = false;
      }
      else
      {
         newTurn = true;
      }

      if (jump)
      {
         moveLog += oldPos + "x" + newPos + " ";
      }
      else
      {
         moveLog += oldPos + "-" + newPos + " ";
      }
   }

   public String toString()
   {
      return moveLog;
   }

}
