import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class ParallelQueries {
    private Semaphore Access;
    private Semaphore Full;
    private Semaphore Empty;
    private ArrayList<String> StorageItem = new ArrayList<>();
    private final int Items;
    private final int StorageSize;
    private Callback ProducerCallback = null;
    private Callback ConsumerCallback = null;

    private class Consumer implements Runnable {
        public Consumer() {
            new Thread(this).start();
        }

        @Override
        public void run() {
            for (int i = 0; i < Items; i++) {
                String item;
                try {
                    Empty.acquire();
                    Thread.sleep(1000);
                    Access.acquire();

                    if (ConsumerCallback != null)
                        ConsumerCallback.invoke(StorageItem.getFirst());
                    StorageItem.removeFirst();

                    Access.release();
                    Full.release();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class Producer implements Runnable {
        public Producer() {
            new Thread(this).start();
        }

        @Override
        public void run() {
            for (int i = 0; i < Items; i++) {
                try {
                    Full.acquire();
                    Access.acquire();

                    String item = "item " + i;
                    StorageItem.add(item);
                    if (ProducerCallback != null)
                        ProducerCallback.invoke(item);

                    Access.release();
                    Empty.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public ParallelQueries OnProducerCallback(Callback cb) {
        ProducerCallback = cb;
        return this;
    }

    public ParallelQueries OnConsumerCallback(Callback cb) {
        ConsumerCallback = cb;
        return this;
    }

    public ParallelQueries(int items, int storageSize) {
        Items = items;
        StorageSize = storageSize;

        Access = new Semaphore(1);
        Full = new Semaphore(storageSize);
        Empty = new Semaphore(0);

        new Consumer();
        new Producer();
    }
}
