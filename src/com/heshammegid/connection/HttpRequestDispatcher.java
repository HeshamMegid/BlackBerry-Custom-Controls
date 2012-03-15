package com.heshammegid.connection;

import java.io.IOException;
import java.io.InputStream;

import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;

import net.rim.device.api.servicebook.ServiceBook;
import net.rim.device.api.servicebook.ServiceRecord;
import net.rim.device.api.system.CoverageInfo;
import net.rim.device.api.system.DeviceInfo;
import net.rim.device.api.system.WLANInfo;

public class HttpRequestDispatcher extends Thread {
	private String method = "GET"; // GET or POST
	private String url;
	private HttpRequestDelegate callbackScreen;
	
	public HttpRequestDispatcher(HttpRequestDelegate callbackScreen, String url)
	{
		this.callbackScreen = callbackScreen;
		this.url = url;
	}
	
	public void run() {
		try {
		    
		    HttpConnection connection = (HttpConnection)Connector.open(url + getConnectionString());
			connection.setRequestMethod(method);
			
			System.out.println("Starting connection to: " + url);
				        
			int responseCode = connection.getResponseCode();
			
			if (responseCode != HttpConnection.HTTP_OK) {
				callbackScreen.httpRequestFailed("Unexpected response code:" + responseCode);
				connection.close();
				return;
			}
			
			InputStream responseData = connection.openInputStream();
			byte[] responseBytes = new byte[10000];
			responseBytes = net.rim.device.api.io.IOUtilities.streamToBytes(responseData);
		    
		    responseData = null;
			connection.close();
			callbackScreen.httpRequestSucceeded(responseBytes, url);

		} catch (IOException ex) {
			ex.printStackTrace();
			callbackScreen.httpRequestFailed(ex.getMessage());
		}

	}
	
	/**
	 * Determines what connection type to use and returns the necessary string to use it.
	 * @return A string with the connection info
	 */
	public static String getConnectionString()
	{
		// This code is based on the connection code developed by Mike Nelson of AccelGolf.
		// http://blog.accelgolf.com/2009/05/22/blackberry-cross-carrier-and-cross-network-http-connection
		String connectionString = null;                

		// Simulator behavior is controlled by the USE_MDS_IN_SIMULATOR variable.
		if(DeviceInfo.isSimulator())
		{
			System.out.println("Device is a simulator and USE_MDS_IN_SIMULATOR is true");
			connectionString = ";deviceside=false";
		}                                        

		// Wifi is the preferred transmission method
		else if(WLANInfo.getWLANState() == WLANInfo.WLAN_STATE_CONNECTED)
		{
			System.out.println("Device is connected via Wifi.");
			connectionString = ";interface=wifi";
		}

		// Is the carrier network the only way to connect?
		else if((CoverageInfo.getCoverageStatus() & CoverageInfo.COVERAGE_DIRECT) == CoverageInfo.COVERAGE_DIRECT)
		{
			System.out.println("Carrier coverage.");

			String carrierUid = getCarrierBIBSUid();
			if(carrierUid == null)
			{
				// Has carrier coverage, but not BIBS.  So use the carrier's TCP network
				System.out.println("No Uid");
				connectionString = ";deviceside=true";
			}
			else
			{
				// otherwise, use the Uid to construct a valid carrier BIBS request
				System.out.println("uid is: " + carrierUid);
				connectionString = ";deviceside=false;connectionUID="+carrierUid + ";ConnectionType=mds-public";
			}
		}                

		// Check for an MDS connection instead (BlackBerry Enterprise Server)
		else if((CoverageInfo.getCoverageStatus() & CoverageInfo.COVERAGE_MDS) == CoverageInfo.COVERAGE_MDS)
		{
			System.out.println("MDS coverage found");
			connectionString = ";deviceside=false";
		}

		// If there is no connection available abort to avoid bugging the user unnecssarily.
		else if(CoverageInfo.getCoverageStatus() == CoverageInfo.COVERAGE_NONE)
		{
			System.out.println("There is no available connection.");
		}

		// In theory, all bases are covered so this shouldn't be reachable.
		else
		{
			System.out.println("no other options found, assuming device.");
			connectionString = ";deviceside=true";
		}        

		return connectionString;
	}

	/**
	 * Looks through the phone's service book for a carrier provided BIBS network
	 * @return The uid used to connect to that network.
	 */
	public static String getCarrierBIBSUid()
	{
		ServiceRecord[] records = ServiceBook.getSB().getRecords();
		int currentRecord;

		for(currentRecord = 0; currentRecord < records.length; currentRecord++) {             
			if(records[currentRecord].getCid().toLowerCase().equals("ippp")) {                 
				if(records[currentRecord].getName().toLowerCase().indexOf("bibs") >= 0) {
					return records[currentRecord].getUid();
				}
			}
		}

		return null;
	}
	
	public interface HttpRequestDelegate {
		void httpRequestSucceeded(byte[] result, String url);
		void httpRequestFailed(String message);
	}
}