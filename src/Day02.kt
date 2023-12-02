fun main() {
    fun part1(input: List<String>): Int {
        val targetLimits = mapOf("red" to 12, "green" to 13, "blue" to 14)

        var res = 0
        input.map { game ->
            val gameID = game.substringAfter("Game ").substringBefore(":")
            val gameInfo = game.split(";", ":").map { it.trim() }

            val validGame = gameInfo.drop(1).all { subset ->
                subset.split(",").all { cubeInfo ->
                    val (count, color) = cubeInfo.trim().split(" ")
                    count.toInt() <= targetLimits[color]!!
                }
            }

            if (validGame) res += gameID.toInt()
        }
        return res
    }

    fun part2(input: List<String>): Int {
        var res = 0
        input.map { game ->
            val targetLimits = mutableMapOf("red" to 0, "green" to 0, "blue" to 0)

            val gameInfo = game.split(";", ":").map { it.trim() }
            gameInfo.drop(1).forEach { subset ->
                subset.split(",").forEach { cubeInfo ->
                    val (count, color) = cubeInfo.trim().split(" ")
                    targetLimits[color] = targetLimits[color]!!.coerceAtLeast(count.toInt())
                }
            }

            res += targetLimits["red"]!! * targetLimits["blue"]!! * targetLimits["green"]!!
        }
        return res
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 8)
    check(part2(testInput) == 2286)

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}
