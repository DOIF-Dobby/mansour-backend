package org.mansour.shared.domain.enums

enum class MarketType(
    val isDomestic: Boolean,
) {
    KOSPI(isDomestic = true),
    KOSDAQ(isDomestic = true),
    NASDAQ(isDomestic = false),
    ;

    val location: String
        get() = if (isDomestic) "domestic" else "overseas"
}
