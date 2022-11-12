fun printInfo() {
    println("""===Minesweeper===
        |if you want exit - write "exit"
        |if you want open cell - write coordinates and keyword free (Example: 2 5 free)
        |if you want to mark mine - write coordinates and keyword mine (Example: 3 6 mine)
        |
    """.trimMargin())
}

fun Minesweeper.isValid(a: Int, b: Int): Boolean {
    return this.getBoard(a, b) == MINE || this.getBoard(a, b) == FREE
}

fun Array<Array<String>>.copy2D(h: Int, w: Int): Array<Array<String>> {
    val newArr = Array(h) { Array(w) {""} }
    for (i in this.indices) {
        newArr[i] = this[i].copyOf()
    }
    return newArr
}
