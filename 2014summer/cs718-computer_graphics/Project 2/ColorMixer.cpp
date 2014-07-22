#include <gl/glut.h>
#include <iostream>
#include <string>

// CS718 Project 2 Color Mixer Submission: Su, Haijun

#define RED 0
#define GREEN 1
#define BLUE 2

using namespace std;

// window size
int width = 600;
int height = 400;

bool isDebug = false; // show debug information on console
int rubberbanding = -1; // if rubberbanding is -1, there is no rubberbanding

// print some debug information on console
void debug(string source, string message) {
	if (isDebug)
		cout << "[" << source << "]: " << message << endl;
}

// return rubberbanding status.
bool isRubberbanded() {
	return rubberbanding > -1;
}

// upper left corner = (0,0)
int getY(int y) { 
	return height - 1 - y;
}

// check whether the coursor is in window
bool isOutsideOfWindow(int x, int y) {
	if (x < 0 || x > width || y < 0 || y > height)
		return true;
	return false;
}

// R/G/B slider
class slider {
	private:
		int lowValue, highValue, value; // the slider identifier value
		int lineHeight, posX, poxY, space; // the slider size and the position to draw the slider
		int blockWidth, blockHeight; // block size
		int color; // slider color: RED, GREEN, BLUE
		int vLineX, vLineY, blockX, blockY; // line and block position
		int rubberY, rubberValue; // the coursor value and slide value when slider is rubberbanding
		bool changeBlockColor, drawSliderOutline; // highlight slider

	public:
		// construct slider with default value. color is RED
		slider() {
			lowValue = 0;
			highValue = 255;
			lineHeight = 255;
			value = 0;
			space = 20;
			blockWidth = 25;
			blockHeight = 15;
			posX = 0;
			poxY = 0;
			color = RED;
			changeBlockColor = false;
		}
		// construct slider with specified values
		slider(int low, int high, int initValue, int lineLength, int sliderColor) {
			lowValue = low;
			highValue = high;
			lineHeight = lineLength;
			value = initValue;
			space = 20;
			blockWidth = 25;
			blockHeight = 15;
			posX = 0;
			poxY = 0;
			color = sliderColor;
			changeBlockColor = false;
			// check value range
			if (value < lowValue) value = lowValue;
			else if (value > highValue) value = highValue;
			// adjust value for painting
			value = (value - lowValue) * lineHeight / (highValue - lowValue);
		}

		// distroy slider
		~slider() {
			lowValue = 0;
			highValue = 0;
			lineHeight = 0;
			value = 0;
			space = 0;
			blockWidth = 0;
			blockHeight = 0;
			posX = 0;
			poxY = 0;
			color = -1;		
		}

		// set the slider position
		void setPosition(int x, int y) {
			posX = x;
			poxY = y;
		}

		// mouse event

		void mouse(int button, int state, int x, int y) {
			if (isRubberbanded() && rubberbanding != color) { // engaged by other
					return;
			}

			switch(button) {
			case GLUT_LEFT_BUTTON:
				if (state == GLUT_DOWN) {
					if (isInBlock(x, y)) {
						rubberbanding = color;
						rubberY = y;
						rubberValue = value;
					} else if (isInSide(x, y)) {
						value = y - vLineY - blockHeight/2;
						if (value > lineHeight) value = lineHeight;
						if (value < 0) value = 0;
						changeBlockColor = true;
						rubberbanding = color; // auto attach
						rubberY = y;
						rubberValue = value;					}
				} else {
					if (isRubberbanded() && rubberbanding == color) {
						rubberbanding = -1;
					}
				}
				break;
			case GLUT_RIGHT_BUTTON:
				// noop
				break;
			}
		}
		// handle motion
		void motion(int x, int y) {
			debug(getName(), "motion");
			if (isRubberbanded() && rubberbanding == color) {
				value = rubberValue + y - rubberY;
				if (value > lineHeight) value = lineHeight;
				if (value < 0) value = 0;
			}
		}

		// handle possive
		void possive(int x, int y) {
			if (isInBlock(x, y)) {
				changeBlockColor = true;
			} else {
				changeBlockColor = false;
			}
			if (isInSide(x, y)) {
				drawSliderOutline = true;
			} else {
				drawSliderOutline = false;
			}
		}

		// check whether the coursor is in block
		bool isInBlock(int x, int y) {
			return ((x >= blockX && x <= (blockX + blockWidth)) 
				&& (y >= blockY && y <= (blockY + blockHeight)));
		}

		// check whether the coursor is in slider
		bool isInSide(int x, int y) {
			return ((x >= blockX && x <= (blockX + blockWidth)) 
				&& (y >= vLineY && y <= (vLineY + lineHeight + blockHeight)));
		}

