package com.sportygroup.betting;

import org.springframework.boot.SpringApplication;

public class TestBettingApplication {

	public static void main(String[] args) {
		SpringApplication.from(BettingApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
