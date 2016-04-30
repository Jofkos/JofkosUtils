package com.jofkos.utils;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.UUID;


public class DataOutputWrapper {
	
	private ByteArrayOutputStream byteStream;
	private DataOutputStream dataStream;
	
	public DataOutputWrapper() {
		this.byteStream = new ByteArrayOutputStream();
		this.dataStream = new DataOutputStream(byteStream);
	}
	
	public DataOutputWrapper writeBoolean(boolean bool) {
		try {
			dataStream.writeBoolean(bool);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this;
	}
	
	public DataOutputWrapper writeByte(int byteValue) {
		try {
			dataStream.writeByte(byteValue);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this;
	}
	
	public DataOutputWrapper writeChar(char c) {
		try {
			dataStream.writeChar(c);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this;
	}
	
	public DataOutputWrapper writeDouble(double d) {
		try {
			dataStream.writeDouble(d);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this;
	}
	
	public DataOutputWrapper writeFloat(float f) {
		try {
			dataStream.writeFloat(f);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this;
	}
	
	public DataOutputWrapper writeInt(int i) {
		try {
			dataStream.writeInt(i);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this;
	}
	
	public DataOutputWrapper writeLong(long l) {
		try {
			dataStream.writeLong(l);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this;
	}
	
	public DataOutputWrapper writeShort(short c) {
		try {
			dataStream.writeShort(c);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this;
	}
	
	public DataOutputWrapper writeUTF(String utf) {
		try {
			dataStream.writeUTF(utf);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this;
	}
	
	public DataOutputWrapper writeUUID(UUID uuid) {
		try {
			dataStream.writeUTF(uuid.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this;
	}
	
	public byte[] getBytes() {
		return this.byteStream.toByteArray();
	}
	
}
