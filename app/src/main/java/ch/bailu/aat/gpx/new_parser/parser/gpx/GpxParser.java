package ch.bailu.aat.gpx.new_parser.parser.gpx;


import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

import ch.bailu.aat.gpx.new_parser.parser.TagParser;
import ch.bailu.aat.gpx.new_parser.scanner.Scanner;

public class GpxParser  extends TagParser {

    private MetadataParser metadata = new MetadataParser();
    private TrkParser trk = new TrkParser("trk");
    private TrkParser rte = new TrkParser("rte");
    private TrkParser way = new TrkParser("way");


    public GpxParser() {
        super("gpx");
    }


    @Override
    protected void parseText(XmlPullParser parser, Scanner scanner) throws IOException, XmlPullParserException {

    }

    @Override
    protected void parseAttributes(XmlPullParser parser, Scanner scanner) {

    }

    @Override
    public boolean parseTags(XmlPullParser parser, Scanner scanner) throws IOException, XmlPullParserException {
        return
                metadata.parse(parser, scanner) ||
                trk.parse(parser, scanner) ||
                way.parse(parser, scanner) ||
                rte.parse(parser, scanner);
    }

    @Override
    public void parsed(XmlPullParser parser, Scanner scanner) {

    }
}


