package com.swjtu.javacvDemo;

import static org.bytedeco.javacpp.opencv_imgcodecs.IMREAD_COLOR;
import static org.bytedeco.javacpp.opencv_imgcodecs.imdecode;
import static org.bytedeco.javacpp.opencv_imgcodecs.imencode;

import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.OpenCVFrameConverter;

public class Utils {

	private static OpenCVFrameConverter.ToIplImage converter = new OpenCVFrameConverter.ToIplImage();

	public static Frame matToframe(Mat mat) {
		return converter.convert(mat);
	}
	
	public static Mat frameTomat(Frame frame) {
		return converter.convertToMat(frame);
		
	}

	public static byte[] matTobytes(Mat mat) {
		int size = (int) mat.total() * mat.channels();
		byte[] bytes = new byte[size];
		mat.data().get(bytes);
		imencode(".jpg", mat, bytes);
		return bytes;
	}

	public static Mat bytesToMat(byte[] bytes) {
		Mat mat = new Mat(bytes);
		return imdecode(mat, IMREAD_COLOR);
	}

}
