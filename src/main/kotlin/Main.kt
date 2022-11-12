fun main() {
    printInfo()
    println("How many mines do you want on the field?")
    val mineCount = readln().toInt()
    val game = Minesweeper(mineCount)
    game.printGUI()
    var firstInit = false
    while (true) {
        println("Set/unset mine marks or claim a cell as free:")
        val (n1, n2, c)= readln().split(" ")
        val a = n1.toInt() - 1
        val b = n2.toInt() - 1

        if (!firstInit) {
            firstInit = true
            game.initBoard(a, b)
        }
        when (c){
            "free" -> game.openCell(a, b)
            "mine" -> if (game.getGUI(a, b) == MARK) game.setGUI(a, b, EMPTY) else game.setGUI(a, b, MARK)
            "exit" -> break
            else -> {
                println("Incorrect input!")
                continue
            }
        }

        if (game.isBomb(a, b)) {
            println("You stepped on a mine and failed!")
            game.showMines()
            break
        }

        if (game.isWin(mineCount)){
            println("Congratulations! You found all the mines!")
            break
        }
        game.printGUI()
    }

}