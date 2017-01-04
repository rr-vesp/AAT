package ch.bailu.aat.map.layer.grid;

import android.content.SharedPreferences;
import android.graphics.Point;

import org.mapsforge.core.model.BoundingBox;
import org.mapsforge.core.model.LatLong;

import java.util.Locale;

import ch.bailu.aat.coordinates.WGS84Sexagesimal;
import ch.bailu.aat.description.AltitudeDescription;
import ch.bailu.aat.map.MapContext;
import ch.bailu.aat.map.layer.MapLayerInterface;

public class WGS84Layer implements MapLayerInterface {
    private final static int MIN_ZOOM_LEVEL=7;

    private final MapContext clayer;
    private final AltitudeDescription altitudeDescription;

    public WGS84Layer(MapContext cl) {
        clayer = cl;
        altitudeDescription= new AltitudeDescription(cl.getContext());
    }


    @Override
    public void draw(MapContext mcontext) {
        byte zoomLevel = (byte)mcontext.getMetrics().getZoomLevel();
        BoundingBox boundingBox = mcontext.getMetrics().getBoundingBox();

        final LatLong point = boundingBox.getCenterPoint();

        drawGrid();
        drawCoordinates(point);
        drawElevation(zoomLevel, point);

    }

    @Override
    public boolean onTap(LatLong tapLatLong, org.mapsforge.core.model.Point layerXY, org.mapsforge.core.model.Point tapXY) {
        return false;
    }


    private void drawGrid() {
        final Point pixel = clayer.getMetrics().getCenterPixel();

        clayer.draw().vLine(pixel.x);
        clayer.draw().hLine(pixel.y);
        clayer.draw().point(pixel);
    }


    private void drawCoordinates(LatLong point) {
        clayer.draw().textBottom(new WGS84Sexagesimal(point.getLatitude(), point.getLongitude()).toString(),2);
        clayer.draw().textBottom(
                String.format((Locale)null,"%.6f/%.6f",
                        ((double)point.getLatitude()),
                        ((double)point.getLongitude())),
                1);
    }

    private void drawElevation(int zoom, LatLong point) {
        if (zoom > MIN_ZOOM_LEVEL && clayer.getSContext().isUp()) {
            final short ele = clayer.getSContext().getElevationService().getElevation(point.getLatitudeE6(), point.getLongitudeE6());
            clayer.draw().textBottom(altitudeDescription.getValueUnit(ele),3);
        }
    }



    @Override
    public void onLayout(boolean changed, int l, int t, int r, int b) {

    }



    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

    }

    @Override
    public void onAttached() {

    }

    @Override
    public void onDetached() {

    }
}
