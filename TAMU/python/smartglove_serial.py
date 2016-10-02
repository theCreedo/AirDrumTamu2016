#!/usr/bin/env python

# README #
# This program is an example that reads from the SmartGlove and plots
# the accelerometer data using matplotlib
#
# The code should be compatible with Python 2 and potentially Python 3
#
# If you don't want to use matplotlib, remove the lines enclosed by:
#   ### REMOVE IF NOT USING MATPLOTLIB ###
#

# built in libraries
import argparse
import json
import sys
import traceback
import pyglet
from pprint import pprint

cymbal = pyglet.media.load('cymbal.wav')
bass = pyglet.media.load('bass.wav')
snare = pyglet.media.load('snare.wav')


### REMOVE IF NOT USING MATPLOTLIB ###
# For Ubuntu or other linux distros:
#   sudo apt-get install python-matplotlib python-mpl_toolkits.basemap python-serial
# For Windows:
#   You might need to get modules from here: http://www.lfd.uci.edu/~gohlke/pythonlibs
#   Then install using: pip install <module_name>.whl
# For OS X:
#   I recommend using pip or homebrew to install

import numpy as np
import serial

# this try-except block binds the 'raw_input' function to the 'input' function
# allowing the code to run using both python2 and python3
# to regain functionality of input in python2, comment out this block or use eval(input())
try:
    input = raw_input
except NameError:
    pass


# main function, takes arguments from command line
def main(argv):
    # parse input arguments
    parser = argparse.ArgumentParser(description="Read JSON from the SmartGlove via serial and plots the acceleromter data")
    parser.add_argument("-p", "--port",
            required=True, type=str,
            help="Specify serial port. Usually a COM* or /dev/tty* port.")
    parser.add_argument("-b", "--baud",
            type=int, default=115200,
            help="Baud rate of serial port. Default is 115200.")
    args = parser.parse_args()

    # try to open the serial port
    try:
        ser = serial.Serial(args.port, args.baud)
    except serial.SerialException:
        # if you can't open the serial port, make sure the Arduino IDE serial monitor is closed
        # you can find the correct serial port by looking at Tools->Port in the IDE
        print("Could not open serial port.")
        sys.exit(2)


    soundWait = 0
    previousG = 0.0
    currentG = 0.0
    delta = 0
    playSound = 0
    # main loop
    while True:
        try:
            # gets json data and decodes it for python3 compatibility
            glove_data = json.loads(ser.readline().decode('utf-8'))
            print(str(json.dumps(glove_data, indent=4, sort_keys=True))) # pretty print
            isBass = (int(glove_data["fingers"]["pressure"][1]) <= 60) and (int(glove_data["fingers"]["pressure"][2]) <= 100)
            isSnare = (int(glove_data["fingers"]["pressure"][1]) <= 60) and (int(glove_data["fingers"]["pressure"][2]) <= 100)
            isCymbal = (int(glove_data["fingers"]["pressure"][1]) <= 60) and (int(glove_data["fingers"]["pressure"][2]) <= 100)
            if isBass :
                if not (playSound == 0) and delta == -1 and soundWait == 0 :
                    bass.play()
                    playSound = 1
                    soundWait = soundWait + 1
                else:
                    soundWait = 0
                    playSound = 0
            if isSnare :
                if not (playSound == 0) and delta == -1 and soundWait == 0 :
                    snare.play()
                    playSound = 1
                    soundWait = soundWait + 1
                else:
                    soundWait = 0
                    playSound = 0            if isCymbal :
                if not (playSound == 0) and delta == -1 and soundWait == 0 :
                    cymbal.play()
                    playSound = 1
                    soundWait = soundWait + 1
                else:
                    soundWait = 0
                    playSound = 0
        # print exceptions and keep moving
        except:
            #traceback.print_exc()
            continue

        # wait for the user to update
        #input("Press Any Key to Continue...")
        #plt.waitforbuttonpress(timeout=0.05)

# main
if __name__ == '__main__':
    main(sys.argv[1:])
