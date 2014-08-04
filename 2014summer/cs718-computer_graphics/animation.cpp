#include <gl/glut.h>
#include <math.h>

GLfloat x, y, z = -2.0, dz=0.02, theta = 0, dt = 0.2, p=3.141593/180;

void display(void) {
    glClear(GL_COLOR_BUFFER_BIT|GL_DEPTH_BUFFER_BIT);
    glColor3f(1, 0, 0);
    glBegin(GL_POLYGON);
        glVertex3f(-1, 0, 2);
        glVertex3f(1, -0.5, -4);
        glVertex3f(0.8, 1.25, -6);
    glEnd();
    x = cos(theta * p);
    y = sin(theta * p);
    glColor3f(0, 1, 0);
    glBegin(GL_POLYGON);
        glVertex3f(x, y, z);
        glVertex3f(-y, x, z);
        glVertex3f(-x, -y, z);
        glVertex3f(y, -x, z);
    glEnd();
    glutSwapBuffers();
}

void idle(void) {
    if(dz < 0 && z <=-5.5 || dz>0 && z>=-0.6)
        dz = -dz;
    z += dz;
    theta = (theta < 360) ? theta + dt : dt;
    glutPostRedisplay();
}

void main(int argc, char ** argv) {
    glutInit(&argc, argv);
    glutInitDisplayMode(GLUT_RGB | GLUT_DEPTH | GLUT_DOUBLE);
    glutInitWindowSize(512, 512);
    glutInitWindowPosition(250, 100);
    glutCreateWindow("Double buffer animation");
    glClearColor(0.0, 0.0, 0.0, 0.0);
    glMatrixMode(GL_PROJECTION);
    glLoadIdentity();
    glOrtho(-2, 2, -2, 2, 0.5, 6);
    glEnable(GL_DEPTH_TEST);
    glutDisplayFunc(display);
    glutIdleFunc(idle);
    glutMainLoop();
}

