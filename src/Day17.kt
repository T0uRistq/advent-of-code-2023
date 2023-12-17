import java.util.TreeSet

const val INF = 1 shl 16

fun main() {
    val di = listOf( 0, 1, 0, -1)
    val dj = listOf(-1, 0, 1,  0)
    data class MyList(
        val i: Int,
        val j: Int,
        val dir: Int,
        val moves: Int,
        val curVal: Int
    ) : Comparable<MyList> {
        override fun compareTo(other: MyList): Int {
            if (this.i != other.i) {
                return this.i.compareTo(other.i)
            }
            if (this.j != other.j) {
                return this.j.compareTo(other.j)
            }
            if (this.dir != other.dir) {
                return this.dir.compareTo(other.dir)
            }
            if (this.moves != other.moves) {
                return this.moves.compareTo(other.moves)
            }
            return this.curVal.compareTo(other.curVal)
        }
    }
    fun solveAll(input: List<String>, atL: Int, atM: Int): Long {
        val dp: List<List<List<MutableList<Int>>>> = List(input.size) { List(input[0].length) { List(4) { MutableList(atM + 1) { INF } } } }
        dp[0][0][2][0] = 0
        dp[0][0][1][0] = 0
        val q: TreeSet<MyList> = TreeSet()
        q.add(MyList(0, 0, 2, 0, 0))
        q.add(MyList(0, 0, 1, 0, 0))
        while (!q.isEmpty()) {
            val u = q.first()
            q.remove(u)
            val (i, j, d, m) = u

            if (m < atM) {
                val ni = i + di[d]
                val nj = j + dj[d]
                if (ni in input.indices && nj in input[0].indices && dp[ni][nj][d][m + 1] > dp[i][j][d][m] + input[ni][nj].digitToInt()) {
                    q.remove(MyList(ni, nj, d, m + 1, dp[ni][nj][d][m + 1]))
                    dp[ni][nj][d][m + 1] = dp[i][j][d][m] + input[ni][nj].digitToInt()
                    q.add(MyList(ni, nj, d, m + 1, dp[ni][nj][d][m + 1]))
                }
            }
            if (m >= atL) {
                for (o in listOf(1, 3)) {
                    val nd = (d + o) % 4
                    val ni = i + di[nd]
                    val nj = j + dj[nd]
                    if (ni in input.indices && nj in input[0].indices && dp[ni][nj][nd][1] > dp[i][j][d][m] + input[ni][nj].digitToInt()) {
                        q.remove(MyList(ni, nj, nd, 1, dp[ni][nj][nd][1]))
                        dp[ni][nj][nd][1] = dp[i][j][d][m] + input[ni][nj].digitToInt()
                        q.add(MyList(ni, nj, nd, 1, dp[ni][nj][nd][1]))
                    }
                }
            }
        }
        var result = INF
        for (d in 0..3)
            for (m in atL..atM)
                result = minOf(result, dp[input.size - 1][input[0].length - 1][d][m])
        return result.toLong()
    }

    fun part1(input: List<String>): Long {
        return solveAll(input, 0, 3)
    }

    fun part2(input: List<String>): Long {
        return solveAll(input, 4, 10)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day17_test")
    check(part1(testInput) == 102L)
    check(part2(testInput) == 94L)

    val input = readInput("Day17")
    part1(input).println()
    part2(input).println()
}
