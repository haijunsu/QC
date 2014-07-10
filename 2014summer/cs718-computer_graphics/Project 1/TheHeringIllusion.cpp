#include <gl/glut.h>

// CS718 Project 1 (the Hering illusion) by Haijun Su

// draw intersect lines
void drawIntersectLines() {
	glColor3f(0.0, 0.0, 0.0); // white color
	int height = 299; // height of window
	int lineNums = 22; // home many lines to draw
	int distance = 50; // distance between two vertics
	glBegin(GL_LINES);
	for(int i=0; i<=lineNums/2; i++) {
		glVertex2i(199 - i*distance, 0);
		glVertex2i(199 + i*distance, height); 
		if (i > 0) {
			glVertex2i(199 + i*distance, 0); 
			glVertex2i(199 - i*distance, height);
		}
	}
	glEnd();
	glFlush();
}

// draw two horizonal lines
void drawHorizonalLines() {
	glColor3f(1.0, 0.0, 0.0); // red color
	glBegin(GL_LINES);
		glVertex2i(0, 100);
		glVertex2i(400, 100); 
		glVertex2i(0, 200); 
		glVertex2i(400, 200);
	glEnd();
	glFlush();
}
void display(void) {
	glClear(GL_COLOR_BUFFER_BIT);
	// enable antialiasing
	glEnable(GL_BLEND);
	glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
	glEnable(GL_LINE_SMOOTH);	// Smooth out lines.
	glHint(GL_POLYGON_SMOOTH_HINT, GL_NICEST);
	drawIntersectLines();
	drawHorizonalLines();
}

void main(int argc, char ** argv) {
	glutInit(&argc, argv);
	glutInitDisplayMode(GLUT_SINGLE | GLUT_RGB);
	glutInitWindowSize(400, 300);
	glutInitWindowPosition(200, 100);
	glutCreateWindow("The Hering Illusion");
	glClearColor(1.0, 1.0, 1.0, 0.0);
	gluOrtho2D(0.0, 400.0, 0.0, 300.0);
	glutDisplayFunc(display);
	glutMainLoop();
}