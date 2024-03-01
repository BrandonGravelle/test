package project6test;

import java.io.File;
import java.util.Scanner;

public class Main {

	// Menu
	public static void displayMenu() {

		System.out.println("0 - Exit");
		System.out.println("1 - Select directory");
		System.out.println("2 - List directory content");
		System.out.println("3 - Display file (hexadecimal view)");
		System.out.println("4 - Delete file");
		System.out.println("5 - Mirror reflect file (byte level)");

	}

	public static void main(String[] args) {

		// Declare constants.
		final int EXIT_PROGRAM = 0;
		final int SELECT_DIRECTORY = 1;
		final int LIST_DIRECTORY_CONTENT = 2;
		final int DISPLAY_FILE = 3;
		final int DELETE_FILE = 4;
		final int MIRROR_REFLECT_FILE = 5;

		// Declare local variables
		int menuChoice = 0;
		String directory = "";

		// Open scanner.
		Scanner scan = new Scanner(System.in);

		do {
			displayMenu();

			// Prompt user to make selection from menu.
			System.out.print("Select option: ");
			menuChoice = scan.nextInt();
			System.out.println(" ");

			// Create switch to perform the selected menu option.
			switch (menuChoice) {

			case (SELECT_DIRECTORY):
				System.out.println("Option 1 Selected.");

				// Prompt the user to enter a directory path.
				System.out.println("Please enter an [absolute] directory name (path):");
				// D:\UMGC\Classes\Semester 7\CMSC 412 Operating Systems
				scan.nextLine(); // Consume the newline character
				String inputDirectory = scan.nextLine();

				// Validate the entered directory path.
				File dir = new File(inputDirectory);
				if (dir.exists() && dir.isDirectory()) {
					directory = inputDirectory;
					System.out.println("Selected directory: " + directory);
				} else {
					System.out.println("Error: Invalid directory path or directory does not exist.");
				}
				System.out.println();
				break;

			case (LIST_DIRECTORY_CONTENT):

				if (directory != null) {
					System.out.println("Option 2 Selected.");
					FileManager.listDirectoryContent(directory);
				} else {
					System.out.println("Error: Please select a directory first.");
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
					System.out.println("Error: Please select a directory first.");
				}

				break;

			case (DELETE_FILE):
				if (directory != null) {
					// Delete file
					System.out.println("Option 4 Selected. Working with directory: " + directory);
					System.out.println("Enter the filename to delete (without the path):");
					scan.nextLine(); // Consume the newline character
					String filename = scan.nextLine();
					FileManager.deleteFile(directory, filename);
				} else {
					System.out.println("Error: Please select a directory first.");
				}
				break;

			case (MIRROR_REFLECT_FILE):
				if (directory != null) {
					// Mirror reflect file
					System.out.println("Option 5 Selected. Working with directory: " + directory);
					System.out.println("Enter the filename to mirror reflect (without the path):");
					scan.nextLine(); // Consume the newline character
					String filename = scan.nextLine();
					FileManager.mirrorReflectFile(directory, filename);
				} else {
					System.out.println("Error: Please select a directory first.");
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
