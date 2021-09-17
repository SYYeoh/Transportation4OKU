package com.example.transportation4oku.oku

import com.google.firebase.firestore.GeoPoint

data class LocationClass(var ID: String?=null, var Name: String?=null, var Coordinate: GeoPoint? = null)
