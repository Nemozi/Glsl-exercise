package projekt;
import static org.lwjgl.opengl.GL30.*;

import lenz.opengl.AbstractOpenGLBase;
import lenz.opengl.ShaderProgram;
import lenz.opengl.Texture;

import java.io.*;

public class Projekt extends AbstractOpenGLBase {

	private ShaderProgram shaderProgram;
	private ShaderProgram shaderProgram2;
	private float angle;
	private Texture highQual;
	private Texture lowQual;

	public static void main(String[] args) {
		new Projekt().start("CG Projekt", 1000, 1000);
	}
	private	final int matrixLocation=0;
	private int vaoId;
	private int vaoId2;
	private int objVaoId;

	int customObjectFacesCount = 0;
	String objectFilePath = "goat.obj";


	@Override
	protected void init() {
		// Matrizen initialisieren
		Matrix4 projektionsMatrix = new Matrix4(1, 100);
		Matrix4 viewMatrix= new Matrix4().translate(0, 0, -4);

		//Texturen
		shaderProgram = new ShaderProgram("projekt");
		glUseProgram(shaderProgram.getId());
		int location = glGetUniformLocation(shaderProgram.getId(), "projektionsMatrix");
		glUniformMatrix4fv(location, false, projektionsMatrix.getValuesAsArray());


		int locOfviewMat = glGetUniformLocation(shaderProgram.getId(), "viewMatrix");
		glUniformMatrix4fv(locOfviewMat, false, viewMatrix.getValuesAsArray());

		highQual = new Texture("/textures/Jean-Dubuffet.jpg",1);
		glBindTexture(GL_TEXTURE_2D, highQual.getId());
		glGenerateMipmap(GL_TEXTURE_2D);
		lowQual = new Texture("/textures/img.png", 40);
		glBindTexture(GL_TEXTURE_2D, lowQual.getId());
		//glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR); // Enable mipmapping



		//einfache Farben
		shaderProgram2 = new ShaderProgram("projekt2" );
		glUseProgram(shaderProgram2.getId());
		int location2 = glGetUniformLocation(shaderProgram2.getId(), "projektionsMatrix");
		glUniformMatrix4fv(location2, false, projektionsMatrix.getValuesAsArray());

		int locOfviewMat2 = glGetUniformLocation(shaderProgram2.getId(), "viewMatrix");
		glUniformMatrix4fv(locOfviewMat2, false, viewMatrix.getValuesAsArray());


		float[] objectFaces = null;
		try {
			customObjectFacesCount = countObjectFaces(objectFilePath);
			objectFaces = combineIndicesAndVertices(readVerticesFromFile(objectFilePath), readIndicesFromFile(objectFilePath));
		} catch (IOException e) {
			e.printStackTrace();
		}




	/*	timeUniformLocation = glGetUniformLocation(shaderProgram.getId(), "time");
		startTime= System.currentTimeMillis();*/



		// Koordinaten, VAO, VBO, ... hier anlegen und im Grafikspeicher ablegen
		float[] cubeCoordinates =
				{
						-1.0f, -1.0f,  1.0f,
						1.0f, -1.0f,  1.0f,
						-1.0f,  1.0f,  1.0f,

						1.0f, -1.0f,  1.0f,
						1.0f,  1.0f,  1.0f,
						-1.0f,  1.0f,  1.0f,

						-1.0f, -1.0f, -1.0f,
						1.0f, -1.0f, -1.0f,
						-1.0f,  1.0f, -1.0f,

						1.0f, -1.0f, -1.0f,
						1.0f,  1.0f, -1.0f,
						-1.0f,  1.0f, -1.0f,

						-1.0f, -1.0f, -1.0f,
						-1.0f, -1.0f,  1.0f,
						-1.0f,  1.0f, -1.0f,

						-1.0f, -1.0f,  1.0f,
						-1.0f,  1.0f,  1.0f,
						-1.0f,  1.0f, -1.0f,

						1.0f, -1.0f, -1.0f,
						1.0f, -1.0f,  1.0f,
						1.0f,  1.0f, -1.0f,

						1.0f, -1.0f,  1.0f,
						1.0f,  1.0f,  1.0f,
						1.0f,  1.0f, -1.0f,

						-1.0f,  1.0f,  1.0f,
						1.0f,  1.0f,  1.0f,
						-1.0f,  1.0f, -1.0f,

						1.0f,  1.0f,  1.0f,
						1.0f,  1.0f, -1.0f,
						-1.0f,  1.0f, -1.0f,

						-1.0f, -1.0f,  1.0f,
						1.0f, -1.0f,  1.0f,
						-1.0f, -1.0f, -1.0f,

						1.0f, -1.0f,  1.0f,
						1.0f, -1.0f, -1.0f,
						-1.0f, -1.0f, -1.0f

				};

		float[] cubeColors =
				{
						// Front face
						1.0f, 0.0f, 0.0f,
						0.0f, 0.7f, 0.0f,
						0.0f, 0.0f, 0.7f,
						0.7f, 0.7f, 0.0f,
						0.7f, 0.0f, 0.7f,
						0.0f, 0.7f, 0.7f,

						// Back face
						0.7f, 0.35f, 0.0f,
						0.0f, 0.7f, 0.35f,
						0.35f, 0.0f, 0.7f,
						0.7f, 0.0f, 0.35f,
						0.35f, 0.7f, 0.0f,
						0.0f, 0.35f, 0.7f,

						// Left face
						0.35f, 0.7f, 0.0f,
						0.0f, 0.35f, 0.7f,
						0.7f, 0.0f, 0.35f,
						0.0f, 0.0f, 0.7f,
						0.7f, 0.35f, 0.0f,
						0.35f, 0.0f, 0.7f,

						// Right face
						0.7f, 0.35f, 0.35f,
						0.35f, 0.7f, 0.35f,
						0.35f, 0.35f, 0.7f,
						0.35f, 0.35f, 0.0f,
						0.7f, 0.0f, 0.35f,
						0.0f, 0.35f, 0.7f,

						// Top face
						0.0f, 0.7f, 0.35f,
						0.7f, 0.35f, 0.0f,
						0.35f, 0.0f, 0.7f,
						0.7f, 0.35f, 0.35f,
						0.35f, 0.35f, 0.0f,
						0.0f, 0.35f, 0.7f,

						// Bottom face
						0.0f, 0.35f, 0.7f,
						0.7f, 0.0f, 0.35f,
						0.35f, 0.7f, 0.0f,
						0.7f, 0.0f, 0.35f,
						0.35f, 0.0f, 0.7f,
						0.0f, 0.35f, 0.35f


				};

		float[] norms= {
				// Front face
				0.0f, 0.0f, 1.0f,
				0.0f, 0.0f, 1.0f,
				0.0f, 0.0f, 1.0f,
				0.0f, 0.0f, 1.0f,
				0.0f, 0.0f, 1.0f,
				0.0f, 0.0f, 1.0f,

				// Back face
				0.0f, 0.0f, -1.0f,
				0.0f, 0.0f, -1.0f,
				0.0f, 0.0f, -1.0f,
				0.0f, 0.0f, -1.0f,
				0.0f, 0.0f, -1.0f,
				0.0f, 0.0f, -1.0f,

				// Left face
				-1.0f, 0.0f, 0.0f,
				-1.0f, 0.0f, 0.0f,
				-1.0f, 0.0f, 0.0f,
				-1.0f, 0.0f, 0.0f,
				-1.0f, 0.0f, 0.0f,
				-1.0f, 0.0f, 0.0f,

				// Right face
				1.0f, 0.0f, 0.0f,
				1.0f, 0.0f, 0.0f,
				1.0f, 0.0f, 0.0f,
				1.0f, 0.0f, 0.0f,
				1.0f, 0.0f, 0.0f,
				1.0f, 0.0f, 0.0f,

				// Top face
				0.0f, 1.0f, 0.0f,
				0.0f, 1.0f, 0.0f,
				0.0f, 1.0f, 0.0f,
				0.0f, 1.0f, 0.0f,
				0.0f, 1.0f, 0.0f,
				0.0f, 1.0f, 0.0f,

				// Bottom face
				0.0f, -1.0f, 0.0f,
				0.0f, -1.0f, 0.0f,
				0.0f, -1.0f, 0.0f,
				0.0f, -1.0f, 0.0f,
				0.0f, -1.0f, 0.0f,
				0.0f, -1.0f, 0.0f


		};
		float[] pyramidCoordinates= {

				1, 0, 1,
				3, 0, 1,
				2, 1, 0,

				3, 0, -1,
				1, 0 ,-1,
				2, 1, 0,

				3, 0, 1,
				3, 0, -1,
				2, 1, 0,

				1, 0, -1,
				1, 0, 1,
				2, 1, 0,

				1, 0, -1,
				3, 0, 1,
				1, 0, 1,

				3, 0, 1,
				1, 0, -1,
				3, 0, -1

		};
		float[] pyramidColors= {

				0.5f, 0.5f, 1,
				0.5f, 0.5f, 1,
				0.5f, 0.5f, 1,

				0.5f, 0.5f, 1,
				0.5f, 0.5f, 1,
				0.5f, 0.5f, 1,

				0.5f, 0.5f, 1,
				0.5f, 0.5f, 1,
				0.5f, 0.5f, 1,

				0.5f, 0.5f, 1,
				0.5f, 0.5f, 1,
				0.5f, 0.5f, 1,

				0.5f, 0.5f, 1,
				0.5f, 0.5f, 1,
				0.5f, 0.5f, 1,

				0.5f, 0.5f, 1,
				0.5f, 0.5f, 1,
				0.5f, 0.5f, 1
		};
		float[] pyramidNorms= {

				0.0f, 1.0f, 1.0f,
				0.0f, 1.0f, 1.0f,
				0.0f, 1.0f, 1.0f,

				0.0f, 1.0f, -1.0f,
				0.0f, 1.0f, -1.0f,
				0.0f, 1.0f, -1.0f,

				1.0f, 1.0f, 0.0f,
				1.0f, 1.0f, 0.0f,
				1.0f, 1.0f, 0.0f,

				-1.0f, 1.0f, 0.0f,
				-1.0f, 1.0f, 0.0f,
				-1.0f, 1.0f, 0.0f,

				0.0f, -1.0f, 0.0f,
				0.0f, -1.0f, 0.0f,
				0.0f, -1.0f, 0.0f,

				0.0f, -1.0f, 0.0f,
				0.0f, -1.0f, 0.0f,
				0.0f, -1.0f, 0.0f,
		};
		float[] uv = new float[] {

				0,0,
				0,1,
				1,0,

				0,0,
				0,1,
				1,0,

				0,0,
				0,1,
				1,0,

				0,0,
				0,1,
				1,0,

				0,0,
				0,1,
				1,0,

				0,0,
				0,1,
				1,0,

				0,0,
				0,1,
				1,0,

				0,0,
				0,1,
				1,0
		};

		float[] uv2 = new float[] {
				// Front face
				0, 0,
				1, 0,
				0, 1,
				1, 0,
				1, 1,
				0, 1,

				// Right face
				0, 0,
				1, 0,
				0, 1,
				1, 0,
				1, 1,
				0, 1,

				// Back face
				0, 0,
				1, 0,
				0, 1,
				1, 0,
				1, 1,
				0, 1,

				// Left face
				0, 0,
				1, 0,
				0, 1,
				1, 0,
				1, 1,
				0, 1,

				// Top face
				0, 0,
				1, 0,
				0, 1,
				1, 0,
				1, 1,
				0, 1,

				// Bottom face
				0, 0,
				1, 0,
				0, 1,
				1, 0,
				1, 1,
				0, 1
		};

		//Cube object
		vaoId = glGenVertexArrays();
		glBindVertexArray(vaoId);

		int vboCubeCoordinates = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vboCubeCoordinates);
		glBufferData(GL_ARRAY_BUFFER, cubeCoordinates, GL_STATIC_DRAW);
		glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
		glEnableVertexAttribArray(0);

