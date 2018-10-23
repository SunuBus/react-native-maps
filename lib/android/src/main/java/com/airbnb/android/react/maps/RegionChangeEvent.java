package com.airbnb.android.react.maps;

import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.RCTEventEmitter;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

public class RegionChangeEvent extends Event<RegionChangeEvent> {
  private final LatLngBounds bounds;
  private final boolean continuous;
  private final double tilt;
  private final double bearing;

  public RegionChangeEvent(int id, LatLngBounds bounds, boolean continuous) {
    super(id);
    this.bounds = bounds;
    this.continuous = continuous;
    this.tilt = 0;
    this.bearing = 0;
  }

  public RegionChangeEvent(int id, LatLngBounds bounds, double tilt, double bearing, boolean continuous) {
    super(id);
    this.bounds = bounds;
    this.continuous = continuous;
    this.tilt = tilt;
    this.bearing = bearing;
  }

  @Override
  public String getEventName() {
    return "topChange";
  }

  @Override
  public boolean canCoalesce() {
    return false;
  }

  @Override
  public void dispatch(RCTEventEmitter rctEventEmitter) {

    WritableMap event = new WritableNativeMap();
    event.putBoolean("continuous", continuous);

    WritableMap region = new WritableNativeMap();
    LatLng center = bounds.getCenter();
    region.putDouble("latitude", center.latitude);
    region.putDouble("longitude", center.longitude);
    region.putDouble("latitudeDelta", bounds.northeast.latitude - bounds.southwest.latitude);
    region.putDouble("longitudeDelta", bounds.northeast.longitude - bounds.southwest.longitude);
    region.putDouble("tilt", tilt);
    region.putDouble("bearing", tilt);
    event.putMap("region", region);

    rctEventEmitter.receiveEvent(getViewTag(), getEventName(), event);
  }
}
