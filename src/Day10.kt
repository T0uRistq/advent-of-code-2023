fun main() {
    fun solveAll(input: List<String>): List<Pair<Int, Int>> {
        val sX = input.indexOfFirst { 'S' in it }
        val sY = input[sX].indexOfFirst { it == 'S' }

        val dx = listOf(-1,  0, 0, 1)
        val dy = listOf( 0, -1, 1, 0)
        val symbol = mapOf(
            'F' to listOf(2, 3),
            'J' to listOf(0, 1),
            '7' to listOf(1, 3),
            'L' to listOf(0, 2),
            '-' to listOf(1, 2),
            '|' to listOf(0, 3)
        )

        for (i in dx.indices) {
            val loop: MutableList<Pair<Int, Int>> = mutableListOf()
            var dir = i
            var x = sX
            var y = sY
            while (true) {
                loop.add(Pair(x, y))
                x += dx[dir]
                y += dy[dir]

                if (x !in input.indices || y !in input[0].indices || input[x][y] !in symbol) break
                val newDirMap = symbol[input[x][y]]!!
                var idx = -1
                if (dx[dir] == -dx[newDirMap[0]] && dy[dir] == -dy[newDirMap[0]])
                    idx = 1
                if (dx[dir] == -dx[newDirMap[1]] && dy[dir] == -dy[newDirMap[1]])
                    idx = 0
                if (idx == -1) break
                dir = newDirMap[idx]
            }
            if (x in input.indices && y in input[0].indices && input[x][y] == 'S')
                return loop
        }
        assert(false)
        return emptyList()
    }
    fun part1(input: List<String>): Int {
        return solveAll(input).size / 2
    }

    fun part2(input: List<String>): Int {
        val loop = solveAll(input)
        var area = loop[0].first * (loop[loop.lastIndex].second - loop[0].second)
        for (i in 1 until loop.size)
            area += loop[i].first * (loop[i - 1].second - loop[i].second)
        return area + 1 - loop.size / 2
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day10_test")
    check(part1(testInput) == 8)
    check(part2(testInput) == 1)

    val input = readInput("Day10")
    part1(input).println()
    part2(input).println()
}
