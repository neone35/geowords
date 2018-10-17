package com.github.neone35.geowords.utils;

import android.content.Context;
import android.graphics.Bitmap;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.ui.IconGenerator;

import java.util.List;

public class MapUtils {

    public static MarkerOptions generateMarker(Context ctx, LatLng latLng, int markerBgDrawableId) {
        // Build an marker with custom or default icon
        if (markerBgDrawableId != 0) {
            IconGenerator iconGenerator = new IconGenerator(ctx);
            iconGenerator.setBackground(ctx.getResources().getDrawable(markerBgDrawableId));
            Bitmap bitmap = iconGenerator.makeIcon();
            BitmapDescriptor icon = BitmapDescriptorFactory.fromBitmap(bitmap);
            // Build a marker
            return new MarkerOptions()
                    .position(latLng)
                    .icon(icon);
        } else {
            return new MarkerOptions()
                    .position(latLng);
        }
    }

    public static LatLngBounds getMarkerBounds(List<Marker> markerList) {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (Marker marker : markerList) {
            builder.include(marker.getPosition());
        }
        return builder.build();
    }

}
