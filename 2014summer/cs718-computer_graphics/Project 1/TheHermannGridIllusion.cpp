#include <gl/glut.h>

// CS718 Project 1 (the Hermann grid illusion) by Haijun Su

// draw 25 squares
void drawsquares() {
	glColor3f(0.0, 0.0, 0.0); // white color
	int distance = 10; // distance between two squares
	int edge_length = 80; // each edge length
	glBegin(GL_QUADS);
	for (int i=0; i<5; i++) {
		int x = i * edge_length + (i+1) * distance;
		for (int j=0; j<5; j++) {
			int y = j * edge_length + (j+1) * distance;
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
	glutInitWindowSize(460, 460);
	glutInitWindowPosition(200, 100);
	glutCreateWindow("The Hermann Grid Illusion");
	glClearColor(1.0, 1.0, 1.0, 0.0);
	gluOrtho2D(0.0, 460.0, 0.0, 460.0);
	glutDisplayFunc(display);
	glutMainLoop();
}