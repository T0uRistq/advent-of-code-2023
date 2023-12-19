const val XMAS = "xmas"
const val MX_VAL = 4000
fun main() {
    fun solveAll(input: List<String>, isPart1: Boolean): Long {
        var result = 0L
        var workflowsStart = false
        val workflows: MutableList<List<Int>> = mutableListOf()
        val rules: MutableMap<String, List<List<String>>> = mutableMapOf()
        for (line in input) {
            val parts = line.split("{", "}", ":", ",")
            if (parts.size < 2) {
                workflowsStart = true
                continue
            }
            if (workflowsStart) {
                val cur: MutableList<Int> = mutableListOf()
                for (i in 1..parts.size - 2)
                    cur.add(parts[i].split('=').last().toInt())
                workflows.add(cur)
            } else {
                val cur: MutableList<List<String>> = mutableListOf()
                for (i in 1..parts.size - 3 step 2)
                    cur.add(listOf(parts[i][0].toString(), parts[i][1].toString(), parts[i].substring(2), parts[i + 1]))
                cur.add(listOf("x", ">", "0", parts[parts.size - 2]))
                rules[parts[0]] = cur
            }
        }
        if (isPart1) {
            fun dfs(u: String, xmas: List<Int>) {
                if (u == "A") {
                    result += xmas.sum()
                    return
                }
                if (u == "R") return
                for (rule in rules[u]!!) {
                    val i = XMAS.indexOf(rule[0][0])
                    if ((xmas[i] - rule[2].toInt()) * (if (rule[1] == ">") 1 else -1) > 0) {
                        dfs(rule[3], xmas)
                        return
                    }
                }
            }
            for (workflow in workflows)
                dfs("in", workflow)
        } else {
            fun dfs(u: String): List<List<Pair<Int, Int>>> {
                if (u == "A") return  listOf(List(4) { Pair(1, MX_VAL + 1) })
                if (u == "R") return listOf()

                fun Pair<Int, Int>.intersectRanges(other: Pair<Int, Int>): Pair<Int, Int> {
                    val l = maxOf(this.first, other.first)
                    val r = minOf(this.second, other.second)
                    return if (l < r) Pair(l, r) else Pair(1, 1)
                }
                val cur: MutableList<Pair<Int, Int>> = MutableList(4) { Pair(1, MX_VAL + 1) }
                val ret: MutableList<List<Pair<Int, Int>>> = mutableListOf()
                for (rule in rules[u]!!) {
                    val i = XMAS.indexOf(rule[0][0])
                    val thisCondition = cur[i].intersectRanges(if (rule[1] == ">") Pair(rule[2].toInt() + 1, MX_VAL + 1) else Pair(1, rule[2].toInt()))
                    val nextCondition = cur[i].intersectRanges(if (rule[1] == ">") Pair(1, rule[2].toInt() + 1) else Pair(rule[2].toInt(), MX_VAL + 1))
                    cur[i] = thisCondition
                    for (cond in dfs(rule[3])) {
                        if (cond.isEmpty()) continue
                        val ranges: MutableList<Pair<Int, Int>> = MutableList(4) { Pair(1, MX_VAL + 1) }
                        for (j in 0..3) {
                            val l = maxOf(cond[j].first, cur[j].first)
                            val r = minOf(cond[j].second, cur[j].second)
                            ranges[j] = if (l < r) Pair(l, r) else Pair(1, 1)
                        }
                        ret.add(ranges)
                    }
                    cur[i] = nextCondition
                }
                return ret
            }
            val ranges = dfs("in")
            for (range in ranges) {
                if (range.isEmpty()) continue
                var cur = 1L
                for (pr in range)
                    cur *= pr.second - pr.first
                result += cur
            }
        }
        return result
    }

    fun part1(input: List<String>): Long {
        return solveAll(input, true)
    }

    fun part2(input: List<String>): Long {
        return solveAll(input, false)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day19_test")
    check(part1(testInput) == 19114L)
    check(part2(testInput) == 167409079868000L)

    val input = readInput("Day19")
    part1(input).println()
    part2(input).println()
}
