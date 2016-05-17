package com.leoni.q_gate.task;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

/**
 * 
 * @author SABER KHALIFA
 *
 */
public class Q_GateTask {
	public static Service<Integer> getService() {
		Service<Integer> service = new Service<Integer>() {
			@Override
			protected Task<Integer> createTask() {
				return new Task<Integer>() {
					@Override
					protected Integer call() throws Exception {
						Integer max = 36;
						if (max > 35) {
							max = 30;
						}
						updateProgress(0, max);
						for (int k = 0; k < max; k++) {
							Thread.sleep(40);
							updateProgress(k + 1, max);
						}
						return max;
					}
				};
			}
		};
		return service;
	}
}
