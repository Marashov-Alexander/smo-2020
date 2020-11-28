package alexander.marashov.smo.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import alexander.marashov.smo.Main;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 683;
//		config.width = 1280;
//		config.height = 720;
		config.height = 384;
		new LwjglApplication(new Main(), config);
	}
}
