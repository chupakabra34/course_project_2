package server;
import setting.Settings;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
/**
 * \* Created with IntelliJ IDEA.
 * \* Author: Prekrasnov Sergei
 * \* Date: 20.06.2022
 * \* ----- group JAVA-27 -----
 * \*
 * \* Description: Курсовой проект "Сетевой чат"
 * \*
 */
public class ServerRun {

    // список клиентов, которые будут подключаться к серверу
    private final ArrayList<ClientsChat> clients = new ArrayList<>();

    public ServerRun() {

        // сокет клиента, который будет подключаться к серверу по адресу и порту
        Socket clientSocket = null;
        // Серверный сокет
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(Settings.PORT);
            System.out.println("Сервер запущен");
            // Запускаем бесконечный цикл
            while (true) {
                clientSocket = serverSocket.accept(); //тут ждём подключения от сервера
                ClientsChat client = new ClientsChat(this, clientSocket); //создаём клиента который подключится к серверу
                clients.add(client);
                new Thread(client).start(); // каждому новому клиенту - новый поток
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            //Закрываем подключение
            try {
                clientSocket.close();
                System.out.println("Сервер остановлен");
                serverSocket.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }

    /**
     * Отправка сообщений клиентам
     *
     * @param msg - текст сообщения
     */
    public void sendMessageAllClient(String msg) {
        for (ClientsChat o : clients) {
            o.sendMessage(msg);
        }
    }

    /**
     * удаляем клиента при выходе из чата
     *
     * @param client - текущий чатовец - который захотел выйти
     */
    public void removeClient(ClientsChat client) {
        clients.remove(client);
    }
}