# TheHardTruth v1.2 (The Scrambler Update...?)
TheHardTruth calculates some statistics based on solve times input by the user.
Currently, the following information (or more) is displayed: 
- Sample Mean
- Sample Variance
- Sample Standard Deviation
- Standard Error of Mean
- Confidence Interval (at 95% confidence level)
- Prediction Interval (at 95% predictive confidence level)

The following change depending on user input:
- The *t* Value
- The *p* Value
- Custom Confidence Interval
- Custom Prediction Interval

Here are some of the program's features, other than displaying the statistics above: 
- Loading and saving times to a TSV file (without saving the scrambles as of now).
- Adding/deleting/changing solve times.
- Viewing scrambles for solves that were generated in the current session.
- Random-state scrambles
- Timer

Note that v1.2 contains scrambler functions and may cause the application to lag at times.

# Explanation for Non-cubers
Speedcubing is a mind sport where cubers solve various twisty puzzles under a myriad of different conditions. The most common "events", as pre-defined sets of conditions under which someone completes a solve are typically called, involve solving a puzzle in the shortest time possible by twisting it (as opposed to, say, dismantling and assembling it). The times are then recorded and used to calculate a value akin to a score. The most common terms are as follows: 
- Best of X: Take the best of X solves. Commonly used in blindfolded (BLD) events, where the cuber memorises then solves a puzzle blindfolded and has to balance speed and accuracy (since a single mistake can result in a Did Not Finish (DNF)).
- Mean of X (MoX): Take the mean of X times. Mo3s are used in actual World Cube Association (WCA) competitions for events that take a considerable amount of time, such as 6x6, 7x7 (cube sizes are usually shortened from NxNxN to NxN) and Fewest Moves.
- Average of X (AoX): Take away the fastest and the slowest solve, to reduce the effect a single good or bad solve can have, then take the statistical mean of the remaining solves. Ao5s are used in most events during WCA competitions, but cubers also use other common formats such as Ao12, Ao50, Ao100 and Ao1000 informally. For larger numbers, alternative definitions exist, where one takes away the fastest and the slowest 10% or so of the number of solves instead.

It should be apparent that none of the above metrics are truly statistically sound ways of measuring one's true "average", in a colloquial sense. However, the cubing community often engages in heated debates on what is a good way to measure one's average for a certain event. Some say Ao100s are sufficient, whereas others say only official solves should matter (and by others I mean a particular person on the Speedcubing forums).

In fact, statistics are rarely used in cubing, despite obvious applications that can go beyond measuring one's average, such as calculating the likelihood of certain cases coming up during the solving process or deciding how fast one should go for BLD events based on previous success rates when going at certain speeds.

Hence this program was made to provide a means for cubers to use statistics to their advantage to estimate their average or obtain other insights into their solving speeds.


# The Truth to Mend All Truths
This program was made to ~~end~~ provide another view point on the discussion surrounding cubing averages, namely what someone truly averages. Measures cubers typically use, such as Mo3, Ao5, Ao12, Ao50, Ao100 or even Ao1000, where the AoX, X being a certain number of solves, is computed by taking away the best and the worst solve (assuming one considers a faster solve a better one and a slower solve a worse one) in the session and deriving the mean of the remaining times after doing so, do not have a strong basis in statistics. Different definitions exist for higher numbers, such as taking away the fastest and the slowest 10% of the solves for longer (in terms of the number of solves) sessions instead.

Therefore, this program attempts to compute some numbers based not on arbitrary definitions coined by the speedcubing community but on statistics, in hopes of estimating an actual mean rather than misappropriating what should be applied in accomplishing a fun and/or challenging goal, such as getting a pure sub-10 (meaning no solves taking 10s or longer) Ao1000, by using it as a measure of someone's average.

Of course, assumptions and estimations are made: the program assumes that cubing times follow a normal distribution and uses the Student's t-distribution for many of its calculations, when in fact time distributions in speedcubing are more likely to be slightly skewed, since solving 5s faster than someone's average is typically less likely than solving 5s slower, given how mistakes can increase times drastically compared to the rare and occasionally unimpactful skips (literally the skipping of certain steps in solving a cube, the details of which depend on the solving method one uses) and lucky cases. In addition, cubers improve as they do more solves, so in a sense the events are not independent of each other in the long term.

However, as the t-distribution is robust to the normality assumption, in addition to the fact the distribution of cubing times are approximately normal in the first place, estimations should be a relatively decent.

