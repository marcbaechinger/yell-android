package yellcast.com.net;

import android.os.AsyncTask;
import android.util.Log;

import org.xml.sax.helpers.DefaultHandler;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class AsyncXmlParseTask extends AsyncTask<URL, Integer, DefaultHandler> {
    private static final String TAG = AsyncXmlParseTask.class.getCanonicalName();
    private DefaultHandler defaultHandler;
    private CompletionListener listener;

    public interface CompletionListener {
        public void onCompleted(DefaultHandler parser);
    }

    public AsyncXmlParseTask(DefaultHandler parser, AsyncXmlParseTask.CompletionListener listener) {
        this.listener = listener;
        defaultHandler = parser == null ? new DefaultHandler() : parser;
    }

    @Override
    protected void onCancelled(DefaultHandler defaultHandler) {

    }

    @Override
    protected DefaultHandler doInBackground(URL... urls) {
        if (urls.length > 0) {
            URL url = urls[0];
            InputStream is = null;
            try {
                URLConnection conn = url.openConnection();
                conn.setDoOutput(false);
                conn.setDoInput(true);
                is = conn.getInputStream();
                parse(is, defaultHandler);
            } catch (IOException e) {
                Log.e("error while downloading " + url.toExternalForm(), e.getMessage());
            } finally {
                if (is != null) {
                    try { is.close(); } catch (IOException e) { /* ignored */}
                }
            }
        }
        return defaultHandler;
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
