package com.taxi.backend;

import com.taxi.backend.repository.UserRepository;
import com.taxi.backend.utils.socket.SocketServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
@EnableCaching
@SpringBootApplication
public class TaxiBooking {

	public static void main(String[] args) {
		SpringApplication.run(TaxiBooking.class, args);
	}
	@Bean
	public SocketServer socketServer() throws IOException {
		SocketServer socketServer = new SocketServer(8081); // Socket server port
		socketServer.start();
		return socketServer;
	}

}
