#version 330
in vec3 normale;
in vec3 gedrehtePositionen;
in vec2 textur;
in vec3 color;

out vec3 fragColor;
uniform sampler2D sampler;

void main () {
    vec3 N = normalize(normale);
    vec3 L = normalize((vec3(-10, 3, 4) - gedrehtePositionen));
    vec3 R = reflect(-L, N);
    vec3 V = normalize(vec3(-gedrehtePositionen));

    vec3 ambientColor = color;
    float ambientIntensity = 0.5f;

    float diffuseTerm = max(0.0, dot(N, L));
    float specularTerm = pow(max(0.0, dot(R, V)), 200);

    vec3 I = ambientColor + (0.6f * diffuseTerm) + (0.6f * specularTerm);

    fragColor = I * texture(sampler, textur).rgb * color;
}