# Lab 11 - Second Lab Test

## Due at the end of your lab section on Nov. 12

## GroundRules for In-Lab Tests

In general, in-lab tests are similar to a normal lab, but there are a few special ground rules we need to establish to make these tests fair.

1. Lab tests must represent your own work. Any copying from other students or any other source is strictly forbidden, and will result in a zero grade. Therefore, there will be no discussion between students during the lab test period.

2. Lab tests must be worked on during the lab period, and the final submission must occur before your lab period ends. If you cannot complete the lab during the normal lab period, turn in what you have finished at the end of the period. Partial credit will be available.

3. You may use your own laptop or one of the Linux Lab machines to complete the lab test, but must work on it in the Linux Lab room.

4. You may use the internet to access the Java API documentation, and the class web page, as well as GitHub to read the directions and clone the lab test repository. Any other use of the internet is considered plaigiarism, and will result in a zero grade.

5. For this second lab test, you **are** allowed to use Eclipse to write your code. You may use a text editor, and run compile and test commands from the command window instead, but if you want to use Eclipse, please feel free to do so. 

6. If your code does not compile, there will be significant deductions because the code cannot be tested. You will get a better grade if you comment out sections of code which do not compile so that we can run and test the resulting code.

7. All classes must be in package lab11. Points will be deducted otherwise.

## The GraphPanel Class

For today's lab test, we will combine using a Swing Graphing technology with the concept of functional programming to enable interactive graphing to occur.

We are providing a GraphPanel class in GraphPanel.java that is already coded, and does not need to be modified. The GraphPanel class is an extension of the JPanel class, and it keeps track of a two-dimensional array of point data, and graphs the connections between these points. In order to do so, GraphPanel has a ```double points[][]``` field, takes ```double points[][]``` as an argument to the GraphPanel constructor, and has a ```setPoints(double points[][])``` method to change the current value of points.  The points array may have an arbitrary number of rows - one row for each coordinate, but uses only two columns... column 0 for the x coordinate of the point, and column 1 for the y coordinate of the point.

There are several other fields in the GraphPanel class, but these are used internally to the class to keep track of various parameters used to make the graph, and are not so important.

When an object of the GraphPanel class is rendered on the screen, the GraphPanel ```paintComponent``` method determines the correct scaling so that all points in the points array fit on the screen, draws x and y axes with labels that attempt to indicate the "real" x and y values, and then plots every point on the screen, and connects it with a line to the next point on the screen.

Note that there may be cases where the GraphPanel class does not behave exactly as desired. For instance, if y values get very big, there is not enough room in the ylabels to display all the digits required. You will not lose points for any problems in the GraphPanel class, and do not need to fix these kinds of errors. This is primarilly demonstration code, and we haven't made the effort to clean up all the rough edges in the code.

## The PointGen class

Your job today is to create a PointGen class that will produce input data for the GraphPanel class, a two-dimensional array of doubles, where the first column (index 0) is an x coordinate, and the second column (index 1) is the y coordinate.

The PointGen class should have four fields:

1. ```int nPoints``` - the counter of the number of points (rows in the points matrix) to generate.
2. ```double xmin``` - the minimum x value
3. ```double xmax``` - the maximum x value
4. ```DoubleFunction<Double> func``` - A function that takes a double argument, and returns a Double result. There is a ```DoubleFunction<T>``` interface defined in the Java library that makes it clear that DoubleFunction is a functional interface that requires a single ```Double apply(double)``` method.

The PointGen creator should require the initial values for all four of these fields as arguments, in the order specified above. You may use the Eclipse "Source/Generate Constructors Using Fields..." menu item to generate the creator.

There should be a getter and setter method for each of the four fields. You may use the Eclipse "Source/Generate Getters and Setters..." feature to create these methods.

There should also be a ```public double[][] genPoints()``` method in this class. The genPoints method should 

- Instantiate a new array of doubles big enough to hold ```nPoints``` rows, and two columns for x and y.
- Calculate an xInterval value so that we can create ```nPoints``` x values, starting at ```xmin```, spaced evenly, and ending at ```xmax```.
- Loop through each x point, and invoke ```func``` with the x value as the argument; putting the x value into ```points[i][0]```, and the result of ```func``` applied to the x value into ```points[i][1]```.
- Return the resulting array of points.

## The ShowGraph Class

The ShowGraph class is the class that brings a GraphPanel object together with a PointGen object, and provides controls to modify the parameters of the PointGen class, generate new sets of points, and draw the resulting modified graph. Other than a couple of tweaks at the end of the lab, the basic function of the ShowGraph class is complete, and does not need to be modified. But just to get an idea of what is going on, here's what is in the class...

