Template.election.votes = function () {
  return Votes.find({}, {sort: {created: -1}, limit: 10});
};
 
Template.election.events({
  'click input.add': function (event, template) {
    var name = template.find("th .name").value;
    var party = template.find("th .party").value;
    var deltaLongitude = ((Math.random() - 0.5) * 2.0);
    var longitude = parseFloat(template.find("th .location").value.split(";")[0]) + deltaLongitude;
    var deltaLatitude = ((Math.random() - 0.5) * 1.0);
    var latitude = parseFloat(template.find("th .location").value.split(";")[1]) + deltaLatitude;
    Votes.insert({name: name, party: party, longitude: longitude, latitude: latitude});
  }
});
 
Template.vote.prettifyDate = function(timestamp) {
  var prettifiedDate = "";
  if(typeof timestamp != "undefined") {
    var s = timestamp.getSeconds();
    var min = timestamp.getMinutes();
    var h = timestamp.getHours();
    var d = timestamp.getDate();
    var m = timestamp.getMonth() + 1;
    var y = timestamp.getFullYear();   
    prettifiedDate = d + '.' + m + '.' + y + '  ' + h + ':' + (min <= 9 ? '0' + min : min) + ':' + (s <= 9 ? '0' + s : s);
  };
  return prettifiedDate;
};

var osmMap = null;
var votesVectorLayer = null;
var proj4326 = null;
var projmerc = null;
var partyColorMap = null;
 
Template.map.rendered = function () {
  if (!osmMap) {
    Deps.autorun(function () {
      var newest = Votes.findOne({}, {sort: {created: -1}, limit: 1});
      if (newest) {
        Session.set("lastVote", newest);
      }
    });
 
    proj4326 = new OpenLayers.Projection("EPSG:4326");
    projmerc = new OpenLayers.Projection("EPSG:900913");
    partyColorMap = {"CDU":"#aaa", "SPD":"#f44", "Gruene": "#4f4", "FDP":"#ff4", "Linke": "#d4d", "Piraten": "#fa4"};
 
    var mapCenterPositionAsLonLat = new OpenLayers.LonLat(7, 51.3);
    var mapCenterPositionAsMercator = mapCenterPositionAsLonLat.transform(proj4326, projmerc);
    var mapZoom = 6;
  
    osmMap = new OpenLayers.Map("map", {
       controls: [
          new OpenLayers.Control.KeyboardDefaults(),
          new OpenLayers.Control.Navigation(),
          new OpenLayers.Control.LayerSwitcher({'ascending':false}),
          new OpenLayers.Control.PanZoomBar(),
          new OpenLayers.Control.MousePosition()
        ],
        maxExtent: new OpenLayers.Bounds(-20037508.34,-20037508.34, 20037508.34, 20037508.34),
        numZoomLevels: 18,
        maxResolution: 156543,
        units: 'm',
        projection: projmerc,
        displayProjection: proj4326
    } );
  
    var osmLayer = new OpenLayers.Layer.OSM("OpenStreetMap");
    osmMap.addLayer(osmLayer);
    votesVectorLayer = new OpenLayers.Layer.Vector("Votes Layer", {
                styleMap: new OpenLayers.StyleMap({
                    pointRadius: 10,
                    fillColor: "${color}"
                })});
    osmMap.addLayer(votesVectorLayer);
    osmMap.setCenter(mapCenterPositionAsMercator, mapZoom);
 
    selectControl = new OpenLayers.Control.SelectFeature(votesVectorLayer);
    osmMap.addControl(selectControl);
    selectControl.activate();
 
    votesVectorLayer.events.on({
      'featureselected' : function(evt) {
        feature = evt.feature;
        popup = new OpenLayers.Popup.FramedCloud("featurePopup",
          feature.geometry.getBounds().getCenterLonLat(),
            new OpenLayers.Size(100, 100), "<h2>"
            + feature.attributes.title + "</h2>"
            + feature.attributes.description, null, true,
          function (evt) { selectControl.unselect(this.feature); });
        feature.popup = popup;
        popup.feature = feature;
        osmMap.addPopup(popup);
      },
 
      'featureunselected' : function(evt) {
        feature = evt.feature;
        if (feature.popup) {
          popup.feature = null;
          osmMap.removePopup(feature.popup);
          feature.popup.destroy();
          feature.popup = null;
        }
      }
    });
  }
 
   Votes.find().forEach(function (vote) {
     createOsmVote(vote);
   });
 
  var self = this;
  if (! self.handle) {
    self.handle = Deps.autorun( function(){
      var vote = Session.get("lastVote");
      if (vote != null) {
        createOsmVote(vote);
      };
    });
  }
};
 
function createOsmVote(vote) {
     var votePoint = new OpenLayers.Geometry.Point(vote.longitude, vote.latitude).transform(proj4326, projmerc);
     var voteAttributes = {
        title: vote.party,
        description: ("<b>" + vote.name + ": </b>" + Template.vote.prettifyDate(vote.created)),
        color: partyColorMap[vote.party]
      };
     var voteFeature = new OpenLayers.Feature.Vector(votePoint, voteAttributes);
     votesVectorLayer.addFeatures(voteFeature);
};