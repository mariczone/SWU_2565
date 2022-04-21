package com.example.myapplication

import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.esri.arcgisruntime.ArcGISRuntimeEnvironment
import com.esri.arcgisruntime.geometry.*
import com.esri.arcgisruntime.mapping.ArcGISMap
import com.esri.arcgisruntime.mapping.Basemap
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
    private lateinit var mMapView: MapView
    private lateinit var graphicLayer: GraphicsOverlay
    private lateinit var shopPointAdapter: ShopPointAdapter
    private lateinit var shopViewPager2: ViewPager2

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
        val map = ArcGISMap(Basemap.Type.DARK_GRAY_CANVAS_VECTOR, latitude, longitude, levelOfDetail)
        mMapView.map = map
        mMapView.isAttributionTextVisible = false
        addGraphics()

        // Create List of Page
        val shopPointList = listOf<ShopPointViewItem>(
            ShopPointViewItem("Shop 1", "11/56 Soi Phetchaburi 47 · 02 821 5889", R.drawable.onboarding1, genShopPoint(100.53892960569901, 13.701952685466564)),
            ShopPointViewItem("Shop 2", "21/56 Soi Phetchaburi 47 · 02 821 5889", R.drawable.onboarding2, genShopPoint(100.53033429238417, 13.71200666513494)),
            ShopPointViewItem("Shop 3", "31/56 Soi Phetchaburi 47 · 02 821 5889", R.drawable.onboarding3, genShopPoint(100.52811496041558, 13.695102867638928))
        )
        shopPointAdapter = ShopPointAdapter(shopPointList)
        shopViewPager2 = view.findViewById<ViewPager2>(R.id.shopViewPager2);
        shopViewPager2.adapter = shopPointAdapter;
        mMapView.setViewpointCenterAsync(shopPointList[0].location, 20000.0)
        shopViewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                mMapView.setViewpointCenterAsync(shopPointList[position].location, 20000.0)
            }
        })
//        addPoint()
//        addLine()
//        addPolygon()
//        addPointPictureSymbol()
    }

    private fun genShopPoint(x: Double, y: Double): Point {
        // create a point geometry with a location and spatial reference
        // Point(latitude, longitude, spatial reference)
        val point = Point(x, y, SpatialReferences.getWgs84())
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
            SimpleFillSymbol(SimpleFillSymbol.Style.SOLID, Color.parseColor("#FB848A"), blueOutlineSymbol)

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
        val polylineSymbol = SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, Color.parseColor("#EA9035"), 3f)
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
        val simpleMarkerSymbol = SimpleMarkerSymbol(SimpleMarkerSymbol.Style.CIRCLE, Color.YELLOW, 10f)
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