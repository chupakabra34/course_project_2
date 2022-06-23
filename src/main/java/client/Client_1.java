package client;

import setting.Settings;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

import static setting.Settings.SERVERNAME;

public class Client_1 {
    // адрес сервера
    private static final String HOST = SERVERNAME;
    // порт
    private static final int PORT = Settings.PORT;
    // клиентский сокет
    private Socket clientSocket;
    // входящее сообщение
    private Scanner inMessage;
    // исходящее сообщение
    private PrintWriter outMessage;
    final Scanner sc = new Scanner(System.in);

    public Client_1() {
        try {
            // подключаемся к серверу
            clientSocket = new Socket(SERVERNAME, PORT);
            inMessage = new Scanner(clientSocket.getInputStream());
            outMessage = new PrintWriter(clientSocket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.print("Введите ваше имя: ");
        String clientName = sc.nextLine();

        Thread sender = new Thread(() -> {
            while (true) {
                System.out.print("[Текст сообщения]: ");
                String msg = sc.nextLine();
                sendMsg(clientName, msg);

            }
        });
        sender.start();
        Thread receiver = new Thread(() -> {
            try {
                // бесконечный цикл
                while (true) {
                    // если есть входящее сообщение
                    if (inMessage.hasNext()) {
                        // считываем его
                        String inMes = inMessage.nextLine();
                        System.out.println(inMes);
                    }
                }
            } catch (Exception e) {
            }
        });
        receiver.start();
    }

    // отправка сообщения
    public void sendMsg(String clientName, String msg) {
        // формируем сообщение для отправки на сервер
        String messageStr = clientName + ": " + msg;
        // отправляем сообщение
        outMessage.println(messageStr);
        outMessage.flush();
    }
}
