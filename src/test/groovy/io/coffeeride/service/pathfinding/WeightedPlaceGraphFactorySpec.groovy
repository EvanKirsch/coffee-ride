package io.coffeeride.service.pathfinding

import io.coffeeride.SpecDec
import io.coffeeride.adaptors.GooglePlaceAdaptor;
import io.coffeeride.model.gcs.Length;
import io.coffeeride.model.gcs.LatLng;
import io.coffeeride.util.distance.IDistanceCalculator;
import org.springframework.core.convert.ConversionService;
import spock.lang.Ignore

class WeightedPlaceGraphFactorySpec extends SpecDec {

    IDistanceCalculator dc
    ConversionService cs

    WeightedPlaceGraphFactory wpgf

    def setup() {
        dc = Mock()
        cs = Mock()
        wpgf = new WeightedPlaceGraphFactory(dc, cs)
    }

    def "CreateGraph"() {
        given:
        def latLng = CLatLng(3,3)
        def distanceToTerminus = 5.5
        def distanceToStart = 7.7

        when:
        def found = wpgf.createGraph(places, origin, target)

        then:
        1 * dc.approxDistance(origin, target) >> Length.fromMeters(200.0)
        places.size() * cs.convert(places[0].getLocation(), LatLng.class) >> latLng
        places.size() * dc.approxDistance(latLng, target) >> Length.fromMeters(distanceToTerminus)
        places.size() * dc.approxDistance(latLng, origin) >> Length.fromMeters(distanceToStart)
        
        found != null
        found.getNodes().size() == 1
        found.getNodes()[0].getPlace() == places[0]
        found.getNodes()[0].getDistanceToTerminus() == distanceToTerminus
        found.getNodes()[0].getDistanceToStart() == distanceToStart

        0 * _

        where:
        places                                                    | origin                         | target
        [new GooglePlaceAdaptor(CLatLng(0, 0))]                   | CLatLng(0, 0)                  | CLatLng(0,0)
        [new GooglePlaceAdaptor(CLatLng(10, 0))]                  | CLatLng(0, -10)                | CLatLng(10, 10)
        [new GooglePlaceAdaptor(CLatLng(37.420761, -122.081356))] | CLatLng(37.41767, -122.079595) | CLatLng(2, 2)
    }

}