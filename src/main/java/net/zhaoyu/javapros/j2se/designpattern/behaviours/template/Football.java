package net.zhaoyu.javapros.j2se.designpattern.behaviours.template;

public class Football extends Game {
	@Override
			void endPlay() {
		System.out.println("Football Game Finished!");
	}

	@Override
			void initialize() {
		System.out.println("Football Game Initialized! Start playing.");
	}

	@Override
			void startPlay() {
		System.out.println("Football Game Started. Enjoy the game!");
	}
}
