package net.catalyst.net;


public class PacketBuilder extends PacketConstructor {
	/**
	 * ID of the packet
	 */
	private int pID = 0;

	/**
	 * Sets the ID for this packet.
	 *
	 * @param id The ID of the packet
	 */
	public PacketBuilder setID(int pID) {
		this.pID = pID;
		return this;
	}
	
	/**
	 * Returns a <code>RSCPacket</code> object for the data contained
	 * in this builder.
	 *
	 * @return A <code>RSCPacket</code> object
	 */
	public Packet toPacket() {
		byte[] data = new byte[curLength];
		System.arraycopy(payload, 0, data, 0, curLength);
		return new Packet(null, data, bare, pID);
	}

}