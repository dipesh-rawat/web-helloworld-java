package webhello;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class WebHello {

  private static String template;

  static class MyHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exch) throws IOException {
      String addr = exch.getRemoteAddress().getAddress().getHostAddress();
      String response = String.format(WebHello.template, addr);
      exch.sendResponseHeaders(200, response.length());
      OutputStream os = exch.getResponseBody();
      os.write(response.getBytes());
      os.close();
    }
  }

  public static void main(String[] args) throws Exception {
    String nl = System.getProperty("line.separator");
    WebHello.template =
      "<!DOCTYPE html>" + nl +
      "<html><head>" + nl +
      "<meta charset=\"utf-8\">" + nl +
      "<title>WebHello</title>" + nl +
      "</head><body>Hello, \"%s\".</body></html>" + nl;
    HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
    server.createContext("/", new MyHandler());
    server.setExecutor(null);
    server.start();
  }
}

