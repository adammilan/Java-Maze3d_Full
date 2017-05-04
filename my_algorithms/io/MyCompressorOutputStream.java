package io;

import java.io.IOException;
import java.io.OutputStream;

/**
 * 
 * compress given data and write it to the output stream source
 * 
 * @author ben
 * 
 */
public class MyCompressorOutputStream extends OutputStream {

	private OutputStream out;

	public MyCompressorOutputStream() {

	}

	public MyCompressorOutputStream(OutputStream out) {
		super();
		this.out = out;
	}

	@Override
	public void write(byte[] b) throws IOException {

		byte currByte = b[0];
		int count = 1;

		for (int i = 1; i < b.length; i++) {
			if (b[i] != currByte) {
				while (count >= 255) {
					out.write(255);
					out.write(currByte);
				//	System.out.println(count+ " , " + currByte);
					count -= 255;
				}
			//	System.out.println(count+ " , " + currByte);

				out.write(count);
				out.write(currByte);
				currByte = b[i];
				count = 1;
			} else {
				count++;
			}
		}
		out.write(count);
		out.write(currByte);

	}

	@Override
	public void write(int num) throws IOException {
		out.write(num);
	}

}
