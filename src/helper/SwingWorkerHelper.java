package helper;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;

import javax.swing.JFrame;
import javax.swing.SwingWorker;

public class SwingWorkerHelper extends JFrame {

	static final long serialVersionUID = 1L;

	private SwingWorkerHelper() {}
	
	public static <T> void workerUtilitario(Callable<T> callable, Consumer<T> consumidor) {
		SwingWorker<T, Void> worker = new SwingWorker<T, Void>(){
			@Override
			protected T doInBackground() throws Exception {
				if (callable != null) {
					return callable.call();
				}else {
					return null;
				}
			}
			
			@Override
			protected void done() {
				if (consumidor != null) {
					try {
						T t = get();
						consumidor.accept(t);
					} catch (InterruptedException | ExecutionException e) {
						e.printStackTrace();
					}
				}
			}
		};
		worker.execute();
	}
	
	
	
	
	
}


