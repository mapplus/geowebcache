/**
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * @author Arne Kepp, The Open Planning Project, Copyright 2008
 */
package org.geowebcache.service.kml;

import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.geowebcache.GeoWebCacheException;
import org.geowebcache.cache.Cache;
import org.geowebcache.cache.CacheException;
import org.geowebcache.cache.CacheKey;
import org.geowebcache.layer.RawTile;
import org.geowebcache.layer.SRS;
import org.geowebcache.layer.TileLayer;
import org.geowebcache.layer.TileRequest;
import org.geowebcache.layer.TileResponse;
import org.geowebcache.mime.MimeType;
import org.geowebcache.mime.XMLMime;
import org.geowebcache.service.ServiceRequest;
import org.geowebcache.util.wms.BBOX;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;

/**
 * 
 * Creates a grid of tiles and puts the grid index on each of them
 * 
 */
public class KMLDebugGridLayer implements TileLayer, Cache, CacheKey {

    public static String LAYERNAME = "debugGrid";
    
    public static final int IS_KMZ = 100;
    
    private static Log log = LogFactory.getLog(org.geowebcache.service.kml.KMLDebugGridLayer.class);
    
    private static KMLDebugGridLayer instance;
    
    private KMLDebugGridLayer() {
        
    }
    
    synchronized static public KMLDebugGridLayer getInstance() {
        if(instance == null) {
            instance = new KMLDebugGridLayer();
        }
        return instance;
    }
    
    public void acquireLayerLock() {
        // TODO Auto-generated method stub
        //log.warn("acquireLayerLock()");
    }

    public void destroy() {
        // TODO Auto-generated method stub
        //log.warn("destroy()");
    }

    public TileResponse doNonMetatilingRequest(int[] gridLoc, int idx,
            String formatStr) throws GeoWebCacheException {
        // TODO Auto-generated method stub
        //log.warn("doNonMetatilingRequest(int[] gridLoc, int idx,String formatStr)");
        return null;
    }

    public BBOX getBboxForGridLoc(int srsIdx, int[] gridLoc) {
        // TODO Auto-generated method stub
        //log.warn("done - getBboxForGridLoc(int srsIdx, int[] gridLoc)");
        
        double tileWidth = 180.0 / Math.pow(2, gridLoc[2]);

        BBOX bbox = new BBOX(
                     -180.0 + tileWidth * gridLoc[0],
                     -90.0 + tileWidth * gridLoc[1],
                     -180.0 + tileWidth * (gridLoc[0] + 1),
                     -90.0 + tileWidth * (gridLoc[1] + 1));
        
        return bbox;
    }

    public BBOX getBounds(int srsIdx) {
        // TODO Auto-generated method stub
        //log.warn("done - getBounds");
        return new BBOX(-180.0, -90.0, 180.0, 90.0);
    }

    public Cache getCache() {
        // TODO Auto-generated method stub
        //log.warn("getCache");
        return null;
    }

    public CacheKey getCacheKey() {
        // TODO Auto-generated method stub
        //log.warn("done - getCacheKey");
        return this;
    }

    public String getCachePrefix() {
        // TODO Auto-generated method stub
        //log.warn("done - getCachePrefix");
        return null;
    }

    public int[][] getCoveredGridLevels(int srsIdx, BBOX bounds) {
        // TODO Auto-generated method stub
        //log.warn("getCoveredGridLevels(int srsIdx, BBOX bounds)");
        return null;
    }

    public MimeType getDefaultMimeType() {
        // TODO Auto-generated method stub
        //log.warn("getDefaultMimeType()");
        return null;
    }

    public int[] getGridLocForBounds(int srsIdx, BBOX bounds)
            throws GeoWebCacheException {
        // TODO Auto-generated method stub
        //log.warn("getGridLocForBounds(int srsIdx, BBOX bounds)");
        return null;
    }

    public int[] getMetaTilingFactors() {
        // TODO Auto-generated method stub
        //log.warn("getMetaTilingFactors()");
        return null;
    }

    public MimeType[] getMimeTypes() {
        // TODO Auto-generated method stub
        //log.warn("getMimeTypes()");
        return null;
    }

