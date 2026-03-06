package io.coffeeride.service.api

import io.coffeeride.SpecDec
import io.coffeeride.model.gcs.Length
import io.coffeeride.util.distance.IDistanceCalculator

class SearchNearbyPlacesApiWrapperSpec extends SpecDec {

    SearchNearbyPlacesApiWrapper snp // testing impl not interface
    IDistanceCalculator dc

    def setup() {
        dc = Mock()
        snp = new SearchNearbyPlacesApiWrapper(dc)
    }

    def "GetLocationRestriction"() {
        when:
        def found = snp.getLocationRestriction(p0, p1)

        then:
        1 * dc.approxDistance(p0, p1) >> Length.fromMeters(expectedRadius)
        found.circle.radius == expectedRadius
        0 * _

        where:
        p0                              | p1                             | expectedRadius
        CLatLng(0, 0)                   | CLatLng(0, 0)                  | 0
        CLatLng(10, 0)                  | CLatLng(0, -10)                | 10
        CLatLng(37.420761, -122.081356) | CLatLng(37.41767, -122.079595) | 2
    }

}