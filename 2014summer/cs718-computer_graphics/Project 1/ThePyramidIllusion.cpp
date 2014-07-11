#include <gl/glut.h>

// CS718 Project 1 (the Pyramid illusion) by Haijun Su

#define WINDOW_WIDTH  500 

// draw squares
void drawSquares() {
	float colorSpan = 1.0f/WINDOW_WIDTH*2; // different for each squares
	float color = 0.0f; // start with black color

	glBegin(GL_QUADS);
	for(int i=0; i<WINDOW_WIDTH/2; i++) {
		glColor3f(color, color, color);
		glVertex2i(i, i);
		glVertex2i(i, WINDOW_WIDTH-i); 
		glVertex2i(WINDOW_WIDTH - i,  WINDOW_WIDTH- i);
		glVertex2i(WINDOW_WIDTH - i, i); 
		color += colorSpan;
	}
	glEnd();

	glFlush();
}

void display(void) {
	glClear(GL_COLOR_BUFFER_BIT);
	drawSquares();
}

void main(int argc, char ** argv) {
	glutInit(&argc, argv);
	glutInitDisplayMode(GLUT_SINGLE | GLUT_RGB);
	glutInitWindowSize(WINDOW_WIDTH, WINDOW_WIDTH);
	glutInitWindowPosition(200, 100);
	glutCreateWindow("The Pyramid Illusion");
	glClearColor(0.0, 0.0, 0.0, 0.0);
	gluOrtho2D(0.0, WINDOW_WIDTH, 0.0, WINDOW_WIDTH);
	glutDisplayFunc(display);
	glutMainLoop();
}