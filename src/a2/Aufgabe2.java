package a2;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

import lenz.opengl.AbstractOpenGLBase;
import lenz.opengl.ShaderProgram;

public class Aufgabe2 extends AbstractOpenGLBase {
	private int timeUniformLocation;
	private long startTime;

	public static void main(String[] args) {
		new Aufgabe2().start("CG Aufgabe 2", 700, 700);
	}

	@Override
	protected void init() {
		// folgende Zeile laed automatisch "aufgabe2_v.glsl" (vertex) und "aufgabe2_f.glsl" (fragment)
		ShaderProgram shaderProgram = new ShaderProgram("aufgabe2");
		glUseProgram(shaderProgram.getId());

		//die uniformVariable anlegen und die zeit zum beginn des programms festlegen
		timeUniformLocation = glGetUniformLocation(shaderProgram.getId(), "time");
		startTime= System.currentTimeMillis();

		float[] dreiecksKoordinaten = new float[]{0.5f,0.5f,-0.5f,0.5f,-0.5f,-0.5f};


		int vaoId = glGenVertexArrays();
		glBindVertexArray(vaoId);
		int vboId = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vboId);
		glBufferData(GL_ARRAY_BUFFER,
				dreiecksKoordinaten, GL_STATIC_DRAW);
		glVertexAttribPointer(0, 2, GL_FLOAT,
				false, 0, 0);
		glEnableVertexAttribArray(0);
		// Koordinaten, VAO, VBO, ... hier anlegen und im Grafikspeicher ablegen
	}

	@Override
	public void update() {
		// vergangene Zeit berechnen
		float elapsedTime = (System.currentTimeMillis() - startTime) / 1000.0f;
		// die zeig als Uniform variable an den shader weitergeben
		glUniform1f(timeUniformLocation, elapsedTime);
	}

	@Override
	protected void render() {
		glClear(GL_COLOR_BUFFER_BIT);
		int anzEcken = 3;
		// hier vorher erzeugte VAOs zeichnen
		glDrawArrays(GL_TRIANGLES, 0, anzEcken);
	}
}
