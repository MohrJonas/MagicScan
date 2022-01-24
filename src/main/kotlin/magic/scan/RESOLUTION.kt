package magic.scan

enum class RESOLUTION {

    RESOLUTION_100,
    RESOLUTION_200,
    RESOLUTION_300,
    RESOLUTION_400,
    RESOLUTION_600;

    companion object {
        fun asNumber(resolution: RESOLUTION) = when (resolution) {
            RESOLUTION_100 -> 100
            RESOLUTION_200 -> 200
            RESOLUTION_300 -> 300
            RESOLUTION_400 -> 400
            RESOLUTION_600 -> 600
        }

        fun fromNumber(number: Int) = when (number) {
            100 -> RESOLUTION_100
            200 -> RESOLUTION_200
            300 -> RESOLUTION_300
            400 -> RESOLUTION_400
            600 -> RESOLUTION_600
            else -> throw IllegalArgumentException("Unexpected number $number")
        }
    }

}
