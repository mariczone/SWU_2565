package com.example.myapplication

import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.esri.arcgisruntime.ArcGISRuntimeEnvironment
import com.esri.arcgisruntime.geometry.*
import com.esri.arcgisruntime.mapping.ArcGISMap
import com.esri.arcgisruntime.mapping.Basemap
import com.esri.arcgisruntime.mapping.Viewpoint
import com.esri.arcgisruntime.mapping.view.Graphic
import com.esri.arcgisruntime.mapping.view.GraphicsOverlay
import com.esri.arcgisruntime.mapping.view.MapView
import com.esri.arcgisruntime.symbology.PictureMarkerSymbol
import com.esri.arcgisruntime.symbology.SimpleFillSymbol
import com.esri.arcgisruntime.symbology.SimpleLineSymbol
import com.esri.arcgisruntime.symbology.SimpleMarkerSymbol
import com.example.myapplication.adapter.ShopPointAdapter
import com.example.myapplication.adapter.ShopPointViewItem

class MapFragment : Fragment() {
    private lateinit var shopPointList: List<ShopPointViewItem>
    private lateinit var mMapView: MapView
    private lateinit var graphicLayer: GraphicsOverlay
    private lateinit var shopPointAdapter: ShopPointAdapter
    private lateinit var shopViewPager2: ViewPager2
    private var isFirst: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ArcGISRuntimeEnvironment.setLicense(resources.getString(R.string.arcgis_license_key))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mMapView = view.findViewById<MapView>(R.id.mapView);
        val latitude = 13.700547
        val longitude = 100.535619
        val levelOfDetail = 15
        val map =
            ArcGISMap(Basemap.Type.DARK_GRAY_CANVAS_VECTOR, latitude, longitude, levelOfDetail)
        mMapView.map = map
        mMapView.isAttributionTextVisible = false
        addGraphics()

//        testDriverTracking()
//        val mainHandler = Handler(Looper.getMainLooper())
//
//        val routeList = mutableListOf<Point>()
//        routeList.add( Point(100.53372329771109, 13.689353441036856, SpatialReferences.getWgs84()))
//        routeList.add( Point(100.53419865621609, 13.69042230315064, SpatialReferences.getWgs84()))
//        routeList.add( Point(100.53440238128965, 13.691082092154375, SpatialReferences.getWgs84()))
//        routeList.add( Point(100.53399493114252, 13.692296099085201, SpatialReferences.getWgs84()))
//        routeList.add( Point(100.53362822601008, 13.693167013240297, SpatialReferences.getWgs84()))
//        routeList.add( Point(100.53320719419138, 13.69423585802231, SpatialReferences.getWgs84()))
//        routeList.add( Point(100.5330985408188, 13.694948418511174, SpatialReferences.getWgs84()))
//        routeList.add( Point(100.53262318231381, 13.695753345354818, SpatialReferences.getWgs84()))
//        routeList.add( Point(100.53205275210782, 13.697033305316276, SpatialReferences.getWgs84()))
//        routeList.add( Point(100.53168604697537, 13.698154913986576, SpatialReferences.getWgs84()))
//        routeList.add( Point(100.53121068847038, 13.69931610320697, SpatialReferences.getWgs84()))
//        routeList.add( Point(100.53074891163695, 13.70054326285297, SpatialReferences.getWgs84()))
//        routeList.add( Point(100.53043653319082, 13.701453731998242, SpatialReferences.getWgs84()))
//        routeList.add( Point(100.53021922644568, 13.70202112402942, SpatialReferences.getWgs84()))
//        routeList.add( Point(100.53095263671052, 13.702628100034536, SpatialReferences.getWgs84()))
//
//        var hasAlert = false
//
//        mainHandler.post(object : Runnable {
//            override fun run() {
//                if (routeList.size > 0 ) {
//                    // find exist driver
//                    val existDriverGraphic = graphicLayer.graphics.find { it.attributes["id"] == 1 }
//                    if (existDriverGraphic != null) {
//                        // update geometry
//                        var nextPosition: Point = routeList.removeAt(0)
//                        existDriverGraphic.geometry = nextPosition
//
//                        val existBuffer = graphicLayer.graphics.find { it.attributes["type"] == "buffer" }
//                        if (existBuffer != null) {
//                            var isInside = GeometryEngine.intersects(existBuffer.geometry, nextPosition)
//                            if (isInside && !hasAlert) {
//                                Toast.makeText(activity, "Driver is here!!", Toast.LENGTH_LONG).show()
//                                hasAlert = true
//                            }
//                        }
//                    }
//                    mainHandler.postDelayed(this, 1000)
//                }
//            }
//        })
//        // Create List of Page
//        shopPointList = listOf<ShopPointViewItem>(
//            ShopPointViewItem(
//                "Shop 1",
//                "11/56 Soi Phetchaburi 47 · 02 821 5889",
//                R.drawable.onboarding1,
//                genShopPoint(100.53892960569901, 13.701952685466564)
//            ),
//            ShopPointViewItem(
//                "Shop 2",
//                "21/56 Soi Phetchaburi 47 · 02 821 5889",
//                R.drawable.onboarding2,
//                genShopPoint(100.53033429238417, 13.71200666513494)
//            ),
//            ShopPointViewItem(
//                "Shop 3",
//                "31/56 Soi Phetchaburi 47 · 02 821 5889",
//                R.drawable.onboarding3,
//                genShopPoint(100.52811496041558, 13.695102867638928)
//            ),
//            ShopPointViewItem(
//                "Shop 4",
//                "31a/56 Soi Phetchaburi 47 · 02 821 5889",
//                R.drawable.onboarding1,
//                genShopPoint(100.48636850713976, 13.80197600326033)
//            ),
//            ShopPointViewItem(
//                "Shop 5",
//                "31b/56 Soi Phetchaburi 47 · 02 821 5889",
//                R.drawable.onboarding2,
//                genShopPoint(100.46775777539006, 13.74168823101948)
//            ),
//            ShopPointViewItem(
//                "Shop 6",
//                "31c/56 Soi Phetchaburi 47 · 02 821 5889",
//                R.drawable.onboarding3,
//                genShopPoint(100.47410731916348, 13.684682347016444)
//            ),
//            ShopPointViewItem(
//                "Shop 7",
//                "31d/56 Soi Phetchaburi 47 · 02 821 5889",
//                R.drawable.onboarding1,
//                genShopPoint(100.54449967513439, 13.750301721391395)
//            ),
//            ShopPointViewItem(
//                "Shop 8",
//                "31e/56 Soi Phetchaburi 47 · 02 821 5889",
//                R.drawable.onboarding2,
//                genShopPoint(100.58588118455431, 13.725311092155396)
//            ),
//            ShopPointViewItem(
//                "Shop 9",
//                "31f/56 Soi Phetchaburi 47 · 02 821 5889",
//                R.drawable.onboarding3,
//                genShopPoint(100.55927878564151, 13.666705610048975)
//            )
//        )
//        // find Extent of all point
//        val listOfPoint = mutableListOf<Point>()
//        shopPointList.forEach {
//            listOfPoint.add(it.location)
//        }
//        var mCompleteExtent: Envelope = GeometryEngine.combineExtents(listOfPoint);
//        var newX1 = mCompleteExtent.xMin - mCompleteExtent.xMin * 0.0001
//        var newY1 = mCompleteExtent.yMin - mCompleteExtent.yMin * 0.0001
//        var newX2 = mCompleteExtent.xMax + mCompleteExtent.xMax * 0.0001
//        var newY2 = mCompleteExtent.yMax + mCompleteExtent.yMax * 0.0001
//        var mExtent2 = Envelope(newX1, newY1, newX2, newY2, mCompleteExtent.spatialReference)
//
//        createBuffer7km()
//        shopPointAdapter = ShopPointAdapter(shopPointList)
//        shopViewPager2 = view.findViewById<ViewPager2>(R.id.shopViewPager2);
//        shopViewPager2.adapter = shopPointAdapter;
//        mMapView.setViewpointCenterAsync(shopPointList[0].location, 20000.0)
//        shopViewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
//            override fun onPageSelected(position: Int) {
//                super.onPageSelected(position)
//                if (isFirst) {
//                    isFirst = false;
//                    addCurrentLocation(mExtent2)
//                } else mMapView.setViewpointCenterAsync(shopPointList[position].location, 20000.0)
//            }
//        })
//        //        addPoint()
//        //        addLine()
//        //        addPolygon()
//        //        addPointPictureSymbol()
    }

    private fun testDriverTracking() {
        // add Customer Location
        val currentLocationPoint = Point(100.53168604697537, 13.703314244935012, SpatialReferences.getWgs84())
        // create an opaque orange (0xFFFF5733) point symbol with a blue (0xFF0063FF) outline symbol
        val simpleMarkerSymbol = SimpleMarkerSymbol(SimpleMarkerSymbol.Style.CIRCLE, Color.YELLOW, 10f)
        val blueOutlineSymbol = SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, Color.RED, 2f)
        simpleMarkerSymbol.outline = blueOutlineSymbol
        // create a graphic with the point geometry and symbol
        val pointGraphic = Graphic(currentLocationPoint, simpleMarkerSymbol)
        // add the point graphic to the graphics overlay
        graphicLayer.graphics.add(pointGraphic)
        // zoom to current location point
        mMapView.setViewpointCenterAsync(currentLocationPoint, 20000.0)

        // Add Driver
        val point = Point(100.53331584756394, 13.6877699326815, SpatialReferences.getWgs84())
        // code for  get image in drawable folder
        val pictureMarkerSymbol = PictureMarkerSymbol.createAsync(
            ContextCompat.getDrawable(
                requireContext(),
                R.drawable.marker
            ) as BitmapDrawable
        ).get()

        // set width, height, z from ground
        pictureMarkerSymbol.height = 52f
        pictureMarkerSymbol.width = 52f
        pictureMarkerSymbol.offsetY = 11f
        // create a graphic with the point geometry and symbol
        val driverGraphic = Graphic(point, pictureMarkerSymbol)

        // add attribute to graphic
        val drivefAttribute = mutableMapOf<String, Any>()
        drivefAttribute["id"] = 1
        drivefAttribute["name"] = "Mr.John"
        driverGraphic.attributes.putAll(drivefAttribute)

        // add the point graphic to the graphics overlay
        graphicLayer.graphics.add(driverGraphic)

        // create buffer geometry 100 meter
        val geometryBuffer = GeometryEngine.bufferGeodetic(currentLocationPoint, 300.0,
            LinearUnit(LinearUnitId.METERS), Double.NaN, GeodeticCurveType.GEODESIC)
        // create symbol for buffer geometry
        val geodesicOutlineSymbol = SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, Color.BLACK, 2F)
        // 0x4D00FF00 = Green Color with 25% Opacity (4D = 25%)
        val geodesicBufferFillSymbol = SimpleFillSymbol(SimpleFillSymbol.Style.SOLID,
            0x4D00FF00.toInt(), geodesicOutlineSymbol)

        // new graphic
        val graphicBuffer =  Graphic(geometryBuffer, geodesicBufferFillSymbol)
        // add attribute to graphic
        val bufferAttribute = mutableMapOf<String, Any>()
        bufferAttribute["type"] = "buffer"
        graphicBuffer.attributes.putAll(bufferAttribute)
        graphicLayer.graphics.add(graphicBuffer)
    }

    private fun createBuffer7km() {
        val currentLocationPoint =
            Point(100.53168604697537, 13.703314244935012, SpatialReferences.getWgs84())
        // create buffer polygon
        // create buffer geometry 100 meter
        val geometryBuffer = GeometryEngine.bufferGeodetic(
            currentLocationPoint, 7.0,
            LinearUnit(LinearUnitId.KILOMETERS), Double.NaN, GeodeticCurveType.GEODESIC
        )

        // create symbol for buffer geometry
        val geodesicOutlineSymbol = SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, Color.BLACK, 2F)
        // 0x4D00FF00 = Green Color with 25% Opacity (4D = 25%)
        val geodesicBufferFillSymbol = SimpleFillSymbol(
            SimpleFillSymbol.Style.SOLID,
            0x4D00FF00.toInt(), geodesicOutlineSymbol
        )

        // new graphic
        val graphicBuffer = Graphic(geometryBuffer, geodesicBufferFillSymbol)
