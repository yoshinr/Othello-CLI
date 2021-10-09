package game.board

import java.util.Objects

class Position {
    val row: Int
    val column: Int

    constructor(position: String) {
        val uPosition = position.uppercase()
        require(uPosition.matches(Regex("[A-H][1-8]"))) {
            "A1のように2文字で指定してください"
        }
        column = uPosition[0] - 'A'
        row = uPosition[1] - '1'
    }

    constructor(row: Int, column: Int) {
        require(row in 0 until Board.length) { "rowは0から7の範囲でなければなりません。" }
        require(column in 0 until Board.length) { "columnは0から7の範囲でなければなりません。" }
        this.row = row
        this.column = column
    }

    override fun toString(): String {
        Objects.hash()
        return "${'A' + column}${'1' + row}"
    }


    override fun equals(other: Any?): Boolean =
        other is Position && row == other.row && column == other.column

    override fun hashCode(): Int = Objects.hash(row, column)
}
