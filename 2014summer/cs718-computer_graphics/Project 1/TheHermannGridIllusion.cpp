#include <gl/glut.h>

// CS718 Project 1 (the Hermann grid illusion) by Haijun Su

#define WINDOW_WIDTH 500
#define SQUARES_SPACING 15 // space between two squares

// draw 25 squares
void drawsquares() {
	glColor3f(0.0, 0.0, 0.0); // black color
	int edge_length = (WINDOW_WIDTH - 6 * SQUARES_SPACING)/5; // each edge length
	glBegin(GL_QUADS);
	for (int i=0; i<5; i++) {
		int x = i * edge_length + (i+1) * SQUARES_SPACING;
		for (int j=0; j<5; j++) {
			int y = j * edge_length + (j+1) * SQUARES_SPACING;
			glVertex2i(x, y);
			glVertex2i(x, y+edge_length); 
			glVertex2i(x+edge_length, y+edge_length); 
			glVertex2i(x+edge_length, y);
		}
	}
	glEnd();
	glFlush();
}

void display(void) {
	glClear(GL_COLOR_BUFFER_BIT);
	drawsquares();
}

void main(int argc, char ** argv) {
	glutInit(&argc, argv);
	glutInitDisplayMode(GLUT_SINGLE | GLUT_RGB);
	glutInitWindowSize(WINDOW_WIDTH, WINDOW_WIDTH);
	glutInitWindowPosition(200, 100);
	glutCreateWindow("The Hermann Grid Illusion");
	glClearColor(1.0, 1.0, 1.0, 0.0);
	gluOrtho2D(0.0, WINDOW_WIDTH, 0.0, WINDOW_WIDTH);
	glutDisplayFunc(display);
	glutMainLoop();
}