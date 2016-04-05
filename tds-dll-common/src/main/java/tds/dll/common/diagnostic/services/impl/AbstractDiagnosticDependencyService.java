package tds.dll.common.diagnostic.services.impl;

import tds.dll.common.diagnostic.services.DiagnosticDependencyService;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public abstract class AbstractDiagnosticDependencyService implements DiagnosticDependencyService {
    /**
     * Pings a HTTP URL. This effectively sends a HEAD request and returns <code>true</code> if the response code is in
     * the 200-399 range.
     * @param url The HTTP URL to be pinged.
     * @param timeout The timeout in millis for both the connection timeout and the response read timeout. Note that
     * the total timeout is effectively two times the given timeout.
     * @return <code>true</code> if the given HTTP URL has returned response code 200-399 on a HEAD request within the
     * given timeout, otherwise <code>false</code>.
     */
    public static boolean pingURL(String url, int timeout) {
        int responseCode = httpResponseCode(url, "HEAD", timeout);

        return (200 <= responseCode && responseCode <= 399);
    }

    public static boolean pingURL(String url) {
        return pingURL(url, 2000);
    }

    public static Integer httpResponseCode(String url, String method, int timeout) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setConnectTimeout(timeout);
            connection.setReadTimeout(timeout);
            connection.setRequestMethod(method);
            int responseCode = connection.getResponseCode();
            connection.disconnect();

            return responseCode;
        } catch (IOException exception) {
            return 500;
        }
    }

    public static Integer httpResponseCode(String url, String method) {
        return httpResponseCode(url, method, 2000);
    }

    public static Integer httpResponseCode(String url) {
        return httpResponseCode(url, "GET");
    }
}
