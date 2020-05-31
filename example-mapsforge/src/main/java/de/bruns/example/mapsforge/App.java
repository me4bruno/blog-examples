package de.bruns.example.mapsforge;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;
import org.mapsforge.core.graphics.Bitmap;
import org.mapsforge.core.graphics.GraphicFactory;
import org.mapsforge.core.model.LatLong;
import org.mapsforge.core.model.MapPosition;
import org.mapsforge.core.model.Point;
import org.mapsforge.map.awt.graphics.AwtBitmap;
import org.mapsforge.map.awt.graphics.AwtGraphicFactory;
import org.mapsforge.map.awt.util.AwtUtil;
import org.mapsforge.map.awt.view.MapView;
import org.mapsforge.map.datastore.MultiMapDataStore;
import org.mapsforge.map.layer.cache.TileCache;
import org.mapsforge.map.layer.overlay.Marker;
import org.mapsforge.map.layer.renderer.TileRendererLayer;
import org.mapsforge.map.reader.MapFile;
import org.mapsforge.map.rendertheme.InternalRenderTheme;

public final class App {

    private static final String PIN_FILE = "/poi.png";
    private static final String MAP_FILE = "bremen.map";
    private static final LatLong LAT_LONG = new LatLong(53.3, 8.7);
    private static final LatLong LAT_LONG_PIN = new LatLong(53.076199, 8.80755);
    private static final byte ZOOM_LEVEL = 8;

    private static final int TILE_SIZE = 512;
    private static final GraphicFactory GRAPHIC_FACTORY = AwtGraphicFactory.INSTANCE;
    private static final InternalRenderTheme RENDER_THEME = InternalRenderTheme.OSMARENDER;

    public static void main(String[] args) {
        final MapView mapView = createMap();
        final JMenuBar menu = createMenu(mapView);

        final JFrame frame = new JFrame();
        frame.add(mapView);
        frame.setJMenuBar(menu);
        frame.setTitle("Mapsforge Samples");
        frame.pack();
        frame.setSize(1024, 768);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                mapView.destroyAll();
                AwtGraphicFactory.clearResourceMemoryCache();
                frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            }
        });
        frame.setVisible(true);
    }

    static class PinMarker extends Marker {

        private final String description;

        public PinMarker(LatLong latLong, Bitmap bitmap, int horizontalOffset, int verticalOffset, String description) {
            super(latLong, bitmap, horizontalOffset, verticalOffset);
            this.description = description;
        }

        @Override
        public boolean onTap(LatLong tapLatLong, Point layerXY, Point tapXY) {
            double centerX = layerXY.x + getHorizontalOffset();
            double centerY = layerXY.y + getVerticalOffset();
            double radiusX = (getBitmap().getWidth() / 2) * 1.1;
            double radiusY = (getBitmap().getHeight() / 2) * 1.1;
            double distX = Math.abs(centerX - tapXY.x);
            double distY = Math.abs(centerY - tapXY.y);
            if (distX < radiusX && distY < radiusY) {
                JOptionPane.showMessageDialog(null, "Pin: " + description);
                return true;
            }
            return false;
        }
    }

    private static MapView createMap() {
        // AWT-Container MapView erstellen
        final MapView mapView = new MapView();
        mapView.getModel().mapViewPosition.setZoomLevelMin((byte) 6);
        mapView.getModel().mapViewPosition.setZoomLevelMax((byte) 20);
        mapView.getModel().displayModel.setFixedTileSize(TILE_SIZE);
        mapView.getModel().mapViewPosition.setMapPosition(new MapPosition(LAT_LONG, ZOOM_LEVEL));

        // MapDataStore mit der Bremen-Karte
        MultiMapDataStore mapDataStore = new MultiMapDataStore(MultiMapDataStore.DataPolicy.RETURN_ALL);
        mapDataStore.addMapDataStore(new MapFile(MAP_FILE), false, false);

        // Cache für die erstellten Kartenausschnitte
        TileCache tileCache = AwtUtil.createTileCache(TILE_SIZE,
          mapView.getModel().frameBufferModel.getOverdrawFactor(), 1024,
          new File(System.getProperty("java.io.tmpdir"), UUID.randomUUID().toString()));

        // Layer zur Anzeige der Karte
        TileRendererLayer tileRendererLayer = new TileRendererLayer(
          tileCache, mapDataStore, mapView.getModel().mapViewPosition, false, true, false, GRAPHIC_FACTORY, null) {
            @Override
            public boolean onTap(LatLong tapLatLong, Point layerXY, Point tapXY) {
                JOptionPane.showMessageDialog(null, "Position: " + tapLatLong);
                return true;
            }
        };
        tileRendererLayer.setXmlRenderTheme(RENDER_THEME);
        mapView.getLayerManager().getLayers().add(tileRendererLayer);

        return mapView;
    }

    private static JMenuBar createMenu(MapView mapView) {
        JMenuBar menubar = new JMenuBar();
        JMenu menuNavigation = new JMenu("Navigation");
        menubar.add(menuNavigation);

        JMenuItem zoomIn = new JMenuItem("Zoom in");
        zoomIn.addActionListener(e -> mapView.getModel().mapViewPosition.setZoomLevel((byte) (mapView.getModel().mapViewPosition.getZoomLevel() + 1)));
        menuNavigation.add(zoomIn);

        JMenuItem zoomOut = new JMenuItem("Zoom out");
        zoomOut.addActionListener(e -> mapView.getModel().mapViewPosition.setZoomLevel((byte) (mapView.getModel().mapViewPosition.getZoomLevel() - 1)));
        menuNavigation.add(zoomOut);

        JMenu menuMarker = new JMenu("Marker");
        menubar.add(menuMarker);

        JMenuItem addDescription = new JMenuItem("Add pin (with message)");
        addDescription.addActionListener(e -> {
            try {
                InputStream resourceAsStream = App.class.getResourceAsStream(PIN_FILE);
                AwtBitmap icon = new AwtBitmap(resourceAsStream);
                icon.scaleTo(31, 43);
                Marker pinMarker = new PinMarker(LAT_LONG_PIN, icon, 0, -icon.getHeight() / 2, "Bremer Stadtmusikanten");
                mapView.getLayerManager().getLayers().add(pinMarker);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        menuMarker.add(addDescription);
        return menubar;
    }
}
