package TicTacToe;

import java.util.Scanner;

import TicTacToe.Game;
import TicTacToe.HumanPlayer;
import TicTacToe.RandomComputerPlayer;
import TicTacToe.GeniusComputerPlayer;
public class TicTacToe {

	public static void main(String[] args) throws InterruptedException {
		start_game();
	}
	
	@SuppressWarnings("resource")
	static void start_game() throws InterruptedException {
		Scanner scanner =new java.util.Scanner(System.in);
		String user_choice;
		byte player_turn;
		int game_mode;
		HumanPlayer player1;
		String other_choice;
		System.out.print("Choose Game Mode:\n \t1.Easy\n\t2.Hard\n"); 
		game_mode=scanner.nextInt();
		// Ask user in which mode he /she want to play. 
		
		if(game_mode<2) System.out.println("You have selected Easy Mode!.");
		else System.out.println("You have selected Hard Mode!.");
		
		/*
		 * There are two mode to Play.
		 * In Easy mode, user play with the Random Computer Player which randomly select the position.
		 * In Hard mode, user play with the Random Computer Player which is AI and use MinMax algorithm to select position. 
		*/
			
		while(true) {
			System.out.print("Choose either (X/x) or (O/o): ");
			user_choice=scanner.next().toUpperCase();
			if(user_choice.equals("X") || user_choice.equals("O")) break;
		}
		/*
		 * Ask users whether they want to choose 'X' or 'O'.
		 * If User choose 'X' then he/she start the game.
		 * Else computer start the game.
		 *  */
		if(user_choice.equals("X")) {
		    player1=new HumanPlayer("X");
			player_turn=0;
			other_choice="O";
		}
		else {
			 player1=new HumanPlayer("O");
			 player_turn=1;
			 other_choice="X";
		}
		
		if(game_mode<2) {
			RandomComputerPlayer player2=new RandomComputerPlayer(other_choice);
			play_game(player1,player2,player_turn);
			
		}
		else {
			GeniusComputerPlayer player2=new GeniusComputerPlayer(other_choice);
			play_game(player1,player2,player_turn);
		}
		
	}
	
	@SuppressWarnings("resource")
	static   <T extends Player> void play_game(HumanPlayer player1, T player2, byte player_turn) throws InterruptedException {
		boolean player1_win=false;
		boolean player2_win=false;
		byte player_no=2;
		Game game=new Game();
		while(((! player1_win) && (!player2_win))) {
			if(((byte)player_turn%player_no)==0) {
				player1_win=player1.make_move(game);
				
				//If player select non-empty position,then game show invalid move error.
				//And ask player to enter new empty position.
				
				if (game.invalid_move) {
					player_turn--;
					System.out.println(player1.letter+" make an invalid move!.");
				}
			}
			else {
				Thread.sleep(800);
				// Here player2 has type of Player.
				// Player class and its subclass have different definition of 'make_move' method.So, here player2 object casted to appropriate subclass.
				
				if(player2 instanceof RandomComputerPlayer) {
					player2_win=((RandomComputerPlayer)player2).make_move(game);
				}
				else if(player2 instanceof GeniusComputerPlayer) {
					player2_win=((GeniusComputerPlayer)player2).make_move(game);
				}
				
				//If player select non-empty position,then game show invalid move error.
				//And ask player to enter new empty position.
				
				if (game.invalid_move) {
					player_turn--;
				}
			}
			player_turn++;
			if(game.game_tie) break;

		}
		if (game.game_tie) System.out.println("It's a Tie");
		else System.out.println("\n"+game.current_winnner+" win!.");
		System.out.println("Want to Play Again? Press \n\t(Y/y) for Yes \n\t(N/n) for No");
		String choice= new Scanner(System.in).next();
		if(choice.toUpperCase().equals("Y")) start_game();
		else System.out.println("Thanks for playing the game:)");
		
	}

}
