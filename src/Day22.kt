import java.util.*

fun main() {
    fun solveAll(input: List<String>, isPart1: Boolean): Long {
        val array = input.map { line ->
            line.split("~").flatMap { part -> part.split(",") }.map { it.toInt() }
        }
        val n = array.size
        val sortedArray = array.sortedBy { minOf(it[2], it[5]) }

        val underXY: List<MutableList<Pair<Int, Int>>> = List(10) { MutableList(10) { Pair(0, -1) } }
        val mustHave: TreeSet<Int> = TreeSet()
        val par: List<MutableList<Int>> = List(n) { mutableListOf() }
        val chd: List<MutableList<Int>> = List(n) { mutableListOf() }
        for (i in sortedArray.indices) {
            val brick = sortedArray[i]
            val (x, y, z) = brick.subList(0, 3)
            val customComparator = compareBy<Pair<Int, Int>> { it.first }.thenBy { it.second }
            val curUnder: TreeSet<Pair<Int, Int>> = TreeSet(customComparator)
            if (x != brick[3]) {
                for (j in x..brick[3])
                    curUnder.add(underXY[j][y])
                val mxZ = curUnder.last().first
                val iterator = curUnder.iterator()
                while (iterator.hasNext()) {
                    val e = iterator.next()
                    if (e.first < mxZ) {
                        iterator.remove()
                    }
                }
                for (e in curUnder)
                    if (e.second != -1) {
                        par[e.second].add(i)
                        chd[i].add(e.second)
                    }
                if (curUnder.size == 1 && curUnder.first().second != -1)
                    mustHave.add(curUnder.first().second)

                for (j in x..brick[3])
                    underXY[j][y] = Pair(mxZ + 1, i)
                continue
            }
            if (y != brick[4]) {
                for (j in y..brick[4])
                    curUnder.add(underXY[x][j])
                val mxZ = curUnder.last().first
                val iterator = curUnder.iterator()
                while (iterator.hasNext()) {
                    val e = iterator.next()
                    if (e.first < mxZ) {
                        iterator.remove()
                    }
                }
                for (e in curUnder)
                    if (e.second != -1) {
                        par[e.second].add(i)
                        chd[i].add(e.second)
                    }
                if (curUnder.size == 1 && curUnder.first().second != -1)
                    mustHave.add(curUnder.first().second)

                for (j in y..brick[4])
                    underXY[x][j] = Pair(mxZ + 1, i)
                continue
            }
            if (underXY[x][y].second != -1) {
                par[underXY[x][y].second].add(i)
                chd[i].add(underXY[x][y].second)
                mustHave.add(underXY[x][y].second)
            }
            underXY[x][y] = Pair(underXY[x][y].first + (brick[5] - z + 1), i)
        }
        if (isPart1)
            return n - mustHave.size.toLong()

        var ans = 0L
        for (i in 0..<n) {
            val q: Queue<Int> = LinkedList()
            val cnt: MutableList<Int> = MutableList(n) { 0 }
            var cur = -1L
            q.offer(i)
            while (!q.isEmpty()) {
                val u = q.poll()
                cur++
                for (v in par[u]) {
                    cnt[v]++
                    if (cnt[v] == chd[v].size)
                        q.offer(v)
                }
            }
            ans += cur
        }
        return ans
    }

    fun part1(input: List<String>): Long {
        return solveAll(input, true)
    }

    fun part2(input: List<String>): Long {
        return solveAll(input, false)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day22_test")
    check(part1(testInput) == 5L)
    check(part2(testInput) == 7L)

    val input = readInput("Day22")
    part1(input).println()
    part2(input).println()
}
