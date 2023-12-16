fun main() {
    val di = listOf( 0, 1, 0, -1)
    val dj = listOf(-1, 0, 1,  0)
    fun solveAll(input: List<String>, si: Int, sj: Int, sd: Int): Long {
        val vis: List<List<MutableList<Boolean>>> = List(input.size) { List(input[0].length ) { MutableList(4) { false } } }
        val q: MutableList<List<Int>> = mutableListOf()
        q.add(listOf(si, sj, sd))
        vis[si][sj][sd] = true
        while (q.isNotEmpty()) {
            val (i, j, d) = q.last()
            q.removeLast()
            when (input[i][j]) {
                '.' -> {
                    val ni = i + di[d]
                    val nj = j + dj[d]
                    if (ni in input.indices && nj in input[0].indices && !vis[ni][nj][d]) {
                        vis[ni][nj][d] = true
                        q.add(listOf(ni, nj, d))
                    }
                }
                '/' -> {
                    val nd = d + 1 - 2 * (d % 2)
                    val ni = i + di[nd]
                    val nj = j + dj[nd]
                    if (ni in input.indices && nj in input[0].indices && !vis[ni][nj][nd]) {
                        vis[ni][nj][nd] = true
                        q.add(listOf(ni, nj, nd))
                    }
                }
                '\\' -> {
                    val nd = 3 - d
                    val ni = i + di[nd]
                    val nj = j + dj[nd]
                    if (ni in input.indices && nj in input[0].indices && !vis[ni][nj][nd]) {
                        vis[ni][nj][nd] = true
                        q.add(listOf(ni, nj, nd))
                    }
                }
                else -> {
                    if (d % 2 == if (input[i][j] == '-') 1 else 0) {
                        for (diff in listOf(1, 3)) {
                            val nd = (d + diff) % 4
                            val ni = i + di[nd]
                            val nj = j + dj[nd]
                            if (ni in input.indices && nj in input[0].indices && !vis[ni][nj][nd]) {
                                vis[ni][nj][nd] = true
                                q.add(listOf(ni, nj, nd))
                            }
                        }
                    } else {
                        val ni = i + di[d]
                        val nj = j + dj[d]
                        if (ni in input.indices && nj in input[0].indices && !vis[ni][nj][d]) {
                            vis[ni][nj][d] = true
                            q.add(listOf(ni, nj, d))
                        }
                    }
                }
            }
        }

        var result = 0L
        for (i in input.indices)
            for (j in input[0].indices)
                for (d in 0..3)
                    if (vis[i][j][d]) {
                        result++
                        break
                    }
        return result
    }

    fun part1(input: List<String>): Long {
        return solveAll(input, 0, 0, 2)
    }

    fun part2(input: List<String>): Long {
        var result = 0L
        for (i in input.indices)
            result = maxOf(maxOf(result, solveAll(input, i, 0, 2)), solveAll(input, i, input[0].length - 1, 0))
        for (j in input[0].indices)
            result = maxOf(maxOf(result, solveAll(input, 0, j, 1)), solveAll(input, input.size - 1, j, 3))
        return result
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day16_test")
    check(part1(testInput) == 46L)
    check(part2(testInput) == 51L)

    val input = readInput("Day16")
    part1(input).println()
    part2(input).println()
}
