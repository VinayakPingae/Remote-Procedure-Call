

import java.util.Map;
import java.util.HashMap;
import org.apache.thrift.TEnum;

public enum Status implements org.apache.thrift.TEnum {
  FAILED(0),
  SUCCESSFUL(1);

  private final int value;

  private Status(int value) {
    this.value = value;
  }

  /**
   * Get the integer value of this enum value, as defined in the Thrift IDL.
   */
  public int getValue() {
    return value;
  }

  /**
   * Find a the enum type by its integer value, as defined in the Thrift IDL.
   * @return null if the value is not found.
   */
  public static Status findByValue(int value) { 
    switch (value) {
      case 0:
        return FAILED;
      case 1:
        return SUCCESSFUL;
      default:
        return null;
    }
  }
}
