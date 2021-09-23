package Classes;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import java.util.Iterator;

/*
Ker ima JSON za Head file drugacno sintakso za prevorbo ima jo HEAD tag-i
svoj class za zgradbo HTML-a
*/
public class HTMLHead extends HTMLTag {

    public HTMLHead(JSONObject headObject) throws JSONException {
        Iterator<String> tags = headObject.keys();
        while (tags.hasNext()) {
            String tag = tags.next();

            //HANDLE META TAGS
            if (tag.equals("meta")) {
                JSONObject metaObject = headObject.getJSONObject("meta");

                Iterator<String> metaTags = metaObject.keys();
                while (metaTags.hasNext()) {
                    String metaTag = metaTags.next();

                    HTMLTag newHTMLTag = new HTMLTag();
                    newHTMLTag.tagName = tag;
                    if (!metaTag.equals("charset") && !metaTag.equals("viewport")) {
                        newHTMLTag.attributes.add("name=\"" +  metaTag + "\" " + "content=\"" + metaObject.get(metaTag) + "\"");
                    }
                    else {
                        if (metaTag.equals("viewport")) {
                            String cleanUp = metaObject.get(metaTag).toString();
                            cleanUp = cleanUp.replaceAll("[{}\"]", "");
                            cleanUp = cleanUp.replaceAll(":", "=");
                            System.out.println("I GOT HERE");
                            newHTMLTag.attributes.add(metaTag + "=\"" +  cleanUp + "\"");
                        }
                        else {
                            newHTMLTag.attributes.add(metaTag + "=\"" +  metaObject.get(metaTag) + "\"");
                        }
                    }
                    this.tags.add(newHTMLTag);
                }
            }

            //HANDLE LINK ARRAY
            else if (tag.equals("link")) {
                JSONArray jsonArray = headObject.getJSONArray(tag);


                for (int i = 0; i < jsonArray.length(); ++i) {
                    JSONObject linkObject = jsonArray.getJSONObject(i);

                    HTMLTag newHTMLTag = new HTMLTag();
                    newHTMLTag.tagName = tag;
                    Iterator<String> linkTags = linkObject.keys();
                    while (linkTags.hasNext()) {
                        String linkTag = linkTags.next();
                        newHTMLTag.attributes.add(linkTag + "=\"" +  linkObject.get(linkTag) + "\"");
                    }
                    this.tags.add(newHTMLTag);
                }
            }
            else  {
                //OTHER TAG HANDLE
                HTMLTag newHTMLTag = new HTMLTag();
                newHTMLTag.tagName = tag;
                newHTMLTag.content = headObject.get(tag).toString();
                this.tags.add(newHTMLTag);
            }
        }
    }

    //Override ki vrne HTML string
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("");
        builder.append("<head>\n");
        for (HTMLTag curr : this.tags) {
            builder.append("\t" + curr.generateTag() + "\n");
        }
        builder.append("</head>\n");
        return builder.toString();
    }
}
