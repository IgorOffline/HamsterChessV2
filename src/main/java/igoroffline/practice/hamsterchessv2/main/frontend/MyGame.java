package igoroffline.practice.hamsterchessv2.main.frontend;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Graphics;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import igoroffline.practice.hamsterchessv2.main.board.LetterNumber;
import igoroffline.practice.hamsterchessv2.main.board.PieceColor;
import igoroffline.practice.hamsterchessv2.main.board.Square;
import igoroffline.practice.hamsterchessv2.main.game.GameMaster;
import imgui.ImGui;
import imgui.ImGuiIO;
import imgui.gl3.ImGuiImplGl3;
import imgui.glfw.ImGuiImplGlfw;
import imgui.type.ImString;
import java.util.Arrays;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;

@Slf4j
public class MyGame extends ApplicationAdapter {

    private GameMaster gameMaster;
    private final int boardSize = 8;
    private final int squareSize = 80;
    private final int windowHeight = 720;
    // temp hack
    private final int[] moveRightIllegal = new int[boardSize];
    private int selectedSquareIndex = 0;
    private Optional<Square> selectedSquare = Optional.empty();
    private Optional<Square> fromSquare = Optional.empty();
    private Optional<Square> toSquare = Optional.empty();

    private Camera cam;
    private Viewport viewport;
    private ShapeRenderer shapeRenderer;
    private SpriteBatch spriteBatch;
    private BitmapFont font;
    private Textures textures;

    private MyImgui myImgui;

    ImString guiInput = new ImString("", 8);
    private int counter1 = 0;
    private int counter2 = 0;

    @Override
    public void create() {
        gameMaster = new GameMaster();

        for (int i = 0; i < moveRightIllegal.length; i++) {
            moveRightIllegal[i] = i * boardSize + boardSize - 1;
        }
        newSquareSelected();

        cam = new OrthographicCamera(1280, windowHeight);
        viewport = new FitViewport(1280, windowHeight, cam);
        shapeRenderer = new ShapeRenderer();
        spriteBatch = new SpriteBatch();
        font = new BitmapFont();
        textures = new Textures();

        myImgui = new MyImgui();
    }

