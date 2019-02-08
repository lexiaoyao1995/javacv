package com.swjtu.javacvDemo;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class SendContiousThread extends Thread {

	private byte[] data;

	private Socket socket = null;

	public volatile boolean isWork = false;
	
	@Override
	public void run() {

		while (true) {
			if(isWork) {
				System.out.println("isWork = "+isWork);
				data = ClientFrame.getCurrentData();
				OutputStream outputStream = null;
				try {
					socket = new Socket("localhost", 8889);
					outputStream = socket.getOutputStream();
					outputStream.write(data, 0, data.length);
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					try {
						socket.shutdownOutput();
						outputStream.close();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

		}

	}

}
