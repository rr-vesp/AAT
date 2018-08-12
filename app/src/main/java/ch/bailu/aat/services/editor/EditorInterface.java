package ch.bailu.aat.services.editor;

import ch.bailu.aat.gpx.GpxPoint;
import ch.bailu.aat.gpx.GpxPointNode;
import ch.bailu.util_java.foc.Foc;

public interface EditorInterface {
    EditorInterface NULL = new EditorInterface() {

        @Override
        public void save() {}

        @Override
        public void toggle() {}

        @Override
        public void remove() {}

        @Override
        public void add(GpxPoint point) {}

        @Override
        public void up() {}

        @Override
        public void down() {}

        @Override
        public boolean isModified() {
            return false;
        }

        @Override
        public GpxPointNode getSelected() {
            return null;
        }

        @Override
        public void select(GpxPointNode p) {}

        @Override
        public void saveAs() {}

        @Override
        public void clear() {}

        @Override
        public void redo() {}

        @Override
        public void undo() {}

        @Override
        public void discard() {}

        @Override
        public void inverse() {

        }

        @Override
        public void attach(Foc file) {

        }

        @Override
        public void fix() {

        }

        @Override
        public void simplify() {

        }
    };
    void save();
    void toggle();
    void remove();
    void add(GpxPoint point);
    void up();
    void down();
    boolean isModified();

    GpxPointNode getSelected();
    void select(GpxPointNode p);

    void saveAs();
    void clear();
    void redo();
    void undo();
    void discard();

    void inverse();

    void attach(Foc file);

    void fix();

    void simplify();
}
