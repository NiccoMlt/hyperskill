data class Box(val height: Int, val length: Int, val width: Int) {
    override fun toString(): String {
        return "Box(height=$height, width=$width)"
    }
}

fun main() {
    val input = Scanner(System.`in`)
    val height = input.nextInt()
    val length = input.nextInt()
    val width = input.nextInt()

    val box = Box(height, length, width)

    println(box)
}
