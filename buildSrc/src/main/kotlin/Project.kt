@file:Suppress("unused")

object Project {

    val versions = Versions
    val dependencies = Dependencies

    object Versions {

        const val buildTools = "28.0.3"

        val sdk = SDK

        object SDK {
            const val compile = 28
            const val target = 28
            const val min = 15
        }
    }
}
