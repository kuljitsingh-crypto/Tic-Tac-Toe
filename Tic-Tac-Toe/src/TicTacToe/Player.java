package TicTacToe;
import   TicTacToe.Game;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

class Player {
	String letter;
	
	Player(String letter){
		this.letter=letter;
	}
	
	boolean make_move(String letter, Game game,int pos) {
		// Add letter 'X' or 'O' to position defined by 'pos' variable.
		
		System.out.println("\n"+letter+" make a move to square "+pos+".");
		boolean res= game.add_letter(letter, pos);
		game.show_board();
		return res;
	}

}
class HumanPlayer extends Player{
	Scanner scanner=new Scanner(System.in);
	HumanPlayer(String letter){
		super(letter);
		
	}
	
	boolean make_move(Game game) {
		 // Choose the empty position to place the letter in board.
		 System.out.print("\n"+this.letter+"'s turn. Input your move (1-9): ");
		 
		 int user_pos=this.scanner.nextInt();
		 return super.make_move(this.letter,game,user_pos);
		 
	 }
	
}

class RandomComputerPlayer extends Player{
	 
	RandomComputerPlayer(String letter){
		super(letter);
	}
	 boolean make_move(Game game)  {
		 // Choose randomly empty position to place the letter in board.
		 
		 int index=new Random().nextInt(game.available_pos.size());
		 int computer_pos=game.available_pos.get(index);
		 
	
		 return super.make_move(this.letter,game,computer_pos);
		 
		 
	 }
	
}
class GeniusComputerPlayer extends Player{
	 
	GeniusComputerPlayer (String letter){
		super(letter);
	}
	 boolean make_move(Game game) {
		 int size=game.available_pos.size();
		 int computer_pos;
		 if (size==9) {
			 // If Computer has stared the game then randomly choose a empty position.
			 int index=new Random().nextInt(size);
			  computer_pos=game.available_pos.get(index);
		 }
		 
		 else {
			  String other_player=this.letter.equals("X")?"O":"X";
			  
			  //Else calculate the position based on Minmax Algorithm.
			 computer_pos=this.minmax(this.letter,other_player,game,true,0).get("position").intValue();
		 }
		
		 return super.make_move(this.letter,game,computer_pos);  
	 }
	 Map<String,Double> minmax(String max_player,String min_player,Game game,boolean max_player_move,int depth){
		 
		 /* Here max_player is computer selected letter and min_plyer is user selected letter.
		  * If winner of game is computer then return score based on calculation (10-depth).
		  * If winner of user is computer then return score based on calculation (-10+depth).
		  * Else If game is tie then return 0.
		  * Return type of function is dictionary which contain position and score of current move made by computer.
		*/

		 if((!game.game_tie) && (game.current_winnner.equals(max_player))){
			 Map<String,Double> res=new HashMap<>();
			 res.put("position", -1.0);
			 res.put("score", (double)(10-depth));
			 return res;
		 } 
		 else if((!game.game_tie) && (game.current_winnner.equals(min_player))){
			 Map<String,Double> res=new HashMap<>();
			 res.put("position", -1.0);
			 res.put("score", (double)(-10+depth));
			 return res;
		 } 
		else if(game.game_tie){
			 Map<String,Double> res=new HashMap<>();
			 res.put("position", -1.0);
			 res.put("score", 0.0);
			 return res;  
		 }
		 
		 /*
		  *'best' contain best move made by computer.
		  *Initial score of 'best' for max_player -Infinity 
		  *Initial score of 'best' for min_player Infinity 
		 */
		 
		 Map<String,Double> best=new HashMap<>();
		 best.put("position", -1.0);
		 if(max_player_move) {
			 best.put("score",Double.NEGATIVE_INFINITY); 
			 for(int pos=1;pos<10;pos++)  {
				 if(game.isempty(pos)) {
					 
					 /*Loop over all empty positions and try each position and check which position give maximum score. 
					  * Select that position as next move for computer. 
					  * */
					 // Add max_player to board.
					 game.add_letter(max_player, pos);
					 Map<String,Double> sim_score=this.minmax(max_player,min_player,game,false,depth+1);  // Alternate, Between  max_player and min_player 
					
					 /*
					  * If position given by 'pos' variable make score greater the current best score then replace that score and position.
					  * */
					 
					 if (sim_score.get("score")>best.get("score")) {
						 best.put("position",(double)pos); 
						 best.put("score",sim_score.get("score"));
					 }
					 
					 //Remove the letter from position given by 'pos' to bring the board to its previous state.
					 game.remove_letter(pos); 
				 }
			 }
			 return best; 
		 }
		 
		 else {
			 best.put("score",Double.POSITIVE_INFINITY);
			 for(int pos=1;pos<10;pos++) {
				 if(game.isempty(pos)) {
					 /*Loop over all empty positions and try each position and check which position give minimum score. 
					  * Select that position as next move for computer. 
					  * */
					 // Add min_player to board.
					 game.add_letter(min_player, pos);
					 Map<String,Double> sim_score=this.minmax(max_player,min_player,game,true,depth+1);// Alternate, Between  max_player and min_player 
					 /*
					  * If position given by 'pos' variable make score greater the current best score then replace that score and position.
					  * */
					 if (sim_score.get("score")<best.get("score")) {
						 best.put("position",(double)pos); 
						 best.put("score",sim_score.get("score"));
					 }
					 
					 //Remove the letter from position given by 'pos' to bring the board to its previous state.
					 game.remove_letter(pos); 
				 }
			 }
			 return best;  
			 
		 }
		 	 
	 }
	
}