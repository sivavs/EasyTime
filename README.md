# EasyTime

Usage Instructions:

//New timer object

EasyTime easytime = new EasyTime();

//start() and resume() are the same

//pause() and stop() are the same


for(int i = 0; i < 100; i++) {

easyTime.resume();

//your code

easyTime.pause();

}


easyTime.printStatResults("Function name");


//To clear easyTime

easyTime.clearAll();


#Additional Functionality

EasyTime could return a StatResults Object which stores :

-Mean

-Median

-Max (Mode)

-getPercentile(Double percentilePoint)

-Standard Deviation



