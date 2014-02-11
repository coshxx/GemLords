attribute vec2 Position;

uniform mat4 u_projView;

void main() {
	gl_Position = vec4(Position, 1., 1.) * u_projView;
}