#include <gl/glut.h>
#include <iostream>

// CS718 Project 3 train track Submission: Su, Haijun

using namespace std;

GLfloat pos[] = {-2, 4, 5, 1}; // light source

GLfloat theta = 0, theta_s = 0, theta_t = 0, theta_h = 0,dt=0.5, dt_stop = 0;

GLfloat r_sphere = 4;

GLfloat track_offset = r_sphere - 0.1;


/**
 * change light source position 
 */
void lightPos() {
    glPushMatrix();
        glRotated(theta_s, 0, 0, 1);
        glRotated(-120, 0, 0, 1);
        glLightfv(GL_LIGHT0, GL_POSITION, pos);
	glPopMatrix();
}

//draw sphere and sun
void solidSphere() {
	GLfloat front_amb_diff[] = {0.7, 0.5, 0.1, 1.0};
	GLfloat back_amb_diff[] = {0.4, 0.7, 0.1, 1.0};
	GLfloat spe[] = {0.25, 0.25, 0.25, 1.0};
	GLfloat amb[] = {0, 1, 0, 1.0};
	GLfloat front_amb_diff1[] = {1, 1, 1, 1.0};
	GLfloat back_amb_diff1[] = {1, 1, 1, 1.0};
	GLfloat spe1[] = {1, 1, 1, 1.0};
	GLfloat amb1[] = {1, 1, 1, 1.0};

  
	glPushMatrix();// sun
		glMaterialfv(GL_FRONT, GL_AMBIENT_AND_DIFFUSE, front_amb_diff1);
		glMaterialfv(GL_BACK, GL_AMBIENT_AND_DIFFUSE, back_amb_diff1);
		glMaterialfv(GL_FRONT_AND_BACK, GL_SPECULAR, spe1);
		glLightfv(GL_LIGHT0, GL_AMBIENT, amb1);
		glRotated(theta_s, 0, 0, 1);
 		glTranslated(r_sphere + 2, 0.0, 0);
		glutSolidSphere(0.2, 48, 48);

	glPopMatrix();
	glPushMatrix();//sphere
		glMaterialfv(GL_FRONT, GL_AMBIENT_AND_DIFFUSE, front_amb_diff);
		glMaterialfv(GL_BACK, GL_AMBIENT_AND_DIFFUSE, back_amb_diff);
		glMaterialfv(GL_FRONT_AND_BACK, GL_SPECULAR, spe);
		glLightfv(GL_LIGHT0, GL_AMBIENT, amb);
		glutSolidSphere(r_sphere, 48, 48);
		glPopMatrix();

	glPopMatrix();
}

//draw solidTetrahedron
void solidTetrahedron() {
	GLfloat front_amb_diff[] = {0.7, 0.5, 0.1, 1.0};
	GLfloat back_amb_diff[] = {0.4, 0.7, 0.1, 1.0};
	GLfloat spe[] = {0.25, 0.25, 0.25, 1.0};
	GLfloat amb[] = {1, 1, 0, 1.0};

	glPushMatrix();
		glMaterialfv(GL_FRONT, GL_AMBIENT_AND_DIFFUSE, front_amb_diff);
		glMaterialfv(GL_BACK, GL_AMBIENT_AND_DIFFUSE, back_amb_diff);
		glMaterialfv(GL_FRONT_AND_BACK, GL_SPECULAR, spe);
		glLightfv(GL_LIGHT0, GL_AMBIENT, amb);
		glRotated(theta, 1, 0, 0);
 		glRotated(120, -1, 0, 0);
		glTranslated(1, r_sphere, 0);
		glRotated(theta_h, 0, 1, 0);
		glutSolidTetrahedron();
    glPopMatrix();
}

//draw solidIcosahedron
void solidIcosahedron() {
	glPushMatrix();
 	glRotated(theta, 1, 0, 0);
 	glRotated(45, 0, 1, 0);
	glTranslated(-1.7, r_sphere, 0);
	glRotated(theta_h, 0, 1, 0);

	glutSolidIcosahedron();

    glPopMatrix();
}

