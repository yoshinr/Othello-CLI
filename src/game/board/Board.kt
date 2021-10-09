package game.board

import game.Piece

class Board {
    private val rawBoard: List<List<Piece?>>

    companion object {
        // 一辺の長さ
        const val length = 8
    }

    constructor() {
        // 8 × 8 の何も置かれていないオセロ盤
        rawBoard = List(length) { List(length) { null } }
    }

    private constructor(rawBoard: List<List<Piece?>>) {
        require(rawBoard.size == length) { "ボードの縦の長さは${length}です。" }
        require(rawBoard[0].size == length) { "ボードの横の長さは${length}です。" }
        this.rawBoard = rawBoard
    }

    operator fun get(position: Position): Piece? =
        rawBoard[position.row][position.column]


    fun placePiece(piece: Piece, position: Position): Board {
        check(rawBoard[position.row][position.column] == null) {
            "指定さた場所にはすで石が置かれています。"
        }
        val newRawBoard = List(length) { rawBoard[it].toMutableList() }
        newRawBoard[position.row][position.column] = piece
        return Board(newRawBoard)
    }

    fun reversePiece(position: Position): Board {
        check(rawBoard[position.row][position.column] != null) {
            "指定された場所には何も置かれていません。"
        }
        val newRawBoard = List(length) { rawBoard[it].toMutableList() }
        newRawBoard[position.row][position.column] = this[position]!!.reverse()
        return Board(newRawBoard)
    }

    override fun toString() = rawBoard.joinToString("\n")

    fun count(piece: Piece) = rawBoard.flatten().count { it == piece }

}
