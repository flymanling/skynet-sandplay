import org.mortbay.jetty.Connector;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.handler.RequestLogHandler;
import org.mortbay.jetty.nio.SelectChannelConnector;
import org.mortbay.jetty.webapp.WebAppContext;

/**
 * 启动jetty
 * 
 * @author ljb
 * @version 1.0.0 2013-10-10 下午4:38:52
 */
public class ServerStartup {
	private Server server;

    public static void main(String[] args) {
    	ServerStartup web = new ServerStartup();
		web.startup();
	}
    
    public ServerStartup() {}
    

    public void startup() {
        server = new Server();
        
        int wapPort = 99;
        Connector connector = new SelectChannelConnector();
        connector.setPort(wapPort);
        server.setConnectors(new Connector[]{connector});
        connector.setHost("0.0.0.0");
        WebAppContext context = new WebAppContext("src/main/webapp", "/");
        
        server.addHandler(context);
        RequestLogHandler requestLogHandler = new RequestLogHandler();
        server.addHandler(requestLogHandler);
        try {
//        	NCSARequestLog requestLog = new NCSARequestLog("log/jetty.log");
//	        requestLog.setExtended(false);
//	        requestLogHandler.setRequestLog(requestLog);
        	
	        server.setStopAtShutdown(true);
	        server.setSendServerVersion(true);
	        server.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }    

    /**
     * Shutdown the HTTP Bind service, freeing any related resources.
     *
     * @throws Exception if there is an error shutting down the service.
     */
    public void shutdown() {
        try {
			server.stop();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}

