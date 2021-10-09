package game.move

import game.board.Board
import game.board.Position


class ValidMoveSpecification : IValidMoveSpecification {

    // indexがボードの範囲外であるか
    private fun isOutOfRange(vararg index: Int): Boolean {
        for (i in index) {
            if (i !in 0 until Board.length) return true
        }
        return false
    }

    // 指定した方向で裏返せる石があるか
    private fun canReverse(move: Move, board: Board, rDiff: Int, cDiff: Int): Boolean {
        var r = move.position.row + rDiff
        var c = move.position.column + cDiff
        if (isOutOfRange(r, c)) return false

        // 取る相手の石が一つもなければfalseを返す
        val opponent = move.piece.reverse()
        if (board[Position(r, c)] != opponent) return false

        // 取る相手の石が連続している間
        while (board[Position(r, c)] == opponent) {
            r += rDiff
            c += cDiff
            if (isOutOfRange(r, c)) return false
        }
        // 囲まれているか
        return board[Position(r, c)] == move.piece
    }

    /** ひっくり返せる場所があるならtrueを返す。 */
    override fun isSatisfiedBy(move: Move, board: Board): Boolean {
        if (board[move.position] != null) {
            return false
        }

        // 差分を使って8方向を調べる
        for (rDiff in -1..1) {
            for (cDiff in -1..1) {
                if (rDiff == 0 && cDiff == 0) continue
                if (canReverse(move, board, rDiff, cDiff)) {
                    return true
                }
            }
        }
        return false
    }

}
