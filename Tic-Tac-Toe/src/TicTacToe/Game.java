package TicTacToe;
import java.util.ArrayList;


class Game {
	private final int row_len=5;
	private final int col_len=5;
	String current_winnner=""; // To store winner information.
	boolean game_tie=false; //Check whether game is tie or not.
	boolean invalid_move=false; //Check for any invalid move made by player.
	boolean first_move=true;
	
	
	 String board[][]= new String[row_len][col_len];
	 ArrayList<Integer> available_pos= new ArrayList<>();
	 
	 Game() {
		 
		 this.set_board();
		 this.init_board(" ",true );
		 this.show_board();
		 for(int i=1;i<10;i++) {
			 available_pos.add(i);
		 }
	 }
	 // Make the board
	 void set_board() {
		 for(int i=0;i<row_len;i++) {
			 for(int j=0;j<col_len;j++) {
				 if(j%2==1) {
					 this.board[i][j]="|"; 
				 }
				 if(i%2==1) {
					 if(j==col_len-1)this.board[i][j]="-----";
					 else this.board[i][j]="---";
				 }
				 
			 }
		 }
		 
	 }
	 /*Initialize the board
	  * If its first time method called .i.e., players have not made any move then show the user  number in  board.
	  * Number Signifies allowed cell position of  board.
	    1 | 2 | 3 
	    ----------
	    4 | 5 | 6 
	    ----------
	    7 | 8 | 9 
	    
	  * Whether 'init_board' method called second time it replace the numbers with space.
	  * */
     void init_board( String value,boolean first_time) {
    	int val=1;
		 for(int i=0;i<row_len;i++) {
			 for(int j=0;j<col_len;j++) {
				 if(j%2==0 && i%2==0) {
					 if(first_time) {
						 this.board[i][j]="  "+val+"  "; // val is used show the numbers in board. 
						 val++;
					 }
					 else {
						 this.board[i][j]="  "+value+"  ";// Replace numbers with space
					 }
				 }
				 
			 }
		 }
		 
	 }
     
     // show the current state of board
	 void show_board() {
		 System.out.println("");
		 for(int i=0;i<row_len;i++) {
			 for(int j=0;j<col_len;j++) {
				System.out.print(this.board[i][j]);
				 
			 }
			 System.out.println("");
		 }
	 }
	 
	 //check whether position given 'pos' is occupied by letter or not.
	 
	 boolean isempty(int pos) {
		 pos=Math.max(0, pos-1);
		 int row_index=(int)(pos/3)*2;
		 int col_index=(int)(pos%3)*2;
		 if(this.board[row_index][col_index].trim().length()==0) return true;
		 else return false;
		  
	 }
	 //Remove letter from specified position.
	 void remove_letter(int pos) {
		 this.game_tie=false;
		 this.current_winnner="";
		 this.available_pos.add(pos);
		 
		 //To make sure pos lies (0-8)
		 
		 pos=Math.max(0, pos-1);
		 
		 //Use 'pos' value as column index and row Index.
		 // If pos =7 then row=7/3=2 .But the allowed rows are (0,2,4). So multiplying it by 2.
		 //Similarly,for column.
		 int row_index=(int)(pos/3)*2;
		 int col_index=(int)(pos%3)*2;
		 this.board[row_index][col_index]="     ";
		  	 
	 }
	 
	 boolean add_letter(String letter, int pos) {
		 // If it is first time, player  made an move then  replace numbers by space.
		 if(this.first_move) {
			 this.init_board(" ", false);
			 this.first_move=false;
		 }
		 // If player gives position number greater then 9 or less than 0  than tell player its a invalid move.
		 if(pos>9 || pos<0) {
			 this.invalid_move=true;
			 return false;
		 }
		 else {
			 //To make sure pos lies (0-8)
			 pos=Math.max(0, pos-1);
			 
			 //Use 'pos' value as column index and row Index.
			 // If pos =7 then row=7/3=2 .But the allowed rows are (0,2,4). So multiplying it by 2.
			 //Similarly,for column.
			 
			 int row_index=(int) (pos/3)*2;
			 int col_index =(int) (pos%3)*2;
			 // If player gives position number which is non-empty than tell player its a invalid move.
			 if(this.board[row_index][col_index].trim().length()>0) {
				 this.invalid_move=true;
				 return false;
			 }
			 else {
				 
				 this.invalid_move=false;
				 //Add letter to specified row-column 
				 this.board[row_index][col_index]="  "+letter+"  ";
				 //Remove that position from list of available position(which keep track of allowed position in board).
				 Integer element=pos+1;
				 this.available_pos.remove(element); 
				 
				// check whether current move decided the winner.If yes return true. Else false.
				 boolean res=get_move_result(letter,row_index,col_index); 
				 if(res) this.current_winnner=letter;
				 return res;
			 }
			 
		 }
	 }
	 
	 boolean get_move_result(String letter,int row_index,int col_index) {
		 boolean letter_wins=true;
		 
//		 row check
		 for(int col=0;col<this.col_len;col=col+2) {
			 if(!this.board[row_index][col].trim().equals(letter)) {
				 letter_wins=false;
				 break;
			 } 
		 }
//		  column check
		 if (!letter_wins) {
			 letter_wins=true;
			 for(int row=0;row<this.row_len;row=row+2) {
				 if(!this.board[row][col_index].trim().equals(letter)) {
					 letter_wins=false;
					 break;
				 }	 
			 }
		 }
		 
		 // diagonal check
		 //diagonal check needed when letter is in corner or in middle
	     //if letter is in the middle of board then there are two diagonal check else one diagonal check 
		 if(!letter_wins) {
			 if(!((row_index==2 && col_index!=2) || (row_index!=2 && col_index==2))){
				 if(row_index==col_index) {
					 letter_wins=true;
					 for(int index=0;index<this.row_len;index=index+2) {
						 if(!this.board[index][index].trim().equals(letter)) {
							 letter_wins=false;
							 break;
						 }
					 }
				 }
				 if((row_index!=col_index)|| row_index==2) {
					 letter_wins=true;
					 for(int index=0;index<this.row_len;index=index+2) {
						 int index2=this.col_len-index-1;
						 if(!this.board[index][index2].trim().equals(letter)) {
							 letter_wins=false;
							 break;
						 }
					 }
				 }
				 
			 }
		 }
		  
		 if(!letter_wins && this.available_pos.size()==0) this.game_tie=true;

		 return letter_wins;
	 }

}

