package io.coffeeride.service.api

import com.google.maps.GeoApiContext
import com.google.maps.model.GeocodingResult
import com.google.maps.model.Geometry
import com.google.maps.model.LatLng
import io.coffeeride.SpecDec
import io.coffeeride.service.api.proxy.IGeocodeApiProxy
import io.coffeeride.util.exception.CoffeeRideApiException

class GeocodeApiWrapperSpec extends SpecDec {

    IGeocodeApiProxy proxy
    GeocodeApiWrapper geo

    def setup() {
        proxy = Mock()
        geo = new GeocodeApiWrapper(proxy)
    }

    def "Geocode"() {
        given:
        def result = new GeocodingResult(
                geometry: new Geometry(
                        location: new LatLng(
                                lat: lat,
                                lng: lng
                        )
                )
        )

        when:
        def found = geo.geocode(address)

        then:
        1 * proxy.geocode(_ as GeoApiContext, address) >> [result].toArray()

        found.latitude.toDegrees() == lat
        found.longitude.toDegrees() == lng

        0 * _

        where:
        address   | lat | lng
        "address" | 10  | 20

    }

    def "Geocode - empty response"() {
        given:
        def address = "address"

        when:
        geo.geocode(address)

        then:
        1 * proxy.geocode(_ as GeoApiContext, address) >> [].toArray()
        thrown(CoffeeRideApiException)

    }

}

