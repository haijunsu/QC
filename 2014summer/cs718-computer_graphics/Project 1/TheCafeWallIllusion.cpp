#include <gl/glut.h>

// CS718 Project 1 (the Cafe Wall illusion) by Haijun Su

#define WINDOW_WIDTH  600 
#define WINDOW_HEIGHT 300
#define EDGE_LENGTH 30 // square edge lengt

int line_nums = WINDOW_HEIGHT/EDGE_LENGTH - 1; // how many horizonal lines will be drawn

// draw horizonal lines
void drawHorizonalLines() {
	glColor3f(0.0, 0.0, 0.0); // black color
	glBegin(GL_LINES);
	for (int i=1; i<=line_nums; i++) {
		glVertex2i(0, i*EDGE_LENGTH);
		glVertex2i(WINDOW_WIDTH, i*EDGE_LENGTH); 
	}
	glEnd();
	glFlush();	
}

// draw squares
void drawsquares() {
	glColor3f(0.0, 0.0, 0.0); // black color
	glBegin(GL_QUADS);
	int shiftOption = 1; // shift direction: 1 -> right, -1 -> left
	int xOffset = 0; // shift value
	for (int i=0; i<=line_nums; i++) {
		int y = i * EDGE_LENGTH;
		if(shiftOption == 1) {
			xOffset += EDGE_LENGTH/3;
		} else {
			xOffset -= EDGE_LENGTH/3;
		}
		for (int j=0; j<=WINDOW_WIDTH/EDGE_LENGTH; j++) {
			if (j%2 != 0){
				continue;
			}
			int x = j * EDGE_LENGTH + xOffset;
			glVertex2i(x, y);
			glVertex2i(x, y+EDGE_LENGTH); 
			glVertex2i(x+EDGE_LENGTH, y+EDGE_LENGTH); 
			glVertex2i(x+EDGE_LENGTH, y);
		}
		if ((xOffset == 0) || (xOffset == EDGE_LENGTH*2/3)) {// change shift direction
			shiftOption = 0 - shiftOption;
		}
	}
	glEnd();
	glFlush();
}

void display(void) {
	glClear(GL_COLOR_BUFFER_BIT);
	drawHorizonalLines();
	drawsquares();
}

void main(int argc, char ** argv) {
	glutInit(&argc, argv);
	glutInitDisplayMode(GLUT_SINGLE | GLUT_RGB);
	glutInitWindowSize(WINDOW_WIDTH, WINDOW_HEIGHT);
	glutInitWindowPosition(200, 100);
	glutCreateWindow("The Cafe Wall Illusion");
	glClearColor(1.0, 1.0, 1.0, 0.0);
	gluOrtho2D(0.0, WINDOW_WIDTH, 0.0, WINDOW_HEIGHT);
	glutDisplayFunc(display);
	glutMainLoop();
}