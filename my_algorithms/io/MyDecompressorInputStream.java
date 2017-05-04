package io;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MyDecompressorInputStream extends InputStream {

	private InputStream in;

	public MyDecompressorInputStream(InputStream in) {

		super();
		this.in = in;
	}

	@Override
	public int read() throws IOException {
		return in.read();
	}

	@Override
	public int read(byte[] arrByteToRead) throws IOException {

		int k = 0;
		//System.out.println(arrByteToRead.length);
		while (k < arrByteToRead.length) {
			byte count = (byte) in.read();
			byte b = (byte) in.read();

			for (int j = 0; j < count; j++) {
				arrByteToRead[k++] = b;
			}
		}
		return arrByteToRead.length;

	}

}
