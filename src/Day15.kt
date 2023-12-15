fun main() {
    val mod = 256
    val p = 17
    fun hash(s: String): Int {
        var hash = 0
        for (ch in s)
            hash = (hash + ch.code) * p % mod
        return hash
    }
    fun part1(input: List<String>): Long {
        var result = 0L
        for (s in input[0].split(','))
            result += hash(s)
        return result
    }

    fun part2(input: List<String>): Long {
        var seqNum = 0
        val hMaps: List<HashMap<String, Pair<Int, Int>>> = List(mod) { hashMapOf() }
        for (s in input[0].split(',')) {
            val (label, num) = s.split('-', '=')
            val i = hash(label)
            if (num.isEmpty()) {
                hMaps[i].remove(label)
            } else {
                val oldP = hMaps[i][label]
                if (hMaps[i].containsKey(label) && oldP != null)
                    hMaps[i][label] = Pair(oldP.first, num.toInt())
                else
                    hMaps[i][label] = Pair(seqNum++, num.toInt())
            }
        }

        var result = 0L
        for (i in 0..<mod) {
            var l: MutableList<Pair<Int, Int>> = mutableListOf()
            for (e in hMaps[i])
                l.add(e.value)
            l = l.sortedWith(compareBy({ it.first }, { it.second })).toMutableList()
            for (j in l.indices) {
                result += (i + 1) * (j + 1) * l[j].second
            }
        }
        return result
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day15_test")
    check(part1(testInput) == 1320L)
    check(part2(testInput) == 145L)

    val input = readInput("Day15")
    part1(input).println()
    part2(input).println()
}
