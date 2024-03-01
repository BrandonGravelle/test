package project6test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

public class FileManager {

	public static void listDirectoryContent(String directory) {
		File dir = new File(directory);
		File[] files = dir.listFiles();
		if (files != null) {
			// Sort files and directories separately (directories first)
			Arrays.sort(files, Comparator.comparing(File::isDirectory).reversed().thenComparing(File::getName));
			for (File file : files) {
				if (file.isDirectory()) {
					System.out.println("[DIR] " + file.getName());
				} else {
					System.out.println(file.getName() + " (" + file.length() + " bytes)");
				}
			}
		} else {
			System.out.println("Error: Unable to list directory content.");
		}
	}

	public static void displayFile(String directory, String filename) {
		File file = new File(directory, filename);
		try (FileInputStream fis = new FileInputStream(file)) {
			System.out.println("Hexadecimal view of file: " + filename);
			int i = 0;
			int bytes;
			StringBuilder hexBuilder = new StringBuilder();
			StringBuilder asciiBuilder = new StringBuilder();
			while ((bytes = fis.read()) != -1) {
				hexBuilder.append(String.format("%02X ", bytes));
				char ch = (char) bytes;
				if (Character.isLetterOrDigit(ch) || Character.isWhitespace(ch)) {
					asciiBuilder.append(ch);
				} else {
					asciiBuilder.append(".");
				}
				i++;
				if (i % 16 == 0) {
					System.out.println(hexBuilder.toString() + "  " + asciiBuilder.toString());
					hexBuilder.setLength(0);
					asciiBuilder.setLength(0);
				}
			}
			if (hexBuilder.length() > 0) {
				System.out.println(hexBuilder.toString() + "  " + asciiBuilder.toString());
			}
		} catch (IOException e) {
			System.out.println("Error reading file: " + e.getMessage());
		}
	}

	public static void deleteFile(String directory, String filename) {
		File file = new File(directory, filename);
		if (file.exists()) {
			if (file.delete()) {
				System.out.println("File deleted successfully: " + filename);
			} else {
				System.out.println("Error deleting file: " + filename);
			}
		} else {
			System.out.println("Error: File does not exist in the selected directory.");
		}
	}

	private static final byte[] mirrorLookupTable = new byte[256];

	static {
	    for (int i = 0; i < 256; i++) {
	        mirrorLookupTable[i] = (byte) (
	            ((i & 0x01) << 7) | ((i & 0x02) << 5) |
	            ((i & 0x04) << 3) | ((i & 0x08) << 1) |
	            ((i & 0x10) >> 1) | ((i & 0x20) >> 3) |
	            ((i & 0x40) >> 5) | ((i & 0x80) >> 7)
	        );
	    }
	}

	// Method to perform mirror reflection on a byte using lookup table
	public static byte mirrorReflectByte(byte b) {
	    byte mirroredByte = 0;
	    for (int i = 0; i < 8; i++) {
	        mirroredByte |= ((b >> i) & 1) << (7 - i);
	    }
	    return mirroredByte;
	}

	public static void mirrorReflectFile(String directory, String filename) {
	    File file = new File(directory, filename);
	    if (file.exists()) {
	        try (FileInputStream fis = new FileInputStream(file)) {
	            byte[] buffer = new byte[(int) file.length()];
	            fis.read(buffer);

	            // Mirror reflect each byte in the buffer
	            for (int i = 0; i < buffer.length; i++) {
	                byte originalByte = buffer[i];
	                byte mirroredByte = mirrorReflectByte(originalByte);
	                
	                // Print original byte and its mirrored byte
	                System.out.println("Original byte: " + originalByte + ", Mirrored byte: " + mirroredByte);
	                
	                buffer[i] = mirroredByte;
	            }

	            // Write the mirrored buffer back to the file, overwriting its content
	            try (FileOutputStream fos = new FileOutputStream(file)) {
	                fos.write(buffer);
	                System.out.println("File mirrored successfully: " + filename);
	            } catch (IOException e) {
	                System.out.println("Error writing mirrored content to file: " + e.getMessage());
	            }
	        } catch (IOException e) {
	            System.out.println("Error reading file: " + e.getMessage());
	        }
	    } else {
	        System.out.println("Error: File does not exist in the selected directory.");
	    }
	}

}
