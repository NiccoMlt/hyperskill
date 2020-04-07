import java.util.Scanner

data class Box(val height: Int, val length: Int, val width: Int) {
    var size: Int = height + length + width

    override fun toString(): String {
        return "Box(height=$height, width=$width, size=$size)"
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