//        graphicLayer.graphics.add(graphicBuffer)

        // filter 7km shop
        shopPointList = shopPointList.filter {
            var isInside = GeometryEngine.intersects(geometryBuffer, it.location)
            isInside
        }
        // add all filtered shop to map
        shopPointList.forEach {
            genShopPoint(it.location.x, it.location.y, true)
        }
    }

    private fun addCurrentLocation(extent: Envelope) {
        val currentLocationPoint =
            Point(100.53168604697537, 13.703314244935012, SpatialReferences.getWgs84())
        // create an opaque orange (0xFFFF5733) point symbol with a blue (0xFF0063FF) outline symbol
        val simpleMarkerSymbol =
            SimpleMarkerSymbol(SimpleMarkerSymbol.Style.CIRCLE, Color.YELLOW, 10f)
        val blueOutlineSymbol = SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, Color.RED, 2f)
        simpleMarkerSymbol.outline = blueOutlineSymbol
        // create a graphic with the point geometry and symbol
        val pointGraphic = Graphic(currentLocationPoint, simpleMarkerSymbol)
        // add the point graphic to the graphics overlay
        graphicLayer.graphics.add(pointGraphic)
        // zoom to current location point
        mMapView.setViewpointAsync(Viewpoint(extent));

        // create buffer polygon
        // create buffer geometry 100 meter
