package yellcast.com.net;

import org.xml.sax.helpers.DefaultHandler;

public abstract class FormatValidator extends DefaultHandler {
    public abstract boolean isValid();
}
