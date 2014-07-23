package yellcast.com.yell.net;

import org.xml.sax.helpers.DefaultHandler;

/**
 * Created by marcbaechinger on 22.07.14.
 */
public abstract class FormatValidator extends DefaultHandler {
    public abstract boolean isValid();
}
