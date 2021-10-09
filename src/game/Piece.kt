package game

enum class Piece {
    BLACK, WHITE;

    fun reverse() =
        when (this) {
            BLACK -> WHITE
            WHITE -> BLACK
        }

}
