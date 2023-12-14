fun main() {
    fun solveAll(input: List<String>): Long {
        var result = 0L
        for (j in input[0].indices) {
            var seen = 0
            for (i in input.indices) {
                if (input[i][j] == 'O')
                    result += input.size - seen++
                if (input[i][j] == '#')
                    seen = i + 1
            }
        }
        return result
    }
    fun part1(input: List<String>): Long {
        return solveAll(input)
    }

    fun part2(input: List<String>): Long {
        val goal = 1000000000
        val mutInput: MutableList<MutableList<Char>> = mutableListOf()
        for (line in input)
            mutInput.add(line.toMutableList())

        val states: MutableList<List<String>> = mutableListOf()

        val idxOf: HashMap<String, Int> = hashMapOf()

        for (ti in 0..<goal) {
            // go north
            for (j in mutInput[0].indices) {
                var seen = 0
                for (i in mutInput.indices) {
                    if (mutInput[i][j] == 'O') {
                        mutInput[i][j] = '.'
                        mutInput[seen++][j] = 'O'
                    }
                    if (mutInput[i][j] == '#')
                        seen = i + 1
                }
            }
            // go west
            for (i in mutInput.indices) {
                var seen = 0
                for (j in mutInput[0].indices) {
                    if (mutInput[i][j] == 'O') {
                        mutInput[i][j] = '.'
                        mutInput[i][seen++] = 'O'
                    }
                    if (mutInput[i][j] == '#')
                        seen = j + 1
                }
            }
            // go south
            for (j in mutInput[0].indices) {
                var seen = mutInput.size - 1
                for (i in mutInput.size - 1 downTo 0) {
                    if (mutInput[i][j] == 'O') {
                        mutInput[i][j] = '.'
                        mutInput[seen--][j] = 'O'
                    }
                    if (mutInput[i][j] == '#')
                        seen = i - 1
                }
            }
            // go east
            for (i in mutInput.indices) {
                var seen = mutInput[0].size - 1
                for (j in mutInput[0].size - 1 downTo 0) {
                    if (mutInput[i][j] == 'O') {
                        mutInput[i][j] = '.'
                        mutInput[i][seen--] = 'O'
                    }
                    if (mutInput[i][j] == '#')
                        seen = j - 1
                }
            }
            var cur = ""
            for (line in mutInput) {
                for (ch in line)
                    cur += ch
            }
            val theHash = cur.md5()
            if (idxOf.containsKey(theHash)) {
                val st = idxOf[theHash]!!
                val len = ti - st
                val toGo = (goal - 1 - ti) % len
                val sta = states[st + toGo]
                var result = 0L
                for (i in sta.indices)
                    for (j in sta[0].indices)
                        if (sta[i][j] == 'O')
                            result += sta.size - i
                return result
            }
            idxOf[theHash] = ti
            val newInput = mutInput.map { chars ->
                chars.joinToString("")
            }
            states.add(newInput)
        }
        return 0L
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day14_test")
    check(part1(testInput) == 136L)
    check(part2(testInput) == 64L)

    val input = readInput("Day14")
    part1(input).println()
    part2(input).println()
}
