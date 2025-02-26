#version 150

uniform sampler2D DiffuseSampler;

in vec2 texCoord;
in vec2 oneTexel;

uniform vec2 InSize;

uniform vec2 BlurDir;
uniform float Radius;

out vec4 fragColor;

float gaussian(float x) {
    return exp(-(x * x) / (2.0 * (Radius / 3.0) * (Radius / 3.0))) / (sqrt(2.0 * 3.141592653589793) * (Radius / 3.0));
}

void main() {
    vec4 blurred = vec4(0.0);
    float totalStrength = 0.0;
    float totalAlpha = 0.0;
    for(float r = -Radius; r <= Radius; r += 1.0) {
        vec4 sampleValue = texture(DiffuseSampler, texCoord + oneTexel * r * BlurDir);
        totalAlpha += sampleValue.a * gaussian(r);
        blurred += sampleValue * gaussian(r);
        totalStrength += gaussian(r);
    }
    blurred /= totalStrength;


    vec2 center = vec2(0.5, 0.5);
    vec4 color = texture(DiffuseSampler, texCoord);

    float fadeAmount = smoothstep(0.0, 0.5, abs(texCoord.y - center.y));
    if (texCoord.y > center.y) {
        color.rgb = mix(color.rgb, blurred.rgb, fadeAmount);
        color.rgb *= (1.0 - fadeAmount);
    }
    fragColor = color;
}