		// draw slider
		void draw() {
			debug(getName(), "draw");
			switch(color) {
			case RED:
				glColor3f(1, 0, 0);
				break;
			case GREEN:
				glColor3f(0, 1, 0);
				break;
			case BLUE:
				glColor3f(0, 0, 1);
				break;
			}
			int sliderHeight = 40 + lineHeight + blockHeight; // create space for characters

			// draw vertical line
			glLineWidth(2.0);
			vLineX = posX + blockWidth / 2;
			vLineY = poxY + 40;
			glBegin(GL_LINES);
				glVertex2i(vLineX, vLineY);
				glVertex2i(vLineX, vLineY + lineHeight + blockHeight); // value is the center of block.
			glEnd();

			// write characters
			writeCharacters(vLineX, vLineY - 15,  getColorShortName(color));
			writeCharacters(vLineX, vLineY + lineHeight + blockHeight + 5,  to_string(getValue()));

			// draw block
			blockX = vLineX - blockWidth/2;
			// block position is related the value.
			blockY = vLineY + value;
			// change block color
			if (changeBlockColor) 
				glColor3f(0.9, 0.9, 0.9);
			glBegin(GL_QUADS);
				glVertex2i(blockX, blockY);
				glVertex2i(blockX + blockWidth, blockY);
				glVertex2i(blockX + blockWidth, blockY + blockHeight);
				glVertex2i(blockX, blockY + blockHeight);
			glEnd();
			//draw slider outline
			if (drawSliderOutline) {
				glColor3f(0.2, 0.2, 0.2);
				glLineWidth(1.0);
				glEnable(GL_LINE_STIPPLE);
				glLineStipple(1,0x000F); 
				glBegin(GL_LINE_LOOP);
					glVertex2i(blockX - 1, vLineY - 1);
					glVertex2i(blockX + blockWidth + 1, vLineY - 1);
					glVertex2i(blockX + blockWidth + 1, vLineY + lineHeight + blockHeight + 1);
					glVertex2i(blockX - 1, vLineY + lineHeight + blockHeight + 1);
				glEnd();
				glDisable(GL_LINE_STIPPLE);
			}
		}

		// draw bitmap characters on screen
		void writeCharacters(int x, int y, string str) {
			switch(str.size()) {
			case 1:
				x = x - 5;
				break;
			case 2:
				x = x - 9;
				break;
			case 3:
				x = x - 13;
				break;
			}
			char * tmp = new char[str.size() + 1];
			memcpy(tmp, str.c_str(), str.size());
			char c;
			glPushMatrix();
			glRasterPos2i(x, y);
			for( ; ( c = *tmp ) != '\0'; tmp++) {
				glutBitmapCharacter(GLUT_BITMAP_9_BY_15, c);
			}
			glPopMatrix(); 
		}

		// get current value
		int getValue() {
			return (float)value * (highValue - lowValue)/lineHeight + 0.5f + lowValue;
		}

		// get current color value
		float getColorValue() {
			return ((float) getValue())/(highValue - lowValue);
		}

		// change the value of slider
		void setColorValue(float v) {
			value = (int)(v * lineHeight);
		}
		
		// get slider width
		int getWidth() {
			return blockWidth;
		}

		// return "R", "G", "B"
		string getColorShortName(int colorValue) {
			switch(colorValue) {
			case RED:
				return "R";
				break;
			case GREEN:
				return "G";
				break;
			case BLUE:
				return "B";
				break;
			}
			return "UNDEFINED";
		}

		// get name of slider "slider" + Color
		string getName() {
			return "slider-" + getColorShortName(color);
		}
};

// initial sliders
slider redSlider(0, 255, 127, 255, RED);
slider greenSlider(0, 255, 127, 255, GREEN);
slider blueSlider(0, 255, 127, 255, BLUE);

// colors for picker rectangle
float colors[2][3];
int pickerX, pickerY, pickerHeight = 60, pickerWidth = 260;
bool isInitPicker = false;

