#include <gl/glut.h>

// CS718 Project 1 (the Hering illusion) by Haijun Su

#define WINDOW_WIDTH  600 
#define WINDOW_HEIGHT 300
#define LINE_NUMS 30 // how many horizonal lines will be drawn
#define VERTICS_SPACING 50 // distance between two vertics


// draw intersect lines
void drawIntersectLines() {
	glColor3f(0.0, 0.0, 0.0); // black color
	glBegin(GL_LINES);
	for(int i=0; i<=LINE_NUMS/2; i++) {
		glVertex2i(WINDOW_WIDTH/2 - i*VERTICS_SPACING, 0);
		glVertex2i(WINDOW_WIDTH/2 + i*VERTICS_SPACING, WINDOW_HEIGHT); 
		if (i > 0) {
			glVertex2i(WINDOW_WIDTH/2 + i*VERTICS_SPACING, 0); 
			glVertex2i(WINDOW_WIDTH/2 - i*VERTICS_SPACING, WINDOW_HEIGHT);
		}
	}
	glEnd();
	glFlush();
}

// draw two horizonal lines
void drawHorizonalLines() {
	glColor3f(1.0, 0.0, 0.0); // red color
	glBegin(GL_LINES);
		glVertex2i(0, WINDOW_HEIGHT/3);
		glVertex2i(WINDOW_WIDTH, WINDOW_HEIGHT/3); 
		glVertex2i(0, 2*WINDOW_HEIGHT/3); 
		glVertex2i(WINDOW_WIDTH, 2*WINDOW_HEIGHT/3);
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
	glutInitWindowSize(WINDOW_WIDTH, WINDOW_HEIGHT);
	glutInitWindowPosition(200, 100);
	glutCreateWindow("The Hering Illusion");
	glClearColor(1.0, 1.0, 1.0, 0.0);
	gluOrtho2D(0.0, WINDOW_WIDTH, 0.0, WINDOW_HEIGHT);
	glutDisplayFunc(display);
	glutMainLoop();
}