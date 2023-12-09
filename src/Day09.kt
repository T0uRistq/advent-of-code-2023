fun main() {
    fun solveAll(input: List<String>, isPart1: Boolean): Long {
        var res = 0L
        for (line in input) {
            var a = line.split(" ").map { it.toLong() }.toList()
            var diff = a[if (isPart1) a.lastIndex else 0]
            var pos = false
            while (!a.all { it == 0L }) {
                val newA: MutableList<Long> = mutableListOf()
                for (i in 1 until a.size)
                    newA.add(a[i] - a[i - 1])
                a = newA
                val elem = a[if (isPart1) a.lastIndex else 0]
                diff += if (isPart1 || pos) elem else -elem
                pos = !pos
            }
            res += diff
        }
        return res
    }
    fun part1(input: List<String>): Long {
        return solveAll(input, true)
    }

    fun part2(input: List<String>): Long {
        return solveAll(input, false)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day09_test")
    check(part1(testInput) == 114L)
    check(part2(testInput) == 2L)

    val input = readInput("Day09")
    part1(input).println()
    part2(input).println()
}