// draw a color mixer
void display() {
	glClear(GL_COLOR_BUFFER_BIT);
	// set position of sliders
	int sliderX = 60;
	int sliderY = 30;
	int space = 30;
	redSlider.setPosition(sliderX, sliderY);
	sliderX += space + redSlider.getWidth();
	greenSlider.setPosition(sliderX, sliderY);
	sliderX += space + greenSlider.getWidth();
	blueSlider.setPosition(sliderX, sliderY);
	
	debug("display", "draw sliders");
	// draw sliders
	redSlider.draw();
	greenSlider.draw();
	blueSlider.draw();

	// draw square with mixed color
	int rectX = sliderX + space + blueSlider.getWidth() + 100;
	int rectY = 160;
	int rectEdge = 180;
	// draw border
	glColor3f(0.0, 0.0, 0.0);
	glLineWidth(1.0);
	glBegin(GL_LINE_LOOP);
		glVertex2i(rectX - 1, rectY - 1);
		glVertex2i(rectX - 1, rectY + rectEdge + 1);
		glVertex2i(rectX + rectEdge + 1, rectY + rectEdge + 1);
		glVertex2i(rectX + rectEdge + 1, rectY - 1);
	glEnd();
	glColor3f(redSlider.getColorValue(), greenSlider.getColorValue(), blueSlider.getColorValue());
	// fill mixed color
	glBegin(GL_QUADS);
		glVertex2i(rectX, rectY);
		glVertex2i(rectX, rectY + rectEdge);
		glVertex2i(rectX + rectEdge, rectY + rectEdge);
		glVertex2i(rectX + rectEdge, rectY);
	glEnd();

	// draw color picker
	pickerX = rectX - 40;
	pickerY = sliderY + 20;
	if (!isInitPicker) {
		colors[0][0] = colors[1][0] = redSlider.getColorValue();
		colors[0][1] = colors[1][1] = greenSlider.getColorValue();
		colors[0][2] = colors[1][2] = blueSlider.getColorValue();
		isInitPicker = true;
	}
	float colorSpanR = (colors[1][0] - colors[0][0])/pickerWidth; // span of red color
	float colorR = colors[0][0]; // red color start value
	float colorSpanG = (colors[1][1] - colors[0][1])/pickerWidth; // span of green color
	float colorG = colors[0][1]; // green color start value
	float colorSpanB = (colors[1][2] - colors[0][2])/pickerWidth; // span of blue color
	float colorB = colors[0][2];// blue color start value
	// draw border
	glColor3f(0.0, 0.0, 0.0);
	glLineWidth(1.0);
	glBegin(GL_LINE_LOOP);
		glVertex2i(pickerX - 1, pickerY - 1);
		glVertex2i(pickerX - 1, pickerY + pickerHeight + 1);
		glVertex2i(pickerX + pickerWidth + 1, pickerY + pickerHeight + 1);
		glVertex2i(pickerX + pickerWidth + 1, pickerY - 1);
	glEnd();
	// fill colors
	glBegin(GL_LINES);
	for(int i=0; i<pickerWidth; i++) {
		glColor3f(colorR, colorG, colorB);
		glVertex2i(pickerX + i, pickerY);
		glVertex2i(pickerX + i, pickerY + pickerHeight); 
		colorR += colorSpanR;
		colorG += colorSpanG;
		colorB += colorSpanB;
	}
	glEnd();

	glutSwapBuffers();
}

// pick color from color picker
void pickerColor(int button, int state, int x, int y) {
	if ((x >= pickerX && x <= (pickerX + pickerWidth)) 
				&& (y >= pickerY && y <= (pickerY + pickerHeight))) {
		if ((button == GLUT_LEFT_BUTTON) && (state == GLUT_DOWN) 
			&& (!isRubberbanded())) { // no attach anything and click left button
			int posX = x - pickerX;
			redSlider.setColorValue(((colors[1][0] - colors[0][0])/pickerWidth) * posX + colors[0][0]);
			greenSlider.setColorValue(((colors[1][1] - colors[0][1])/pickerWidth) * posX + colors[0][1]);
			blueSlider.setColorValue(((colors[1][2] - colors[0][2])/pickerWidth) * posX + colors[0][2]);
		}
	}
}

// handle mouse event
void mouse(int button, int state, int x, int y) {
	int vy = getY(y);
	redSlider.mouse(button, state, x, vy);
	greenSlider.mouse(button, state, x, vy);
	blueSlider.mouse(button, state, x, vy);
	pickerColor(button, state, x, vy);
	glutPostRedisplay();
}

// handle motion event
void motion(int x, int y) {
	if (isOutsideOfWindow(x, y)) return; // avoid engaged out of window
	int vy = getY(y);
	redSlider.motion(x, vy);
	greenSlider.motion(x, vy);
	blueSlider.motion(x, vy);
	glutPostRedisplay();
}

// handle possive event
void possive(int x, int y) {
	int vy = getY(y);
	redSlider.possive(x, vy);
	greenSlider.possive(x, vy);
	blueSlider.possive(x, vy);
	glutPostRedisplay();
}

// handle keyboard event
void keyboard(unsigned char key, int x, int y) {
	debug("main", "keyboard::key=" + to_string(key));
	switch(key) {
	case 'l':
	case 'L':
		colors[0][0] = redSlider.getColorValue();
		colors[0][1] = greenSlider.getColorValue();
		colors[0][2] = blueSlider.getColorValue();
		break;
	case 'r':
	case 'R':
		colors[1][0] = redSlider.getColorValue();
		colors[1][1] = greenSlider.getColorValue();
		colors[1][2] = blueSlider.getColorValue();
		break;
	}
	glutPostRedisplay();
}

// main function
void main(int argc, char** argv) {
	glutInit(&argc, argv);
	glutInitDisplayMode(GLUT_RGB|GLUT_DOUBLE);
	glutInitWindowSize(width, height);
	glutInitWindowPosition(100, 100);
	glutCreateWindow("Project 2 - Color Mixer");
	glClearColor(160.0/255, 160.0/255, 160.0/255, 0.0);
	glMatrixMode(GL_PROJECTION);
	glLoadIdentity();
	gluOrtho2D(0, width, 0, height);
	glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
	glutDisplayFunc(display);
	glutKeyboardFunc(keyboard);
	glutMouseFunc(mouse);
	glutMotionFunc(motion);
	glutPassiveMotionFunc(possive);
	glutMainLoop();
	//system("pause");
}
