fun main() {
    fun solveAll(input: List<String>, k: Long): Long {
        val idxX: MutableList<Int> = mutableListOf()
        val idxY: MutableList<Int> = mutableListOf()
        for (i in input.indices)
            for (j in input[i].indices)
                if (input[i][j] == '#') {
                    idxX.add(i)
                    idxY.add(j)
                }
        idxY.sort()
        var result = 0L
        for (idx in listOf(idxX, idxY)) {
            var sum = idx[0].toLong()
            var dist = 0L
            for (i in 1 until idx.size) {
                dist += (k - 1) * maxOf(idx[i] - idx[i - 1] - 1, 0)
                result += i * (idx[i] + dist) - sum
                sum += idx[i] + dist
            }
        }
        return result
    }
    fun part1(input: List<String>): Long {
        return solveAll(input, 2)
    }

    fun part2(input: List<String>): Long {
        return solveAll(input, 1000000)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day11_test")
    check(part1(testInput) == 374L)
//    check(part2(testInput) == 1030L)

    val input = readInput("Day11")
    part1(input).println()
    part2(input).println()
}
