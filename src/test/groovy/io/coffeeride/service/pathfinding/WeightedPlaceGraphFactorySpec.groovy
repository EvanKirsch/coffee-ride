package io.coffeeride.service.pathfinding

import io.coffeeride.SpecDec
import io.coffeeride.util.distance.IDistanceCalculator;
import org.springframework.core.convert.ConversionService;
import spock.lang.Ignore

@Ignore // TODO
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
        when:
        def found = wpgf.createGraph(places, origin, target)

        then:
        found == null

        where:
        places                            | origin                         | target
        [CLatLng(0, 0)]                   | CLatLng(0, 0)                  | CLatLng(0,0)
        [CLatLng(10, 0)]                  | CLatLng(0, -10)                | CLatLng(10, 10)
        [CLatLng(37.420761, -122.081356)] | CLatLng(37.41767, -122.079595) | CLatLng(2, 2)
    }

}