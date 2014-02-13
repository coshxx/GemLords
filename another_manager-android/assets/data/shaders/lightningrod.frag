precision mediump float;

varying vec4 v_color;
varying vec2 v_texCoord0;

uniform float u_darkness;
uniform vec2 u_startPos;
uniform sampler2D u_sampler2D;

void main() {
    v_color.rgb = v_color.rgb - u_darkness;
    gl_FragColor = texture2D(u_sampler2D, v_texCoord0) * v_color;

}