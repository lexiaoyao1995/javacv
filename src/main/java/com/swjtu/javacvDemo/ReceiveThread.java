package com.swjtu.javacvDemo;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class ReceiveThread extends Thread {

	private Socket socket = null;

	public volatile byte[] videoByte = null;

	public ReceiveThread(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		InputStream inputStream = null;
		ByteArrayOutputStream baoc = null;
		try {
			inputStream = socket.getInputStream();
			baoc = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = inputStream.read(buffer)) != -1) {
				baoc.write(buffer, 0, len);
			}
			videoByte = baoc.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				socket.shutdownInput();
				baoc.close();
				inputStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}


}