//draw solidOctahedron
void solidOctahedron() {
	glPushMatrix();
 	glRotated(theta, 1, 0, 0);
	glRotated(130, 1, 0, 0);
	glRotated(45, 0, 1, 0);
	glTranslated(2, r_sphere, 0);
	glRotated(theta_h, 0, -1, 0);

	glutSolidOctahedron();

    glPopMatrix();
}
// combine cone and cube as a little house
void solidhouse() {
	GLfloat front_amb_diff[] = {0.7, 0.2, 0.1, 1.0};
	GLfloat back_amb_diff[] = {0.4, 0.7, 0.1, 1.0};
	GLfloat spe[] = {0.25, 0.25, 0.25, 1.0};
	GLfloat amb[] = {1, 0, 0, 1.0};

	glPushMatrix();
 		glMaterialfv(GL_FRONT, GL_AMBIENT_AND_DIFFUSE, front_amb_diff);
		glMaterialfv(GL_BACK, GL_AMBIENT_AND_DIFFUSE, back_amb_diff);
		glMaterialfv(GL_FRONT_AND_BACK, GL_SPECULAR, spe);
		glLightfv(GL_LIGHT0, GL_AMBIENT, amb);
		glRotated(theta, 1, 0, 0);
 		glRotated(80, -1, 0, 0);
 		glRotated(15, 0, 0, 1);
		glTranslated(0, r_sphere, 0);
		glRotated(theta_h, 0, 1, 0);
		glutSolidCube(0.5);
		glTranslated(0, 0.3, 0);
		glRotated(90, -1, 0, 0);
		glutSolidCone(0.5, 0.5, 40, 40);

    glPopMatrix();
}

// draw a slid cone
void solidCone() {
	GLfloat front_amb_diff[] = {0.7, 0.2, 0.1, 1.0};
	GLfloat back_amb_diff[] = {0.4, 0.7, 0.1, 1.0};
	GLfloat spe[] = {0.25, 0.25, 0.25, 1.0};
	GLfloat amb[] = {1, 0.3, 0.5, 1.0};

	glPushMatrix();
 		glMaterialfv(GL_FRONT, GL_AMBIENT_AND_DIFFUSE, front_amb_diff);
		glMaterialfv(GL_BACK, GL_AMBIENT_AND_DIFFUSE, back_amb_diff);
		glMaterialfv(GL_FRONT_AND_BACK, GL_SPECULAR, spe);
		glLightfv(GL_LIGHT0, GL_AMBIENT, amb);
		glRotated(theta, 1, 0, 0);
 		glRotated(180, -1, 0, 0);
 		glRotated(15, 0, 0, 1);
		glTranslated(0, r_sphere - 0.1, 0);
		glRotated(90, -1, 0, 0);
		glutSolidCone(0.5, 1, 40, 40);

    glPopMatrix();
}

// draw solid dodecahedron
void solidDodecahedron() {
	glPushMatrix();

		glRotated(theta, 1, 0, 0);
		glRotated(45, 0.2, 1, 0);
 		glRotated(5, -1, 0, 0);
		glTranslated(1.7, (r_sphere/2) + 0.3, 0);
		glutSolidDodecahedron();

    glPopMatrix();
}

// draw a cylinder
void cylinder() {
	GLfloat front_amb_diff[] = {0.7, 0.5, 0.1, 1.0};
	GLfloat back_amb_diff[] = {0.4, 0.7, 0.1, 1.0};
	GLfloat spe[] = {0.25, 0.25, 0.25, 1.0};
	GLfloat amb[] = {0.7, 0.2, 1, 1.0};
	
	glPushMatrix();
		glMaterialfv(GL_FRONT, GL_AMBIENT_AND_DIFFUSE, front_amb_diff);
		glMaterialfv(GL_BACK, GL_AMBIENT_AND_DIFFUSE, back_amb_diff);
		glMaterialfv(GL_FRONT_AND_BACK, GL_SPECULAR, spe);
		glLightfv(GL_LIGHT0, GL_AMBIENT, amb);
		glRotated(theta, 1, 0, 0);
		glRotated(80, 1, 0, 0); 
		glRotated(15, 0, 0, 1);

		glPushMatrix();
			glTranslated(0.1, r_sphere + 0.4, 0);
			glRotated(90, 1, 0, 0);
			GLUquadricObj* quadObj;
			quadObj = gluNewQuadric();
			gluQuadricDrawStyle(quadObj, GLU_LINE);
			gluCylinder(quadObj, 0.1, 0.4, 0.5, 80, 20);
		glPopMatrix();
	glPopMatrix();
}

// draw the track
void track() {
	GLfloat front_amb_diff[] = {0.5, 0.5, 0.2, 1.0};
	GLfloat back_amb_diff[] = {0.5, 0.4, 0.6, 1.0};
	GLfloat spe[] = {0.25, 0.25, 0.25, 1.0};
	GLfloat amb[] = {0.3, 0.3, 0.3, 1.0};

	glPushMatrix();
		glMaterialfv(GL_FRONT, GL_AMBIENT_AND_DIFFUSE, front_amb_diff);
		glMaterialfv(GL_BACK, GL_AMBIENT_AND_DIFFUSE, back_amb_diff);
		glMaterialfv(GL_FRONT_AND_BACK, GL_SPECULAR, spe);
		glLightfv(GL_LIGHT0, GL_AMBIENT, amb);
		glRotated(theta, 1.0, 0, 0);
		glRotated(90, 0.0, 1, 0);
 		for (int i = 0; i < 360; i+=6)
		{
			glPushMatrix();
			glRotated(i, 0, 0, 1);
			glTranslated(0, track_offset , 0);
			glScalef(1, 1, 3);
			glutSolidCube(0.2);
			glPopMatrix();
		}
	glPopMatrix();
		glPushMatrix();
		glTranslated(-0.3, 0, 0);
		glRotated(90, 0.0, 1, 0);
		glutSolidTorus(0.03, track_offset + 0.1, 48, 96);
	glPopMatrix();
	
	glPushMatrix();
		glTranslated(0.3, 0, 0);
		glRotated(90, 0.0, 1, 0);
		glutSolidTorus(0.03, track_offset + 0.1, 48, 96);
	glPopMatrix();


}

