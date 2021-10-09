package game

import game.board.Position
import game.move.ValidMoveSpecification
import java.text.Normalizer

var blackCircle = '●'
var whiteCircle = '○'
var blackStar = '★'
var whiteStar = '☆'

fun invertChar() {
    if (blackCircle == '●') {
        blackCircle = '○'
        whiteCircle = '●'
        blackStar = '☆'
        whiteStar = '★'

    } else {
        blackCircle = '●'
        whiteCircle = '○'
        blackStar = '★'
        whiteStar = '☆'
    }
}

fun Piece.toChar() = when (this) {
    Piece.BLACK -> blackCircle
    Piece.WHITE -> whiteCircle
}

fun Game.printBoard() {
    val validMoves = this.validMoves
    val previousMove = this.previousMove
    println()
    println("　ＡＢＣＤＥＦＧＨ")
    for (row in 0..7) {
        print('１' + row)
        for (col in 0..7) {
            val currentPosition = Position(row, col)
            val char = when {
                validMoves.contains(currentPosition) -> '＋'
                previousMove?.position == currentPosition -> {
                    when (previousMove.piece) {
                        Piece.BLACK -> blackStar
                        Piece.WHITE -> whiteStar
                    }
                }
                else -> this.board[currentPosition]?.toChar() ?: '・'
            }
            print(char)

        }
        println()
    }
    println()
}

fun Game.printResult() {
    val blackCount = this.count(Piece.BLACK)
    val whiteCount = this.count(Piece.WHITE)
    println("${Piece.BLACK.toChar()} $blackCount  ${Piece.WHITE.toChar()} $whiteCount")
    when {
        blackCount > whiteCount -> println("「${Piece.BLACK.toChar()}」の勝ち！")
        blackCount < whiteCount -> println("「${Piece.WHITE.toChar()}」の勝ち！")
        else -> println("引き分け！")
    }
}


// p、<、>、置ける場所 どれかを入力させて返す
fun input(validMoves: List<Position>): String {
    while (true) {
        print("置く場所 (${validMoves.joinToString()}): ")
        // 半角にする
        val input = Normalizer.normalize(readLine(), Normalizer.Form.NFKD) ?: throw AssertionError()
        when (input) {
            "" -> continue
            "P", "p" -> return "P"
            "<", ">" -> return input
            "I", "i" -> return "I"
        }
        if (input.matches(Regex("[A-Ha-h][1-8]"))) {
            val position = Position(input)
            if (validMoves.contains(position)) return input
            println("${position}には置けません。")
            println("「＋」の場所（${validMoves.joinToString()}）にのみ置けます。")
        } else {
            println("置く場所、または以下のコマンドを入力してください。")
            println("P ・・・ パス")
            println("< ・・・ 一手戻る")
            println("> ・・・ 一手進む")
            println("I ・・・ 表示する石の色を反転する")
        }
    }
}


fun main() {
    val game = Game(GameService(ValidMoveSpecification()))
    game.printBoard()
    println("「${game.currentTurn.toChar()}」の番です。")

    while (true) {
        when (val input = input(game.validMoves)) {
            "" -> continue
            "P" -> game.pass()
            "<" -> {
                try {
                    game.undo()
                } catch (_: NoSuchHistoryException) {
                    println("履歴がありません。")
                    continue
                }
                if (game.validMoves.isEmpty()) {
                    game.undo()
                    println("一つ前の状態では「${game.currentTurn.reverse().toChar()}」に置ける場所がなくパスしたので、さらに前の状態にしました。")
                }
            }
            ">" -> {
                try {
                    game.redo()
                } catch (_: NoSuchHistoryException) {
                    println("履歴がありません。")
                    continue
                }
                if (!game.isOver && game.validMoves.isEmpty()) {
                    game.redo()
                    println("一手進めても「${game.currentTurn.reverse().toChar()}」には置ける場所がなかったため、パスした後の状態にしました。")
                }
            }
            "I" -> invertChar()
            else -> game.place(Position(input))
        }


        if (game.isOver) {
            game.printBoard()
            game.printResult()

            print("もう一度遊びますか？(Y/N): ")
            when (readLine() ?: AssertionError()) {
                "Y", "y" -> {
                    game.reset()
                }
                "<" -> game.undo()
                else -> return
            }
        }

        game.printBoard()
        if (game.validMoves.isEmpty()) {
            println("「${game.currentTurn.toChar()}」は置ける場所がないため、パスします。")
            game.pass()
            game.printBoard()
        }
        println("「${game.currentTurn.toChar()}」の番です。")
    }
}
