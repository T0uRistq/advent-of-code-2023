fun main() {
    fun solveAll(input: List<String>, isPart1: Boolean): Long {
        val seeds = input[0].substringAfter("seeds: ").split(" ").map { it.toLong() }.toTypedArray()

        val maps = mutableListOf<List<List<Long>>>()
        val curMap = mutableListOf<List<Long>>()

        for (line in input) {
            if (line.endsWith("map:")) {
                if (curMap.isNotEmpty()) {
                    maps.add(curMap.toList())
                    curMap.clear()
                }
            } else if (line.isNotBlank() && line.matches(Regex("\\d+(\\s\\d+)+"))) {
                curMap.add(line.split(" ").map { it.toLong() })
            }
        }

        if (curMap.isNotEmpty()) {
            maps.add(curMap.toList())
        }

        fun findRange(mapIdx: Int, cur: List<Pair<Long, Long>>): List<Pair<Long, Long>> {
            if (mapIdx == 7) return cur
            val applied = mutableListOf<Pair<Long, Long>>()
            var curSrc = cur.toMutableList()

            for ((dest, src, sz) in maps[mapIdx]) {
                val srcEnd = src + sz
                val newSrc = mutableListOf<Pair<Long, Long>>()

                while (curSrc.isNotEmpty()) {
                    val (st, ed) = curSrc.removeAt(0)

                    val prefix = Pair(st, minOf(ed, src))
                    val intersection = Pair(maxOf(st, src), minOf(srcEnd, ed))
                    val suffix = Pair(maxOf(srcEnd, st), ed)

                    if (prefix.second > prefix.first) {
                        newSrc.add(prefix)
                    }
                    if (intersection.second > intersection.first) {
                        applied.add(Pair(intersection.first - src + dest, intersection.second - src + dest))
                    }
                    if (suffix.second > suffix.first) {
                        newSrc.add(suffix)
                    }
                }
                curSrc = newSrc.toMutableList()
            }
            return findRange(mapIdx + 1, applied + curSrc)
        }

        if (isPart1)
            return seeds.minOf { findRange(0, listOf(Pair(it, it + 1))).minOf { it.first } }
        return (seeds.indices step 2)
            .map { findRange(0, listOf(Pair(seeds[it], seeds[it] + seeds[it + 1]))).minOf { it.first } }
            .min()
    }

    fun part1(input: List<String>): Long {
        return solveAll(input, true)
    }

    fun part2(input: List<String>): Long {
        return solveAll(input, false)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    check(part1(testInput) == 35L)
    check(part2(testInput) == 46L)

    val input = readInput("Day05")
    part1(input).println()
    part2(input).println()
}