    public String getName() {
        // TODO Auto-generated method stub
        //log.warn("done - getName()");
        return "Debug grid";
    }

    public SRS[] getProjections() {
        // TODO Auto-generated method stub
        //log.warn("done - getProjections()");
        
        SRS[] srsList = { SRS.getEPSG4326() };
        return srsList;
    }

    public double[] getResolutions(int srsIdx) {
        // TODO Auto-generated method stub
        //log.warn("getResolutions()");
        return null;
    }

    public TileResponse getResponse(TileRequest tileRequest,
            ServiceRequest servReq, HttpServletResponse response)
            throws GeoWebCacheException, IOException {
        // TODO Auto-generated method stub
        //log.warn("getResponse(TileRequest tileRequest,ServiceRequest servReq, HttpServletResponse response)");
        
        int[] gridLoc = tileRequest.gridLoc;

        BBOX bbox = this.getBboxForGridLoc(0, gridLoc);
       
        String data  = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                + "<kml xmlns=\"http://earth.google.com/kml/2.1\">\n"
                + "<Document>\n"
                //+"<!-- Name>DocumentName</Name --->"
                +"<Style id=\"square\">\n"
                +"<PolyStyle><color>7fffffff</color><colorMode>random</colorMode>\n"  
                +"</PolyStyle>\n"
                +"<LabelStyle id=\"name\"><color>ffffffff</color><colorMode>normal</colorMode><scale>1.0</scale></LabelStyle>\n"
                +"</Style>\n"
                +"<Placemark id=\"PlaceMarkId\">\n"
                +"<styleUrl>#square</styleUrl>\n"
                +"<name>x:"+gridLoc[0]+ " y:"+gridLoc[1]+" z:"+gridLoc[2]+"</name>"
                +"<MultiGeometry>\n"
                +"<Point><coordinates>"+((bbox.coords[0]+bbox.coords[2])/2) 
                +","+ ((bbox.coords[1]+bbox.coords[3])/2) 
                +",0</coordinates></Point>\n"
                +"<Polygon><outerBoundaryIs><LinearRing>\n"
                +"<coordinates decimal=\".\" cs=\",\" ts=\" \">\n"
                + bbox.coords[0] +","+ bbox.coords[1] + " "
                + bbox.coords[2] +","+ bbox.coords[1] + " "
                + bbox.coords[2] +","+ bbox.coords[3] + " "
                + bbox.coords[0] +","+ bbox.coords[3]
                +"</coordinates>\n"
                +"</LinearRing></outerBoundaryIs></Polygon>\n"
                +"</MultiGeometry>\n"
                +"</Placemark>\n"
                + "</Document>\n"
                + "</kml>";
        
        TileResponse tr = new TileResponse(data.getBytes(),XMLMime.kml,200);
        return tr;
    }

    public int getSRSIndex(SRS reqSRS) {
        // TODO Auto-generated method stub
        //log.warn("done - getSRSIndex");
        return 0;
    }

    public String getStyles() {
        // TODO Auto-generated method stub
        //log.warn("getStyles()");
        return null;
    }

    public int[][] getZoomInGridLoc(int srsIdx, int[] gridLoc) {
        //log.warn("done - getZoomInGridLoc(srsIdx, gridLoc)");
        
        int[][] retVal = new int[4][3];
        
        int x = gridLoc[0] * 2;
        int y = gridLoc[1] * 2;
        int z = gridLoc[2] + 1;
        
        // Don't link to tiles past the last zoomLevel
        if(z > 25) {
            z = -1;
        }
        
        // Now adjust where appropriate
        retVal[0][0] = retVal[2][0] = x;
        retVal[1][0] = retVal[3][0] = x + 1;
        
        retVal[0][1] = retVal[1][1] = y;
        retVal[2][1] = retVal[3][1] = y + 1;

        retVal[0][2] = retVal[1][2] = retVal[2][2] = retVal[3][2] = z;
        
        return retVal;
    }

