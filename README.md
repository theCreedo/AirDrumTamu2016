Demo Link : https://www.youtube.com/watch?v=EH0TK0etGXw

## Inspiration
We were inspired to do this as we really wanted to implement a design using Arduino related technology. After learning the basic ideas of how they work from a basic board, we happened across a Smart Glove implemented with Arduino Yun to detect data input from the hand. After trying to integrate the hardware with Alex, we realized certain gestures could be interpreted. This led us to decide to create a drum player application that could utilize the data received from the hand gestures/movement to simulate actually playing the drums.

## What it does
This hack interprets a user's hand gesture/movements when wearing the Smart Glove into drum set sounds. This includes a bass drum, snare drum, and crash cymbal.

## How we built it
Implemented with Arduino code to turn Smart Glove data into JSON format, Python to reformat the data into a file, and Java to read from that file, play sound, react to certain key events, and have data visualization. By pipelining the outputs of the file created by Python, we took the raw data and calculated the gestures and signals based on pressure, change in acceleration, flexing of the fingers.

## Challenges we ran into
Learning smart glove + Arduino Yun code, finding key event moments out of data analysis, and playing sound in multiple instances without repeats.

Finding the calculation of the g-force for the smart glove for only a certain subset of data was difficult, since the rest of the data was considered jitter data, and was useless for our implementation.

## Accomplishments that we're proud of
Figuring out how to work with Arduino code, taking data and putting it out in a human-readable format, being able to recognize certain gestures, and register signals and play sound in multiple instances.

## What we learned
We learned the steps of how an external hardware device communicates to the computer through data visualization, analysis, and providing events through data analysis. Additionally, we learned how to handle and play multiple instances of similar and unique sounds at given events. Most of all, we were able create a working demo that can be used for its application.

## What's next for AirDrum?
The next step would be to further enhance data visualization, add additional  gestures to the application, and integrate it with Rock Band so users can interact and play Rock Band without a drum set.
