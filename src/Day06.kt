fun main() {
    fun solveAll(input: List<String>, isPart1: Boolean): Long {
        val times = if (isPart1) {
            Regex("\\d+").findAll(input[0]).map { it.value.toLong() }.toList()
        } else {
            listOf(Regex("\\d+").findAll(input[0]).map { it.value }.joinToString("").toLong())
        }
        val distances = if (isPart1) {
            Regex("\\d+").findAll(input[1]).map { it.value.toLong() }.toList()
        } else {
            listOf(Regex("\\d+").findAll(input[1]).map { it.value }.joinToString("").toLong())
        }

        var result = 1L
        for (i in times.indices) {
            val integerHalfTime = times[i] shr 1
            var l = 0L; var r = integerHalfTime
            while (l < r) {
                val mid = (l + r) shr 1
                if (mid * (times[i] - mid) > distances[i])
                    r = mid
                else
                    l = mid + 1
            }
            result *= times[i] - (l shl 1) + 1
        }
        return result
    }
    fun part1(input: List<String>): Long {
        return solveAll(input, true)
    }

    fun part2(input: List<String>): Long {
        return solveAll(input, false)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    check(part1(testInput) == 288L)
    check(part2(testInput) == 71503L)

    val input = readInput("Day06")
    part1(input).println()
    part2(input).println()
}
