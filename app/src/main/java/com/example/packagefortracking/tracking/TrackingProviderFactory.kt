package com.example.packagefortracking.tracking

object TrackingProviderFactory {

    fun createProvider(
        postalService: String
    ): TrackingProvider {
        return when (postalService) {
            "BH Post" -> BhPostTrackingProvider()
            "HP Mostar" -> HpMostarTrackingProvider()
            "Pošta Srpske" -> PostaSrpskeTrackingProvider()
            else -> BhPostTrackingProvider()
        }
    }
}