# How It Was Made
Java SE 8.0

Apache Commons Math (under the Apache License, Version 2.0)

Scrambler functions were added thanks to Herbert Kociemba's two-phase algorithm demonstration in Java. The solver basically uses a two-phase algorithm, instead of using algorithms such as IDA*.
http://kociemba.org/cube.htm

*That's all folks!*

# How to Run the Program
Under most conditions, the following should suffice: 

On Windows, simply double-click 'TheHardTruth.jar' in the 'dist' folder.

On Linux, run 'java -jar TheHardTruth.jar' in the 'dist' folder.

Otherwise, ~~tough luck~~ please look up how to run jar files on your device.

# Potential Future Functions
(To wit, a vision of the program, considering the program is technically a prototype.)

In addition to features already implemented: 
- Graphs and charts for solves: Users will be able to view how their solve times fluctuate both on a per-solve basis (meaning the solves will be visualised sequentially) and what their time distribution looks like (visualisation of the deviation from the mean). Potential tools include JFreeCharts; images generated by JFreeCharts can be added on the application's screen.
- Sorting of times: although not immediately useful, users may want to sort their times. Combined with the fact that this could facilitate the ease of visualisation, it may be a useful function to add.
- Other forms of analysis: Success rates can used to estimate the likelihood of the number of successes for BLD using the binomial distribution. This can be useful for BLD solvers who want maintain a certain level of accuracy while trying to solve as fast as possible.
- Better file data: Making full use of the Solve class and storing the data in each Solve object in an appropriate format, when all the fields are actively used in the full program.
- Scramblers for multiple events or even subsets of the puzzles (for practising/analysing the speed of solving specific sections or steps).
- Multiple concurrent windows/sessions.

In general, this program is meant to be a tool to relay cubers a more accurate representation of their solve times, success rates, consistency, etc. Although it was not meant to be a timer, timer functions have been added.

# Changelog (since v1.2)

- v1.2
  - Added scrambler functions. You can view the scrambles of the solves done during the current session of the program. Thank you to Herbert Kociemba for making the demonstration available!
- v1.1
  - Added different types of intervals.
- v1.1
  - Initial commit.


## The Old README.md
### (v1.0 or v0.1, depending on how you look at it)

Cubes.
Created with the help of Apache Commons Math.
Simply run TheHardTruth.jar

The program is a prototype of a program that tells the user what they are likely to actually average, since ao5s, ao12s or even ao50s and ao100s are not necessarily a good indication of someone's average.
Technically it is not meant to be a timer but it contains basic timing functions. Calculation of ao5s, ao12s, etc. are omitted since they are not particularly useful when it comes to actual statistics, which for some reason the speedcubing community in general does not use despite far from being wanting of the brightest minds. Instead, some statistics and values are given.
Currently the program allows you to test if your hypotheses about your average should or should not be rejected. You can save or load times and even manually add, delete and change times. It even comes with a basic timer function, as was mentioned.
The program should be easy to navigate. What you see is what you get; trying out the program will probably give you a better understanding of what the program is than further explanations. However, if you do not understand what those values represent, it might help to open up your nearest Statistics textbook.

Features yet to be implemented (since it's a prototype): 
-Providing the user with information related to the confidence interval, tolerance interval and prediction interval (Eh, easy peasy lemon squeezy, I just got sick of adding JLabels-y).
-Power of the test? (You can just test another specific alternative hypothesis with the current version)
-Testing against hallmark values such as sub-10, sub-12, sub-15, etc. automatically. (I know, other values like sub-20 exist. Just listing examples. But I thought it would be too much clutter. And JLabels.)
-Actually using the Solve class (and other classes too) to its full extent. (Might be useful for calculating DNF rates vs times for BLD (blindfolded solving) and the like)
-(Random state) scrambles (for multiple events, but this program was not meant to be a full-fledged timer).
-Sorting, visulisation. (Doesn't make sense to have just sorting without visualisation, so I left it out. I guess I could use JFreeChart for that).

Notes: 
Some of the code is unnecessary but it was left in since it might be used in the future anyway and the impact it makes is negligible.

The distribution of cubing times is probably a skewed one since mistakes typically more often than lucky cases (unless that's just me). In addition, despite there not being a guarantee of normality, the t-distribution was used since it is quite robust to non-normality. The code uses the t-distribution regardless of the sample size despite the Central Limit Theorem since the distributions basically converge anyway given the estimators.