The ShowGraph creator takes no arguments, but creates several objects:

- The creator creates a new Jframe and keeps track of it in the ```frame``` field.
- The creator creates a new PointGen object and keeps track of it in the ```pointgen``` field. The initial ```pointgen``` object is created with a default of 300 points, xmin=0, xmax=10.0, and an initial function of ```x->x``` - which is just a lamba expression for the identify function, or y=x.
- The creator creates a new GraphPanel object and keeps track of it in the ```graphPanel``` field. The initial set of points is a result of invoking ```pointgen.getPoints()```.
- The creator also creates a control panel to modify the parameters used by ```pointGen```. There are JTextField components for the number of points, the x minimum, and the x maximum, all provided with ```DocumentListener``` iterfaces (similar to ActionListeners, but more complicated so that every keystroke is detected and monitored in these fields). Note that the DocumentListener interface is NOT a functional interface, but (thanks to Google) we found a way to simplify the DocumentListener into a simplified "SimpleDocumentListener" interface which **is** a functional interface.  Hence the need for the ```SimpleDocumentListener``` interface. This allows us to invoke ```pointgen``` setter methods to update these three paramaeters via lambda expressions. Note that changing the fields on the GUI does not automatically redraw the graph - it just modifies the fields in ```pointgen```.
- The fourth ```pointgen``` field, the ```DoubleFunction<Double> func``` parameter is more complicated. There is no good way in Java to translate a text string into a function.  The compiler translates a lambda expression from a .java file into a function, but we can't invoke the Java compiler every time our user wants to change the function. Therefore, we need a list of pre-defined functions, and some descriptive text attached to that function so our user can choose it. We haven't discussed hash maps yet, but I've given you examples of everthing that needs to be done, and it's a perfect application, so let's use a hash map to keep track of different functions we want to graph.

   The hash map is declares as a field in ShowGraph called ```fnList```. The key for this hash map is a descriptive string which describes the function. The value for each key is the function itself. We can add new functions to the hashmap using the ```put``` method. For instance ```fnList.put("x->",x->x)``` says to create a new entry in this hash map with a key of the text string ```"x->x"```, and a value of the actual lambda expression ```x->x```.  Note that the ShowGraph class has an ```addFunction``` method which takes a string description and a ```DoubleFunction<Double> fn``` as an argument, and adds it to the hash map.
   
   There is also a JComboBox component in the control panel to manage use selection of a functional description. The action listenter associated with this JComboBox says that whenever the user chooses a different function description, that description is used to look up the actual function in the hash map, and the ```pointGen.setFunc``` method is invoked with that new function.  Again, the graph does not change when the user selects a new point - only the fields in ```pointGen```.
   
- Finally, the control panel implements a "Redraw" button. When the user clicks on the Redraw button, the action listener for the button invokes the GraphPanel ```setPoints``` method, passing in a new invocation of ```pointGen.genPoints()```. At this point, the graph is actually updated.

The other methods in the ShowGraph class are as follows:

- An addFunction method which adds a new function to the list of graph functions.
- A readInt static method that converts the text in a JTextField into an integer.
- A readDouble static method that converts the text in a JTextField into a double.
- A createAndShowGUI method which causes the actual rendering of the frame and graph panel, designed to be run in the Swing event loop.
- A main method to start everything off. Notice that the main function has an example of adding a y=x squared function to the list of available functions.

## Completing the Lab

Once you have coded your PointGen class, run the main method in ShowGraph, and see what happens. Try changing the parameters and redrawing the graph. Did everything work as expected? If not, was it because there was an error in your PointGen class? If so, here is a good chance to fix it.

Also add at least one more function to the list of graphable functions. Maybe graph a polynomial, or square root, or even trig functions. Note that very little checking is performed, and some things might break (like trying to take the square root of a negative number.)... the point of this lab test is not to guarantee that EVERYTHING will work... just to get a feel for how everything hangs together.

## Committing and Submitting your Results

Remember to commit and push your modifications before the end of your lab period. Also, please copy and paste the resulting hash code into myCourses under CS-140/content/Lab Submissions/Lab 11. Please remember to do so BEFORE the end of the lab period.

## Grading Criteria

This lab test is worth 30 points of your test 02 grade. If your code does not compile, you will get a 25 point deduction. If your code gets a compiler warning, you will get a 5 point deduction. The professor will run your lab11.ShowGraph main function. If the main method does not correctly graph the original functions even with modifications of all four ```pointGen``` parameters, you will get up to a 15 point deduction, depending on the severity of your error. If you do not enable additional functional choices of any kind, you will get an additional 5 point deduction. Up to five point bonus credit is available for extra or creative additional functions. These bonus points can only be used to counteract deductions on the lab part of the test.
