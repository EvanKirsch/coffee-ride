package org.kirsch

import org.kirsch.model.gcs.LatLng
import org.kirsch.model.gcs.Coordinate
import spock.lang.Specification

class SpecDec extends Specification {

    def "CLatLng"(double lat, double lng) {
        return LatLng.builder()
                .latitude(Coordinate.fromDegrees(lat))
                .longitude(Coordinate.fromDegrees(lng))
                .build()
    }

    def "IsWithinError"(double n0, double n1, double percentError) {
        n0 == 0 ? n0 = Double.MIN_VALUE : _ // prevent 0
        n1 == 0 ? n1 = Double.MIN_VALUE : _ // prevent 0
        return Math.abs(1 - (n0 / n1)) <= percentError
    }

}