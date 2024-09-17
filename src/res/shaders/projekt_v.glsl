#version 330
out vec3 normale;
out vec3 gedrehtePositionen;
out vec2 textur;
out vec3 color;

layout(location=0) in vec4 cubeCoordinates;
layout(location=1) in vec3 cubeColors;
layout(location=2) in vec3 cubeNorms;
layout(location=3) in vec2 textures;


uniform mat4 hauptMatrix;
uniform mat4 projektionsMatrix;
uniform mat4 viewMatrix;
void main() {

    normale = normalize(mat3(transpose(inverse(hauptMatrix))) * cubeNorms);
    gedrehtePositionen = vec3(hauptMatrix*cubeCoordinates);
    color = cubeColors;
    textur=textures;
    gl_Position = projektionsMatrix *viewMatrix * hauptMatrix * cubeCoordinates;
}
