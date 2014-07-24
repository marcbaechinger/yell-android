package yellcast.com.yell.validator;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import yellcast.com.net.FormatValidator;

/**
 * Created by marcbaechinger on 22.07.14.
 */
public class TravisSvgBuildBadgeValidator extends FormatValidator {

    private static final String TAG = TravisSvgBuildBadgeValidator.class.getCanonicalName();

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
