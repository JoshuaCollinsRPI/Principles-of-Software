package hw0;

import java.util.Random;

public class RandomHello {

	public String getGreeting() {
		String[] greetings = new String[5];
		greetings[0] = "Hello, World";
		greetings[1] = "Hola Mundo";
		greetings[2] = "Bonjour, le Monde";
		greetings[3] = "Hallo Welt";
		greetings[4] = "Ciao Mondo";
		
		Random random = new Random();
		
		return greetings[random.nextInt(5)];
	}
	
	public static void main(String[] args) {
		RandomHello randomHello = new RandomHello();
		System.out.println(randomHello.getGreeting());
		return;
	}
	
}
