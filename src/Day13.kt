fun main() {
    fun solveAll(input: List<String>, isPart1: Boolean): Long {
        val pattern: MutableList<String> = mutableListOf()
        var result = 0L
        for (line in input + listOf("")) {
            if (line.isEmpty()) {
                // try rows
                val hashes: MutableList<String> = mutableListOf()
                for (i in 0..<pattern.size)
                    hashes.add(pattern[i].md5())
                // try to place before that index
                for (i in 1..<pattern.size) {
                    var badCnt = 0
                    var lastBad = -1
                    for (j in 0..minOf(i - 1, pattern.size - i - 1))
                        if (hashes[i - j - 1] != hashes[i + j]) {
                            badCnt++
                            lastBad = j
                        }

                    if (isPart1) {
                        if (badCnt == 0)
                            result += 100 * i
                    } else {
                        if (badCnt == 1) {
                            var diff = 0
                            for (j in 0..<pattern[0].length)
                                if (pattern[i - lastBad - 1][j] != pattern[i + lastBad][j])
                                    diff++
                            if (diff == 1)
                                result += 100 * i
                        }
                    }

                }

                // try columns
                hashes.clear()
                for (i in 0..<pattern[0].length) {
                    var s = ""
                    for (j in 0..<pattern.size)
                        s += pattern[j][i]
                    hashes.add(s.md5())
                }
                // try to place before that index
                for (i in 1..<pattern[0].length) {
                    var badCnt = 0
                    var lastBad = -1
                    for (j in 0..minOf(i - 1, pattern[0].length - i - 1))
                        if (hashes[i - j - 1] != hashes[i + j]) {
                            badCnt++
                            lastBad = j
                        }

                    if (isPart1) {
                        if (badCnt == 0)
                            result += i
                    } else {
                        if (badCnt == 1) {
                            var diff = 0
                            for (j in 0..<pattern.size)
                                if (pattern[j][i - lastBad - 1] != pattern[j][i + lastBad])
                                    diff++
                            if (diff == 1)
                                result += i
                        }
                    }
                }

                pattern.clear()
            } else {
                pattern.add(line)
            }
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
    val testInput = readInput("Day13_test")
    check(part1(testInput) == 405L)
    check(part2(testInput) == 400L)

    val input = readInput("Day13")
    part1(input).println()
    part2(input).println()
}
