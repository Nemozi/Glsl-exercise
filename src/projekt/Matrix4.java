package projekt;

//Alle Operationen aendern das Matrixobjekt selbst und geben das eigene Matrixobjekt zurueck
//Dadurch kann man Aufrufe verketten, z.B.
//Matrix4 m = new Matrix4().scale(5).translate(0,1,0).rotateX(0.5f);
public class Matrix4 {

	float[] matrix;

	public Matrix4() {
		// TODO mit der Identitaetsmatrix initialisieren
		matrix = new float[] {
				1.0f, 0.0f, 0.0f, 0.0f,
				0.0f, 1.0f, 0.0f, 0.0f,
				0.0f, 0.0f, 1.0f, 0.0f,
				0.0f, 0.0f, 0.0f, 1.0f
		};
	}

	public Matrix4(Matrix4 copy) {
		// TODO neues Objekt mit den Werten von "copy" initialisieren
		matrix = new float[16];
		for(int i = 0; i < 16; i++) {
			matrix[i] = copy.matrix[i];
		}
	}

	public Matrix4(float near, float far) {
		// TODO erzeugt Projektionsmatrix mit Abstand zur nahen Ebene "near" und Abstand zur fernen Ebene "far", ggf. weitere Parameter hinzufuegen
		matrix = new float[] {
				1.0f, 0.0f, 0.0f, 0.0f,
				0.0f, 1.0f, 0.0f, 0.0f,
				0.0f, 0.0f, (-far - near)/(far - near), -1.0f,
				0.0f, 0.0f, (-2 * near * far)/(far - near), 0.0f
		};
	}

	public Matrix4 multiply(Matrix4 other) {
		// TODO hier Matrizenmultiplikation "this = other * this" einfuegen
		//create a pivot matrix so that changed values don't interfere with calculations
		float[] pivot = new float[16];
		//--------warum ist this.matrix hier Null?---------
		int pos;
		int posO;
		int posT;
		float sum;

		//iterate over every 'cell' of the matrix
		for(int y = 0; y < 4; y++) {
			for(int x = 0; x < 4; x++) {
				//reset sum to 0
				sum = 0;

				//add the products of row-column multiplication to sum
				for(int i = 0; i < 4; i++) {
					posO = y * 4 + i;
					posT = i * 4 + x;
					sum += other.matrix[posO] * this.matrix[posT];
				}

				//calculate position of current 'cell'
				pos = y * 4 + x;

				//fill value into 'cell'
				pivot[pos] = sum;
			}
		}
		this.setMatrix(pivot);

		return this;
	}

	public Matrix4 translate(float x, float y, float z) {
		Matrix4 translate = new Matrix4();
		translate.matrix[12] = x;
		translate.matrix[13] = y;
		translate.matrix[14] = z;

		this.multiply(translate);

		return this;
	}

	public Matrix4 scale(float uniformFactor) {
		// TODO gleichmaessige Skalierung um Faktor "uniformFactor" zu this hinzufuegen
		Matrix4 scaleMatrix = new Matrix4();
		scaleMatrix.matrix[0] = uniformFactor;
		scaleMatrix.matrix[5] = uniformFactor;
		scaleMatrix.matrix[10] = uniformFactor;
		this.multiply(scaleMatrix);

		return this;
	}

	public Matrix4 scale(float sx, float sy, float sz) {
		// TODO ungleichfoermige Skalierung zu this hinzufuegen
		matrix[0] = matrix[0] * sx;
		matrix[5] = matrix[5] * sy;
		matrix[10] = matrix[10] * sz;

		return this;
	}

	public Matrix4 rotateX(float angle) {
		// TODO Rotation um X-Achse zu this hinzufuegen
		Matrix4 rotation = new Matrix4();
		angle = (float) Math.toRadians(angle);
		rotation.matrix[5] = (float) Math.cos(angle);
		rotation.matrix[9] = (float) Math.sin(angle);
		rotation.matrix[6] = (float) -Math.sin(angle);
		rotation.matrix[10] = (float) Math.cos(angle);

		this.multiply(rotation);

		return this;
	}

	public Matrix4 rotateY(float angle) {
		// TODO Rotation um Y-Achse zu this hinzufuegen
		Matrix4 rotation = new Matrix4();
		angle = (float) Math.toRadians(angle);
		rotation.matrix[0] = (float) Math.cos(angle);
		rotation.matrix[8] = (float) Math.sin(angle);
		rotation.matrix[2] = (float) -Math.sin(angle);
		rotation.matrix[10] = (float) Math.cos(angle);

		this.multiply(rotation);

		return this;
	}

	public Matrix4 rotateZ(float angle) {
		// TODO Rotation um Z-Achse zu this hinzufuegen
		Matrix4 rotation = new Matrix4();
		angle = (float) Math.toRadians(angle);
		rotation.matrix[0] = (float) Math.cos(angle);
		rotation.matrix[4] = (float) Math.sin(angle);
		rotation.matrix[1] = (float) -Math.sin(angle);
		rotation.matrix[5] = (float) Math.cos(angle);

		this.multiply(rotation);

		return this;
	}

	public float[] getValuesAsArray() {
		// TODO hier Werte in einem Float-Array mit 16 Elementen (spaltenweise gefuellt) herausgeben
		return this.matrix;
	}

	public void print() {
		int pos;
		for(int i = 0; i < 4; i++) {
			System.out.print("( ");
			for(int j = 0; j < 4; j++) {
				pos = j * 4 + i;
				System.out.print(matrix[pos]);
				if(pos <= 11) {
					System.out.print("  ");
				}
			}
			System.out.print(" )");
			System.out.println();
		}
		System.out.println();
	}

	public void setMatrix(float[] newMatrix) {
		matrix = newMatrix;
	}
}
