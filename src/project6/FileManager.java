package project6;

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
			Arrays.sort(files, Comparator.comparing(File::isDirectory).reversed().thenComparing(File::getName));
			for (File file : files) {
				if (file.isDirectory()) {
					System.out.println("[DIR] " + file.getName());
				} else {
					System.out.println(file.getName() + " (" + file.length() + " bytes)");
				}
			}
		} else {
			System.out.println("Error: No directory was selected yet.");
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
			System.out.println("Error: File doesn't exist.");
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
			System.out.println("Error: File doesn't exist.");
		}
	}

	private static byte mirrorReflectByte(byte b) {
		int result = 0;
		for (int i = 0; i < 8; i++) {
			result |= ((b >> i) & 1) << (7 - i);
		}
		return (byte) result;
	}

	public static void mirrorReflectFile(String directory, String filename) {
		File file = new File(directory, filename);
		if (file.exists()) {
			try (FileInputStream fis = new FileInputStream(file)) {
				byte[] buffer = new byte[(int) file.length()];
				fis.read(buffer);

				// Mirror reflect each byte.
				for (int i = 0; i < buffer.length; i++) {
					buffer[i] = mirrorReflectByte(buffer[i]);
				}

				fis.close();

				// Write to output stream.
				try (FileOutputStream fos = new FileOutputStream(file)) {
					fos.write(buffer);
					System.out.println("File mirrored successfully.");
				} catch (IOException e) {
					System.out.println("Error writing to file: " + e.getMessage());
				}
			} catch (IOException e) {
				System.out.println("Error reading file: " + e.getMessage());
			}
		} else {
			System.out.println("Error: File doesn't exist.");
		}
	}

}
