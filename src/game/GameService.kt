package game

import game.board.Board
import game.board.Position
import game.move.IValidMoveSpecification
import game.move.Move

class GameService(private val validMoveSpecification: IValidMoveSpecification) {

    /** 両者とも置く場所がなければtrueを返す。 */
    fun isOver(board: Board): Boolean {
        if (getValidMoves(board, Piece.BLACK).isNotEmpty()) return false
        if (getValidMoves(board, Piece.WHITE).isNotEmpty()) return false
        return true
    }

    fun getValidMoves(board: Board, piece: Piece): List<Position> {
        val validMoves = mutableListOf<Position>()
        for (i in 0 until Board.length) {
            for (j in 0 until Board.length) {
                val move = Move(piece, Position(i, j))
                if (validMoveSpecification.isSatisfiedBy(move, board)) {
                    validMoves.add(move.position)
                }
            }
        }
        return validMoves
    }
}