		int vboCubeColors = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vboCubeColors);
		glBufferData(GL_ARRAY_BUFFER, cubeColors, GL_STATIC_DRAW);
		glVertexAttribPointer(1, 3, GL_FLOAT, false, 0, 0);
		glEnableVertexAttribArray(1);

		int vboCubeNorms = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vboCubeNorms);
		glBufferData(GL_ARRAY_BUFFER, norms, GL_STATIC_DRAW);
		glVertexAttribPointer(2, 3, GL_FLOAT, false, 0, 0);
		glEnableVertexAttribArray(2);

		int vboCubeTexture = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vboCubeTexture);
		glBufferData(GL_ARRAY_BUFFER, uv2, GL_STATIC_DRAW);
		glVertexAttribPointer(3, 2, GL_FLOAT, false, 0, 0);
		glEnableVertexAttribArray(3);



		//second object
		vaoId2 = glGenVertexArrays();
		glBindVertexArray(vaoId2);

		int vboPyramidCoordinates = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vboPyramidCoordinates);
		glBufferData(GL_ARRAY_BUFFER, pyramidCoordinates, GL_STATIC_DRAW);
		glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
		glEnableVertexAttribArray(0);

		int vboPyramidColors = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vboPyramidColors);
		glBufferData(GL_ARRAY_BUFFER, pyramidColors, GL_STATIC_DRAW);
		glVertexAttribPointer(1, 3, GL_FLOAT, false, 0, 0);
		glEnableVertexAttribArray(1);

		int vboPyramidNormale = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vboPyramidNormale);
		glBufferData(GL_ARRAY_BUFFER, pyramidNorms, GL_STATIC_DRAW);
		glVertexAttribPointer(2, 3, GL_FLOAT, false, 0, 0);
		glEnableVertexAttribArray(2);

		int vboTexture =  glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vboTexture);
		glBufferData(GL_ARRAY_BUFFER, uv, GL_STATIC_DRAW);
		glVertexAttribPointer(3, 2, GL_FLOAT, false, 0, 0);
		glEnableVertexAttribArray(3);


		//Sphere object
		objVaoId = glGenVertexArrays();
		glBindVertexArray(objVaoId);

		int vboIdOBJ = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vboIdOBJ);
		glBufferData(GL_ARRAY_BUFFER, objectFaces, GL_STATIC_DRAW);
		glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
		glEnableVertexAttribArray(0);

		int vboIdOBJ2 = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vboIdOBJ2);
		glBufferData(GL_ARRAY_BUFFER, objectFaces, GL_STATIC_DRAW);
		glVertexAttribPointer(1, 3, GL_FLOAT, false, 0, 0);
		glEnableVertexAttribArray(1);




		glEnable(GL_DEPTH_TEST); // z-Buffer aktivieren
		glEnable(GL_CULL_FACE); // backface culling aktivieren
		glDisable(GL_CULL_FACE);
	}
	private int countObjectFaces(String filePath) throws IOException {
		InputStream inputStream = getClass().getResourceAsStream("/objects/" + filePath);

		if (inputStream == null) {
			// Handle the case where the resource is not found
			throw new FileNotFoundException("Resource not found: " + filePath);
		}

		InputStreamReader fileReader = new InputStreamReader(inputStream);
		BufferedReader bufferedReader = new BufferedReader(fileReader);

		int facesCount = 0;

		String line;
		while((line = bufferedReader.readLine()) != null) {
			if(line.startsWith("f ")) {
				facesCount++;
			}
		}
		bufferedReader.close();

		return facesCount;
	}
	private float[][] readVerticesFromFile(String filePath) throws IOException {
		float[][] verticies = new float[customObjectFacesCount][3];
		int index = 0;

		InputStreamReader fileReader = new InputStreamReader(getClass().getResourceAsStream("/objects/" + filePath));
		BufferedReader bufferedReader = new BufferedReader(fileReader);


		String line;
		while((line = bufferedReader.readLine()) != null) {
			if(line.startsWith("v ")){
				String[] splitLine = line.split(" ");

				float[] vertex = new float[3];

				vertex[0] = Float.parseFloat(splitLine[1]);
				vertex[1] = Float.parseFloat(splitLine[2]);
				vertex[2] = Float.parseFloat(splitLine[3]);

				verticies[index++] = vertex;
			}
		}
		bufferedReader.close();

		return verticies;
	}

	private float[] readIndicesFromFile(String filePath) throws IOException {
		float[] indices = new float[customObjectFacesCount*3];
		int index = 0;

		InputStreamReader fileReader = new InputStreamReader(getClass().getResourceAsStream("/objects/" + filePath));
		BufferedReader bufferedReader = new BufferedReader(fileReader);


		String line;
		while((line = bufferedReader.readLine()) != null) {
			if(line.startsWith("f ")) {
				String[] splitLine = line.split(" "); //-> f 1/2 3/4 5/6
				String first = splitLine[1]; //-> 1/2
				first = first.split("/")[0]; //-> 1

				String second = splitLine[2].split("/")[0]; //-> 3/4 -> 3
				String third = splitLine[3].split("/")[0]; //-> 5/6 -> 5

				indices[index++] = Float.parseFloat(first);
				indices[index++] = Float.parseFloat(second);
				indices[index++] = Float.parseFloat(third);
			}
		}
		bufferedReader.close();

		return indices;
	}

	float[] combineIndicesAndVertices(float[][] vertices2D, float[] indices) {
		float[] faces = new float[indices.length * 3];

		for (int i = 0; i < indices.length; i++) {
			int vertexIndex = (int) indices[i] - 1; //.obj starts at 1, Java at 0
			for (int j = 0; j < 3; j++) {
				faces[i * 3 + j] = vertices2D[vertexIndex][j];
			}
		}
		return faces;
	}

	@Override
	public void update() {
		angle += 0.8f;
	}

	@Override
	protected void render() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

		int location = glGetUniformLocation(shaderProgram.getId(), "hauptMatrix");
		int location2 = glGetUniformLocation(shaderProgram2.getId(), "hauptMatrix");

			//Objekte mit Texturen
		//Cube
			glUseProgram(shaderProgram.getId());

			Matrix4 cubeMatrix = new Matrix4().rotateY(angle).rotateZ(angle).scale(0.5f);
			glUniformMatrix4fv(location, false, cubeMatrix.getValuesAsArray());
			glUniformMatrix4fv(matrixLocation, false, cubeMatrix.getValuesAsArray());

			glBindTexture(GL_TEXTURE_2D, highQual.getId());
			glBindVertexArray(vaoId);
			glDrawArrays(GL_TRIANGLES, 0, 36);

		//Pyramide
			glUseProgram(shaderProgram.getId());


			Matrix4 pyramidMatrix = new Matrix4().multiply(cubeMatrix);
			pyramidMatrix.translate(0, 0, 4).rotateZ(angle);
			glUniformMatrix4fv(location, false, pyramidMatrix.getValuesAsArray());
			glUniformMatrix4fv(matrixLocation, false, pyramidMatrix.getValuesAsArray());


			glBindTexture(GL_TEXTURE_2D, lowQual.getId());
			glBindVertexArray(vaoId2);
			glDrawArrays(GL_TRIANGLES, 0, 36);

			//geladenes Objekt

			glUseProgram(shaderProgram2.getId());

			Matrix4 objectMatrix = new Matrix4().multiply(pyramidMatrix);
			objectMatrix.translate(0,2,0).rotateX(-angle).rotateZ(angle).scale(0.002f);
			glUniformMatrix4fv(location2, false, objectMatrix.getValuesAsArray());
			glUniformMatrix4fv(matrixLocation, false, objectMatrix.getValuesAsArray());

			glBindVertexArray(objVaoId);
			glDrawArrays(GL_TRIANGLES, 0, customObjectFacesCount * 3);

	}
}
