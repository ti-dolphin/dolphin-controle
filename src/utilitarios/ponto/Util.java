/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilitarios.ponto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ProtocolException;
import java.net.URL;
import java.security.cert.X509Certificate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 *
 * @author Engenharia01
 */
public class Util {

    private AdvancedConfigurationResult advanced = new AdvancedConfigurationResult();
    private LoadAFD load_afd = new LoadAFD();
    private SimpleConfigurationResult simple = new SimpleConfigurationResult();

    public String ReadResult(HttpsURLConnection http) {
        BufferedReader br;
        String result = "";
        StringBuilder result_to_afd = new StringBuilder();
        try {
            br = new BufferedReader(new InputStreamReader(http.getInputStream()));
            //TODO refazer esse metodo
            if ("get_afd".equals(SendJSON.met)) {
                for (int i = 0; i != br.readLine().length(); i++) {
                    result_to_afd.append(br.readLine()).append("\r\n");
                }
                LoadLog(result_to_afd);
            } else {
                while ((result = br.readLine()) != null) {
                    IdentifyMethod(SendJSON.met, result);
                }
            }
            br.close();
        } catch (IOException ex) {
            Logger.getLogger(SendJSON.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public HttpsURLConnection OpenConnection(URL url) {
        HttpsURLConnection conn = null;
        try {
            conn = (HttpsURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-type", "application/json");
            conn.setDoInput(true);
            conn.setDoOutput(true);
        } catch (ProtocolException ex) {
            Logger.getLogger(SendJSON.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SendJSON.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conn;
    }
// ignorar certificado SSL

    public void Ignore_SSL() throws Exception {
        // fazendo uma requisição SSL sem registrar o certificado na JVM.
        final X509TrustManager ignore = new X509TrustManager() {
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }
            // "valida" o certificado

            public void checkServerTrusted(X509Certificate[] certs,
                    String authType)
                    throws java.security.cert.CertificateException {
                return;
            }

            public void checkClientTrusted(X509Certificate[] certs,
                    String authType)
                    throws java.security.cert.CertificateException {
                return;
            }
        };

        // cria socket ssl
        SSLContext _ssl = SSLContext.getInstance("SSL");
        _ssl.init(null, new TrustManager[]{ignore}, null);

        // ativa o socket para a requisicao
        HttpsURLConnection.setDefaultSSLSocketFactory(_ssl.getSocketFactory());

        final HostnameVerifier hv = new HostnameVerifier() {
            public boolean verify(String urlHostName, SSLSession session) {
                return true;
            }
        };

        HttpsURLConnection.setDefaultHostnameVerifier(hv);
    }

    public void IdentifyMethod(String method, String result) {
        switch (method) {
            case "login":
                SessionResult.getToken(result);
                break;
            case "get_system_information": {
                advanced.setText(result);
            }
            break;
            case "remove_admins": {
                advanced.setText(result);
            }
            break;
            case "add_users": {
                simple.setText(result);
            }
            break;
            case "load_users": {
                simple.setText(result);
            }
            break;
            case "remove_users": {
                simple.setText(result);
            }
            break;
            case "count_users": {
                simple.setText(result);
            }
            break;
            case "load_company": {
                simple.setText(result);
            }
            break;
            case "edit_company": {
                simple.setText(result);
            }
            break;
        }
    }

    public void LoadLog(StringBuilder afd) {
        load_afd.setText(afd);
    }
}
