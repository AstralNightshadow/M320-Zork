package ch.bbw.zork;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Stack;

/**
 * Class Game - the main class of the "Zork" game.
 *
 * Author: Michael Kolling, 1.1, March 2000
 * refactoring: Rinaldo Lanza, September 2020
 */

public class Game {

	private Parser parser;
	private Room currentRoom;
	private Room foyer, hallway1, hallway2, library, securityRoom, masterBedroom, livingRoom, kitchen, guestBedroom,
			staffQuarters;

	public Game() {

		parser = new Parser(System.in);

		// create rooms
		foyer = new Room("the central foyer of the mansion");
		hallway1 = new Room("a hallway connecting the foyer to the library and master bedroom");
		hallway2 = new Room("a hallway connecting the foyer to the kitchen, guest bedroom, and staff quarters");
		library = new Room("a grand library filled with ancient books");
		securityRoom = new Room("a secure room with surveillance equipment");
		masterBedroom = new Room("a luxurious master bedroom");
		livingRoom = new Room("a comfortable and elegant living room");
		kitchen = new Room("a spacious and well-equipped kitchen");
		guestBedroom = new Room("a cozy guest bedroom");
		staffQuarters = new Room("the living space for the mansion's staff");

		// initialise room exits
		foyer.setExits(hallway1, hallway2, livingRoom, null);
		hallway1.setExits(library, null, foyer, masterBedroom);
		hallway2.setExits(kitchen, staffQuarters, guestBedroom, foyer);
		library.setExits(null, securityRoom, null, hallway1);
		securityRoom.setExits(null, null, null, library);
		masterBedroom.setExits(null, hallway1, null, null);
		livingRoom.setExits(null, null, null, foyer);
		kitchen.setExits(null, null, hallway2, null);
		guestBedroom.setExits(null, hallway2, null, null);
		staffQuarters.setExits(null, null, null, hallway2);

		currentRoom = foyer; // start game in the foyer
	}

	/**
	 * Main play routine. Loops until end of play.
	 */
	public void play() {
		printWelcome();

		// Enter the main command loop. Here we repeatedly read commands and
		// execute them until the game is over.
		boolean finished = false;
		while (!finished) {
			Command command = parser.getCommand();
			finished = processCommand(command);
		}
		System.out.println("Thank you for playing.  Good bye.");
	}

	private void printWelcome() {
		System.out.println();
		System.out.println("Welcome to Zork!");
		System.out.println("Zork is a simple adventure game.");
		System.out.println("Type 'help' if you need help.");
		System.out.println();
		System.out.println(currentRoom.longDescription());
	}

	private boolean processCommand(Command command) {
		if (command.isUnknown()) {
			System.out.println("I don't know what you mean...");
			return false;
		}

		String commandWord = command.getCommandWord();
		if (commandWord.equals("help")) {
			printHelp();

		} else if (commandWord.equals("go")) {
			goRoom(command);

		} else if (commandWord.equals("quit")) {
			if (command.hasSecondWord()) {
				System.out.println("Quit what?");
			} else {
				return true; // signal that we want to quit
			}
		}
		return false;
	}

	private void printHelp() {
		System.out.println("You are lost. You are alone. You wander");
		System.out.println("around at Monash Uni, Peninsula Campus.");
		System.out.println();
		System.out.println("Your command words are:");
		System.out.println(parser.showCommands());
	}

	private void goRoom(Command command) {
		if (!command.hasSecondWord()) {
			System.out.println("Go where?");
		} else {

			String direction = command.getSecondWord();

			// Try to leave current room.
			Room nextRoom = currentRoom.nextRoom(direction);

			if (nextRoom == null)
				System.out.println("There is no door!");
			else {
				currentRoom = nextRoom;
				System.out.println(currentRoom.longDescription());
			}
		}
	}
}
