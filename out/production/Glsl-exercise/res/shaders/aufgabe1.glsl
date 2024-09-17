#version 330
out vec3 pixelFarbe;

void main(){

    if(gl_FragCoord.x<=350 && (gl_FragCoord.y<325)){
        pixelFarbe = vec3(1.0,1.0,0.0);
    }
    else {
        pixelFarbe = vec3(0.3,0.4,0.2);
    }
}