package game

import game.board.BoardFactory
import game.board.Position

class Game(private val gameService: GameService) {
    private val boardFactory = BoardFactory()
    private val phaseHistory = PhaseHistory()
    private var phase = Phase(boardFactory.othello(), Piece.BLACK).also { phaseHistory.add(it) }

    /**
     * 現在のボードと手番で有効な差し手のリスト
     */
    val validMoves get() = gameService.getValidMoves(phase.board, phase.currentTurn)
    val isOver get() = gameService.isOver(phase.board)
    val board get() = phase.board
    val currentTurn get() = phase.currentTurn
    val previousMove get() = phase.previousMove


    /**
     * 履歴も含め完全に初期状態にする。
     */
    fun reset() {
        phaseHistory.clear()
        phase = Phase(boardFactory.othello(), Piece.BLACK)
        phaseHistory.add(phase)
    }

    /**
     * 現在の状態を記録し、指定された [position] に石を置く。
     *
     * @throws IllegalStateException 指定された [position] にすでに石が置かれていた場合
     */
    fun place(position: Position) {
        phase = phase.toNextPhase(position)
        phaseHistory.add(phase)
    }

    fun count(piece: Piece) = board.count(piece)

    /**
     * 現在の状態を記録し、パスする。
     */
    fun pass() {
        phase = phase.toNextPhase()
        phaseHistory.add(phase)
    }

    /**
     *  一つ前の状態に戻す。
     *
     *  @throws NoSuchHistoryException 以前の状態の記録がない場合
     */
    fun undo() {
        phase = phaseHistory.undo()
    }

    /**
     * [undo] を呼び出す前の状態に戻す。
     *
     * @throws NoSuchHistoryException [undo] を呼び出す前の状態の記録がない場合
     */
    fun redo() {
        phase = phaseHistory.redo()
    }
}
