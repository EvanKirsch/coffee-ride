package org.kirsch.util.distance

import org.kirsch.model.gcs.Length
import org.kirsch.SpecDec

class SphereDistanceCalculatorSpec extends SpecDec {

    SphereDistanceCalculator sdc // testing impl not interface

    def setup() {
        DistanceCalculatorFactory dcf = new SphereDistanceCalculatorFactory()
        sdc = dcf.getCalculator()
    }

    def "ApproxDistance"() {
        when:
        def found = sdc.approxDistance(p0, p1).toMeters()

        then:
        IsWithinError(found, expected, 0.01)
        0 * _

        where:
        p0                                              | p1                                               | expected
        CLatLng(38.897778, -77.036389)                  | CLatLng(48.858222, 2.2945)                       | (3828.64) * 1609.34
        CLatLng(0.0, 0.0)                               | CLatLng(0.0, 0.0)                                | (0.0) * 1609.34
        CLatLng(35.66145292768596, -117.6372638312428)  | CLatLng(39.55882733378412, -88.63335914189523)   | (1615.45) * 1609.34
        CLatLng(49.48572475342304, -86.26031239458499)  | CLatLng(34.29321860183609, -86.52398425539724)   | (1057.73) * 1609.34
        CLatLng(39.96420120463865, -111.13335725965388) | CLatLng(-5.9825189108786265, -38.53570308106527) | (5561.26) * 1609.34
        CLatLng(49.14194338968351, 43.20257811218381)   | CLatLng(-19.98919078065685, -64.02398245355315)  | (8040.90) * 1609.34
    }

    def "HaversineImpl"() {
        when:
        def found = sdc.haversineImpl(p0, p1).toMeters()

        then:
        IsWithinError(found, expected, 0.03)
        0 * _

        where:
        p0                                              | p1                                               | expected
        CLatLng(38.897778, -77.036389)                  | CLatLng(48.858222, 2.2945)                       | (3828.64) * 1609.34
        CLatLng(0.0, 0.0)                               | CLatLng(0.0, 0.0)                                | (0.0) * 1609.34
        CLatLng(35.66145292768596, -117.6372638312428)  | CLatLng(39.55882733378412, -88.63335914189523)   | (1615.45) * 1609.34
        CLatLng(49.48572475342304, -86.26031239458499)  | CLatLng(34.29321860183609, -86.52398425539724)   | (1057.73) * 1609.34
        CLatLng(39.96420120463865, -111.13335725965388) | CLatLng(-5.9825189108786265, -38.53570308106527) | (5561.26) * 1609.34
        CLatLng(49.14194338968351, 43.20257811218381)   | CLatLng(-19.98919078065685, -64.02398245355315)  | (8040.90) * 1609.34
    }

    def "RelativeGapDist"() {
        when:
        def found = sdc.relativeGapDist(p0, p1, Length.fromMeters(1000)).toMeters()

        then:
        IsWithinError(found, expected, 0.03)
        0 * _

        where:
        p0                                              | p1                                               | expected
        CLatLng(38.897778, -77.036389)                  | CLatLng(48.858222, 2.2945)                       | 750
        CLatLng(0.0, 0.0)                               | CLatLng(0.0, 0.0)                                | 0      // no vector exists
        CLatLng(35.66145292768596, -117.6372638312428)  | CLatLng(39.55882733378412, -88.63335914189523)   | 795
        CLatLng(49.48572475342304, -86.26031239458499)  | CLatLng(34.29321860183609, -86.52398425539724)   | 1000
        CLatLng(39.96420120463865, -111.13335725965388) | CLatLng(-5.9825189108786265, -38.53570308106527) | 968
        CLatLng(49.14194338968351, 43.20257811218381)   | CLatLng(-19.98919078065685, -64.02398245355315)  | 977
    }

    def "ApproxCenter"() {
        when:
        def found = sdc.getCenter(p0, p1)

        then:
        found.equals(expected)
        0 * _

        where:
        p0                               | p1                               | expected
        CLatLng(0, 0)                    | CLatLng(0, 0)                    | CLatLng(0, 0)
        CLatLng(10, 0)                   | CLatLng(0, -10)                  | CLatLng(5, -5)
        CLatLng(37.420761, -122.081356)  | CLatLng(37.41767, -122.079595)   | CLatLng(37.4192155, -122.0804755)
    }

    def "FindNextTarget"() {
        when:
        def found = sdc.findNextTarget(p0, p1, Length.fromMeters(10000))

        then:
        found == expected
        0 * _

        where:
        p0              | p1              | expected
        CLatLng(0, 0)   | CLatLng(0, 0)   | CLatLng(0, 0)
        CLatLng(10, 0)  | CLatLng(0, -10) | CLatLng(9.936366957722056, -0.06363304227794352)
        CLatLng(10, 10) | CLatLng(0, 0)   | CLatLng(9.936366957722056, 9.936366957722056)
        CLatLng(10, 10) | CLatLng(0, 10)  | CLatLng(9.910067839408127, 10.0)
    }

}