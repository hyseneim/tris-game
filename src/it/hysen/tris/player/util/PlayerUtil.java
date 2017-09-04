package it.hysen.tris.player.util;

import java.util.ArrayList;
import java.util.List;

import it.hysen.tris.board.Cell;
import it.hysen.tris.board.Pawn;
import it.hysen.tris.board.Position;
import it.hysen.tris.game.Game;
import it.hysen.tris.player.Player;
import it.hysen.tris.tree.TreeNode;

/**
 * @author Gabriele Cipolloni
 */
public class PlayerUtil {

	public static Cell[][] cloneBoard(Cell[][] board) {
		final Cell[][] cloneBoard = board.clone();
		for (int i = 0; i < board.length; i++) {
			cloneBoard[i] = board[i].clone();
		}
		return cloneBoard;
	}

	public static TreeNode generateTree(Game game, TreeNode currentNode, int currentTurns) {
		final Player currentPlayer = (currentTurns % 2) != 0 ? game.getPlayer1() : game.getPlayer2();

		final List<Position> legalMoves = PlayerUtil.listCurrentLegalMoves(currentNode.getBoard(), currentPlayer);
		legalMoves.forEach(legalMove -> {
			final int row = legalMove.getRow();
			final int column = legalMove.getColumn();

			final Cell[][] boardClone = PlayerUtil.cloneBoard(currentNode.getBoard());

			boardClone[row][column] = new Cell(currentPlayer.getPawn());

			final TreeNode treeNode = new TreeNode(currentNode, boardClone);

			currentNode.getChildren().add(treeNode);

			if (PlayerUtil.hasWin(boardClone, currentPlayer)) {
				final double value = (currentTurns % 2) != 0 ? 1 : -1;
				treeNode.setValue(value);
				treeNode.setLeaf(true);
			}
			else if (PlayerUtil.isDraw(boardClone)) {
				final double value = 0;
				treeNode.setValue(value);
				treeNode.setLeaf(true);
			}
			else {
				PlayerUtil.generateTree(game, treeNode, currentTurns + 1);
			}
		});

		return currentNode;
	}

	public static boolean hasWin(Cell[][] currentBoard, Player currentPlayer) {
		if ((currentBoard[0][0].getPawn().equals(currentPlayer.getPawn())
				&& currentBoard[0][1].getPawn().equals(currentPlayer.getPawn())
				&& currentBoard[0][2].getPawn().equals(currentPlayer.getPawn()))
				|| (currentBoard[1][0].getPawn().equals(currentPlayer.getPawn())
						&& currentBoard[1][1].getPawn().equals(currentPlayer.getPawn())
						&& currentBoard[1][2].getPawn().equals(currentPlayer.getPawn()))
				|| (currentBoard[2][0].getPawn().equals(currentPlayer.getPawn())
						&& currentBoard[2][1].getPawn().equals(currentPlayer.getPawn())
						&& currentBoard[2][2].getPawn().equals(currentPlayer.getPawn()))
				|| (currentBoard[0][0].getPawn().equals(currentPlayer.getPawn())
						&& currentBoard[1][0].getPawn().equals(currentPlayer.getPawn())
						&& currentBoard[2][0].getPawn().equals(currentPlayer.getPawn()))
				|| (currentBoard[0][1].getPawn().equals(currentPlayer.getPawn())
						&& currentBoard[1][1].getPawn().equals(currentPlayer.getPawn())
						&& currentBoard[2][1].getPawn().equals(currentPlayer.getPawn()))
				|| (currentBoard[0][2].getPawn().equals(currentPlayer.getPawn())
						&& currentBoard[1][2].getPawn().equals(currentPlayer.getPawn())
						&& currentBoard[2][2].getPawn().equals(currentPlayer.getPawn()))
				|| (currentBoard[0][2].getPawn().equals(currentPlayer.getPawn())
						&& currentBoard[1][1].getPawn().equals(currentPlayer.getPawn())
						&& currentBoard[2][0].getPawn().equals(currentPlayer.getPawn()))
				|| (currentBoard[0][0].getPawn().equals(currentPlayer.getPawn())
						&& currentBoard[1][1].getPawn().equals(currentPlayer.getPawn())
						&& currentBoard[2][2].getPawn().equals(currentPlayer.getPawn()))) {
			return true;
		}

		return false;
	}

	public static boolean isDraw(Cell[][] currentBoard) {
		if (PlayerUtil.isFullOfPawns(currentBoard)) {
			return true;
		}

		return false;
	}

	private static boolean isFullOfPawns(Cell[][] currentBoard) {
		for (final Cell[] element : currentBoard) {
			for (final Cell element2 : element) {
				if (element2.getPawn().equals(Pawn.EMPTY)) {
					return false;
				}
			}
		}
		return true;
	}

	public static List<Position> listCurrentLegalMoves(Cell[][] currentBoard, Player currentPlayer) {
		final List<Position> legalMoves = new ArrayList<>(9);

		for (int i = 0; i < currentBoard.length; i++) {
			for (int j = 0; j < currentBoard[i].length; j++) {
				final Cell currentCell = currentBoard[i][j];
				if (currentCell.getPawn().equals(Pawn.EMPTY)) {
					final Position position = new Position(i, j);
					legalMoves.add(position);
				}
			}
		}

		return legalMoves;
	}

	private PlayerUtil() {
		throw new AssertionError();
	}

}
