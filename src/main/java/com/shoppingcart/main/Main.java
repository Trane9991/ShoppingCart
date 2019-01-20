package com.shoppingcart.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.shoppingcart.util.ShoppingCartPropertyReader;
import com.shoppingcart.workflow.MultiThreadEngine;
import com.shoppingcart.workflow.SingleThreadEngine;

public class Main {

	private final static Logger log = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) {
		log.info("Starting Retail Application ...Waiting for 15 seconds for Overops to Initialize");
		waiting(15000);

		log.info("Reading Property Variables ...");
		//Get default values from property file.
		String noOfThreads = ShoppingCartPropertyReader.
			getInstance().getProperty("SHOPPING_CART.NO.OF.THREADS");
		String numberOfIterations = ShoppingCartPropertyReader.
			getInstance().getProperty("SHOPPING_CART.NO.OF.ITERATIONS");
		String runMode = ShoppingCartPropertyReader.
			getInstance().getProperty("SHOPPING_CART.RUN_MODE");

		log.info("Lets start by creating 2 random Exceptions ...");
		// Every run will create 2 random exceptions (used by Jenkins builds for new errors)
		Main2RandomExceptions.main(args);
		log.info("Generation of 2 random Exceptions complete");
		
		//Override the default properties from the command line program arguments (if passed)
		if (args != null && args.length != 0) {
			int i = 0;
			while (i < args.length) {
				if (args[i].contains("run_mode")) {
					runMode = args[i].substring(9);
					log.info("Overriding runMode with: " + runMode);
				}
				if (args[i].contains("no_of_threads")) {
					noOfThreads = args[i].substring(14);
					log.info("Overriding noOfThreads with: " + noOfThreads);
				}
				if (args[i].contains("no_of_iterations")) {
					numberOfIterations = args[i].substring(17);
					log.info("Overriding numberOfIterations with: " + numberOfIterations);
				}				
				i++;
			}
		} else {
			log.info("No of threads is: " + noOfThreads);
			log.info("No of iterations is: " + numberOfIterations);
			log.info("RunMode is: " + runMode);
		}
		
		log.info("Lets start the runs");
		if (!runMode.equalsIgnoreCase("UNCAUGHT_EXCEPTIONS") && !runMode.equalsIgnoreCase("SWALLOWED_EXCEPTION")) {
			log.info("Entering Uncaught or Swallowed exceptions loop");
			MultiThreadEngine engine = new MultiThreadEngine(
				new Integer(noOfThreads), new Integer(numberOfIterations), runMode);
			log.info("Running the multi thread engine");
			engine.run();
		} else {
			SingleThreadEngine engine = new SingleThreadEngine(
				new Integer(numberOfIterations), runMode);
			engine.run();
		}
		log.info("We are done ...");
	}

	private static void waiting(int i) {
		try {
			Thread.sleep(i);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}		
	}
}