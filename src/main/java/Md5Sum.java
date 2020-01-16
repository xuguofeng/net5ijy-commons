import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.nio.ByteOrder;
import java.security.AccessController;
import java.security.DigestException;
import java.security.ProviderException;
import sun.misc.Unsafe;
import sun.security.action.GetPropertyAction;

/**
 * Md5Sum
 *
 * @author xuguofeng
 * @date 2020/1/15 11:56
 */
public class Md5Sum {

  public static final char[] DIGITS_LOWER = {'0', '1', '2', '3', '4', '5',
      '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

  private final String algorithm;
  private final int digestLength;
  private final int blockSize;
  private byte[] buffer;
  private int bufOfs;
  private long bytesProcessed;
  private static final byte[] PADDING = new byte[136];

  private MD5 md5;

  static {
    PADDING[0] = -128;
  }

  public Md5Sum() {
    this.algorithm = "MD5";
    this.digestLength = 16;
    this.blockSize = 64;
    this.buffer = new byte[64];
    this.md5 = new MD5();
  }

  private void engineUpdate(byte[] var1, int var2, int var3) {
    if (var3 != 0) {
      if (var2 >= 0 && var3 >= 0 && var2 <= var1.length - var3) {
        if (this.bytesProcessed < 0L) {
          this.engineReset();
        }

        this.bytesProcessed += (long) var3;
        int var4;
        if (this.bufOfs != 0) {
          var4 = Math.min(var3, this.blockSize - this.bufOfs);
          System.arraycopy(var1, var2, this.buffer, this.bufOfs, var4);
          this.bufOfs += var4;
          var2 += var4;
          var3 -= var4;
          if (this.bufOfs >= this.blockSize) {
            md5.implCompress(this.buffer, 0);
            this.bufOfs = 0;
          }
        }

        if (var3 >= this.blockSize) {
          var4 = var2 + var3;
          var2 = this.implCompressMultiBlock(var1, var2, var4 - this.blockSize);
          var3 = var4 - var2;
        }

        if (var3 > 0) {
          System.arraycopy(var1, var2, this.buffer, 0, var3);
          this.bufOfs = var3;
        }

      } else {
        throw new ArrayIndexOutOfBoundsException();
      }
    }
  }

  private int implCompressMultiBlock(byte[] var1, int var2, int var3) {
    while (var2 <= var3) {
      md5.implCompress(var1, var2);
      var2 += this.blockSize;
    }
    return var2;
  }

  private void engineReset() {
    if (this.bytesProcessed != 0L) {
      md5.implReset();
      this.bufOfs = 0;
      this.bytesProcessed = 0L;
    }
  }

  private byte[] engineDigest() {
    byte[] var1 = new byte[this.digestLength];

    try {
      this.engineDigest(var1, 0, var1.length);
      return var1;
    } catch (DigestException var3) {
      throw new ProviderException("Internal error", var3);
    }
  }

  private int engineDigest(byte[] var1, int var2, int var3) throws DigestException {
    if (var3 < this.digestLength) {
      throw new DigestException(
          "Length must be at least " + this.digestLength + " for " + this.algorithm + "digests");
    } else if (var2 >= 0 && var3 >= 0 && var2 <= var1.length - var3) {
      if (this.bytesProcessed < 0L) {
        this.engineReset();
      }

      md5.implDigest(var1, var2);
      this.bytesProcessed = -1L;
      return this.digestLength;
    } else {
      throw new DigestException("Buffer too short to store digest");
    }
  }

  private char[] encodeHex(final byte[] data, final char[] toDigits) {
    final int l = data.length;
    final char[] out = new char[l << 1];
    for (int i = 0, j = 0; i < l; i++) {
      out[j++] = toDigits[(0xF0 & data[i]) >>> 4];
      out[j++] = toDigits[0x0F & data[i]];
    }
    return out;
  }

  public String md5Sum(File file) throws Exception {
    return md5Sum(new FileInputStream(file));
  }

  public String md5Sum(InputStream inputStream) throws Exception {

    try {

      Md5Sum md5Sum = new Md5Sum();

      // 200MB缓冲区
      byte[] buffer = new byte[1024 * 1024 * 200];

      // 读取文件
      int len = inputStream.read(buffer);

      while (len > -1) {
        md5Sum.engineUpdate(buffer, 0, len);
        len = inputStream.read(buffer);
      }

      return new String(md5Sum.encodeHex(md5Sum.engineDigest(), Md5Sum.DIGITS_LOWER));

    } finally {
      if (inputStream != null) {
        inputStream.close();
      }
    }
  }

  private class MD5 {

    private int[] state = new int[4];
    private int[] x = new int[16];

    MD5() {
      this.implReset();
    }

    void implReset() {
      this.state[0] = 1732584193;
      this.state[1] = -271733879;
      this.state[2] = -1732584194;
      this.state[3] = 271733878;
    }

    void implDigest(byte[] var1, int var2) {
      long var3 = bytesProcessed << 3;
      int var5 = (int) bytesProcessed & 63;
      int var6 = var5 < 56 ? 56 - var5 : 120 - var5;
      engineUpdate(PADDING, 0, var6);
      ByteArrayAccess.i2bLittle4((int) var3, buffer, 56);
      ByteArrayAccess.i2bLittle4((int) (var3 >>> 32), buffer, 60);
      this.implCompress(buffer, 0);
      ByteArrayAccess.i2bLittle(this.state, 0, var1, var2, 16);
    }

    private int FF(int var0, int var1, int var2, int var3, int var4, int var5, int var6) {
      var0 += (var1 & var2 | ~var1 & var3) + var4 + var6;
      return (var0 << var5 | var0 >>> 32 - var5) + var1;
    }

    private int GG(int var0, int var1, int var2, int var3, int var4, int var5, int var6) {
      var0 += (var1 & var3 | var2 & ~var3) + var4 + var6;
      return (var0 << var5 | var0 >>> 32 - var5) + var1;
    }

    private int HH(int var0, int var1, int var2, int var3, int var4, int var5, int var6) {
      var0 += (var1 ^ var2 ^ var3) + var4 + var6;
      return (var0 << var5 | var0 >>> 32 - var5) + var1;
    }

    private int II(int var0, int var1, int var2, int var3, int var4, int var5, int var6) {
      var0 += (var2 ^ (var1 | ~var3)) + var4 + var6;
      return (var0 << var5 | var0 >>> 32 - var5) + var1;
    }

    void implCompress(byte[] var1, int var2) {
      ByteArrayAccess.b2iLittle64(var1, var2, this.x);
      int var3 = this.state[0];
      int var4 = this.state[1];
      int var5 = this.state[2];
      int var6 = this.state[3];
      var3 = FF(var3, var4, var5, var6, this.x[0], 7, -680876936);
      var6 = FF(var6, var3, var4, var5, this.x[1], 12, -389564586);
      var5 = FF(var5, var6, var3, var4, this.x[2], 17, 606105819);
      var4 = FF(var4, var5, var6, var3, this.x[3], 22, -1044525330);
      var3 = FF(var3, var4, var5, var6, this.x[4], 7, -176418897);
      var6 = FF(var6, var3, var4, var5, this.x[5], 12, 1200080426);
      var5 = FF(var5, var6, var3, var4, this.x[6], 17, -1473231341);
      var4 = FF(var4, var5, var6, var3, this.x[7], 22, -45705983);
      var3 = FF(var3, var4, var5, var6, this.x[8], 7, 1770035416);
      var6 = FF(var6, var3, var4, var5, this.x[9], 12, -1958414417);
      var5 = FF(var5, var6, var3, var4, this.x[10], 17, -42063);
      var4 = FF(var4, var5, var6, var3, this.x[11], 22, -1990404162);
      var3 = FF(var3, var4, var5, var6, this.x[12], 7, 1804603682);
      var6 = FF(var6, var3, var4, var5, this.x[13], 12, -40341101);
      var5 = FF(var5, var6, var3, var4, this.x[14], 17, -1502002290);
      var4 = FF(var4, var5, var6, var3, this.x[15], 22, 1236535329);
      var3 = GG(var3, var4, var5, var6, this.x[1], 5, -165796510);
      var6 = GG(var6, var3, var4, var5, this.x[6], 9, -1069501632);
      var5 = GG(var5, var6, var3, var4, this.x[11], 14, 643717713);
      var4 = GG(var4, var5, var6, var3, this.x[0], 20, -373897302);
      var3 = GG(var3, var4, var5, var6, this.x[5], 5, -701558691);
      var6 = GG(var6, var3, var4, var5, this.x[10], 9, 38016083);
      var5 = GG(var5, var6, var3, var4, this.x[15], 14, -660478335);
      var4 = GG(var4, var5, var6, var3, this.x[4], 20, -405537848);
      var3 = GG(var3, var4, var5, var6, this.x[9], 5, 568446438);
      var6 = GG(var6, var3, var4, var5, this.x[14], 9, -1019803690);
      var5 = GG(var5, var6, var3, var4, this.x[3], 14, -187363961);
      var4 = GG(var4, var5, var6, var3, this.x[8], 20, 1163531501);
      var3 = GG(var3, var4, var5, var6, this.x[13], 5, -1444681467);
      var6 = GG(var6, var3, var4, var5, this.x[2], 9, -51403784);
      var5 = GG(var5, var6, var3, var4, this.x[7], 14, 1735328473);
      var4 = GG(var4, var5, var6, var3, this.x[12], 20, -1926607734);
      var3 = HH(var3, var4, var5, var6, this.x[5], 4, -378558);
      var6 = HH(var6, var3, var4, var5, this.x[8], 11, -2022574463);
      var5 = HH(var5, var6, var3, var4, this.x[11], 16, 1839030562);
      var4 = HH(var4, var5, var6, var3, this.x[14], 23, -35309556);
      var3 = HH(var3, var4, var5, var6, this.x[1], 4, -1530992060);
      var6 = HH(var6, var3, var4, var5, this.x[4], 11, 1272893353);
      var5 = HH(var5, var6, var3, var4, this.x[7], 16, -155497632);
      var4 = HH(var4, var5, var6, var3, this.x[10], 23, -1094730640);
      var3 = HH(var3, var4, var5, var6, this.x[13], 4, 681279174);
      var6 = HH(var6, var3, var4, var5, this.x[0], 11, -358537222);
      var5 = HH(var5, var6, var3, var4, this.x[3], 16, -722521979);
      var4 = HH(var4, var5, var6, var3, this.x[6], 23, 76029189);
      var3 = HH(var3, var4, var5, var6, this.x[9], 4, -640364487);
      var6 = HH(var6, var3, var4, var5, this.x[12], 11, -421815835);
      var5 = HH(var5, var6, var3, var4, this.x[15], 16, 530742520);
      var4 = HH(var4, var5, var6, var3, this.x[2], 23, -995338651);
      var3 = II(var3, var4, var5, var6, this.x[0], 6, -198630844);
      var6 = II(var6, var3, var4, var5, this.x[7], 10, 1126891415);
      var5 = II(var5, var6, var3, var4, this.x[14], 15, -1416354905);
      var4 = II(var4, var5, var6, var3, this.x[5], 21, -57434055);
      var3 = II(var3, var4, var5, var6, this.x[12], 6, 1700485571);
      var6 = II(var6, var3, var4, var5, this.x[3], 10, -1894986606);
      var5 = II(var5, var6, var3, var4, this.x[10], 15, -1051523);
      var4 = II(var4, var5, var6, var3, this.x[1], 21, -2054922799);
      var3 = II(var3, var4, var5, var6, this.x[8], 6, 1873313359);
      var6 = II(var6, var3, var4, var5, this.x[15], 10, -30611744);
      var5 = II(var5, var6, var3, var4, this.x[6], 15, -1560198380);
      var4 = II(var4, var5, var6, var3, this.x[13], 21, 1309151649);
      var3 = II(var3, var4, var5, var6, this.x[4], 6, -145523070);
      var6 = II(var6, var3, var4, var5, this.x[11], 10, -1120210379);
      var5 = II(var5, var6, var3, var4, this.x[2], 15, 718787259);
      var4 = II(var4, var5, var6, var3, this.x[9], 21, -343485551);
      this.state[0] += var3;
      this.state[1] += var4;
      this.state[2] += var5;
      this.state[3] += var6;
    }
  }

  private static class ByteArrayAccess {

    private static Unsafe unsafe;
    private static final boolean LITTLE_ENDIAN_UNALIGNED;
    private static final boolean BIG_ENDIAN;
    private static final int BYTE_ARRAY_OFS;

    private static boolean unaligned() {
      String var0 = AccessController.doPrivileged(new GetPropertyAction("os.arch", ""));
      return "i386".equals(var0) || "x86".equals(var0) || "amd64".equals(var0) || "x86_64"
          .equals(var0);
    }

    static void b2iLittle(byte[] var0, int var1, int[] var2, int var3, int var4) {
      if (var1 >= 0 && var0.length - var1 >= var4 && var3 >= 0 && var2.length - var3 >= var4 / 4) {
        if (LITTLE_ENDIAN_UNALIGNED) {
          var1 += BYTE_ARRAY_OFS;

          for (var4 += var1; var1 < var4; var1 += 4) {
            var2[var3++] = unsafe.getInt(var0, (long) var1);
          }
        } else if (BIG_ENDIAN && (var1 & 3) == 0) {
          var1 += BYTE_ARRAY_OFS;

          for (var4 += var1; var1 < var4; var1 += 4) {
            var2[var3++] = Integer.reverseBytes(unsafe.getInt(var0, (long) var1));
          }
        } else {
          for (var4 += var1; var1 < var4; var1 += 4) {
            var2[var3++] =
                var0[var1] & 255 | (var0[var1 + 1] & 255) << 8 | (var0[var1 + 2] & 255) << 16
                    | var0[var1 + 3] << 24;
          }
        }

      } else {
        throw new ArrayIndexOutOfBoundsException();
      }
    }

    static void b2iLittle64(byte[] var0, int var1, int[] var2) {
      if (var1 >= 0 && var0.length - var1 >= 64 && var2.length >= 16) {
        if (LITTLE_ENDIAN_UNALIGNED) {
          var1 += BYTE_ARRAY_OFS;
          var2[0] = unsafe.getInt(var0, (long) var1);
          var2[1] = unsafe.getInt(var0, (long) (var1 + 4));
          var2[2] = unsafe.getInt(var0, (long) (var1 + 8));
          var2[3] = unsafe.getInt(var0, (long) (var1 + 12));
          var2[4] = unsafe.getInt(var0, (long) (var1 + 16));
          var2[5] = unsafe.getInt(var0, (long) (var1 + 20));
          var2[6] = unsafe.getInt(var0, (long) (var1 + 24));
          var2[7] = unsafe.getInt(var0, (long) (var1 + 28));
          var2[8] = unsafe.getInt(var0, (long) (var1 + 32));
          var2[9] = unsafe.getInt(var0, (long) (var1 + 36));
          var2[10] = unsafe.getInt(var0, (long) (var1 + 40));
          var2[11] = unsafe.getInt(var0, (long) (var1 + 44));
          var2[12] = unsafe.getInt(var0, (long) (var1 + 48));
          var2[13] = unsafe.getInt(var0, (long) (var1 + 52));
          var2[14] = unsafe.getInt(var0, (long) (var1 + 56));
          var2[15] = unsafe.getInt(var0, (long) (var1 + 60));
        } else if (BIG_ENDIAN && (var1 & 3) == 0) {
          var1 += BYTE_ARRAY_OFS;
          var2[0] = Integer.reverseBytes(unsafe.getInt(var0, (long) var1));
          var2[1] = Integer.reverseBytes(unsafe.getInt(var0, (long) (var1 + 4)));
          var2[2] = Integer.reverseBytes(unsafe.getInt(var0, (long) (var1 + 8)));
          var2[3] = Integer.reverseBytes(unsafe.getInt(var0, (long) (var1 + 12)));
          var2[4] = Integer.reverseBytes(unsafe.getInt(var0, (long) (var1 + 16)));
          var2[5] = Integer.reverseBytes(unsafe.getInt(var0, (long) (var1 + 20)));
          var2[6] = Integer.reverseBytes(unsafe.getInt(var0, (long) (var1 + 24)));
          var2[7] = Integer.reverseBytes(unsafe.getInt(var0, (long) (var1 + 28)));
          var2[8] = Integer.reverseBytes(unsafe.getInt(var0, (long) (var1 + 32)));
          var2[9] = Integer.reverseBytes(unsafe.getInt(var0, (long) (var1 + 36)));
          var2[10] = Integer.reverseBytes(unsafe.getInt(var0, (long) (var1 + 40)));
          var2[11] = Integer.reverseBytes(unsafe.getInt(var0, (long) (var1 + 44)));
          var2[12] = Integer.reverseBytes(unsafe.getInt(var0, (long) (var1 + 48)));
          var2[13] = Integer.reverseBytes(unsafe.getInt(var0, (long) (var1 + 52)));
          var2[14] = Integer.reverseBytes(unsafe.getInt(var0, (long) (var1 + 56)));
          var2[15] = Integer.reverseBytes(unsafe.getInt(var0, (long) (var1 + 60)));
        } else {
          b2iLittle(var0, var1, var2, 0, 64);
        }

      } else {
        throw new ArrayIndexOutOfBoundsException();
      }
    }

    static void i2bLittle(int[] var0, int var1, byte[] var2, int var3, int var4) {
      if (var1 >= 0 && var0.length - var1 >= var4 / 4 && var3 >= 0 && var2.length - var3 >= var4) {
        if (LITTLE_ENDIAN_UNALIGNED) {
          var3 += BYTE_ARRAY_OFS;

          for (var4 += var3; var3 < var4; var3 += 4) {
            unsafe.putInt(var2, (long) var3, var0[var1++]);
          }
        } else {
          int var5;
          if (BIG_ENDIAN && (var3 & 3) == 0) {
            var3 += BYTE_ARRAY_OFS;

            for (var4 += var3; var3 < var4; var3 += 4) {
              unsafe.putInt(var2, (long) var3, Integer.reverseBytes(var0[var1++]));
            }
          } else {
            for (var4 += var3; var3 < var4; var2[var3++] = (byte) (var5 >> 24)) {
              var5 = var0[var1++];
              var2[var3++] = (byte) var5;
              var2[var3++] = (byte) (var5 >> 8);
              var2[var3++] = (byte) (var5 >> 16);
            }
          }
        }

      } else {
        throw new ArrayIndexOutOfBoundsException();
      }
    }

    static void i2bLittle4(int var0, byte[] var1, int var2) {
      if (var2 >= 0 && var1.length - var2 >= 4) {
        if (LITTLE_ENDIAN_UNALIGNED) {
          unsafe.putInt(var1, (long) (BYTE_ARRAY_OFS + var2), var0);
        } else if (BIG_ENDIAN && (var2 & 3) == 0) {
          unsafe.putInt(var1, (long) (BYTE_ARRAY_OFS + var2), Integer.reverseBytes(var0));
        } else {
          var1[var2] = (byte) var0;
          var1[var2 + 1] = (byte) (var0 >> 8);
          var1[var2 + 2] = (byte) (var0 >> 16);
          var1[var2 + 3] = (byte) (var0 >> 24);
        }

      } else {
        throw new ArrayIndexOutOfBoundsException();
      }
    }

    static {

      unsafe = null;
      try {
        Field f = Unsafe.class.getDeclaredField("theUnsafe");
        f.setAccessible(true);
        unsafe = (Unsafe) f.get(null);
      } catch (NoSuchFieldException | IllegalAccessException e) {
        e.printStackTrace();
      }

      BYTE_ARRAY_OFS = unsafe.arrayBaseOffset(byte[].class);
      boolean var0 =
          unsafe.arrayIndexScale(byte[].class) == 1 && unsafe.arrayIndexScale(int[].class) == 4
              && unsafe.arrayIndexScale(long[].class) == 8 && (BYTE_ARRAY_OFS & 3) == 0;
      ByteOrder var1 = ByteOrder.nativeOrder();
      LITTLE_ENDIAN_UNALIGNED = var0 && unaligned() && var1 == ByteOrder.LITTLE_ENDIAN;
      BIG_ENDIAN = var0 && var1 == ByteOrder.BIG_ENDIAN;
    }
  }

  public static void main(String[] args) throws Exception {

    if (args.length < 1) {
      System.out.println("Usage: java Md5Sum <filePath>");
      System.exit(1);
    }

    // long start = System.currentTimeMillis();

    String filePath = args[0];
    File file = new File(filePath);

    Md5Sum md5Sum = new Md5Sum();

    String md5 = md5Sum.md5Sum(file);

    System.out.println(md5 + "  " + filePath);

    // System.out.println(System.currentTimeMillis() - start);
  }
}
