package ch.bailu.aat.gpx.tools;

import ch.bailu.aat.gpx.attributes.GpxListAttributes;
import ch.bailu.aat.gpx.GpxList;
import ch.bailu.aat.gpx.GpxListArray;
import ch.bailu.aat.gpx.GpxPoint;
import ch.bailu.aat.gpx.GpxPointFirstNode;
import ch.bailu.aat.gpx.GpxPointNode;

public class Inverser {


    private final GpxList newList;


    public Inverser(GpxList track) {
        newList = new GpxList(track.getDelta().getType(),
                GpxListAttributes.NULL);


        GpxListArray list = new GpxListArray(track);
        GpxListArray listInverse = new GpxListArray(track);

        if (list.size() > 0) {

            int indexInverse = list.size() -1;
            int index = 0;

            while (indexInverse >=0) {
                listInverse.setIndex(indexInverse);
                list.setIndex(index);

                final GpxPointNode point = list.get();
                final GpxPointNode pointInverse = listInverse.get();

                final GpxPoint pointNew = new GpxPoint(pointInverse, pointInverse.getAltitude(),
                        point.getTimeStamp());


                if (isLastInSegment(pointInverse))
                    newList.appendToNewSegment(pointNew, pointInverse.getAttributes());
                else
                    newList.appendToCurrentSegment(pointNew, pointInverse.getAttributes());


                index++;
                indexInverse--;
            }
        }

    }


    private boolean isLastInSegment(GpxPointNode point) {
        return point.getNext() == null || point.getNext() instanceof GpxPointFirstNode;
    }

    public GpxList getNewList() {
        return newList;
    }
}
