package com.example.packagefortracking.data

val dummyPackages = listOf(
    PackageItem(
        id = 1,
        name = "Shoes from China",
        trackingNumber = "RR123456789CN",
        postalService = PostalService.BH_POST,
        status = PackageStatus.IN_TRANSIT,
        lastUpdate = "10 Mar 2026",
        estimatedDelivery = "18 Mar 2026"
    ),
    PackageItem(
        id = 2,
        name = "Winter jacket",
        trackingNumber = "BA987654321HK",
        postalService = PostalService.HP_MOSTAR,
        status = PackageStatus.DELIVERED,
        lastUpdate = "15 Mar 2026",
        estimatedDelivery = "15 Mar 2026"
    ),
    PackageItem(
        id = 3,
        name = "Documents",
        trackingNumber = "RS552781204BA",
        postalService = PostalService.POSTA_SRPSKE,
        status = PackageStatus.OUT_FOR_DELIVERY,
        lastUpdate = "16 Mar 2026",
        estimatedDelivery = "17 Mar 2026"
    )
)