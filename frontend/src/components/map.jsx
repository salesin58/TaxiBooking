import React, { useState, useEffect } from "react";
import {
  GoogleMap,
  MarkerF,
  useJsApiLoader,
  OverlayView,
  DirectionsRenderer,
  DistanceMatrixService,
} from "@react-google-maps/api";
import currentLocation from "../images/currentLoc.png";
import location from "../images/location.png";

const containerStyle = {
  width: "100%",
  height: "calc(100vh - 100px)",
};

function Map({ source, destination, setDistance }) {
  const [map, setMap] = React.useState(null);
  const [directionRoutePoints, setDirectionRoutePoints] = useState([]);
  const [distanceMatrix, setDistanceMatrix] = useState([]);

  const [center, setCenter] = useState({
    lat: 41.288,
    lng: 36.333,
  });

  useEffect(() => {
    if (source?.length != []) {
      setCenter({ lat: source.lat, lng: source.lng });
    }

    if (source?.length != [] && destination?.length != []) {
      directionRoute();
    }
  }, [source]);

  useEffect(() => {
    if (destination?.length != []) {
      setCenter({ lat: destination.lat, lng: destination.lng });
    }

    if (source?.length != [] && destination?.length != []) {
      directionRoute();
    }
  }, [destination]);

  const directionRoute = () => {
    const DirectionsService = new google.maps.DirectionsService();

    DirectionsService.route(
      {
        origin: {
          lat: source.lat,
          lng: source.lng,
        },
        destination: {
          lat: destination.lat,
          lng: destination.lng,
        },
        travelMode: google.maps.TravelMode.DRIVING,
      },
      (result) => {
        console.log("RES: ", result.status);

        if (result.status === "OK") {
          console.log("RES: ", result);
          setDirectionRoutePoints(result);
          calculateDistanceMatrix();
        } else {
          console.log("Error");
        }
      }
    );
  };

  const calculateDistanceMatrix = () => {
    const service = new google.maps.DistanceMatrixService();
    service.getDistanceMatrix(
      {
        origins: [new google.maps.LatLng(source.lat, source.lng)],
        destinations: [
          new google.maps.LatLng(destination.lat, destination.lng),
        ],
        travelMode: google.maps.TravelMode.DRIVING,
      },
      (response, status) => {
        if (status === "OK") {
          setDistanceMatrix(response);
        } else {
          console.error("Error calculating distance matrix:", status);
        }
      }
    );
  };

  useEffect(() => {
    if(distanceMatrix.length != []) setDistance(distanceMatrix.rows[0].elements[0].distance.text);
  }, [distanceMatrix]);

  const onLoad = React.useCallback(function callback(map) {
    // This is just an example of getting and using the map instance!!! don't just blindly copy!
    // const bounds = new window.google.maps.LatLngBounds(center);
    // map.fitBounds(bounds);
    // setMap(map)
  }, []);

  const onUnmount = React.useCallback(function callback(map) {
    setMap(null);
  }, []);

  return (
    <div style={{border:"1px solid #00000045"}}>
      <GoogleMap
        mapContainerStyle={containerStyle}
        center={center}
        zoom={12.6}
        onLoad={onLoad}
        onUnmount={onUnmount}
        options={{ mapId: "7297f202550d6fee" }}
      >
        {source.length != [] ? (
          <MarkerF
            icon={{
              url: currentLocation,
              scaledSize: {
                width: 20,
                height: 29,
              },
            }}
            position={{ lat: source.lat, lng: source.lng }}
          >
            <OverlayView
              position={{ lat: source.lat, lng: source.lng }}
              mapPaneName={OverlayView.OVERLAY_MOUSE_TARGET}
            >
              <div
                style={{
                  backgroundColor: "#FFFFFFd5",
                  width: "80px",
                  fontSize: "15px",
                  textAlign: "center",
                  color: "black",
                  borderRadius: "10px",
                  boxShadow: "1.5px 1.5px 5px 0px",
                }}
              >
                <p> {source.label} </p>
              </div>
            </OverlayView>
          </MarkerF>
        ) : null}

        {destination.length != [] ? (
          <MarkerF
            icon={{
              url: location,
              scaledSize: {
                width: 20,
                height: 29,
              },
            }}
            position={{ lat: destination.lat, lng: destination.lng }}
          >
            <OverlayView
              position={{ lat: destination.lat, lng: destination.lng }}
              mapPaneName={OverlayView.OVERLAY_MOUSE_TARGET}
            >
              <div
                style={{
                  backgroundColor: "#FFFFFFd5",
                  width: "80px",
                  fontSize: "15px",
                  textAlign: "center",
                  color: "black",
                  borderRadius: "10px",
                  boxShadow: "1.5px 1.5px 5px 0px",
                }}
              >
                <p> {destination.label} </p>
              </div>
            </OverlayView>
          </MarkerF>
        ) : null}

        <DirectionsRenderer
          directions={directionRoutePoints}
          options={{
            polylineOptions: {
              strokeColor: "blue",
              strokeOpacity: 0.8,
              strokeWeight: 5,
            },
            suppressMarkers: true,
          }}
        ></DirectionsRenderer>
      </GoogleMap>
    </div>
  );
}

export default React.memo(Map);
