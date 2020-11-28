package alexander.marashov.smo;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

import java.util.TreeMap;

import alexander.marashov.smo.actor_classes.ADrawable;
import alexander.marashov.smo.actor_classes.AImageTextButton;
import alexander.marashov.smo.actor_classes.HelpAndExitRow;
import alexander.marashov.smo.actor_classes.Parameter;
import alexander.marashov.smo.elements.Arrow;
import alexander.marashov.smo.elements.BufferCell;
import alexander.marashov.smo.elements.NextButton;
import alexander.marashov.smo.elements.ConsumerManager;
import alexander.marashov.smo.elements.Consumer;
import alexander.marashov.smo.elements.MemoryManager;
import alexander.marashov.smo.elements.Request;
import alexander.marashov.smo.elements.AutoButton;
import alexander.marashov.smo.elements.SkipButton;
import alexander.marashov.smo.elements.Supplier;
import alexander.marashov.smo.elements.TrashBin;

public class Main extends Game {

	public Main() {

	}

	public static float screenWidth;
	public static float screenHeight;
	static boolean loading;

	public static AssetManager assetManager;
	public static Image loadingBackground;
	private SpriteBatch batch;
	OrthographicCamera camera;

	private static TreeMap<String, BitmapFont> fonts;
	private static FreeTypeFontGenerator FontGenerator;
	private static final String fontChars = "/±ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyzабвгдеёжзийклмнопрстуфхцчшщъыьэюяАБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ0123456789][_!$%#@|?-+=()&.;:,{}'<>\"";
	private static FreeTypeFontGenerator.FreeTypeFontParameter fontParameters;
	private final String FONT_PATH = "fonts//StenbergC.otf";

	public static ScrollPane.ScrollPaneStyle scrollPaneStyle;

	public static final String BACKGROUND_PATH = "textures//backgrounds//background.jpg";
	public static final String WINDOW_BORDER_PATH = "textures//backgrounds//windowBorder.png";

	public static final String BUTTON_UP_PATH = "textures//buttons//unpressed.png";
	public static final String BUTTON_PRESSED_PATH = "textures//buttons//pressed.png";

	public static final String CHECKBOX_UNCHECKED_PATH = "textures//checkboxes//unchecked.png";
	public static final String CHECKBOX_PRESSED_PATH = "textures//checkboxes//pressed.png";
	public static final String CHECKBOX_CHECKED_PATH = "textures//checkboxes//checked.png";

	public static final String EXIT_ICON_PATH = "textures//icons//exit.png";
	public static final String HELP_ICON_PATH = "textures//icons//help.png";
	public static final String REFRESH_ICON_PATH = "textures//icons//refresh.png";

	public static final String SMO_CONSUMER_PATH = "textures//smo//consumer.png";
	public static final String SMO_CONSUMER_DOWN_PATH = "textures//smo//consumer_down.png";

	public static final String SMO_SUPPLIER_PATH = "textures//smo//supplier.png";
	public static final String SMO_SUPPLIER_DOWN_PATH = "textures//smo//supplier_down.png";

	public static final String SMO_REQUEST_PATH = "textures//smo//request.png";
	public static final String SMO_REQUEST_DOWN_PATH = "textures//smo//request_down.png";

	public static final String SMO_TRASH_PATH = "textures//smo//trash.png";
	public static final String SMO_TRASH_DOWN_PATH = "textures//smo//trash_down.png";

	public static final String SMO_MEMORY_PATH = "textures//smo//memory.png";
	public static final String SMO_MEMORY_DOWN_PATH = "textures//smo//memory_down.png";

	public static final String SMO_CHOOSER_PATH = "textures//smo//chooser.png";
	public static final String SMO_CHOOSER_DOWN_PATH = "textures//smo//chooser_down.png";

	public static final String SMO_START_PATH = "textures//smo//next.png";
	public static final String SMO_START_DOWN_PATH = "textures//smo//next_down.png";
	public static final String SMO_STOP_PATH = "textures//smo//stop.png";

	public static final String SMO_SKIP_PATH = "textures//smo//skip.png";
	public static final String SMO_SKIP_DOWN_PATH = "textures//smo//skip_down.png";

