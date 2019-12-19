import java.util.Random

object GradleUtils {
    private val r = Random(System.currentTimeMillis())

    fun getString(string: String): String {
        val b = string.toByteArray()
        val c = b.size
        val sb = StringBuilder()

        sb.append("(new Object() {")
        sb.append("int t;")
        sb.append("public String toString() {")
        sb.append("byte[] buf = new byte[")
        sb.append(c)
        sb.append("];")

        for (i in 0 until c) {
            var t = r.nextInt()
            val f = r.nextInt(24) + 1

            t = t and (0xff shl f).inv() or (b[i].toInt() shl f)

            sb.append("t = ")
            sb.append(t)
            sb.append(";")
            sb.append("buf[")
            sb.append(i)
            sb.append("] = (byte) (t >>> ")
            sb.append(f)
            sb.append(");")
        }

        sb.append("return new String(buf);")
        sb.append("}}.toString())")

        return sb.toString()
    }
}