package ch.bailu.aat.map.layer.control;

import android.util.SparseArray;
import android.view.View;

import ch.bailu.aat.R;
import ch.bailu.aat.activities.AbsActivity;
import ch.bailu.aat.dispatcher.DispatcherInterface;
import ch.bailu.aat.dispatcher.OnContentUpdatedInterface;
import ch.bailu.aat.gpx.GpxInformation;
import ch.bailu.aat.gpx.InfoID;
import ch.bailu.aat.map.MapContext;
import ch.bailu.aat.preferences.SolidPositionLock;
import ch.bailu.aat.util.ui.AppLog;
import ch.bailu.aat.util.ui.ToolTip;
import ch.bailu.aat.views.bar.ControlBar;

public class NavigationBarLayer extends ControlBarLayer implements OnContentUpdatedInterface {

    private final MapContext mcontext;
    private final View buttonPlus;
    private final View buttonMinus;
    private final View buttonFrame;

    private final SparseArray<GpxInformation> infoCache = new SparseArray<>(10);

    private int boundingCycle=0;


    public NavigationBarLayer(MapContext mc, DispatcherInterface d) {
        this(mc, d, 4);
    }


    public NavigationBarLayer(MapContext mc, DispatcherInterface d, int i) {
        super(mc,new ControlBar(mc.getContext(),
                getOrientation(BOTTOM), i), BOTTOM);

        mcontext = mc;

        buttonPlus = getBar().addImageButton(R.drawable.zoom_in);
        buttonMinus = getBar().addImageButton(R.drawable.zoom_out);
        View lock = getBar().addSolidIndexButton(
                new SolidPositionLock(mc.getContext(), mc.getSolidKey()));
        buttonFrame = getBar().addImageButton(R.drawable.zoom_fit_best);

        ToolTip.set(buttonPlus, R.string.tt_map_zoomin);
        ToolTip.set(buttonMinus,R.string.tt_map_zoomout);
        ToolTip.set(buttonFrame,  R.string.tt_map_frame);
        ToolTip.set(lock, R.string.tt_map_home);

        d.addTargets(this, InfoID.ALL);
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);


        if (v==buttonPlus) {
           mcontext.getMapView().zoomIn();

        } else if (v==buttonMinus) {
            mcontext.getMapView().zoomOut();

        } else if (v==buttonFrame && infoCache.size()>0) {

            if (nextInBoundingCycle()) {
                mcontext.getMapView().frameBounding(infoCache.valueAt(boundingCycle).getBoundingBox());
                AppLog.i(mcontext.getContext(), infoCache.valueAt(boundingCycle).getFile().getName());

            }
        }
    }


    private boolean nextInBoundingCycle() {
        int c = infoCache.size();

        while (c > 0) {
            c--;
            boundingCycle++;

            if (boundingCycle >= infoCache.size())
                boundingCycle=0;

            if (       infoCache.valueAt(boundingCycle).getBoundingBox().hasBounding()
                    || infoCache.valueAt(boundingCycle).getGpxList().getPointList().size()>0)
                return true;
        }
        return false;
    }



    @Override
    public void onContentUpdated(int iid, GpxInformation info) {
        if (info.isLoaded()) {

            infoCache.put(iid, info);

        } else {
            infoCache.remove(iid);
        }
    }

    @Override
    public void drawInside(MapContext mcontext) {

    }

    @Override
    public void onAttached() {

    }

    @Override
    public void onDetached() {

    }


}
