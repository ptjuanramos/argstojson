package argstojson;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Juan Ramos
 */
public class ArgsToJson {
	/**
	 * Front method that is used to handle --[args ...] argument type
	 * <br>Example: $ --home.numberOfRooms=3 --home.numberOfBathRooms=2
	 * <br>The JSONObject::String result is: $ {"home":{"numberOfRooms":3, "numberOfBathRooms":2}}
	 * <br>
	 * <br>Make sure that your commands have the '--' prefix, otherwise it will throw an {@link JSONException}
	 * @param args
	 * @return {@link JSONObject} that has a recursive hierarchy structure:
	 * <br>{group: {group1: {...}, attribute: value} }
	 * <br>Following the example:
	 * <br>{"home":{"numberOfRooms":3, "numberOfBathRooms":2}}
	 * @throws {@link JSONException} if something went wrong using {@link JSONObject} operations
	 * <br> Check {@link JSONObject} for more documentation
	 */
	public static JSONObject getJsonObject(String... args) {
		JSONObject jsonArgs = new JSONObject();

		for (String arg: args) {
			String cleanArg = StringUtils.remove(arg, "-");
			String groupAndAtt = cleanArg.split("=")[0];
			String attValue = cleanArg.split("=")[1];

			if(groupAndAtt.split("\\.").length >= 2) {
				String group = groupAndAtt.split("\\.")[0];
				String attribute = groupAndAtt.split("\\.")[1];

				JSONObject attAndValueJSON = new JSONObject();
				attAndValueJSON.put(attribute, attValue);

				if(jsonArgs.isNull(group)) {
					jsonArgs.putOpt(group, attAndValueJSON);
				} else {
					jsonArgs.putOpt(group, mergeJSONObjects(jsonArgs.getJSONObject(group), attAndValueJSON));
				}
			} else {
				jsonArgs.putOpt(groupAndAtt, attValue);
			}
		}

		return jsonArgs;
	}

	/**
	 * Private method that merges two {@link JSONObject} into one single JSON subset
	 * <br>Example: mergeJSONObject({"numberOfRooms":3}, {"numberOfBathrooms":2}) -> {"numberOfRooms":3, "numberOfBathrooms":2}
	 * <br>Usefull to avoid loops to find the right {@link JSONObject}
	 * @param json1 {@link JSONObject}
	 * @param json2 {@link JSONObject}
	 * @return {@link JSONObject}
	 */
	private static JSONObject mergeJSONObjects(JSONObject json1, JSONObject json2) {
		JSONObject mergedJSON = new JSONObject();
		
		mergedJSON = new JSONObject(json1, JSONObject.getNames(json1));
		for (String crunchifyKey : JSONObject.getNames(json2)) {
			mergedJSON.put(crunchifyKey, json2.get(crunchifyKey));
		}

		return mergedJSON;
	}

}
