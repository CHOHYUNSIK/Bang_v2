/* 
 * class name: Commander
 * purpose: command management
 */
package server;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

import debug.DateTime;
import manager.GameManager;

public class Commander extends Thread{
	// Scanner for command
	private Scanner sc;
	// command info
	private String cmd;

	// constructor
	public Commander(Scanner sc){
		this.sc = sc;
		this.cmd = "";
	}

	@Override
	public void run(){
		while(!this.isInterrupted()){
			cmd = sc.nextLine();
			switch(cmd){
				// help
				case "?":
				case "h":
					help();
					break;
					// stop server
				case "stop server":
					stop_server();
					break;
					// show clients info
				case "show clients":
					show_clients();
					break;
					// game start!
				case "game start":
					game_start();
					break;
					// show password
				case "show pw":
					show_pw("login");
					show_pw("start");
					break;
					// change login password
				case "new login pw":
					new_pw("login");
					break;
					// change start password
				case "new start pw":
					new_pw("start");
					break;
					// nothing...
				case "":
					break;
					// unknown command
				default:
					System.out.println("[System][Commander] > Unknown command: " + cmd);
			}
		}
		// end of run
		System.out.println("[System][Commander] > Thread(commandThread) is stopped.");
	}

	// set new password
	private void new_pw(String target){
		server.App.resetPw(target);
		System.out.println("[System][Commander] > "+target.toUpperCase()+" password changed!");
		show_pw(target);
	}

	// show password
	private void show_pw(String target){
		if(target.equals("login")) System.out.println("[System][Commander] > Login password is ["+server.App.getLoginPw()+"]");
		else if(target.equals("start")) System.out.println("[System][Commander] > Start password is ["+server.App.getStartPw()+"]");
	}

	// game start!
	private void game_start(){
		// if game is already started,
		if(App.getGameStarted() == true){
			System.out.println("[System][Commander] > Game is already started.");
			return;
		}
		// if not in 7 players,
		if(App.getClientsPrintWriter().size() != 7){
			System.out.println("[System][Commander] > Only 7 players required. (now: "+App.getClientsPrintWriter().size()+" / 7)");
			return;
		}
		// if not ready all
		if(GameManager.getReadyPlayer() != 7){
			System.out.println("[System][Commander] > Everyone ready required. (now: "+GameManager.getReadyPlayer()+" / 7)");
			return;
		}
		// check startPw
		System.out.print("[System][Commander] > Starting PW: ");
		String input = sc.nextLine();
		if(!input.equals(Integer.toString(App.getStartPw()))) {
			System.out.println("[System][Commander] > Wrong Password. ABORT.");
			return;
		}
		// game start == true
		App.setGameStarted(true);
		System.out.println("[System][Commander] > setGameStarted: ["+App.getGameStarted()+"]");
		// reset readyPlayer count
		GameManager.setReadyPlayer(0);
		// delete ready button
		App.broadcast("game/DISABLE/READYBUTTON");
		// broadcast game is begin
		for(int cnt=5; cnt>0; cnt--){
			System.out.println("[System][Commander] > Game start in "+cnt+"...");
			App.broadcast("game/SETTEXT/MIDDLE_NOTICE/Game start in "+cnt+"...");
			try{Thread.sleep(1000);}
			catch(InterruptedException e){System.out.println("[ERROR] > while count down."); return;}
		}
		DateTime.showTime();
		System.out.println("[System][Commander] > Game start!");
		// broadcast the game is started
		App.broadcast("game/STATE/START");
		// init gm(setting seat, select role, select scenario, select character, etc)
		App.getGm().init();
		// start gm(start turn until game is ended)
		App.getGm().start();
		// if you here, game is ended!
		DateTime.showTime();
		System.out.println("[System][Commander] > Game Over!");
		// broadcast the game is ended
		App.broadcast("game/STATE/END");
		// game start == false
		App.setGameStarted(false);
		System.out.println("[System][Commander] > setGameStarted: ["+App.getGameStarted()+"]");
		// resetting "Waiting for players..."
		App.broadcast("game/ENABLE/MIDDLE_NOTICE");
		App.broadcast("player/PLAYERNUM/"+server.App.getPlayerNumber());
	}

	// helper
	private void help(){
		System.out.println("[System][Commander] > stop server:\t Stop the server.");
		System.out.println("[System][Commander] > show clients:\t Show clients info.");
		System.out.println("[System][Commander] > game start:\t Start game.");
		System.out.println("[System][Commander] > show pw:\t\t Show passwords.");
		System.out.println("[System][Commander] > new login pw:\t Reset login password.");
		System.out.println("[System][Commander] > new start pw:\t Reset start password.");
	}

	// show online & logined clients
	private void show_clients(){
		// if no clients,
		if(App.getClients().size() == 0){
			System.out.println("[System][Commander] > Nobody in here.");
			return;
		}
		System.out.println("[System][Commander] > =======Log on Clients=======");
		// online clients,
		int idx = 0;
		for(Socket s : App.getClients().keySet()){
			if(!App.getClientsPrintWriter().containsValue(App.getClients().get(s))){
				System.out.println("[System][Commander] > "+s);
				idx++;
			}
		}
		// no online clients,
		if(idx == 0) System.out.println("[System][Commander] > Nobody in here.");

		// ingame clients,
		idx = 0;
		System.out.println("[System][Commander] > =======Log in Clients=======");
		for(String s : App.getClientsPrintWriter().values()){
			System.out.println("[System][Commander] > PlayerName ["+s+"]");
			idx++;
		}
		// no ingame clients,
		if(idx == 0) System.out.println("[System][Commander] > Nobody in here.");
	}

	// stop server
	private void stop_server(){
		System.out.println("[System][Commander] > Server closing...");
		// broadcasting [session/DISCONNECTED] message
		App.broadcast("session/DISCONNECTED");
		// thread stop
		this.interrupt();
		try {
			// listener stop
			App.getServerSocket().close();
			System.out.println("[System][Commander] > ServerSocket(listener) is stopped.");
		} catch (IOException e) {
			System.out.println("[ERROR] > while closing ServerSocket(listener).");
			System.out.println(e.getMessage());
		} finally {
			// sc stop
			sc.close();
			App.getScanner().close();
			System.out.println("[System][Commander] > Scanner(App.sc, Commander.sc) is stopped.");
			// pool stop
			App.getExecutorService().shutdown();
			System.out.println("[System][Commander] > ExecutorService(pool) is terminated.");
		}
	}

} // end of class
