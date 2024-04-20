package igoroffline.practice.hamsterchessv2.main;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Graphics;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import igoroffline.practice.hamsterchessv2.main.board.LetterNumber;
import igoroffline.practice.hamsterchessv2.main.board.Piece;
import igoroffline.practice.hamsterchessv2.main.board.PieceColor;
import imgui.ImGui;
import imgui.ImGuiIO;
import imgui.gl3.ImGuiImplGl3;
import imgui.glfw.ImGuiImplGlfw;
import imgui.type.ImString;
import lombok.extern.slf4j.Slf4j;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;

@Slf4j
public class MyGame extends ApplicationAdapter {

    private GameMaster gameMaster;
    private final int boardSize = 8;
    private final int squareSize = 80;
    private final int windowHeight = 720;

    private Camera cam;
    private Viewport viewport;
    private ShapeRenderer shapeRenderer;
    private SpriteBatch spriteBatch;
    private BitmapFont font;
    private Texture textureKingWhite;
    private Texture textureKingBlack;
    private Texture textureRookWhite;
    private Texture textureRookBlack;

    private MyImgui myImgui;

    ImString guiInput = new ImString("", 8);
    private int counter1 = 0;
    private int counter2 = 0;

    @Override
    public void create() {
        gameMaster = new GameMaster();

        cam = new OrthographicCamera(1280, windowHeight);
        viewport = new FitViewport(1280, windowHeight, cam);
        shapeRenderer = new ShapeRenderer();
        spriteBatch = new SpriteBatch();
        font = new BitmapFont();
        textureKingWhite = new Texture("assets/Chess_klt45_p.png");
        textureKingBlack = new Texture("assets/Chess_kdt45_p.png");
        textureRookWhite = new Texture("assets/Chess_rlt45_p.png");
        textureRookBlack = new Texture("assets/Chess_rdt45_p.png");

        myImgui = new MyImgui();
    }

    @Override
    public void render() {

        if (!ImGui.isAnyItemActive()) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.W)) {
                counter1++;
            }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.W)) {
            counter2++;
        }

        Gdx.gl.glClearColor(0.15f, 0.15f, 0.2f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        shapeRenderer.setProjectionMatrix(cam.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        boolean colorSwitch = true;
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (colorSwitch) {
                    shapeRenderer.setColor(Color.valueOf("FFF0D9B5"));
                } else {
                    shapeRenderer.setColor(Color.valueOf("FFB58863"));
                }
                final var rectX = 25F + i * squareSize;
                final var rectY = windowHeight - 105F - j * squareSize;
                shapeRenderer.rect(rectX, rectY, squareSize, squareSize);
                colorSwitch = !colorSwitch;
            }
            colorSwitch = !colorSwitch;
        }
        shapeRenderer.end();
        spriteBatch.begin();
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                final var drawIndex = j * 8 + i;
                final var square = gameMaster.getBoard().getBoard().get(drawIndex);
                final var rectX = 25F + i * squareSize;
                final var rectY = windowHeight - 105F - j * squareSize;
                final var letterNumber = square.getLetter().name() + LetterNumber.getNumber(square.getNumber().index);
                font.draw(spriteBatch, letterNumber, rectX, rectY + 12F);
            }
        }
        spriteBatch.end();
        spriteBatch.begin();
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                final var drawIndex = j * 8 + i;
                final var square = gameMaster.getBoard().getBoard().get(drawIndex);
                final var xOffset = 8F;
                final var rectX = 25F + i * squareSize + xOffset;
                final var yOffset = 10F;
                final var rectY = windowHeight - 105F - j * squareSize + yOffset;
                final var rectSize = 60F;
                Texture textureRender = null;
                if (square.getPiece() == Piece.KING && square.getPieceColor() == PieceColor.WHITE) {
                    textureRender = textureKingWhite;
                } else if (square.getPiece() == Piece.KING && square.getPieceColor() == PieceColor.BLACK) {
                    textureRender = textureKingBlack;
                } else if (square.getPiece() == Piece.ROOK && square.getPieceColor() == PieceColor.WHITE) {
                    textureRender = textureRookWhite;
                } else if (square.getPiece() == Piece.ROOK && square.getPieceColor() == PieceColor.BLACK) {
                    textureRender = textureRookBlack;
                }
                if (textureRender != null) {
                    spriteBatch.draw(textureRender, rectX, rectY, rectSize, rectSize);
                }
            }
        }
        spriteBatch.end();
        myImgui.render();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
        spriteBatch.dispose();
        font.dispose();
        textureKingWhite.dispose();
        textureKingBlack.dispose();
        textureRookWhite.dispose();
        textureRookBlack.dispose();

        myImgui.dispose();
    }

    class MyImgui {

        ImGuiImplGlfw imGuiGlfw = new ImGuiImplGlfw();
        ImGuiImplGl3 imGuiGl3 = new ImGuiImplGl3();

        public MyImgui() {
            create();
        }

        public void create() {
            long windowHandle = ((Lwjgl3Graphics) Gdx.graphics).getWindow().getWindowHandle();
            GLFW.glfwMakeContextCurrent(windowHandle);
            GL.createCapabilities();
            ImGui.createContext();
            ImGuiIO io = ImGui.getIO();
            io.setIniFilename("hamsterchessv2_imgui.ini");
            io.getFonts().addFontDefault();
            io.getFonts().build();

            imGuiGlfw.init(windowHandle, true);
            imGuiGl3.init("#version 110");
        }

        public void render() {
            imGuiGlfw.newFrame();
            ImGui.newFrame();

            // --- ImGUI draw commands go here ---
            if (ImGui.button("I'm a Button!"))
            {
                log.info("<PRESS>");
            }
            ImGui.inputText("Input", guiInput);
            ImGui.text("Press W, observe the counters");
            ImGui.text("counter1: " + counter1);
            ImGui.text("counter2: " + counter2);
            // ---

            ImGui.render();
            imGuiGl3.renderDrawData(ImGui.getDrawData());
        }

        public void dispose() {
            imGuiGl3.dispose();
            imGuiGlfw.dispose();
            ImGui.destroyContext();
        }
    }
}