// draw all components
void display(void) {
    glClear(GL_COLOR_BUFFER_BIT|GL_DEPTH_BUFFER_BIT);
	glPushMatrix();
		lightPos();
		glRotated(theta_t, 0.0, 1, 0);
	
		glPushMatrix();
 			glTranslated(0, - r_sphere - 0.35, 0);
			solidCone();
			solidTetrahedron();
			cylinder();
			solidOctahedron();
			solidDodecahedron();
			solidhouse();
			solidIcosahedron();
			track();
			solidSphere();
		glPopMatrix();

	glPopMatrix();

	glutSwapBuffers();

}

// Handle special key functions of keybord
void keyboardf(int key, int x, int y) {
	switch(key) {
	case GLUT_KEY_UP:
		dt = dt + 0.1;
		if (dt > 10) dt = 10; // max speed is 10
		break;
	case GLUT_KEY_DOWN:
		dt = dt - 0.1;
		if (dt < 0) dt = 0; // lowest speed is zero (stop)
		break;
	case GLUT_KEY_LEFT: // rotate to left
		theta = (--theta_t < 0)?360 : theta_t;
		theta = (theta < 360) ? theta + dt:dt;
	    theta_s = (theta_s < 360) ? theta_s + dt/3:dt;

		break;
	case GLUT_KEY_RIGHT: // rotate to right
		theta = (++theta_t > 360) ?0 : theta_t;
		theta = (theta < 360) ? theta + dt:dt;
	    theta_s = (theta_s < 360) ? theta_s + dt/3:dt;
		break;
	}
	glutPostRedisplay();

}

// Handle key event
void keyboard(unsigned char key, int x, int y) {
	switch(key) {
	case 's': // toggle normal track and space track
		track_offset = (track_offset < r_sphere + 0.5)? r_sphere + 0.5 : r_sphere - 0.1;
		break;
	case ' ': // toggle stop and start
		if (dt == 0) {
			dt = dt_stop;
		} else {
			dt_stop = dt;
			dt = 0;
		}
		break;

	}
	glutPostRedisplay();

}

// idle animation
void idle(void) {
    theta = (theta < 360) ? theta + dt:dt;
    theta_s = (theta_s < 360) ? theta_s + dt/3:dt;
    theta_h = (theta_h < 360) ? theta_h + 6*dt:dt;
	glutPostRedisplay();
}

void usage() {
	cout << "CS700 Computer graphic project 4 -  Space Track" << endl;
	cout << "Student: Haijun Su" << endl;
	cout << endl;
	cout << "'s'\t Toggle normal track and space track" << endl;
	cout << "'space'\t Toggle start and stop animation" << endl;
	cout << endl;
	cout << "'UP'\t Accelerate" << endl;
	cout << "'DOWN'\t Decelerate" << endl;
	cout << "'LEFT'\t Rotate left" << endl;
	cout << "'RIGHT'\t Rotate right" << endl;

}

void main(int argc, char ** argv) {
    glutInit(&argc, argv);
    glutInitDisplayMode(GLUT_DOUBLE|GLUT_RGB|GLUT_DEPTH);
    glutInitWindowSize(600,600);
    glutInitWindowPosition(200, 100);
    glutCreateWindow("Project 3 - Space Track");
    glClearColor(0.0, 0.0, 1.0, 0.0);
    glEnable(GL_DEPTH_TEST);
    
    glMatrixMode(GL_PROJECTION);
    glLoadIdentity();
    gluPerspective(45, 1.0, 2, 8);

	glMaterialf(GL_FRONT_AND_BACK, GL_SHININESS, 75);
    glLightModeli(GL_LIGHT_MODEL_TWO_SIDE, GL_TRUE);

	glLightfv(GL_LIGHT0, GL_POSITION, pos);

	glMatrixMode(GL_MODELVIEW);
    glLoadIdentity();
    glTranslated(0, 0, -5);

    glEnable(GL_LIGHTING);
    glEnable(GL_LIGHT0);
	glShadeModel(GL_SMOOTH);
    glutDisplayFunc(display);
	glutKeyboardFunc(keyboard);
	glutSpecialFunc(keyboardf);
    glutIdleFunc(idle);
	usage();
    glutMainLoop();
}