(function executeRule(current, previous) {
  var REST_MESSAGE_NAME = "Nick Arellano - OCP Neptune EDA";
  var EVENT_NAME = "SERVICE_CATALOG";

  var r = new sn_ws.RESTMessageV2(REST_MESSAGE_NAME, "POST");

  var json = { event: EVENT_NAME };

  if (current.cat_item) {
    json["catalog_item"] = current.cat_item.getDisplayValue();
  }

  if (current.opened_by) {
    var requester = current.opened_by.getRefRecord();
    if (requester.isValidRecord()) {
      json["requester"] = requester.getValue("email");
    }
  }

  if (current.request) {
    json["request_name"] = current.request.getValue("short_description");
  }

  for (var key in current.variables) {
    if (current.variables.hasOwnProperty(key)) {
      var variable = current.variables[key];
      json[key] =
        variable.getDisplayValue() != null
          ? variable.getDisplayValue()
          : JSON.parse(String(variable));
    }
  }

  var jsonString = JSON.stringify(json);
  r.setRequestBody(jsonString);
  r.setTimeout(10000);
  r.execute();
})(current, previous);
