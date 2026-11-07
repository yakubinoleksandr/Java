package com.example.Task2;

public class ProducerConsumerApp {

    public static void main(String[] args) throws InterruptedException {
        CircularBuffer<String> buffer1 = new CircularBuffer<>(10);
        CircularBuffer<String> buffer2 = new CircularBuffer<>(10);

        for (int i = 1; i <= 5; i++) {
            final int producerId = i;
            Thread producer = new Thread(() -> {
                int msgNum = 1;
                while (true) {
                    try {
                        String msg = "Потік №" + producerId + " згенерував повідомлення #" + msgNum++;
                        buffer1.put(msg);
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            });
            producer.setDaemon(true);
            producer.start();
        }

        for (int i = 1; i <= 2; i++) {
            final int relayerId = i;
            Thread relayer = new Thread(() -> {
                while (true) {
                    try {
                        String inputMsg = buffer1.take();
                        String outputMsg = "Потік №" + relayerId + " переклав повідомлення [" + inputMsg + "]";
                        buffer2.put(outputMsg);
                        Thread.sleep(30);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            });
            relayer.setDaemon(true);
            relayer.start();
        }

        for (int i = 1; i <= 100; i++) {
            String result = buffer2.take();
            System.out.println(i + ": " + result);
        }

        System.out.println("Завершено зчитування 100 повідомлень. Програма завершує роботу.");
    }
}
