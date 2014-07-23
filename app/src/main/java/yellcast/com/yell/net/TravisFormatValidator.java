package yellcast.com.yell.net;

import android.util.Log;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Created by marcbaechinger on 22.07.14.
 */
public class TravisFormatValidator extends FormatValidator {

    private static final String TAG = TravisFormatValidator.class.getCanonicalName();

    private String rootElement;
    private int textElementCount = 0;
    StringBuilder buf;



    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (rootElement == null) {
            rootElement = qName;
        }
        if ("text".equals(qName)) {
            textElementCount++;
        }
    }

    @Override
    public boolean isValid() {
        return "svg".equals(rootElement) && textElementCount == 4;
    }
}
