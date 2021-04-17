package User;

import javax.swing.*;
import java.util.concurrent.*;

public abstract class TimeLimitedCode {

    private int time;
    private TimeUnit timeUnit;

    public TimeLimitedCode(int time, TimeUnit timeUnit){
        this.time = time;
        this.timeUnit = timeUnit;
    }

    public abstract void codeBlock();

    public void start() throws ConnectionException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Action action = new Action();
        Future future = executorService.submit(action);
        executorService.shutdown();


        try {
            future.get(time, timeUnit);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            executorService.shutdownNow();
            JOptionPane.showConfirmDialog(new JOptionPane(), "Закончилось время ожидания ответа от сервера!", "Ошибка подключения", JOptionPane.OK_CANCEL_OPTION);
            throw new ConnectionException();
        }

    }

    private class Action implements Runnable{

        @Override
        public void run() {
            codeBlock();
        }
    }
}
