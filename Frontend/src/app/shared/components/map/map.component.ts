import { Component, Input, OnInit } from '@angular/core';
import Map from 'ol/Map';
import View from 'ol/View';
import OSM from 'ol/source/OSM';
import Feature from 'ol/Feature';
import Point from 'ol/geom/Point';
import { Icon, Style } from 'ol/style';
import VectorSource from 'ol/source/Vector';
import { Tile as TileLayer, Vector as VectorLayer } from 'ol/layer';
import MouseWheelZoom from 'ol/interaction/MouseWheelZoom';
import * as interactions from 'ol/interaction';

@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.scss']
})
export class MapComponent implements OnInit {

  shiftPressed: boolean = false;
  mapFocused: boolean = false;
  mapScrollMessageDisplayed: boolean = false;
  @Input() lat: number = 0;
  @Input() lon: number = 0;
  coords = [0, 0];

  private map!: Map;

  iconStyle = new Style({
    image: new Icon({
      anchor: [0.5, 30],
      anchorXUnits: 'fraction',
      anchorYUnits: 'pixels',
      src: "assets/images/place.png"
    }),
  });

  iconFeature = new Feature({});

  vectorSource = new VectorSource({
    features: [this.iconFeature],
  });

  vectorLayer = new VectorLayer({
    source: this.vectorSource,
  });

  mouseWheelZoomInteraction = new MouseWheelZoom();

  ngOnInit(): void {
    this.coords = this.convertCoordinates(this.lat, this.lon);
    this.vectorLayer.getSource()?.getFeatures()[0].setStyle(this.iconStyle);
    this.vectorLayer.getSource()?.getFeatures()[0].setGeometry(new Point(this.coords));
    this.mouseWheelZoomInteraction.setActive(false);
    this.map = new Map({
      view: new View({
        // projection: 'EPSG:4326',
        center: this.coords,
        zoom: 16,
      }),
      layers: [
        new TileLayer({
          source: new OSM(),
        }),
        this.vectorLayer
      ],
      interactions: [
        this.mouseWheelZoomInteraction,
        new interactions.DragRotate(),
        new interactions.DragPan(),
        new interactions.DoubleClickZoom(),
        new interactions.PinchZoom(),
        new interactions.PinchRotate()
      ],
      target: 'ol-map',
    });

    document.onscroll = () => {
      if(this.mapFocused && !this.mapScrollMessageDisplayed){
        this.mapScrollMessageDisplayed = true;
          setTimeout(
              () => { 
                this.mapScrollMessageDisplayed = false;
              },
              2000
          )
      }
    }
  }

  convertCoordinates(lon: number, lat: number) {
    var x = (lon * 20037508.34) / 180;
    var y = Math.log(Math.tan(((90 + lat) * Math.PI) / 360)) / (Math.PI / 180);
    y = (y * 20037508.34) / 180;
    return [x, y];
  }

  onMouseDown() {
    this.mapScrollMessageDisplayed = false;
  }

  onKeyDown(event: any){
    if (event.shiftKey) {
      this.shiftPressed = true;
      this.mapScrollMessageDisplayed = false;
      this.mouseWheelZoomInteraction.setActive(true);
    }
  }

  onKeyUp(){
    this.shiftPressed = false;
    this.mouseWheelZoomInteraction.setActive(false);
    this.mapFocused = false;
  }

  onMouseEnterMap() {
    document.getElementById('map-container')!.focus({ preventScroll: true });
    this.mapFocused = true;
  }

  onMouseLeaveMap() {
    document.getElementById('map-container')!.blur();
    this.mapFocused = false;
  }

}
