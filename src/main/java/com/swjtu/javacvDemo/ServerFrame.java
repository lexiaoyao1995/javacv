package com.swjtu.javacvDemo;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.Frame;

public class ServerFrame extends CanvasFrame {

	private static final long serialVersionUID = 1L;

	private ServerSocket serverSocket = null;

	public ServerFrame() throws Exception, IOException, InterruptedException {
		super("Server");
		setExtendedState(MAXIMIZED_BOTH);
		// setSize(300, 300);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		setJMenuBar(initMenuBar());
		initServerSocket();
	}

	private JMenuBar initMenuBar() {
		JMenuBar jMenuBar = new JMenuBar();
		JMenu jMenu = new JMenu("file");
		JMenuItem item1 = new JMenuItem("new");
		item1.addMouseListener(new MouseListener() {

			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
			}

			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});
		jMenu.add(item1);
		jMenuBar.add(jMenu);
		return jMenuBar;
	}

	private void initServerSocket() throws Exception, IOException, InterruptedException {
		serverSocket = new ServerSocket(8889);
		/**
		 * Socket socket = serverSocket.accept(); InputStream inputStream =
		 * socket.getInputStream(); ByteArrayOutputStream baoc = new
		 * ByteArrayOutputStream(); byte[] buffer = new byte[1024]; int len = 0; while
		 * ((len = inputStream.read(buffer)) != -1) { baoc.write(buffer, 0, len); }
		 * byte[] b = baoc.toByteArray(); socket.shutdownInput(); Mat mat =
		 * Utils.bytesToMat(b); Frame frame = Utils.matToframe(mat); showImage(frame);
		 * baoc.close(); inputStream.close();
		 */
		Integer time = 0;
		while (true) {
			time++;
			System.out.println("the " + time + " frame start listening");
			Socket socket = serverSocket.accept();
			ReceiveThread receiveThread = new ReceiveThread(socket);
			receiveThread.start();
			receiveThread.join();//等待线程同步
			byte[] byte1 = receiveThread.videoByte;
			Mat mat = Utils.bytesToMat(byte1);
			Frame frame = Utils.matToframe(mat);
			showImage(frame);
		}

	}

}
