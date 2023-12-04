fun main() {
    fun part1(input: List<String>): Int {
        var sum = 0

        for ((i, line) in input.withIndex()) {
            val (first, rest) = line.split('|')
            val (id, card) = first.split(':')
            val winning = card.split(Regex("\\s+"))
            val weHave = rest.split(Regex("\\s+"))
            val res = (winning.toSet() intersect weHave.toSet()).size - 1

            if (res > 0)
                sum += 1 shl (res - 1)
        }
        return sum
    }

    fun part2(input: List<String>): Int {
        var sum = 0
        val copiesNum = mutableMapOf<Int, Int>()

        for ((i, line) in input.withIndex()) {
            copiesNum[i] = copiesNum.getOrDefault(i, 0) + 1
            sum += copiesNum[i]!!

            val (first, rest) = line.split('|')
            val (id, card) = first.split(':')
            val winning = card.split(Regex("\\s+"))
            val weHave = rest.split(Regex("\\s+"))
            val res = (winning.toSet() intersect weHave.toSet()).size - 1

            for (j in 0 until res)
                copiesNum[i + 1 + j] = copiesNum.getOrDefault(i + 1 + j, 0) + copiesNum[i]!!
        }
        return sum
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 13)
    check(part2(testInput) == 30)

    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}
