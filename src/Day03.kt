fun main() {
    fun part1(input: List<String>): Int {
        val rows = input.size
        val cols = input[0].length

        val dx = listOf(-1, -1, -1,  0, 0,  1, 1, 1)
        val dy = listOf(-1,  0,  1, -1, 1, -1, 0, 1)

        var sum = 0
        for (row in 0 until rows) {
            for (col in 0 until cols) {
                if (input[row][col].isDigit()) {
                    if (col > 0 && input[row][col - 1].isDigit()) continue

                    var isAdjacentToSymbol = false
                    var num = 0
                    for (curCol in col until cols) {
                        if (!input[row][curCol].isDigit()) break
                        num = num * 10 + input[row][curCol].digitToInt()
                        for (dir in 0 until 8) {
                            val newRow = row + dx[dir]
                            val newCol = curCol + dy[dir]

                            if (newRow in 0 until rows && newCol in 0 until cols) {
                                if (!input[newRow][newCol].isDigit() && input[newRow][newCol] != '.')
                                    isAdjacentToSymbol = true
                            }
                        }
                    }

                    if (isAdjacentToSymbol) {
                        sum += num
                    }
                }
            }
        }

        return sum
    }

    fun part2(input: List<String>): Int {
        val rows = input.size
        val cols = input[0].length

        val dx = listOf(-1, -1, -1,  0, 0,  1, 1, 1)
        val dy = listOf(-1,  0,  1, -1, 1, -1, 0, 1)

        val gearRatio = mutableMapOf<Int, Int>()
        val gearNum = mutableMapOf<Int, Int>()

        for (row in 0 until rows) {
            for (col in 0 until cols) {
                if (input[row][col].isDigit()) {
                    if (col > 0 && input[row][col - 1].isDigit()) continue

                    var num = 0
                    for (curCol in col until cols) {
                        if (!input[row][curCol].isDigit()) break
                        num = num * 10 + input[row][curCol].digitToInt()
                    }

                    for (curCol in col until cols) {
                        if (!input[row][curCol].isDigit()) break
                        var found = false
                        for (dir in 0 until 8) {
                            val newRow = row + dx[dir]
                            val newCol = curCol + dy[dir]

                            if (newRow in 0 until rows && newCol in 0 until cols) {
                                if (input[newRow][newCol] == '*') {
                                    if (gearRatio.containsKey(newRow * cols + newCol)) gearRatio[newRow * cols + newCol] =
                                        gearRatio[newRow * cols + newCol]!! * num
                                    else gearRatio[newRow * cols + newCol] = num
                                    if (gearNum.containsKey(newRow * cols + newCol)) gearNum[newRow * cols + newCol] =
                                        gearNum[newRow * cols + newCol]!! + 1
                                    else gearNum[newRow * cols + newCol] = 1
                                    found = true
                                }
                            }
                        }
                        if (found) break
                    }
                }
            }
        }

        var sum = 0
        for (row in 0 until rows) {
            for (col in 0 until cols) {
                if (gearNum[row * cols + col] == 2)
                    sum += gearRatio[row * cols + col]!!
            }
        }
        return sum
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 4361)
    check(part2(testInput) == 467835)

    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}
