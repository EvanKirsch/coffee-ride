package io.coffeeride.service.pathfinding;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

import io.coffeeride.adaptors.PlaceAdaptor;
import io.coffeeride.model.PathfindingRequest;
import io.coffeeride.model.RouteDetails;
import io.coffeeride.model.WeightedPlaceGraph;
import io.coffeeride.model.gcs.Coordinate;
import io.coffeeride.model.gcs.LatLng;
import io.coffeeride.model.gcs.Length;
import io.coffeeride.service.api.IGeocodeApiWrapper;
import io.coffeeride.service.api.IRoutesApiWrapper;
import io.coffeeride.service.api.ISearchNearbyPlacesApiWrapper;
import io.coffeeride.util.distance.IDistanceCalculator;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.convert.ConversionService;

@ExtendWith(MockitoExtension.class)
class SdtPathFinderTest {

  private ISearchNearbyPlacesApiWrapper searchPlacesWrapper;
  private IRoutesApiWrapper routesApiWrapper;
  private EdgeCalculator edgeCalculator;
  private IPlaceGraphFactory graphFactory;
  private IGeocodeApiWrapper geocodeApiWrapper;
  private IDistanceCalculator distanceCalculator;
  private ConversionService conversionService;

  private SdtPathFinder sdtPathFinder;

  @BeforeEach
  void beforeEach() {
    this.searchPlacesWrapper = mock(ISearchNearbyPlacesApiWrapper.class);
    this.routesApiWrapper = mock(IRoutesApiWrapper.class);
    this.edgeCalculator = mock(EdgeCalculator.class);
    this.graphFactory = mock(IPlaceGraphFactory.class);
    this.geocodeApiWrapper = mock(IGeocodeApiWrapper.class);
    this.distanceCalculator = mock(IDistanceCalculator.class);
    this.conversionService = mock(ConversionService.class);

    this.sdtPathFinder = new SdtPathFinder(
        searchPlacesWrapper,
        routesApiWrapper,
        edgeCalculator,
        graphFactory,
        geocodeApiWrapper,
        distanceCalculator,
        conversionService
    );
  }

  // @Test - I don't like junit
  void testRoute() {
    PathfindingRequest pathfindingRequest = PathfindingRequest.builder()
        .orgAddress("orgAddress")
        .dstAddress("dstAddress")
        .step(Length.fromMeters(10))
        .build();

    LatLng p0 = new LatLng(Coordinate.fromDegrees(1), Coordinate.fromDegrees(1));
    LatLng p1 = new LatLng(Coordinate.fromDegrees(2), Coordinate.fromDegrees(2));
    LatLng target = new LatLng(Coordinate.fromDegrees(3), Coordinate.fromDegrees(3));
    List<PlaceAdaptor> places = spy(List.class);
    WeightedPlaceGraph graph = spy(WeightedPlaceGraph.class);

    try {

      Mockito.when(geocodeApiWrapper.geocode(pathfindingRequest.getOrgAddress()))
          .thenReturn(p0);

      Mockito.when(geocodeApiWrapper.geocode(pathfindingRequest.getDstAddress()))
          .thenReturn(p1);

      Mockito.when(distanceCalculator.findNextTarget(p0, p1, pathfindingRequest.getStep()))
          .thenReturn(target);

      Mockito.when(searchPlacesWrapper.searchNearby(p0, target))
          .thenReturn(places);

      Mockito.when(graphFactory.createGraph(places, p0, target))
          .thenReturn(graph);

    } catch (Exception e) {
      fail("Failed to create mocks with Exception: " + e.getMessage());
    }

    try {
      RouteDetails found = sdtPathFinder.buildRoute(pathfindingRequest);
    } catch (Exception e) {
      fail("Failed to run test with Exception: " + e.getMessage());
    }

  }

}