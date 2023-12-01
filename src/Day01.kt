fun main() {
    fun part1(input: List<String>): Int {
        var res = 0
        input.forEach { i ->
            res += i.first{ it.isDigit() }.toString().toInt() * 10 + i.last{ it.isDigit() }.toString().toInt()
        }
        return res
    }

    fun part2(input: List<String>): Int {
        val spelledOutDigitMap = mapOf(
                "zero" to 0,
                "one" to 1,
                "two" to 2,
                "three" to 3,
                "four" to 4,
                "five" to 5,
                "six" to 6,
                "seven" to 7,
                "eight" to 8,
                "nine" to 9
        )

        return input.sumOf { line ->
            val spelledOutDigits = spelledOutDigitMap.keys

            var first = -1
            var last = -1

            for (index in line.indices) {
                for (spelledOutDigit in spelledOutDigits) {
                    if (line.startsWith(spelledOutDigit, index)) {
                        last = spelledOutDigitMap[spelledOutDigit]!!
                        if (first == -1)
                            first = last
                    }

                    if (line[index].isDigit()) {
                        last = line[index].toString().toInt()
                        if (first == -1)
                            first = last
                    }
                }
            }

            first * 10 + last
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput_p1 = readInput("Day01_test_p1")
    check(part1(testInput_p1) == 142)
    val testInput_p2 = readInput("Day01_test_p2")
    check(part2(testInput_p2) == 281)

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
