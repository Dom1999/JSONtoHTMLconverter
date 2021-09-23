package Classes;

import java.util.ArrayList;
import java.util.List;

/*
HTML tag je vsak majsi element ki je lahko v Body-u example
<tagName attributes(list STRING)>
    tags(list HTMLTag)
</tagName>
 */

public class HTMLTag {
    String tagName = "";
    String content = "";
    List<String> attributes = new ArrayList<String>();
    List<HTMLTag> tags = new ArrayList<HTMLTag>();

    public HTMLTag() {
    }

    //util metoda za kreacijo string za atribute
    public static String generateAttribute(String identifier, String content) {
        return identifier + "=\"" + content + "\"";
    }


    //metoda glede na spremenjene podatke zgenerira HTML sintakso za posmezni tag
    public String generateTag() {
        String str = "<" + this.tagName;

        if (this.attributes.isEmpty()  && this.tags.isEmpty() && this.content == "") {
            return "<" + this.tagName + "/>";
        }
        else if (this.attributes.isEmpty() && this.tags.isEmpty()){
           return "<" + this.tagName + ">" + content + "</" + this.tagName + ">";
        }
        else if (this.tags.isEmpty()) {
            String attributesString = "";

            for (String curr : this.attributes ) {
                attributesString += curr + " ";
            }
            return "<" + this.tagName + " " + attributesString + "/>";
        }
        else {
            String attributesString = "";
            String tagString = "";

            for (String curr : this.attributes ) {
                attributesString += curr + " ";
            }

            for (HTMLTag curr : this.tags ) {
                tagString += curr.generateTag() + "\n";
            }

            return "<" + this.tagName + " " + attributesString + ">\n" + tagString + "\n</" + this.tagName + ">";

        }

    }
}