	public static final String SMO_BUFFER_CELL_PATH = "textures//smo//buffer_cell.png";
	public static final String SMO_PACKAGE_BUFFER_CELL_PATH = "textures//smo//package_buffer_cell.png";

	public static final String SMO_ARROW_HORIZONTAL_PATH = "textures//smo//arrow_horizontal.png";
	public static final String SMO_ARROW_VERTICAL_PATH = "textures//smo//arrow_vertical.png";

	public static final String SCALE_PATH = "textures//sliders//scale.png";
	public static final String SLIDER_PATH = "textures//sliders//slider.png";

	public static MenuScreen menuScreen;
	public static SimulationScreen simulationScreen;

	public static boolean enableVibration;
	public static boolean isRussian;

	public static SpriteDrawable backgroundDrawable;
	public static Drawable windowBorder;

	public static final Color BUTTONS_COLOR = new Color(127f / 255f, 219f / 255f, 239f / 255f, 1);

	@Override
	public void create() {

		Gdx.input.setCatchKey(Input.Keys.BACK, true);

		enableVibration = true;
		loading = true;
		isRussian = true;

		screenWidth = 1920;
		screenHeight = 1080;

		camera = new OrthographicCamera();
		camera.setToOrtho(false, screenWidth, screenHeight);
		batch = new SpriteBatch();

		assetManager = new AssetManager();

		FileHandleResolver resolver = new InternalFileHandleResolver();
		assetManager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
		assetManager.setLoader(BitmapFont.class, ".otf", new FreetypeFontLoader(resolver));
		assetManager.load(FONT_PATH, FreeTypeFontGenerator.class);
		assetManager.load(BACKGROUND_PATH, Texture.class);
		assetManager.load(BUTTON_UP_PATH, Texture.class);
		assetManager.load(BUTTON_PRESSED_PATH, Texture.class);
		assetManager.load(BACKGROUND_PATH, Texture.class);
		assetManager.load(EXIT_ICON_PATH, Texture.class);
		assetManager.load(HELP_ICON_PATH, Texture.class);

		assetManager.finishLoading();

		AImageTextButton.setButtonsBackground(
				new SpriteDrawable(new Sprite(assetManager.get(BUTTON_UP_PATH, Texture.class))),
				new SpriteDrawable(new Sprite(assetManager.get(BUTTON_PRESSED_PATH, Texture.class))),
				new SpriteDrawable(new Sprite(assetManager.get(BUTTON_PRESSED_PATH, Texture.class)))
		);

		fontParameters = new FreeTypeFontGenerator.FreeTypeFontParameter();
		FontGenerator = assetManager.get(FONT_PATH);

		Sprite sprite = new Sprite(assetManager.get(BACKGROUND_PATH, Texture.class));
		backgroundDrawable = new SpriteDrawable(sprite);

		fonts = new TreeMap<>();

		scrollPaneStyle = new ScrollPane.ScrollPaneStyle(
				null,
				null,
				null,
				null,
				null
		);

		loadingBackground = new Image(new SpriteDrawable(new Sprite((Texture) assetManager.get(BACKGROUND_PATH))));
		loadingBackground.setBounds(0, 0, screenWidth, screenHeight);

		loadSecondarySources();

		menuScreen = new MenuScreen(this, () -> {

			windowBorder = new SpriteDrawable(new Sprite(assetManager.get(WINDOW_BORDER_PATH, Texture.class)));

			HelpAndExitRow.setBackground(
					new SpriteDrawable(new Sprite(assetManager.get(EXIT_ICON_PATH, Texture.class))),
					null,
					new SpriteDrawable(new Sprite(assetManager.get(HELP_ICON_PATH, Texture.class))),
					null
			);

			Request.setBackgrounds(
					new SpriteDrawable(new Sprite(assetManager.get(SMO_REQUEST_PATH, Texture.class))),
					new SpriteDrawable(new Sprite(assetManager.get(SMO_REQUEST_DOWN_PATH, Texture.class))),
					new SpriteDrawable(new Sprite(assetManager.get(SMO_REQUEST_DOWN_PATH, Texture.class)))
			);

			BufferCell.setBackgrounds(
					new SpriteDrawable(new Sprite(assetManager.get(SMO_BUFFER_CELL_PATH, Texture.class))),
					new SpriteDrawable(new Sprite(assetManager.get(SMO_PACKAGE_BUFFER_CELL_PATH, Texture.class)))
			);

			Supplier.setBackgrounds(
					new SpriteDrawable(new Sprite(assetManager.get(SMO_SUPPLIER_PATH, Texture.class))),
					new SpriteDrawable(new Sprite(assetManager.get(SMO_SUPPLIER_DOWN_PATH, Texture.class))),
					new SpriteDrawable(new Sprite(assetManager.get(SMO_SUPPLIER_DOWN_PATH, Texture.class)))
			);

			Consumer.setBackgrounds(
					new SpriteDrawable(new Sprite(assetManager.get(SMO_CONSUMER_PATH, Texture.class))),
					new SpriteDrawable(new Sprite(assetManager.get(SMO_CONSUMER_DOWN_PATH, Texture.class))),
					new SpriteDrawable(new Sprite(assetManager.get(SMO_CONSUMER_DOWN_PATH, Texture.class)))
			);

			TrashBin.setBackgrounds(
					new SpriteDrawable(new Sprite(assetManager.get(SMO_TRASH_PATH, Texture.class))),
					new SpriteDrawable(new Sprite(assetManager.get(SMO_TRASH_DOWN_PATH, Texture.class))),
					new SpriteDrawable(new Sprite(assetManager.get(SMO_TRASH_DOWN_PATH, Texture.class)))
			);

			MemoryManager.setBackgrounds(
					new SpriteDrawable(new Sprite(assetManager.get(SMO_MEMORY_PATH, Texture.class))),
					new SpriteDrawable(new Sprite(assetManager.get(SMO_MEMORY_DOWN_PATH, Texture.class))),
					new SpriteDrawable(new Sprite(assetManager.get(SMO_MEMORY_DOWN_PATH, Texture.class)))
			);

			ConsumerManager.setBackgrounds(
					new SpriteDrawable(new Sprite(assetManager.get(SMO_CHOOSER_PATH, Texture.class))),
					new SpriteDrawable(new Sprite(assetManager.get(SMO_CHOOSER_DOWN_PATH, Texture.class))),
					new SpriteDrawable(new Sprite(assetManager.get(SMO_CHOOSER_DOWN_PATH, Texture.class)))
			);

			NextButton.setBackgrounds(
					new SpriteDrawable(new Sprite(assetManager.get(SMO_ARROW_VERTICAL_PATH, Texture.class))),
					new ADrawable(new Sprite(assetManager.get(SMO_ARROW_VERTICAL_PATH, Texture.class)), Color.BLUE),
					new SpriteDrawable(new Sprite(assetManager.get(SMO_ARROW_VERTICAL_PATH, Texture.class)))
			);

			AutoButton.setBackgrounds(
					new SpriteDrawable(new Sprite(assetManager.get(SMO_START_PATH, Texture.class))),
					new SpriteDrawable(new Sprite(assetManager.get(SMO_START_DOWN_PATH, Texture.class))),
					new SpriteDrawable(new Sprite(assetManager.get(SMO_STOP_PATH, Texture.class)))
			);

			SkipButton.setBackgrounds(
					new SpriteDrawable(new Sprite(assetManager.get(SMO_SKIP_PATH, Texture.class))),
					new SpriteDrawable(new Sprite(assetManager.get(SMO_SKIP_PATH, Texture.class))),
					new SpriteDrawable(new Sprite(assetManager.get(SMO_SKIP_DOWN_PATH, Texture.class)))
			);

			Arrow.setBackgrounds(
					new SpriteDrawable(new Sprite(assetManager.get(SMO_ARROW_HORIZONTAL_PATH, Texture.class))),
					new SpriteDrawable(new Sprite(assetManager.get(SMO_ARROW_VERTICAL_PATH, Texture.class)))
			);

			Parameter.setSliderSprite(new Sprite(assetManager.get(SCALE_PATH, Texture.class)));

//			simulationScreen = new SimulationScreen(this);
			menuScreen.loadingFinished();
		});
		setScreen(menuScreen);
	}

