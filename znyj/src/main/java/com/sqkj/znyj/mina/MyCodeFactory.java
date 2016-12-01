package com.sqkj.znyj.mina;

import java.nio.charset.Charset;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

public class MyCodeFactory implements ProtocolCodecFactory {

	private final MyEncoder encoder;
	private final MyDecoder decoder;
	/* final static char endchar = 0x1a; */
	final static char endchar = 0x0e;
	final static byte[] endbyte = { 0x0e };

	public MyCodeFactory() {
		this(Charset.forName("gbk"));
	}

	public MyCodeFactory(Charset charset) {
		encoder = new MyEncoder();
		decoder = new MyDecoder(charset);
	}

	public ProtocolDecoder getDecoder(IoSession session) throws Exception {
		return decoder;
	}

	public ProtocolEncoder getEncoder(IoSession session) throws Exception {
		return encoder;
	}

	public int getEncoderMaxLineLength() {
		return encoder.getMaxLineLength();
	}

	public void setEncoderMaxLineLength(int maxLineLength) {
		encoder.setMaxLineLength(maxLineLength);
	}

}