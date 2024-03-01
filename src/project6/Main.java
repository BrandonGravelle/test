package project6;

import java.io.File;
import java.util.Scanner;

public class Main {

	public static void displayMenu() {

		System.out.println("0 - Exit");
		System.out.println("1 - Select directory");
		System.out.println("2 - List directory content");
		System.out.println("3 - Display file (hexadecimal view)");
		System.out.println("4 - Delete file");
		System.out.println("5 - Mirror reflect file (byte level)");

	}

	public static void main(String[] args) {

		// Constants.
		final int EXIT_PROGRAM = 0;
		final int SELECT_DIRECTORY = 1;
		final int LIST_DIRECTORY_CONTENT = 2;
		final int DISPLAY_FILE = 3;
		final int DELETE_FILE = 4;
		final int MIRROR_REFLECT_FILE = 5;

		// Local variables
		int menuChoice = 0;
		String directory = null;

		Scanner scan = new Scanner(System.in);

		do {
			displayMenu();

			// Prompt user to make selection from menu.
			System.out.print("Select option: ");
			menuChoice = scan.nextInt();
			System.out.println(" ");

			// Switch to perform the selected menu option.
			switch (menuChoice) {

			case (SELECT_DIRECTORY):
				System.out.println("Option 1 Selected.");

				System.out.println("Please enter an [absolute] directory name (path):");

				// Use below for testing directory.
				// D:\UMGC\Classes\Semester 7\CMSC 412 Operating Systems

				scan.nextLine();
				String inputDirectory = scan.nextLine();

				// Validate path.
				File dir = new File(inputDirectory);
				if (dir.exists() && dir.isDirectory()) {
					directory = inputDirectory;
					System.out.println("Selected directory: " + directory);
				} else {
					System.out.println("Error: Directory does not exist.");
				}
				System.out.println();
				break;

			case (LIST_DIRECTORY_CONTENT):

				if (directory != null) {
					System.out.println("Option 2 Selected.");
					FileManager.listDirectoryContent(directory);
				} else {
					System.out.println("Error: Please select a directory.");
				}
				System.out.println();

				break;

			case (DISPLAY_FILE):

				if (directory != null) {
					System.out.println("Option 3 Selected.");
					System.out.println("Enter the filename (without the path).");
					scan.nextLine();
					String filename = scan.nextLine();
					FileManager.displayFile(directory, filename);

				} else {
					System.out.println("Error: No directory was selected yet.");
				}

				break;

			case (DELETE_FILE):
				if (directory != null) {
					// Delete file
					System.out.println("Option 4 Selected. Directory: " + directory);
					System.out.println("Enter the filename to delete (without the path):");
					scan.nextLine();
					String filename = scan.nextLine();
					FileManager.deleteFile(directory, filename);
				} else {
					System.out.println("Error: No directory was selected yet.");
				}
				break;

			case (MIRROR_REFLECT_FILE):
				if (directory != null) {
					// Mirror reflect file
					System.out.println("Option 5 Selected. Directory: " + directory);
					System.out.println("Enter the filename to mirror reflect (without the path):");
					scan.nextLine();
					String filename = scan.nextLine();
					FileManager.mirrorReflectFile(directory, filename);
				} else {
					System.out.println("Error: No directory was selected yet.");
				}
				break;

			case (EXIT_PROGRAM):
				System.out.print("Thank you for using the program. Goodbye!");
				break;

			default:
				// error if selection is not on menu list
				System.out.println("Invalid choice. Please enter \nan option from the menu.");
				System.out.println(" ");

			}

		} while (menuChoice != EXIT_PROGRAM);

		System.out.println(" ");

		scan.close();

	}

}
