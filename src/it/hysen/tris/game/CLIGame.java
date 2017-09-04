package it.hysen.tris.game;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import it.hysen.tris.board.Cell;
import it.hysen.tris.board.Position;
import it.hysen.tris.player.AIPlayer;
import it.hysen.tris.player.HumanPlayer;
import it.hysen.tris.player.Player;
import it.hysen.tris.player.util.PlayerUtil;
import it.hysen.tris.tree.TreeNode;

/**
 * @author Gabriele Cipolloni
 */
public class CLIGame extends AbstractGame {

	public static void main(String[] args) {
		final Scanner scanner = new Scanner(System.in);
		
		final CLIGame game = new CLIGame();
		
		game.printBoard(game.getBoard());
		
		boolean endgame = false;
		
		do {
			final Player currentPlayer = game.getCurrentPlayer();
			
			if (currentPlayer instanceof HumanPlayer) {
				int selectedRow = -1, selectedColumn = -1;
				
				final List<Position> legalMoves = PlayerUtil.listCurrentLegalMoves(game.getBoard(), currentPlayer);
				
				System.out.println("\n Moves:");
				for (final Position legalMove : legalMoves) {
					final int row = legalMove.getRow();
					final int column = legalMove.getColumn();
					System.out.println(row + " " + column);
				}
				
				boolean correctInput = false;
				
				do {
					System.out.println("\n Insert a move: ");
					
					if (scanner.hasNextInt()) {
						selectedRow = scanner.nextInt();
						if (scanner.hasNextInt()) {
							selectedColumn = scanner.nextInt();
							if (legalMoves.contains(new Position(selectedRow, selectedColumn))) {
								correctInput = true;
							}
						}
					}
				}
				while (!correctInput);
				
				game.getBoard()[selectedRow][selectedColumn].setPawn(currentPlayer.getPawn());
			}
			else if (currentPlayer instanceof AIPlayer) {
				// final List<TreeNode> currentTree =
				// GameUtil.readTreeAtDepthFromBinaryFile(game.getTurns());
				final List<TreeNode> currentTree = game.getTree().stream()
						.filter(treeNode -> treeNode.getDepth() == game.getTurns()).collect(Collectors.toList());
				final AIPlayer aiCurrentPlayer = (AIPlayer) currentPlayer;
				final TreeNode maxValueNode = aiCurrentPlayer.findMaxValueNode(currentTree);
				game.setBoard(maxValueNode.getBoard());
			}
			
			if (PlayerUtil.hasWin(game.getBoard(), currentPlayer)) {
				System.out.println("\n Win: " + currentPlayer.getPawn());
				endgame = true;
			}
			else if (PlayerUtil.isDraw(game.getBoard())) {
				System.out.println("\n Draw.");
				endgame = true;
			}
			else {
				game.setTurns(game.getTurns() + 1);
			}
			
			game.printBoard(game.getBoard());
			
		}
		while (!endgame);
		
		scanner.close();
	}

	public void printBoard(Cell[][] board) {
		System.out.println("\n -------------------");
		for (final Cell[] element : board) {
			System.out.println("\n ");
			for (final Cell element2 : element) {
				System.out.print(element2.getPawn().getContent() + "\t");
			}
		}
		System.out.println("\n -------------------");
	}

}
