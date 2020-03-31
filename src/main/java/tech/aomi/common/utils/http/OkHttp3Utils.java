package tech.aomi.common.utils.http;

import lombok.Getter;
import lombok.Setter;
import okhttp3.*;
import okio.Buffer;

import javax.net.ssl.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 田尘殇Sean(sean.snow @ live.com) createAt 2017/6/5.
 */
public class OkHttp3Utils {

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public static String stringBody(Response response) throws IOException {
        if (null == response) {
            return "";
        }
        ResponseBody body = response.body();
        if (null == body) {
            return "";
        }
        return body.string();
    }


    public static byte[] bytesBody(Response response) throws IOException {
        if (null == response) {
            return new byte[0];
        }
        ResponseBody body = response.body();
        if (null == body) {
            return new byte[0];
        }

        return body.bytes();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private OkHttpClient.Builder builder;

        public Builder() {
            this.builder = new OkHttpClient.Builder();
        }

        public Builder setCertificate(String cerStr) {
            X509TrustManager trustManager;
            SSLSocketFactory sslSocketFactory;
            try {
                trustManager = trustManagerForCertificates(trustedCertificatesInputStream(cerStr));
                SSLContext sslContext = SSLContext.getInstance("TLS");
                sslContext.init(null, new TrustManager[]{trustManager}, null);
                sslSocketFactory = sslContext.getSocketFactory();
            } catch (GeneralSecurityException e) {
                throw new RuntimeException(e);
            }
            this.builder.sslSocketFactory(sslSocketFactory, trustManager);
            return this;
        }

        public Builder keepCookie() {
            this.builder.cookieJar(new CookieJarImpl());
            return this;
        }

        public Builder keepCookie(CookieJar cookieJar) {
            this.builder.cookieJar(cookieJar);
            return this;
        }

        public OkHttpClient.Builder nativeBuilder() {
            return this.builder;
        }

        public OkHttpClient build() {
            return this.builder.build();
        }
    }

    /**
     * Returns an input stream containing one or more certificate PEM files. This implementation just
     * embeds the PEM files in Java strings; most applications will instead read this from a resource
     * file that gets bundled with the application.
     */
    private static InputStream trustedCertificatesInputStream(String str) {
        return new Buffer().writeUtf8(str).inputStream();
    }

    private static X509TrustManager trustManagerForCertificates(InputStream in) throws GeneralSecurityException {
        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
        Collection<? extends Certificate> certificates = certificateFactory.generateCertificates(in);
        if (certificates.isEmpty()) {
            throw new IllegalArgumentException("expected non-empty set of trusted certificates");
        }

        // Put the certificates a key store.
        char[] password = "".toCharArray(); // Any password will work.
        KeyStore keyStore = newEmptyKeyStore(password);
        int index = 0;
        for (Certificate certificate : certificates) {
            String certificateAlias = Integer.toString(index++);
            keyStore.setCertificateEntry(certificateAlias, certificate);
        }

        // Use it to build an X509 trust manager.
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        keyManagerFactory.init(keyStore, password);
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(keyStore);
        TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
        if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
            throw new IllegalStateException("Unexpected default trust managers:" + Arrays.toString(trustManagers));
        }
        return (X509TrustManager) trustManagers[0];
    }

    private static KeyStore newEmptyKeyStore(char[] password) throws GeneralSecurityException {
        try {
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            InputStream in = null; // By convention, 'null' creates an empty key store.
            keyStore.load(in, password);
            return keyStore;
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }

    public static class CookieJarImpl implements CookieJar, Serializable {

        private static final long serialVersionUID = -8228414981777690811L;

        private Map<String, CookieStore> cookieStores = new HashMap<>();

        @Override
        public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
            if (null != cookies && !cookies.isEmpty()) {
                CookieStore cookieStore = cookieStores.get(url.uri().toString());
                CookieStore newStore = new CookieStore(cookies);
                if (null == cookieStore) {
                    cookieStore = newStore;
                } else {
                    cookieStore.addAll(newStore);
                }
                cookieStores.put(url.host(), cookieStore);
            }
        }

        @Override
        public List<Cookie> loadForRequest(HttpUrl url) {
            CookieStore cookieStore = cookieStores.get(url.host());
            return null == cookieStore || cookieStore.isEmpty() ? new ArrayList<>() : cookieStore.getCookies();
        }

        public Map<String, CookieStore> getCookieStores() {
            return cookieStores;
        }

        public void setCookieStores(Map<String, CookieStore> cookieStores) {
            this.cookieStores = cookieStores;
        }
    }

    @Getter
    @Setter
    public static class SerializableCookie implements Serializable {

        private static final long serialVersionUID = -27212034314709271L;

        private String name;
        private String value;
        private Long expiresAt;
        private String domain;
        private String path;
        private Boolean secure;
        private Boolean httpOnly;

        public SerializableCookie(Cookie cookie) {
            this.name = cookie.name();
            this.value = cookie.value();
            this.expiresAt = cookie.expiresAt();
            this.domain = cookie.domain();
            this.path = cookie.path();
            this.secure = cookie.secure();
            this.httpOnly = cookie.httpOnly();
        }

        public Cookie getCookie() {
            Cookie.Builder builder = new Cookie.Builder().name(this.name).value(this.value).expiresAt(this.expiresAt).domain(this.domain).path(this.path);
            if (this.secure) {
                builder.secure();
            }
            if (this.httpOnly) {
                builder.httpOnly();
            }
            return builder.build();
        }
    }

    public static class CookieStore extends ArrayList<SerializableCookie> {
        private static final long serialVersionUID = 3032216414783563417L;

        public CookieStore(List<Cookie> cookies) {
            cookies.forEach(cookie -> this.add(new SerializableCookie(cookie)));
        }

        public List<Cookie> getCookies() {
            return stream().map(SerializableCookie::getCookie).collect(Collectors.toList());
        }
    }

}