//        val geometryBuffer = GeometryEngine.bufferGeodetic(
//            currentLocationPoint, 7.0,
//            LinearUnit(LinearUnitId.KILOMETERS), Double.NaN, GeodeticCurveType.GEODESIC
//        )
//
//        // create symbol for buffer geometry
//        val geodesicOutlineSymbol = SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, Color.BLACK, 2F)
//        // 0x4D00FF00 = Green Color with 25% Opacity (4D = 25%)
//        val geodesicBufferFillSymbol = SimpleFillSymbol(
//            SimpleFillSymbol.Style.SOLID,
//            0x4D00FF00.toInt(), geodesicOutlineSymbol
//        )
//
//        // new graphic
//        val graphicBuffer = Graphic(geometryBuffer, geodesicBufferFillSymbol)
//        graphicLayer.graphics.add(graphicBuffer)
    }

    private fun genShopPoint(x: Double, y: Double, addToMap: Boolean = false): Point {
        // create a point geometry with a location and spatial reference
        // Point(latitude, longitude, spatial reference)
        val point = Point(x, y, SpatialReferences.getWgs84())
        if (addToMap) {
            // code for  get image in drawable folder
            val pictureMarkerSymbol = PictureMarkerSymbol.createAsync(
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.marker
                ) as BitmapDrawable
            ).get()

            // set width, height, z from ground
            pictureMarkerSymbol.height = 52f
            pictureMarkerSymbol.width = 52f
            pictureMarkerSymbol.offsetY = 11f
            // create a graphic with the point geometry and symbol
            val pointGraphic = Graphic(point, pictureMarkerSymbol)

            // add the point graphic to the graphics overlay
            graphicLayer.graphics.add(pointGraphic)
        }
        return point
    }

    private fun addPointPictureSymbol() {
        // create a point geometry with a location and spatial reference
        // Point(latitude, longitude, spatial reference)
        val point = Point(100.54123476818575, 13.692994274846674, SpatialReferences.getWgs84())
        // code for  get image in drawable folder
        val pictureMarkerSymbol = PictureMarkerSymbol.createAsync(
            ContextCompat.getDrawable(
                requireContext(),
                R.drawable.marker
            ) as BitmapDrawable
        ).get()

        // set width, height, z from ground
        pictureMarkerSymbol.height = 52f
        pictureMarkerSymbol.width = 52f
        pictureMarkerSymbol.offsetY = 11f
        // create a graphic with the point geometry and symbol
        val pointGraphic = Graphic(point, pictureMarkerSymbol)

        // add the point graphic to the graphics overlay
        graphicLayer.graphics.add(pointGraphic)
    }

    private fun addPolygon() {
        // create a point collection with a spatial reference, and add five points to it
        val polygonPoints = PointCollection(SpatialReferences.getWgs84()).apply {
            // Point(latitude, longitude)
            add(Point(100.53113129005246, 13.699605900794168))
            add(Point(100.52806591440523, 13.697497348388572))
            add(Point(100.52862994352431, 13.693494620610096))
            add(Point(100.53566804601036, 13.691517057334085))
            add(Point(100.53510401689125, 13.69565086041018))
        }
        // create a polygon geometry from the point collection
        val polygon = Polygon(polygonPoints)
        val blueOutlineSymbol = SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, Color.YELLOW, 2f)
        // create an orange fill symbol with 20% transparency and the blue simple line symbol
        val polygonFillSymbol =
            SimpleFillSymbol(
                SimpleFillSymbol.Style.SOLID,
                Color.parseColor("#FB848A"),
                blueOutlineSymbol
            )

        // create a polygon graphic from the polygon geometry and symbol
        val polygonGraphic = Graphic(polygon, polygonFillSymbol)
        // add the polygon graphic to the graphics overlay
        graphicLayer.graphics.add(polygonGraphic)
    }

    private fun addLine() {
        // create a point collection with a spatial reference, and add three points to it
        val polylinePoints = PointCollection(SpatialReferences.getWgs84()).apply {
            // Point(latitude, longitude)
            add(Point(100.54123476818575, 13.710493830770453))
            add(Point(100.53798546999967, 13.708337727157508))
        }

        // create a polyline geometry from the point collection
        val polyline = Polyline(polylinePoints)

        // create a blue line symbol for the polyline
        val polylineSymbol =
            SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, Color.parseColor("#EA9035"), 3f)
        // create a polyline graphic with the polyline geometry and symbol
        val polylineGraphic = Graphic(polyline, polylineSymbol)

        // add the polyline graphic to the graphics overlay
        graphicLayer.graphics.add(polylineGraphic)
    }

    private fun addPoint() {
        // create a point geometry with a location and spatial reference
        // Point(latitude, longitude, spatial reference)
        val point = Point(100.53892960569901, 13.701952685466564, SpatialReferences.getWgs84())

        // create an opaque orange (0xFFFF5733) point symbol with a blue (0xFF0063FF) outline symbol
        val simpleMarkerSymbol =
            SimpleMarkerSymbol(SimpleMarkerSymbol.Style.CIRCLE, Color.YELLOW, 10f)
        val blueOutlineSymbol = SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, Color.RED, 2f)
        simpleMarkerSymbol.outline = blueOutlineSymbol
        // create a graphic with the point geometry and symbol
        val pointGraphic = Graphic(point, simpleMarkerSymbol)

        // add the point graphic to the graphics overlay
        graphicLayer.graphics.add(pointGraphic)
    }

    private fun addGraphics() {
        // create a graphics overlay and add it to the map view
        graphicLayer = GraphicsOverlay()
        mMapView.graphicsOverlays.add(graphicLayer)
    }


    override fun onPause() {
        super.onPause()
        mMapView.pause()
    }

    override fun onResume() {
        super.onResume()
        mMapView.resume()
    }

    override fun onDestroy() {
        super.onDestroy()
        mMapView.dispose()
    }
}