# TheHardTruth
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
