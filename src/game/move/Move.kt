package game.move

import game.Piece
import game.board.Board
import game.board.Position

/** 指し手を表す */
class Move(val piece: Piece, val position: Position) {

    // indexがボードの範囲外であるか
    private fun isOutOfRange(vararg index: Int): Boolean {
        for (i in index) {
            if (i !in 0 until Board.length) return true
        }
        return false
    }

    // 指定した方向で裏返せる石がある場合はtrueを返す
    private fun canReverse(board: Board, rDiff: Int, cDiff: Int): Boolean {
        var r = position.row + rDiff
        var c = position.column + cDiff
        if (isOutOfRange(r, c)) return false

        // 取る相手の石が一つもなければfalseを返す
        val opponent = piece.reverse()
        if (board[Position(r, c)] != opponent) return false

        // 取る相手の石が連続している間
        while (board[Position(r, c)] == opponent) {
            r += rDiff
            c += cDiff
            if (isOutOfRange(r, c)) return false
        }
        // 囲まれているか
        return board[Position(r, c)] == piece
    }

    /** 指定された [board] をもとに、指し手を適用した新しいボードを生成して返す。 */
    fun applyTo(board: Board): Board {
        var nextBoard = board.placePiece(piece, position)

        // 差分を使って8方向を調べる
        for (rDiff in -1..1) {
            for (cDiff in -1..1) {
                if (rDiff == 0 && cDiff == 0) continue
                if (canReverse(board, rDiff, cDiff)) {
                    var r: Int = position.row
                    var c: Int = position.column
                    // 自分の石がくるまで相手の石を裏返す
                    while (true) {
                        r += rDiff
                        c += cDiff
                        if (board[Position(r, c)] == piece) break
                        nextBoard = nextBoard.reversePiece(Position(r, c))
                    }
                }
            }
        }
        return nextBoard
    }

}
