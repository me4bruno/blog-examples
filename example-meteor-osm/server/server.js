Meteor.startup(function () {
  if (Votes.find().count() === 0) {
    var votes = [
      {name: "Stefan", party: "SPD", longitude: 8.8, latitude: 53.05},
      {name: "Claudia", party: "FDP", longitude: 11.5, latitude: 48.15 },
      {name: "Otto", party: "Piraten", longitude: 13.4, latitude: 52.5 },
      {name: "Melanie", party: "CDU", longitude: 8.7, latitude: 50.1 },
      {name: "Else", party: "Gruene", longitude: 10, latitude: 53.5 },
      {name: "Marvin", party: "SPD", longitude: 7, latitude: 50.95 },
      {name: "Michael", party: "Linke", longitude: 12.4, latitude: 51.35 }
    ];
    for (var i = 0; i < votes.length; i++) {
      Votes.insert({name: votes[i].name, party: votes[i].party, longitude: votes[i].longitude, latitude: votes[i].latitude, created: new Date()});
    }
  }
});