fun main() {
    fun solveAll(input: List<String>, isPart1: Boolean): Long {
        val n = input.size
        val m = input[0].length
        val di = listOf(-1,  0, 0, 1)
        val dj = listOf( 0, -1, 1, 0)
        val uphill = listOf('v', '>', '<', '^')
        val downhill = listOf('^', '<', '>', 'v')
        var result = 0
        var cur = 0
        val vis: List<MutableList<Boolean>> = List(n) { MutableList(m) { false } }
        fun dfs(i: Int, j: Int, pi: Int, pj: Int) {
            cur++
            vis[i][j] = true
            if (i == n - 1 && j == m - 2) {
                vis[i][j] = false
                cur--
                result = maxOf(result, cur)
                return
            }
            var dirRange = 0..3
            if (isPart1 && input[i][j] in downhill) {
                val idx = downhill.indexOf(input[i][j])
                dirRange = idx..idx
            }
            for (dir in dirRange) {
                val ni = i + di[dir]
                val nj = j + dj[dir]
                if (!(ni == pi && nj == pj) && ni in input.indices && nj in input[0].indices && input[ni][nj] != '#') {
                    if (isPart1) {
                        if (input[ni][nj] != uphill[dir])
                            dfs(ni, nj, i, j)
                    } else {
                        if (!vis[ni][nj])
                            dfs(ni, nj, i, j)
                    }
                }
            }
            vis[i][j] = false
            cur--
        }
        dfs(0, 1, -1, -1)
        return result.toLong()
    }

    fun part1(input: List<String>): Long {
        return solveAll(input, true)
    }

    fun part2(input: List<String>): Long {
        return solveAll(input, false)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day23_test")
    check(part1(testInput) == 94L)
    check(part2(testInput) == 154L)

    val input = readInput("Day23")
    part1(input).println()
    part2(input).println()
}
