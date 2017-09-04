package it.hysen.tris.board;

import java.io.Serializable;

/**
 * @author Gabriele Cipolloni
 */
public class Cell implements Cloneable, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Pawn pawn;
	
	public Cell() {
		pawn = Pawn.EMPTY;
	}
	
	public Cell(Pawn pawn) {
		this.pawn = pawn;
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		final Cell other = (Cell) obj;
		if (pawn != other.pawn) {
			return false;
		}
		return true;
	}
	
	public Pawn getPawn() {
		return pawn;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((pawn == null) ? 0 : pawn.hashCode());
		return result;
	}
	
	public void setPawn(Pawn pawn) {
		this.pawn = pawn;
	}
	
}
