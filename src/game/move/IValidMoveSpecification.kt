package game.move

import game.board.Board

interface IValidMoveSpecification {
    fun isSatisfiedBy(move: Move, board: Board): Boolean
}
