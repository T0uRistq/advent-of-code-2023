fun main() {
    fun solveAll(input: List<String>): Long {
        var result = 0L
        for (line in input) {
            val s = line.split(' ').first() + '.'
            val a = line.split(' ').last().split(',').map { it.toInt() }
            val n = s.length
            val prefixCnt: MutableList<Int> = MutableList(n + 1) { 0 }
            for (i in 0..<n)
                prefixCnt[i + 1] = prefixCnt[i] + (if (s[i] == '.') 1 else 0)
            val dp: MutableList<Long> = MutableList(n + 1) { 1 }
            for (i in 0..<n)
                dp[i + 1] = if (s[i] != '#') dp[i] else 0

            for (el in a) {
                for (r in n - 1 downTo el)
                    dp[r + 1] = if (prefixCnt[r - el] == prefixCnt[r] && s[r] != '#') dp[r - el] else 0
                for (i in 0..el)
                    dp[i] = 0
                for (i in 0..<n)
                    dp[i + 1] += if (s[i] != '#') dp[i] else 0
            }
            result += dp.last()
        }
        return result
    }
    fun part1(input: List<String>): Long {
        return solveAll(input)
    }

    fun part2(input: List<String>): Long {
        val modInput: MutableList<String> = mutableListOf()
        for (line in input) {
            val (s, a) = line.split(' ')
            val newS = (List(4) { s } + s).joinToString("?")
            val newA = (List(4) { a } + a).joinToString(",")
            modInput.add("$newS $newA")
        }
        return solveAll(modInput)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day12_test")
    check(part1(testInput) == 21L)
    check(part2(testInput) == 525152L)

    val input = readInput("Day12")
    part1(input).println()
    part2(input).println()
}
