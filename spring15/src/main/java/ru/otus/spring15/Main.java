package ru.otus.spring15;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.integration.channel.QueueChannel;
import ru.otus.spring15.domain.Penalty;
import ru.otus.spring15.domain.Vehicle;
import ru.otus.spring15.endpoint.Radar;
import ru.otus.spring15.repository.PenaltyRepository;
import ru.otus.spring15.repository.VehicleRepository;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@SpringBootApplication
public class Main {

	private static Logger logger = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) {

		ExecutorService executor = Executors.newFixedThreadPool(2);

		ConfigurableApplicationContext configurableApplicationContext = SpringApplication.run(Main.class, args);
		QueueChannel queueChannel = configurableApplicationContext.getBean(QueueChannel.class);

		MongoRepository<Vehicle, String> vehicleRepository = configurableApplicationContext.getBean(VehicleRepository.class);
		MongoRepository<Penalty, String> penaltyRepository = configurableApplicationContext.getBean(PenaltyRepository.class);
		vehicleRepository.deleteAll();
		penaltyRepository.deleteAll();


		Radar radar = configurableApplicationContext.getBean(Radar.class);


		Runnable runnableTask = () -> {
			while (true) {
				radar.aim(new Vehicle(new Random().ints(50, 100).findFirst().getAsInt(),
						(new Random().ints(60, 90).findFirst().getAsInt() / 10) * 10));
				logger.info("Catch new Vehicle, QUEUE Remaining capacity: {}", queueChannel.getRemainingCapacity());
				try {
					Thread.sleep(new Random().longs(0, 500).findFirst().getAsLong());
				} catch (InterruptedException e) {
					Thread.interrupted();
				}
			}
		};

		executor.execute(runnableTask);
		executor.execute(runnableTask);
	}
}