	private void loadSecondarySources() {
		assetManager.load(SMO_CONSUMER_PATH, Texture.class);
		assetManager.load(SMO_CONSUMER_DOWN_PATH, Texture.class);
		assetManager.load(SMO_REQUEST_PATH, Texture.class);
		assetManager.load(SMO_REQUEST_DOWN_PATH, Texture.class);

		assetManager.load(SMO_SUPPLIER_PATH, Texture.class);
		assetManager.load(SMO_SUPPLIER_DOWN_PATH, Texture.class);
		assetManager.load(SMO_TRASH_PATH, Texture.class);
		assetManager.load(SMO_TRASH_DOWN_PATH, Texture.class);

		assetManager.load(SMO_MEMORY_PATH, Texture.class);
		assetManager.load(SMO_MEMORY_DOWN_PATH, Texture.class);
		assetManager.load(SMO_CHOOSER_PATH, Texture.class);
		assetManager.load(SMO_CHOOSER_DOWN_PATH, Texture.class);

		assetManager.load(SMO_START_PATH, Texture.class);
		assetManager.load(SMO_START_DOWN_PATH, Texture.class);
		assetManager.load(SMO_STOP_PATH, Texture.class);
		assetManager.load(SMO_SKIP_PATH, Texture.class);
		assetManager.load(SMO_SKIP_DOWN_PATH, Texture.class);

		assetManager.load(SMO_BUFFER_CELL_PATH, Texture.class);
		assetManager.load(SMO_PACKAGE_BUFFER_CELL_PATH, Texture.class);

		assetManager.load(SMO_ARROW_HORIZONTAL_PATH, Texture.class);
		assetManager.load(SMO_ARROW_VERTICAL_PATH, Texture.class);

		assetManager.load(REFRESH_ICON_PATH, Texture.class);
		assetManager.load(CHECKBOX_CHECKED_PATH, Texture.class);
		assetManager.load(CHECKBOX_PRESSED_PATH, Texture.class);
		assetManager.load(CHECKBOX_UNCHECKED_PATH, Texture.class);
		assetManager.load(WINDOW_BORDER_PATH, Texture.class);

		assetManager.load(SCALE_PATH, Texture.class);
		assetManager.load(SLIDER_PATH, Texture.class);
	}

