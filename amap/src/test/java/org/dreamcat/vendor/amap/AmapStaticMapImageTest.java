package org.dreamcat.vendor.amap;

import java.io.IOException;
import org.dreamcat.vendor.amap.AmapStaticMapImage.Marker;
import org.dreamcat.vendor.amap.AmapStaticMapImage.Path;
import org.junit.Test;

/**
 * @author Jerry Will
 * @since 2021-06-30
 */
public class AmapStaticMapImageTest {

    @Test
    public void fetch() throws IOException {
        AmapStaticMapImage amap = new AmapStaticMapImage();

        amap.setKey("");
        amap.setZoom(getZoomByDistanceForAmap(2.6));
        amap.setSize("750*300");

        amap.getMarkers().add(new Marker("large", "", "送")
                .addLocation("116.31604", "39.96491"));
        amap.getMarkers().add(new Marker("large", "", "取")
                .addLocation("116.320816", "39.966606"));
        amap.getPaths().add(new Path(3, "0x0000FF", "1", "")
                .addLocation("116.32361", "39.966957")
                .addLocation("115.32361", "40.966957"));

        System.out.println(amap.getImageUrl());
        byte[] data = amap.fetch();
    }

    public int getZoomByDistanceForAmap(double distanceKm) {
        int zoom;
        if (distanceKm >= 4) {
            zoom = 11;
        } else if (distanceKm >= 2) {
            zoom = 12;
        } else if (distanceKm >= 1) {
            zoom = 13;
        } else if (distanceKm >= 0) {
            zoom = 14;
        } else {
            zoom = 13;
        }
        return zoom;
    }
}
