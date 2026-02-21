package org.kirsch.util.distance

import org.kirsch.SpecDec

class FlatDistanceCalculatorSpec extends SpecDec {

    FlatDistanceCalculator fdc // testing impl not interface

    def setup() {
        DistanceCalculatorFactory dcf = new FlatDistanceCalculatorFactory()
        fdc = dcf.getCalculator()
    }

    def "ApproxDistance"() {
        when:
        def found = fdc.approxDistance(p0, p1)

        then:
        IsWithinError(found, expected, 0.01) // allow significant error
        0 * _

        where:
        p0                                                 | p1                                               | expected
        CLatLng(0, 0)                                      | CLatLng(0, 0)                                    | 0.0
        CLatLng(10, 0)                                     | CLatLng(0, -10)                                  | 1571348.2455019322
        CLatLng(37.420761, -122.081356)                    | CLatLng(37.41767, -122.079595)                   | 395.27142524625106
        CLatLng(38.897778, -77.036389)                     | CLatLng(48.858222, 2.2945)                       | 8883747.899219226
        CLatLng(35.66145292768596, -117.6372638312428)     | CLatLng(39.55882733378412, -88.63335914189523)   | 3251620.380874653
        CLatLng(49.48572475342304, -86.26031239458499)     | CLatLng(34.29321860183609, -86.52398425539724)   | 1688310.2804167832
        CLatLng(39.96420120463865, -111.13335725965388)    | CLatLng(-5.9825189108786265, -38.53570308106527) | 9546196.324348636
    }

}