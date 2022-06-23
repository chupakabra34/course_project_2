package server;

import setting.Settings;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientsChat implements Runnable{
    private ServerRun serverRun; //сервер
    private PrintWriter outMessage; // исходящие сообщения
    private Scanner inMessage; // входящие сообщения
    private static final String HOST = Settings.SERVERNAME;
    private static final int PORT = Settings.PORT;
    private Socket clientSocket = null;
    public static int clientsCount = 0;

    public ClientsChat(ServerRun serverRun, Socket socket) {
        try {
            this.serverRun = serverRun;
            this.clientSocket = socket;
            clientsCount++;
            this.outMessage = new PrintWriter(socket.getOutputStream());
            this.inMessage = new Scanner(socket.getInputStream());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                // у нас новый посетитель :-)
                serverRun.sendMessageAllClient("У нас есть новый участник чата " );
                serverRun.sendMessageAllClient("Всего участников чата: " + clientsCount);
                break;
            }
            // Что то поступило от клиента чата
            while (true) {
                if (inMessage.hasNext()) {
                    String clientMessage = inMessage.nextLine();
                    if (clientMessage.equalsIgnoreCase("/exit")) {
                        break;
                    }
                    // тест сообщения - потом наверное удалю
                    System.out.println(clientMessage);
                    // Сообщения для всех в чате
                    serverRun.sendMessageAllClient(clientMessage);
                }
                Thread.sleep(100); // на всякий случай задавим храпунка не надолго - может потом удалю...
            }
        } catch (InterruptedException exception) {
            exception.printStackTrace();
        } finally {
            this.closeChat();
        }
    }

    /**
     * Выводим сообщение в чате
     *
     * @param msg - текст сообщения
     */
    public void sendMessage(String msg) {
        try {
            outMessage.println(msg);
            outMessage.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Убираем клиента из чата, который ввёл команду /exit для выхода
     */
    public void closeChat() {
        serverRun.removeClient(this);
        clientsCount--;
        serverRun.sendMessageAllClient("Всего участников чата: " + clientsCount);
    }
}