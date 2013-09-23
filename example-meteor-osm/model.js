Votes = new Meteor.Collection("votes");
Votes.allow( {
  insert: function(id, doc) {
    doc.created = new Date();
    return true;
  },
  update: function (id,doc) {
    return true;
  }
});