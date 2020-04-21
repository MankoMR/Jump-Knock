package ch.band.jumpknock.game;

import java.util.List;
/*
 *Copyright (c) 2020 Fredy Stalder, Manuel Koloska, All rights reserved.
 */
public interface UiNotifier {
	void addPlatform(Platform platform);
	void removePlatform(Platform platform);
	void UpdateGame(List<Platform> platforms, Player player, float reachedHeight, GameVariables gameVariables);
	void updateUi(float height);
	void gameOver(float height);
	void playerCollidedWith(Platform platform);
	//TODO wird das gebraucht?
	float getSmartPhoneRotation();
}
