import java.util.LinkedList
import java.util.Queue

interface Module {
    val destinationModuleNames: List<String>

    fun reset()
    fun processPulse(pulse: Pulse, inputModule: String)
}

enum class Pulse {
    HIGH,
    LOW
}

fun main() {

    fun solveAll(input: List<String>, isPart1: Boolean): Long {
        val modules: MutableMap<String, Module> = mutableMapOf()
        val q: Queue<Pair<String, Pair<Pulse, String>>> = LinkedList()
        var lowPulses = 0L
        var highPulses = 0L
        class FlipFlop(private val name: String, override val destinationModuleNames: List<String>) : Module {
            private var on: Boolean = false

            override fun reset() {
                on = false
            }

            override fun processPulse(pulse: Pulse, inputModule: String) {
                when (pulse) {
                    Pulse.LOW -> {
                        on = !on
                        destinationModuleNames.forEach { moduleName ->
                            q.offer(Pair(moduleName, Pair(if (on) Pulse.HIGH else Pulse.LOW, name)))
                        }
                    }
                    Pulse.HIGH -> {
                    }
                }
            }
        }
        class Conjunction(private val name: String, override val destinationModuleNames: List<String>) : Module {
            private val inputPulses: MutableMap<String, Pulse> = mutableMapOf()

            override fun reset() {
                inputPulses.forEach { inputPulses[it.key] = Pulse.LOW }
            }

            override fun processPulse(pulse: Pulse, inputModule: String) {
                inputPulses[inputModule] = pulse
                if (inputPulses.all { it.value == Pulse.HIGH }) {
                    destinationModuleNames.forEach { moduleName ->
                        q.offer(Pair(moduleName, Pair(Pulse.LOW, name)))
                    }
                } else {
                    destinationModuleNames.forEach { moduleName ->
                        q.offer(Pair(moduleName, Pair(Pulse.HIGH, name)))
                    }
                }
            }
        }
        class Broadcaster(private val name: String, override val destinationModuleNames: List<String>) : Module {
            override fun reset() {
            }

            override fun processPulse(pulse: Pulse, inputModule: String) {
                destinationModuleNames.forEach { moduleName ->
                    q.offer(Pair(moduleName, Pair(pulse, name)))
                }
            }
        }
        for (line in input) {
            val parts = line.split(" -> ")

            var moduleName = parts.first()
            val destinationModuleNames = parts.last().split(", ")

            val module = when {
                moduleName.startsWith("&") -> {
                    moduleName = moduleName.substring(1)
                    Conjunction(moduleName, destinationModuleNames)
                }
                moduleName.startsWith("%") -> {
                    moduleName = moduleName.substring(1)
                    FlipFlop(moduleName, destinationModuleNames)
                }
                else -> Broadcaster(moduleName, destinationModuleNames)
            }
            modules[moduleName] = module
        }
        if (isPart1) {
            fun pushButton() {
                q.offer(Pair("broadcaster", Pair(Pulse.LOW, "button")))
                while (q.isNotEmpty()) {
                    val (moduleName, pr) = q.poll()
                    val (pulse, inputModule) = pr
                    if (pulse == Pulse.HIGH) highPulses++ else lowPulses++
                    if (modules.containsKey(moduleName))
                        modules[moduleName]!!.processPulse(pulse, inputModule)
                }
            }
            repeat(1000) {
                pushButton()
            }
            highPulses = 0
            lowPulses = 0
            for (module in modules)
                module.value.reset()
            repeat(1000) {
                pushButton()
            }
            return lowPulses * highPulses
        }
        val grandp = modules.filter { (_, module) -> "rx" in module.destinationModuleNames }
            .keys.singleOrNull()
            ?.let { sourceModule ->
                modules.filter { (_, module) -> sourceModule in module.destinationModuleNames }.keys
            }!!
        val len: MutableMap<String, Int> = mutableMapOf()
        var t = 0
        fun pushButton() {
            q.offer(Pair("broadcaster", Pair(Pulse.LOW, "button")))
            while (q.isNotEmpty()) {
                val (moduleName, pr) = q.poll()
                val (pulse, inputModule) = pr
                if (moduleName in grandp && pulse == Pulse.LOW)
                    len.putIfAbsent(moduleName, t)
                if (modules.containsKey(moduleName))
                    modules[moduleName]!!.processPulse(pulse, inputModule)
            }
        }
        repeat(100000) {
            t++
            pushButton()
        }
        t = 0
        len.clear()
        for (module in modules)
            module.value.reset()
        while (len.size < grandp.size) {
            t++
            pushButton()
        }
        var result = 1L
        for (pr in len) {
            println(pr)
            result = pr.value * result / gcd(pr.value.toLong(), result)
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
    val testInput = readInput("Day20_test")
    check(part1(testInput) == 11687500L)
//    check(part2(testInput) == 0L)

    val input = readInput("Day20")
    part1(input).println()
    part2(input).println()
}
