package ch.band.jumpknock.game;

import java.util.List;

public interface UiNotifier {
	void addPlatform(Platform platform);
	void removePlatform(Platform platform);
	void UpdateGame(List<Platform> platforms, Player player, int reachedHeight);
	void updateUi(int height);
	void gameOver(int height);
	void playerCollidedWith(Platform platform);
	float getSmartPhoneRotation();
}