	@Override
	public void dispose() {
		super.dispose();
	}

	@Override
	public void pause() {
		super.pause();
	}

	@Override
	public void resume() {
		super.resume();
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	@Override
	public void setScreen(Screen screen) {
		super.setScreen(screen);
	}

	@Override
	public Screen getScreen() {
		return super.getScreen();
	}

	public static BitmapFont getFont(int size, Color color) {
		return getFont(size, color, 0, Color.WHITE);
	}

	public static BitmapFont getFont(int size, Color color, float border, Color borderColor) {
		String params = size + " " + color.toString();
		BitmapFont font = fonts.get(params);
		if (font == null) {
			font = getFont(params, border, borderColor);
			fonts.put(params, font);
		}
		return font;
	}

	public static BitmapFont getFont(String str, float borderWidth, Color borderColor) {

		str = str + " " + borderWidth + " " + borderColor;
		BitmapFont font = fonts.get(str);
		if (font == null) {
			String[] params = str.split(" ");
			fontParameters.size = Integer.parseInt(params[0]);
			fontParameters.color = Color.valueOf(params[1]);
			fontParameters.characters = fontChars;
			fontParameters.borderWidth = borderWidth;
			fontParameters.borderColor = borderColor;

			font = FontGenerator.generateFont(fontParameters);
			fonts.put(str, font);
		}
		return font;
	}

	// очистить экран белым цветом
	private static void clearScreenWhite() {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}

	// скоординировать камеру
	private void setCamera() {
		camera.update();
		batch.setProjectionMatrix(camera.combined);
	}

	// подготовить область для отрисовки с белым фоном
	public void readyWhite() {
		setCamera();
		clearScreenWhite();
	}

	static void vibrate(int milliseconds) {
		if (enableVibration && (milliseconds < 100)) {
			Gdx.input.vibrate(milliseconds);
		}
	}
}