    @Override
    public void render() {

        if (Gdx.input.isKeyJustPressed(Input.Keys.J) && selectedSquareIndex < boardSize * boardSize - boardSize) {
            selectedSquareIndex += boardSize;
            newSquareSelected();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.K) && selectedSquareIndex >= boardSize) {
            selectedSquareIndex -= boardSize;
            newSquareSelected();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.H) && selectedSquareIndex % boardSize != 0) {
            selectedSquareIndex--;
            newSquareSelected();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.L)
                && (Arrays.binarySearch(moveRightIllegal, selectedSquareIndex) < 0)) {
            selectedSquareIndex++;
            newSquareSelected();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.Q) && selectedSquare.isPresent()) {
            boolean colorOk = gameMaster.isWhiteToMove()
                    ? selectedSquare.get().getPieceColor() == PieceColor.WHITE
                    : selectedSquare.get().getPieceColor() == PieceColor.BLACK;
            if (colorOk) {
                fromSquare = gameMaster.getLegalMoves().getLegalMoves().keySet().stream()
                        .filter(sq -> selectedSquareIndex == sq.getIndex())
                        .findFirst();
            }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.E) && fromSquare.isPresent()) {
            final var legalMoves = gameMaster.getLegalMoves().getLegalMoves().get(fromSquare.get());
            final var toSquareFound = legalMoves.stream()
                    .filter(sq -> selectedSquareIndex == sq.getIndex())
                    .findFirst();
            if (toSquareFound.isPresent()) {
                toSquare = toSquareFound;

                gameMaster.moveAndCalculate(
                        fromSquare.get().getIndex(), toSquare.get().getIndex());

                fromSquare = Optional.empty();
                toSquare = Optional.empty();
            }
        }

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
        renderShapesAndTextures();
        myImgui.render();
    }

    private void newSquareSelected() {
        selectedSquare = gameMaster.getLegalMoves().getLegalMoves().keySet().stream()
                .filter(sq -> selectedSquareIndex == sq.getIndex())
                .findFirst();
    }

    private void renderShapesAndTextures() {
        renderBoard();
        renderLetterNumbers();
        renderLegalMoveIndicator();
        renderPieces();
        renderSelectedSquarePointer();
    }

    private void renderBoard() {
        spriteBatch.begin();
        boolean colorSwitch = true;
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                Texture texture;
                if (colorSwitch) {
                    texture = textures.getTextureColor1();
                } else {
                    texture = textures.getTextureColor2();
                }
                final var rectX = 25F + i * squareSize;
                final var rectY = windowHeight - 105F - j * squareSize;
                spriteBatch.draw(texture, rectX, rectY, squareSize, squareSize);
                colorSwitch = !colorSwitch;
            }
            colorSwitch = !colorSwitch;
        }
        spriteBatch.end();
    }

    private void renderLetterNumbers() {
        spriteBatch.begin();
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                final var square = gameMaster.getBoard().getBoard()[row][col];
                final var rectX = 25F + row * squareSize;
                final var rectY = windowHeight - 105F - col * squareSize;
                final var letterNumber = square.getLetter().name() + LetterNumber.getNumber(square.getNumber().index);
                font.draw(spriteBatch, letterNumber, rectX, rectY + 12F);
            }
        }
        spriteBatch.end();
    }

    private void renderLegalMoveIndicator() {
        if (selectedSquare.isPresent()) {
            final var legalMoves = gameMaster.getLegalMoves().getLegalMoves().get(selectedSquare.get());
            spriteBatch.begin();
            for (final var legalMove : legalMoves) {
                for (int i = 0; i < boardSize; i++) {
                    for (int j = 0; j < boardSize; j++) {
                        final var drawIndex = j * 8 + i;
                        if (drawIndex == legalMove.getIndex()) {
                            final var rectX = 25F + i * squareSize;
                            final var rectY = windowHeight - 100F + 55F - j * squareSize;
                            spriteBatch.draw(textures.getTextureLegalMoveIndicator(), rectX, rectY, 20F, 20F);
                        }
                    }
                }
            }
            spriteBatch.end();
        }
    }

    private void renderPieces() {
        spriteBatch.begin();
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                final var square = gameMaster.getBoard().getBoard()[row][col];
                final var xOffset = 8F;
                final var rectX = 25F + row * squareSize + xOffset;
                final var yOffset = 10F;
                final var rectY = windowHeight - 105F - col * squareSize + yOffset;
                final var rectSize = 60F;
                Texture textureRender = textures.getPieceTexture(square);
                if (textureRender != null) {
                    spriteBatch.draw(textureRender, rectX, rectY, rectSize, rectSize);
                }
            }
        }
        spriteBatch.end();
    }

    private void renderSelectedSquarePointer() {
        spriteBatch.begin();
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                final var drawIndex = col * 8 + row;
                final var xOffset = -10F;
                final var rectX = 25F + row * squareSize + xOffset;
                final var yOffset = 15F;
                final var rectY = windowHeight - 105F - col * squareSize + yOffset;
                final var rectSize = 40F;
                if (selectedSquareIndex == drawIndex) {
                    spriteBatch.draw(textures.getTextureSelectedSquarePointer(), rectX, rectY, rectSize, rectSize);
                }
            }
        }
        spriteBatch.end();
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
        textures.dispose();

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
            ImGui.begin("Hamster Chess V2");
            if (ImGui.button("I'm a Button!")) {
                log.info("<PRESS>");
            }
            ImGui.inputText("Input", guiInput);
            ImGui.text("Press W, observe the counters");
            ImGui.text("counter1: " + counter1);
            ImGui.text("counter2: " + counter2);
            ImGui.text("selectedSquareIndex: " + selectedSquareIndex);

            var guiFromIndex = -1;
            if (fromSquare.isPresent()) {
                guiFromIndex = fromSquare.get().getIndex();
            }
            ImGui.text("fromIndex: " + guiFromIndex);

            var guiToIndex = -1;
            if (toSquare.isPresent()) {
                guiToIndex = toSquare.get().getIndex();
            }
            ImGui.text("toIndex: " + guiToIndex);

            var legalSquaresCount = -1;
            if (selectedSquare.isPresent()) {
                legalSquaresCount = gameMaster
                        .getLegalMoves()
                        .getLegalMoves()
                        .get(selectedSquare.get())
                        .size();
            }

            ImGui.text("legalSquaresCount: " + legalSquaresCount);
            ImGui.end();
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
