package yellcast.com.yell.net;

import android.os.AsyncTask;
import android.util.Log;

import org.w3c.dom.Document;
import org.xml.sax.helpers.DefaultHandler;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Created by marcbaechinger on 22.07.14.
 */
public class AsyncXmlParseTask extends AsyncTask<URL, Integer, DefaultHandler> {
    private static final String TAG = AsyncXmlParseTask.class.getCanonicalName();
    private DefaultHandler defautHandler;
    private CompletionListener listener;

    public interface CompletionListener {
        public void onCompleted(DefaultHandler parser);
    }

    public AsyncXmlParseTask(DefaultHandler parser, AsyncXmlParseTask.CompletionListener listener) {
        this.listener = listener;
        defautHandler = parser == null ? new DefaultHandler() : parser;
    }

    @Override
    protected void onCancelled(DefaultHandler defaultHandler) {

    }

    @Override
    protected DefaultHandler doInBackground(URL... urls) {
        Document doc = null;
        if (urls.length > 0) {
            URL url = urls[0];
            InputStream is = null;
            try {
                URLConnection conn = url.openConnection();
                conn.setDoOutput(false);
                conn.setDoInput(true);
                parse(conn.getInputStream(), defautHandler);
            } catch (IOException e) {
                Log.e("error while downloading " + url.toExternalForm(), e.getMessage());
            } finally {
                if (is != null) {
                    try { is.close(); } catch (IOException e) {}
                }
            }
        }
        return defautHandler;
    }

    public void parse(InputStream is, DefaultHandler handler) {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
            SAXParser parser = factory.newSAXParser();
            parser.parse(is, handler);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPostExecute(DefaultHandler parser) {
        Log.d(TAG, "retrieved parser in onPostExecute: " + parser);
        if (listener != null) {
            listener.onCompleted(parser);
        }
    }


}
