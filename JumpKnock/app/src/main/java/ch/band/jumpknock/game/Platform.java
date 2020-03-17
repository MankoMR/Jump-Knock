package ch.band.jumpknock.game;

import androidx.annotation.DrawableRes;

import ch.band.jumpknock.R;

public class Platform extends Placeable {
	public boolean isOneTimeUse = false;
	public Decoration decoration = null;	@DrawableRes
	public static int[] PlatformResIds = new int[]{
			R.drawable.eearthblock1, R.drawable.eearthblock2,
			R.drawable.grasblock1, R.drawable.grasblock2,
			R.drawable.stoneblock1, R.drawable.stoneblock2,
			R.drawable.stoneblockwithgrass1, R.drawable.stoneblockwithgrass2,
			R.drawable.cloud1, R.drawable.cloud2,
	};
}
