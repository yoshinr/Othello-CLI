package game.board

import game.Piece

class BoardFactory {

    /** オセロの初期配置でボードを生成する */
    fun othello(): Board {
        var b = Board()
        b = b.placePiece(Piece.WHITE, Position("D4"))
        b = b.placePiece(Piece.WHITE, Position("E5"))
        b = b.placePiece(Piece.BLACK, Position("E4"))
        b = b.placePiece(Piece.BLACK, Position("D5"))
        return b
    }

}
