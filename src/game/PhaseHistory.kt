package game

import java.util.Stack

class PhaseHistory {
    private val historyStack = Stack<Phase>()
    private val redoStack = Stack<Phase>()

    fun undo(): Phase {
        if (historyStack.size <= 1) throw NoSuchHistoryException("undoできません")
        redoStack.push(historyStack.pop())
        return historyStack.peek()
    }

    fun redo(): Phase {
        if (redoStack.isEmpty()) throw NoSuchHistoryException("redoできません")
        return historyStack.push(redoStack.pop())
    }


    fun add(phase: Phase) {
        historyStack.push(phase)
        redoStack.clear()
    }

    fun clear() {
        historyStack.clear()
        redoStack.clear()
    }
}

class NoSuchHistoryException(message: String? = null) : Exception(message)
