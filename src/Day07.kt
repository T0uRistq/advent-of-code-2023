fun main() {
    fun solveAll(input: List<String>, isPart1: Boolean): Long {
        var cards = input.map { line ->
            val (first, second) = line.split(" ")
            mutableListOf("", first, second)
        }
        for (i in cards.indices) {
            val e = cards[i]
            val counterMap = e[1].groupingBy { it }.eachCount().toMutableMap()
            var jokers = 0
            if (!isPart1 && counterMap.containsKey('J') && counterMap['J'] != 5)
                jokers = counterMap.remove('J')!!
            val counterValues = counterMap.values.toList().sorted().toMutableList()
            counterValues[counterValues.lastIndex] += jokers
            when (counterValues) {
                listOf(1, 1, 1, 1, 1)   -> e[0] = "0"
                listOf(1, 1, 1, 2)      -> e[0] = "1"
                listOf(1, 2, 2)         -> e[0] = "2"
                listOf(1, 1, 3)         -> e[0] = "3"
                listOf(2, 3)            -> e[0] = "4"
                listOf(1, 4)            -> e[0] = "5"
                listOf(5)               -> e[0] = "6"
            }
        }

        val cardStrengths = mapOf(
            "2" to 2,
            "3" to 3,
            "4" to 4,
            "5" to 5,
            "6" to 6,
            "7" to 7,
            "8" to 8,
            "9" to 9,
            "T" to 10,
            "J" to if (isPart1) 11 else 1,
            "Q" to 12,
            "K" to 13,
            "A" to 14
        )
        class MyComp: Comparator<List<String>> {
            override fun compare(e1: List<String>, e2: List<String>): Int {
                if (e1[0] == e2[0]) {
                    for (i in 0 until 5) {
                        if (cardStrengths[e1[1][i].toString()]!! < cardStrengths[e2[1][i].toString()]!!)
                            return -1
                        if (cardStrengths[e1[1][i].toString()]!! > cardStrengths[e2[1][i].toString()]!!)
                            return 1
                    }
                }
                return if (e1[0] < e2[0]) -1 else 1
            }
        }
        cards = cards.sortedWith(MyComp())
        var result = 0L
        for (i in cards.indices)
            result += (i + 1) * cards[i][2].toInt()
        return result
    }
    fun part1(input: List<String>): Long {
        return solveAll(input, true)
    }

    fun part2(input: List<String>): Long {
        return solveAll(input, false)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    check(part1(testInput) == 6440L)
    check(part2(testInput) == 5905L)

    val input = readInput("Day07")
    part1(input).println()
    part2(input).println()
}
