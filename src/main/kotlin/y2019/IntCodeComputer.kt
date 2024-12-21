import java.util.concurrent.BlockingQueue
import java.util.concurrent.Callable
import java.util.concurrent.LinkedBlockingQueue

typealias Input = () -> Long
typealias Output = (Long) -> Unit

class IntCodeComputer(
    inpMemory: LongArray,
    var latestOutput: Long = -1
) : Callable<Long> {

    private val memory = LongArray(10000) { 0 }
    private val inputs = LinkedBlockingQueue<Long>()
    var outputComputer: IntCodeComputer? = null
    var outputArray: BlockingQueue<Long>? = null
    var input: Input? = null
    var output: Output? = null

    init {
        inpMemory.copyInto(memory)
    }

    fun addInput(vararg input: Long) {
        inputs.addAll(input.toList())
    }

    override fun call(): Long {
        var position = 0
        var relativeBase = 0

        do {
            val opcode = memory[position].toString().padStart(5, '0')
            val parsedOpcode = opcode.substring(opcode.lastIndex.dec()).toInt()
            val modes = mutableListOf(
                opcode[0].toString().toInt(),
                opcode[1].toString().toInt(),
                opcode[2].toString().toInt()
            )

            val inp1 = when (modes[2]) {
                0 -> memory.getOrElse(memory.getOrElse(position.inc()) { 0L }.toInt()) { 0L }
                1 -> memory.getOrElse(position.inc()) { 0L }
                2 -> memory.getOrElse(memory.getOrElse(position.inc()) { 0L }.toInt() + relativeBase) { 0L }
                else -> throw IllegalArgumentException("Fucked")
            }
            val inp2 = when (modes[1]) {
                0 -> memory.getOrElse(memory.getOrElse(position.inc().inc()) { 0L }.toInt()) { 0L }
                1 -> memory.getOrElse(position.inc().inc()) { 0L }
                2 -> memory.getOrElse(memory.getOrElse(position.inc().inc()) { 0L }.toInt() + relativeBase) { 0L }
                else -> throw IllegalArgumentException("Fucked")
            }
            val inp3 = when (modes[0]) {
                0, 1 -> memory.getOrElse(position.inc().inc().inc()) { 0L }
                2 -> memory.getOrElse(position.inc().inc().inc()) { 0L } + relativeBase
                else -> throw IllegalArgumentException("Fucked")
            }.toInt()

            when (parsedOpcode) {
                1 -> {
                    memory[inp3] = inp1 + inp2
                    position += 4
                }
                2 -> {
                    memory[inp3] = inp1 * inp2
                    position += 4
                }
                3 -> {
                    val out = when (modes[2]) {
                        0, 1 -> memory.getOrElse(position.inc()) { 0L }
                        2 -> memory.getOrElse(position.inc()) { 0L } + relativeBase
                        else -> throw IllegalArgumentException("Fucked")
                    }.toInt()
                    input?.let {
                        memory[out] = it.invoke()
                    } ?: kotlin.run { memory[out] = inputs.take() }
                    position += 2
                }
                4 -> {
                    latestOutput = inp1
                    output?.invoke(inp1)
                    outputComputer?.inputs?.add(inp1)
                    outputArray?.add(inp1)
                    position += 2
                }
                5 -> {
                    if (inp1 != 0L) {
                        position = inp2.toInt()
                    } else {
                        position += 3
                    }
                }
                6 -> {
                    if (inp1 == 0L) {
                        position = inp2.toInt()
                    } else {
                        position += 3
                    }
                }
                7 -> {
                    memory[inp3] = if (inp1 < inp2) 1L else 0L
                    position += 4
                }
                8 -> {
                    memory[inp3] = if (inp1 == inp2) 1L else 0L
                    position += 4
                }
                9 -> {
                    relativeBase += inp1.toInt()
                    position += 2
                }
                99 -> return latestOutput
                else -> throw IllegalStateException("Unknown opcode $opcode")
            }
        } while (true)
    }
}
