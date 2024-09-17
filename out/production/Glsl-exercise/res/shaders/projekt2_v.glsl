#version 330
out vec3 normale;
out vec3 gedrehtePositionen;
out vec3 color;

layout(location=0) in vec4 cubeCoordinates;
layout(location=1) in vec3 cubeColors;
layout(location=2) in vec3 cubeNorms;
uniform mat4 hauptMatrix;
uniform mat4 projektionsMatrix;
uniform mat4 viewMatrix;
void main() {
    normale = normalize(mat3(transpose(inverse(hauptMatrix))) * cubeNorms);
    gedrehtePositionen = vec3(hauptMatrix*cubeCoordinates);
    color = vec3(0.5f,0,0.5f);
    gl_Position = projektionsMatrix *viewMatrix* hauptMatrix * cubeCoordinates;
}
