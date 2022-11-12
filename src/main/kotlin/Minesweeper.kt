import java.lang.Exception

const val EMPTY = "."
const val MINE = "X"
const val MARK = "*"
const val FREE = "/"

class Minesweeper(val numMine: Int) {

    private var gameBoard = Array(9) { Array(9) { FREE } }
    fun getBoard(a: Int, b: Int): String = gameBoard[b][a]

    fun initBoard(n1: Int, n2: Int) {
        initMine(numMine, n1, n2)
        searchMine()
    }

    private var gameBoardGUI = gameBoard.copy2D(9, 9)
    fun getGUI(a: Int, b: Int): String = gameBoardGUI[b][a]
    fun setGUI(a: Int, b: Int, type: String = MARK) {
        gameBoardGUI[b][a] = type
    }

    init {
        initGUI()
    }


    private fun initMine(numOfMine: Int, n1: Int, n2: Int) {
        var count = 0
        while (count != numOfMine) {
            val r1 = randomNum()
            val r2 = randomNum()
            if (gameBoard[r1][r2] != MINE && !(r1 == n2 && r2 == n1)) {
                gameBoard[r1][r2] = MINE
                count++
            }
        }
    }

    private fun initGUI() {
        for(i in gameBoardGUI.indices) {
            for (j in gameBoardGUI[i].indices) {
                //if (gameBoardGUI[i][j] == MINE) gameBoardGUI[i][j] = EMPTY
                gameBoardGUI[i][j] = EMPTY
            }
        }
    }

    private fun randomNum(n1: Int = 0, n2: Int = 8) = (n1..n2).random()

    //print MAIN board
    fun printBoard() {
        println(" │123456789│")
        println("—│—————————│")
        for (i in gameBoard.indices) {
            print("${i + 1}│")
            for (j in gameBoard[i].indices) {
                print(gameBoard[i][j])
            }
            println("|")
        }
        println("—│—————————│\n")
    }

    fun isBomb(a: Int, b: Int): Boolean = getBoard(a, b) == MINE && getGUI(a, b) == FREE

    //print GUI board
    fun printGUI() {
        println(" │123456789│")
        println("—│—————————│")
        for (i in gameBoardGUI.indices) {
            print("${i + 1}│")
            for (j in gameBoardGUI[i].indices) {
                print(gameBoardGUI[i][j])
            }
            println("|")
        }
        println("—│—————————│")
    }

    fun openCell(a: Int, b: Int) {
        when {
            getBoard(a, b) == MINE -> {
                setGUI(a, b, FREE)
            }
            getBoard(a, b) == FREE -> {
                setGUI(a, b, FREE)
                openCells()
            }
            else -> setGUI(a, b, getBoard(a, b))
        }

    }


    private fun openCells() {
        repeat(2) {
            for (i in gameBoardGUI.indices) {
                for (j in gameBoardGUI[i].indices) {
                    if (gameBoardGUI[i][j] == FREE) {
                        openFree(i, j)
                    }
                }
            }
            for (i in gameBoardGUI.indices.reversed()) {
                for (j in gameBoardGUI[i].indices.reversed()) {
                    if (gameBoardGUI[i][j] == FREE) {
                        openFree(i, j)
                    }
                }
            }
        }
    }

    private fun openFree(n1: Int, n2: Int) {
        for (i in n1 - 1..n1 + 1){
            for (j in n2 - 1..n2 + 1){
                try {
                    gameBoardGUI[i][j] = gameBoard[i][j]
                } catch (_: Exception) {
                }
            }
        }
    }

    private fun searchMine() {
        for(i in gameBoard.indices) {
            for (j in gameBoard[i].indices) {
                if (!gameBoard[i][j].isMine()) {
                    if (countMines(i,j) != 0) gameBoard[i][j] = countMines(i, j).toString()
                }
            }
        }
    }

    private fun String.isMine(): Boolean = this == MINE

    fun isWin(mines: Int): Boolean {
        var count = mines
        for(i in gameBoard.indices) {
            for (j in gameBoard[i].indices) {
                if (gameBoardGUI[i][j] == MARK && gameBoard[i][j] != MINE) {
                    return false
                } else if (gameBoardGUI[i][j] == MARK && gameBoard[i][j] == MINE ) {
                    count--
                }
            }
        }
        return count == 0
    }

    private fun countMines(n1: Int, n2: Int): Int {
        var count = 0
        for (i in n1 - 1..n1 + 1){
            for (j in n2 - 1..n2 + 1){
                try {
                    if (gameBoard[i][j].isMine()) count++
                } catch (_: Exception) {
                }
            }
        }
        return count
    }

    fun showMines() {
        for(i in gameBoard.indices) {
            for (j in gameBoard[i].indices) {
                if (gameBoard[i][j].isMine()) {
                    gameBoardGUI[i][j] = MINE
                }
            }
        }
        printGUI()
    }
}