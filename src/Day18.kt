@OptIn(ExperimentalStdlibApi::class)
fun main() {
    fun solveAll(input: List<String>): Long {
        var result = 0L
        var x = 0L
        var b = 0L
        for (line in input) {
            val (dir, dCh) = line.split(' ')
            val d = dCh.toInt()
            b += d
            when (dir[0]) {
                'U' -> result += x * d
                'D' -> result -= x * d
                'L' -> x -= d
                'R' -> x += d
            }
        }
        if (result < 0)
            result = -result
        result += b / 2 + 1
        return result
    }

    fun part1(input: List<String>): Long {
        return solveAll(input)
    }

    fun part2(input: List<String>): Long {
        val mapDir = mutableMapOf(
            0 to 'L',
            1 to 'D',
            2 to 'R',
            3 to 'U')
        return solveAll(input.map { line ->
            val s = line.split(' ')[2]
            mapDir[s[7].digitToInt()].toString() + ' ' + s.substring(2, 7).hexToInt().toString() + ' ' + s })
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day18_test")
    check(part1(testInput) == 62L)
    check(part2(testInput) == 952408144115L)

    val input = readInput("Day18")
    part1(input).println()
    part2(input).println()
}
