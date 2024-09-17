#version 330
layout(location=0) in vec2 eckenAusJava;
uniform float time;
out vec3 farbe;
mat2 rotationMatrix(float winkel) {
    return mat2(cos(winkel), sin(winkel),- sin(winkel), cos(winkel));
}
vec3 colorGen(float time){

    float value = clamp((sin(time) + 1.0) * 0.4, 0.1, 0.9);
    float value2 = clamp((cos(time) + 1.0) * 0.4, 0.1, 0.9);
    float value3 = clamp((-cos(time) + 1.0) * 0.4, 0.1, 0.9);



    return vec3(value,value2,value3);
}

void main() {

    farbe = colorGen(time);
    float winkel= time;
    vec2 rotierteEcke = rotationMatrix(winkel) * eckenAusJava;
    //hier kann Transformation erfolgen
    gl_Position = vec4(rotierteEcke, 0.0, 1.0);
    //warum nicht als out wie im F.Shader?!

}