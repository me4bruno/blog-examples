
var selectedTower = null;
var allTowers = null;

// Initialisierung der Übersicht-Seite
$(document).on('pageinit', '#towers', function () {
  $.getJSON("towers.json", function(jsonTowers) {
    allTowers = jsonTowers;
    var towerItems = [];
    for (var i=0; i<jsonTowers.length; i++) {
      var tower = jsonTowers[i];
      towerItems.push( '<li><a href="#" data-id="' + tower.id + '" class="detailLink"><img src="' + tower.image + '"><h2>' + tower.name + '</h2><p>' + tower.city + ' / ' + tower.state + '</p><span class="ui-li-count">' + tower.height + ' m</span></a></li>' );
    }
    $('#towerList').empty();
    $('#towerList').append( towerItems.join( "" ) );
    $('#towerList').listview('refresh');
  });
});

// Transition zur Detail-Seite
$(document).on('vclick', '#towerList li a.detailLink', function () {
  var selectedTowerIdAsString = $(this).attr('data-id');
  selectedTower = getTowerById(allTowers, selectedTowerIdAsString);
  $(":mobile-pagecontainer").pagecontainer( "change", "#details", { transition: "slide" } );
});

// Anzeigen der Detail-Seite
$(document).on('pageshow', '#details', function (e, data) {
  $('#details h1').text(selectedTower.name);
  $('#details #towerData #towerName').text(selectedTower.name);
  $('#details #towerData #towerScore').text(selectedTower.id);
  $('#details #towerData #towerHeight').text(selectedTower.height);
  $('#details #towerData #towerCity').text(selectedTower.city);
  $('#details #towerData #towerState').text(selectedTower.state);
  $('#details #towerData #towerYear').text(selectedTower.year);
  $('#details #towerImageTag').animate({width: "20%"}, {duration: 0}).attr("src",selectedTower.image);
  $('#details #popupImage').attr("src",selectedTower.image);
});

// Initialisierung der Karten-Seite
var map = null;
var marker = null;
var popup = null;
$(document).on('pageinit', '#map', function(){
  map = L.map('mapElement');
  L.tileLayer('http://{s}.tile.osm.org/{z}/{x}/{y}.png', {
    minZoom: 2,
    maxZoom: 18,
    attribution: 'Beispiel JQuery Mobile von <a href="http://andreas-bruns.com">Andreas Bruns</a>'
  }).addTo(map);
  marker = L.marker([0,0]).addTo(map);
  marker.bindPopup();
});

// Anzeigen der Karten-Seite
$(document).on('pageshow', '#map', function (e, data) {
  recalculateMapSize();
  map.setView(selectedTower.location, 14);
  marker.setLatLng(selectedTower.location);
  marker.getPopup().setContent("<b>" + selectedTower.name + "</b><br>H&ouml;he: " + selectedTower.height + " m");
  var showPopup = $("#settings #popup-yes").prop('checked');
  if (showPopup) {
    marker.openPopup();
  } else {
    marker.closePopup();
  }
});

function recalculateMapSize() {
  // Use full height in desktop browser: checked on MacOS with Firefox, Chrome and Safari
  $(window).resize();
  // Avoid grey tiles in mobile browser: checked on iOS with Chrome and Safari
  // https://groups.google.com/forum/#!topic/leaflet-js/Br-gY0aJ5Dc
  map.invalidateSize(false);
};

function getTowerById(allTowers, id) {
  for (var i=0; i<allTowers.length; i++) {
    if (allTowers[i].id === id) {
      return allTowers[i];
    }
  }
  return;
};
