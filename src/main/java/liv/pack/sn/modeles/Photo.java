package liv.pack.sn.modeles;

public class Photo {
	private String name;
	private String type;
	private byte[] picByte;
	public Photo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Photo(String name, String type, byte[] picByte) {
		super();
		this.name = name;
		this.type = type;
		this.picByte = picByte;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public byte[] getPicByte() {
		return picByte;
	}
	public void setPicByte(byte[] picByte) {
		this.picByte = picByte;
	}
	
	

}
