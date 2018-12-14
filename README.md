# TheHardTruth v1.2 (The Scrambler Update...?)
TheHardTruth calculates some statistics based on solve times input by the user.
Currently, the following information (or more) is displayed: 
- Sample Mean
- Sample Variance
- Sample Standard Deviation
- Standard Error of Mean
- Confidence Interval (at 95% confidence level)
- Predicton Interval (at 95% predictive confidence level)

The following change depending on user input:
- The *t* Value
- The *p* Value
- Custom Confidence Interval
- Custom Prediction Interval

Note that v1.2 contains scrambler functions and may cause the application to lag at times.

# The Truth to Mend All Truths
This program was made to ~~end~~ provide another view point on the discussion surrounding cubing averages, namely what someone truly averages. Measures cubers typically use such as Mo3 (Mean of 3), Ao5 (Average of 5), Ao12 (what Ao stands for should be obvious by now), Ao50, Ao100 or even Ao1000, where the AoX, X being a certain number of solves, is computed by taking away the best and the worst solve (assuming one considers a faster solve a better one and a slower solve a worse one) in the session and deriving the mean of the remaining times after doing so, do not have a strong basis in statistics. In fact, different definitions exist for the higher numbers, such as taking away the fastest and the slowest 10% of the solves for longer (in terms of the number of solves) sessions.

Therefore, this program attempts to compute some numbers based not on arbitrary definitions coined by the speedcubing community but through statistics, in hopes of estimating an actual mean rather than misappropriating what should be applied in accomplishing a fun and/or challenging goal such as getting a pure sub-10 (meaning no solves taking 10s or longer) Ao1000 by using it as a measure of someone's average. Of course, assumptions and estimations are made: the program assumes that cubing times follow a normal distribution and uses the Student's t-distribution for many of its calculations, when in fact time distributions in speedcubing are more likely to be slightly skewed, since solving 5s faster than someone's average is typically less likely than solving 5s slower, given how mistakes can increase times drastically compared to the rare and occasionally unimpactful skips (literally the skipping of certain steps in solving a cube, the details of which depend on the solving method one uses) and lucky cases. However, as the t-distribution is robust to the normality assumption, in addition to the fact the distribution of cubing times are approximately normal in the first place, estimations should be a relatively decent.

# How It Was Made
Java SE 8.0

Apache Commons Math (under the Apache License, Version 2.0)

Scrambler functions were added thanks to Herbert Kociemba's two-phase algorithm demonstration in Java.
http://kociemba.org/cube.htm

*That's all folks!*

# How to Run the Program
Under most conditions, the following should suffice: 

On Windows, simply double-click 'TheHardTruth.jar' in the 'dist' folder.

On Linux, run 'java -jar TheHardTruth.jar' in the 'dist' folder.

Otherwise, ~~tough luck~~ please look up how to run jar files on your device.

# Potential Future Functions and Unlikely Future Functions
~~blatantly copied from the old README, then edited.~~

Features yet to be implemented: 

- Power of the test? (You can just test another specific alternative hypothesis with the current version)
- Testing against hallmark values such as sub-10, sub-12, sub-15, etc. automatically. (I know, other values like sub-20 exist. Just listing examples. But I thought it would be too much clutter. And JLabels.)
- Actually using the Solve class (and other classes too) to its full extent. (Might be useful for calculating DNF rates vs times for BLD (blindfolded solving) and the like)
- Sorting, visulisation. (Doesn't make sense to have just sorting without visualisation, so I left it out. I guess I could use JFreeChart for that).

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