    public int getZoomStart() {
        // TODO Auto-generated method stub
        //log.warn("getZoomStart()");
        return 0;
    }

    public int getZoomStop() {
        // TODO Auto-generated method stub
        //log.warn("getZoomStop()");
        return 25;
    }

    public int[] getZoomedOutGridLoc(int srsIdx) {
        // TODO Auto-generated method stub
        //log.warn("done - getZoomedOutGridLoc");
        int[] zoomedOutGridLoc = new int[3];
        zoomedOutGridLoc[0] = -1;
        zoomedOutGridLoc[1] = -1;
        zoomedOutGridLoc[2] = -1;
        
        return zoomedOutGridLoc;
    }

    public Boolean initialize() {
        // TODO Auto-generated method stub
        //log.warn("initialize()");
        return null;
    }

    public Boolean isInitialized() {
        // TODO Auto-generated method stub
        //log.warn("isInitialized()");
        return true;
    }

    public void putTile(RawTile tile, Object ck, int[] gridLoc)
            throws CacheException {
        // TODO Auto-generated method stub
        //log.warn("putTile");
    }

    public void releaseLayerLock() {
        // TODO Auto-generated method stub
        //log.warn("releaseLayerLock()");
    }

    public void setExpirationHeader(HttpServletResponse response) {
        // TODO Auto-generated method stub
        //log.warn("setExpirationHeader");
    }

    public String supportsBbox(SRS srs, BBOX bounds)
            throws GeoWebCacheException {
        // TODO Auto-generated method stub
        //log.warn("supportsBbox");
        return null;
    }

    public boolean supportsFormat(String formatStr) throws GeoWebCacheException {
        // TODO Auto-generated method stub
        //log.warn("supportsFormat");
        return false;
    }

    public boolean supportsProjection(SRS srs) throws GeoWebCacheException {
        // TODO Auto-generated method stub
        //log.warn("supportsProjection");
        return false;
    }

    // Returns the KML for the tile, but not a cached KMZ overlay
    public RawTile tryCacheFetch(Object cacheKey) {
        // TODO Auto-generated method stub
        //log.warn("done - tryCacheFetch");
        
        Vector<Integer> lst = (Vector<Integer>) cacheKey;
        
        int kmz = lst.get(0);
        
        if(kmz != KMLDebugGridLayer.IS_KMZ) {
            return null;
        } else {
            System.out.println("OOPS");
        }
        
        return null;
    }

    public Object get(Object key, long ttl) throws CacheException {
        // TODO Auto-generated method stub
        return null;
    }

    public String getDefaultKeyBeanId() {
        // TODO Auto-generated method stub
        return null;
    }

    public String getDefaultPrefix(String param) throws CacheException {
        // TODO Auto-generated method stub
        return null;
    }

    
    public void init(Properties props) throws CacheException {
        // TODO Auto-generated method stub
        
    }

    public boolean remove(Object key) throws CacheException {
        // TODO Auto-generated method stub
        return false;
    }

    public void removeAll() throws CacheException {
        // TODO Auto-generated method stub
        
    }

    /** Cache interface **/
    public void set(Object key, Object obj, long ttl) throws CacheException {
        // TODO Auto-generated method stub
        
    }

    public void setDefaultKeyBeanId(String defaultKeyBeanId) {
        // TODO Auto-generated method stub
        
    }

    public void setUp(String cachePrefix) throws CacheException {
        // TODO Auto-generated method stub
        
    }

    public void setApplicationContext(ApplicationContext arg0)
            throws BeansException {
        // TODO Auto-generated method stub
        
    }

    public Object createKey(String prefix, int x, int y, int z, SRS srs, String format) {
        Vector<Integer> lst = new Vector<Integer>();
        if(format.equalsIgnoreCase("kmz")) {
            lst.add(KMLDebugGridLayer.IS_KMZ);
        } else {
            lst.add(0);
        }
        lst.add(x);
        lst.add(y);
        lst.add(z);  
        
        return (Object) lst;
    }

    public int getType() {
        // TODO Auto-generated method stub
        return 0;
    }

    public void init() {
        // TODO Auto-generated method stub
        
    }

}