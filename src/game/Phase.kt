package game

import game.board.Board
import game.board.Position
import game.move.Move

// ゲームの局面を表す
class Phase(val board: Board, val currentTurn: Piece, val previousMove: Move? = null) {

    fun toNextPhase(move: Position? = null): Phase {
        val nextTurn = currentTurn.reverse()
        return if (move == null) {
            Phase(board, nextTurn)
        } else {
            val moveWithTurn = Move(currentTurn, move)
            val nextBoard = moveWithTurn.applyTo(board)
            Phase(nextBoard, nextTurn, moveWithTurn)
        }
    }

}
