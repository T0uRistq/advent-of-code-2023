fun gcd(a: Long, b: Long): Long {
    return if (b == 0L) a else gcd(b, a % b)
}
fun main() {
    fun solveAll(input: List<String>, isPart1: Boolean): Long {
        val instr = input[0]
        val adj: MutableMap<String, MutableList<String>> = mutableMapOf()
        val pos: MutableList<String> = mutableListOf()
        for (i in 2 until input.size) {
            val u = input[i].substring(0, 3)
            val l = input[i].substring(7, 10)
            val r = input[i].substring(12, 15)
            adj[u] = mutableListOf()
            adj[u]?.add(l)
            adj[u]?.add(r)
            if (u.endsWith(if (isPart1) "AAA" else "A")) pos.add(u)
        }

        var t = 0
        var reached = 0
        val time: MutableList<Long> = MutableList(pos.size){0}
        while (true) {
            for (i in pos.indices) {
                pos[i] = adj[pos[i]]?.get(if (instr[t % instr.length] == 'R') 1 else 0)!!
                if (pos[i].endsWith(if (isPart1) "ZZZ" else "Z")) {
                    reached += if (time[i] == 0L) 1 else 0
                    time[i] = t + 1L
                    if (reached == pos.size) {
                        var lcm = 1L
                        for (ti in time)
                            lcm = (lcm * ti) / gcd(lcm, ti)
                        return lcm
                    }
                }
            }
            ++t
        }
    }
    fun part1(input: List<String>): Long {
        return solveAll(input, true)
    }

    fun part2(input: List<String>): Long {
        return solveAll(input, false)
    }

    // test if implementation meets criteria from the description, like:
    var testInput = readInput("Day08_test_p1")
    check(part1(testInput) == 6L)
    testInput = readInput("Day08_test_p2")
    check(part2(testInput) == 6L)

    val input = readInput("Day08")
    part1(input).println()
    part2(input).println()
}
