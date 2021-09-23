package Classes;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/*
Glavni HTML class, ki zgradi osnovno obliko html objekta z JSONObject-i in
in ga shrani v notranje HTML objekte.
 */

public class HTML {
    String lang = "en";
    String doctype = "html";
    HTMLHead head;
    HTMLBody body;

    //Konstruktor iz JSONObject-a
    public HTML(JSONObject json) throws JSONException {
        //BUILD HEAD
        JSONObject headObject = json.getJSONObject("head");
        this.head = new HTMLHead(headObject);

        //BUILD BODY
        JSONObject bodyObject = json.getJSONObject("body");
        this.body = new HTMLBody(bodyObject);
    }


    //Override toString metode da vrne HTML string
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("");

        builder.append("<!DOCTYPE " + doctype + ">\n");
        builder.append("<html lang=\"" + lang + "\">\n");

        builder.append(head.toString());
        builder.append(body.toString());

        builder.append("</html>");
        return  builder.toString();
    }
}
