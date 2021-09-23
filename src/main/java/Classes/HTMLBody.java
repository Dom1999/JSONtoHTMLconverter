package Classes;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import java.util.Iterator;

/*
Vsak Tag v Body-u ima enako sintakso v JSON-u zato ima
 */
public class HTMLBody extends HTMLTag {
    public HTMLBody(JSONObject bodyObject) throws JSONException {

        this.tagName = "body";
        if (bodyObject.toString().contains("attributes")) {
            //SETTING ATTRIBUTES
            JSONObject attributes = bodyObject.getJSONObject("attributes");

            Iterator<String> keys = attributes.keys();
            while (keys.hasNext()) {
                String key = keys.next();
                String content;

                //Style Tag mora pretvorit v CSS sintakso
                if (key.equals("style")) {
                    content = attributes.getString(key);
                    content = content.replaceAll("[{}\"]", "");
                    content = content.replaceAll("[,]", ";");
                }
                else {
                    content = attributes.getString(key);
                }

                this.attributes.add(generateAttribute(key, content));
            }
        }

        //TAGS
        Iterator<String> tags = bodyObject.keys();
        while (tags.hasNext()) {
            String tag = tags.next();

            if (!(bodyObject.get(tag) instanceof JSONObject)) {
                HTMLTag newHTMLtag = new HTMLTag();
                newHTMLtag.tagName = tag;
                newHTMLtag.content = bodyObject.get(tag).toString();


                this.tags.add(newHTMLtag);
            }//HANDELING DIV
            else if (bodyObject.get(tag) instanceof JSONObject && !tag.equals("attributes")) {
                HTMLBody div = new HTMLBody((JSONObject) bodyObject.get(tag));
                div.tagName = tag;

                this.tags.add(div);

            }

        }
    }

    //Override ki vrne HTML string
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("");
        builder.append("<"+ tagName + " ");

        if (this.attributes.isEmpty()) {
            builder.append(">\n");
        }
        else {
            for (String curr : this.attributes) {
                builder.append(curr + " ");
            }
            builder.append(">\n");
        }

        if (!this.tags.isEmpty()) {
            for (HTMLTag curr : this.tags) {
                if (curr.tagName == "div") {
                    builder.append(curr.toString());
                }
                else {
                    builder.append(curr.generateTag() + "\n");
                }
            }
        }


        builder.append("</"+ tagName + ">\n");



        return builder.toString();
    }
}
