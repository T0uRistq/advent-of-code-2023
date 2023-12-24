import java.util.*

fun main() {
    fun solveAll(input: List<String>, steps: Long): Long {
        val di = listOf(-1,  0, 0, 1)
        val dj = listOf( 0, -1, 1, 0)
        val n = input.size
        val m = input[0].length

        check(input[0].all { it == '.' } && input.last().all { it == '.' } && input.all { it[0] == '.' && it.last() == '.' })
        check(n == m)
        val si = input.indexOfFirst { 'S' in it }
        val sj = input.single {'S' in it }.indexOf('S')
        check(2 * si + 1 == n)
        check(2 * sj + 1 == n)
        fun findDistances(input: List<String>, si: Int, sj: Int): List<List<Long>> {
            val dist: List<MutableList<Long>> = List(n) { MutableList(n) { -1 } }
            dist[si][sj] = 0
            val q: Queue<Pair<Int, Int>> = LinkedList()
            q.offer(Pair(si, sj))
            while (q.isNotEmpty()) {
                val (i, j) = q.poll()
                for (dir in 0..3) {
                    val ni = i + di[dir]
                    val nj = j + dj[dir]
                    if (ni in input.indices && nj in input[0].indices && input[ni][nj] != '#' && dist[ni][nj] == -1L) {
                        dist[ni][nj] = dist[i][j] + 1
                        q.offer(Pair(ni, nj))
                    }
                }
            }
            return dist
        }
        val d = findDistances(input, si, sj)
        val dUL = findDistances(input, 0, 0)
        val dUR = findDistances(input, 0, n - 1)
        val dDL = findDistances(input, n - 1, 0)
        val dDR = findDistances(input, n - 1, n - 1)
        val dU = findDistances(input, 0, sj)
        val dD = findDistances(input, n - 1, sj)
        val dL = findDistances(input, si, 0)
        val dR = findDistances(input, si, n - 1)

        var ans = d.flatten().count { it != -1L && it <= steps && it % 2 == steps % 2 }.toLong()
        for (i in 0..<n)
            for (j in 0..<n) {
                if (d[i][j] == -1L) continue

                val par = (si + sj + i + j + steps) % 2

                for (mx in listOf(
                    steps - d[n - 1][sj] - 1 - dU[i][j],
                    steps - d[0][sj]     - 1 - dD[i][j],
                    steps - d[si][n - 1] - 1 - dR[i][j],
                    steps - d[si][0]     - 1 - dL[i][j]
                )) {
                    if (mx < 0) continue
                    ans += (mx / n + 1 + par) / 2
                }

                for (mx in listOf(
                    steps - d[0][0]         - 2 - dDR[i][j],
                    steps - d[0][n - 1]     - 2 - dDL[i][j],
                    steps - d[n - 1][0]     - 2 - dUR[i][j],
                    steps - d[n - 1][n - 1] - 2 - dUL[i][j]
                )) {
                    if (mx < 0) continue
                    val x = mx / n - (mx / n + par) % 2 + 2
                    ans += (x + 1) / 2 * x / 2
                }
            }
        return ans
    }

    fun part1(input: List<String>): Long {
        return solveAll(input, 64L)
    }

    fun part2(input: List<String>): Long {
        return solveAll(input, 26501365L)
    }

    // test if implementation meets criteria from the description, like:
//    val testInput = readInput("Day21_test")
//    check(part1(testInput) == 16L)
//    check(part2(testInput) == 0L)

    val input = readInput("Day21")
    part1(input).println()
    part2(input).println()
}
