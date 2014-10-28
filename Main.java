import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

import org.apache.commons.io.IOUtils;

import redis.clients.jedis.Jedis;

public class Main {
	public static void main(String[] args) {
		try {
			storeBinaryDataToRedis();
		} catch (IOException e) {
			System.out.println("Caught IOException: " + e.getMessage());
		}
	}

	public static void storeBinaryDataToRedis() throws IOException {
		// Connecting to Redis on localhost
		Jedis jedis = new Jedis("localhost");
		FileInputStream in = null;
		FileOutputStream out = null;
		try {
			in = new FileInputStream("image.png");
			byte[] key = { 'k' };
			byte[] value = IOUtils.toByteArray(in);
			jedis.set(key, value);
			System.out.println(Arrays.toString(jedis.get(key))); // getting the key value
			System.out.println(Arrays.toString(value).equals(Arrays.toString(jedis.get(key))));

		} catch (FileNotFoundException e) {
			System.out.println("Caught FileNotFoundException: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("Caught IOException: " + e.getMessage());
		} finally {
			if (in != null) {
				in.close();
			}
			if (out != null) {
				out.close();
			}
		}
	}
}
