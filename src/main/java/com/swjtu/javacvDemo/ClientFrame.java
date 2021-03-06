package com.swjtu.javacvDemo;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.OpenCVFrameGrabber;

public class ClientFrame extends CanvasFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 10L;

	private OpenCVFrameGrabber grabber = null;

	private Socket socket = null;

	private static Frame currentFrame = null;

	private SendContiousThread sendContiousThread = null;

	public ClientFrame() throws Exception, InterruptedException, org.bytedeco.javacv.FrameGrabber.Exception {
		super("client");
		// TODO Auto-generated constructor stub
		setExtendedState(MAXIMIZED_BOTH);
		// setSize(300, 300);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setJMenuBar(initMenuBar());
		grabber = new OpenCVFrameGrabber(0);
		grabber.start();

		while (true) {
			if (!canvas.isDisplayable()) {// 窗口是否关闭
				grabber.stop();// 停止抓取
				System.exit(2);// 退出
			}
			Frame frame = grabber.grab();
			currentFrame = frame;
			showImage(grabber.grab());// 获取摄像头图像并放到窗口上显示， 这里的Frame frame=grabber.grab(); frame是一帧视频图像

			Thread.sleep(50);// 50毫秒刷新一次图像
		}

	}

	private JMenuBar initMenuBar() {
		JMenuBar jMenuBar = new JMenuBar();
		JMenu jMenu = new JMenu("file");
		JMenuItem jMenuItem = new JMenuItem("send single");
		jMenuItem.addMouseListener(new MouseListener() {

			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				/**
				 * 
				 * Mat mat = Utils.frameTomat(currentFrame); byte[] matTobytes =
				 * Utils.matTobytes(mat); try { sendToServer(matTobytes); } catch
				 * (UnknownHostException e) { e.printStackTrace(); } catch (IOException e) {
				 * e.printStackTrace(); }
				 */
				sendSingle();
			}

			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}
		});
		jMenu.add(jMenuItem);
		JMenuItem jMenuItem2 = new JMenuItem("send contious");
		jMenuItem2.addMouseListener(new MouseListener() {

			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

				// isSendContinus = !isSendContinus;
				// System.out.println("iscontious is " + isSendContinus);
				// while (isSendContinus) {
				// sendSingle();
				// try {
				// Thread.sleep(1000);
				// } catch (InterruptedException e1) {
				// // TODO Auto-generated catch block
				// e1.printStackTrace();
				// }
				// }

				if (sendContiousThread == null) {
					sendContiousThread = new SendContiousThread();
					sendContiousThread.start();
				}

				sendContiousThread.isWork = !sendContiousThread.isWork;
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
		jMenu.add(jMenuItem2);
		jMenuBar.add(jMenu);
		return jMenuBar;
	}

	private void sendToServer(byte[] data) throws UnknownHostException, IOException {
		socket = new Socket("localhost", 8889);
		OutputStream outputStream = socket.getOutputStream();
		outputStream.write(data, 0, data.length);
		socket.shutdownOutput();
		outputStream.close();
	}

	private void sendSingle() {
		Mat mat = Utils.frameTomat(currentFrame);
		byte[] matTobytes = Utils.matTobytes(mat);
		try {
			sendToServer(matTobytes);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static byte[] getCurrentData() {
		Mat mat = Utils.frameTomat(currentFrame);
		byte[] matTobytes = Utils.matTobytes(mat);
		return matTobytes;
	}

}
