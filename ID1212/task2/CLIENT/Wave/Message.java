package Wave;
import java.nio.ByteBuffer;
import java.util.*;

public class Message {
  private ByteBuffer data_;

  public Message(byte[] data) {
    this.data_ = ByteBuffer.allocate(8 + data.length);
    this.data_.clear(); 
    this.data_.putLong(data.length);
    this.data_.put(data);
  }

  public ByteBuffer GetMessage() { 
    this.data_.flip();
    return this.data_; 
  }
}
