package ch.bailu.aat.services.tracker;

import android.content.Context;

import java.io.File;
import java.io.IOException;

import ch.bailu.aat.R;
import ch.bailu.aat.gpx.GpxList;
import ch.bailu.aat.gpx.parser.GpxListReader;
import ch.bailu.aat.gpx.writer.GpxListWriter;
import ch.bailu.aat.util.fs.AppDirectory;
import ch.bailu.aat.util.ui.AppLog;
import ch.bailu.simpleio.foc.Foc;


public class TrackCrashRestorer{
    
    public TrackCrashRestorer (Context context, int presetIndex) throws IOException {
        
        Foc source = AppDirectory.getLogFile(context);

        if (source.isReachable()) {
            
            GpxList track = readFile(source);
            if (track.getPointList().size() > TrackLogger.MIN_TRACKPOINTS) { 
                AppLog.i(context, context.getString(R.string.tracker_restore));
        
                retstoreFile(context,track, presetIndex);
                
            }
            source.rm();
        }

    }

    
    private void retstoreFile(Context context, GpxList track, int presetIndex) throws IOException {
        Foc target = TrackLogger.generateTargetFile(context, presetIndex);
        
        GpxListWriter writer=new GpxListWriter(track,target);
        writer.close();

    }


    
    


    
    private GpxList readFile(Foc remainingLogFile) throws IOException {
        GpxListReader reader = new GpxListReader(remainingLogFile);
        return reader.getGpxList();
    }
}
