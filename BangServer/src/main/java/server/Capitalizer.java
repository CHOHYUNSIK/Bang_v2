package server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Capitalizer implements Runnable{
	// input stream
	private Scanner is;
	// output stream
	private PrintWriter os;

	// constructor
	public Capitalizer(Socket socket){
		try{
			this.is = new Scanner(socket.getInputStream());
			this.os = new PrintWriter(socket.getOutputStream(), true);
		} catch(IOException e){
			System.out.println("[ERROR] > while initalize stream.");
			System.out.println(e.getMessage());
		}
		// sessionThread start
		Session sessionThread = new Session(socket);
		sessionThread.start();
	}

	@Override
	public void run(){
		// TODO: if getMessage == session~~~ ->pass to sessionThread;
	}
}

