import java.util.Scanner;
import java.io.File;


public class ReadDataStudent{
    //I hard-coded the number of rows and columns so 
    //I could use a 2D array
    private double[][] data = new double[21908][14];

    //This should read in the csv file and store the data in a 2D array,
    //data -- don't forget to skip the header line and parse everything
    //as doubles  
    public void read(){
        try{
            Scanner scanner = new Scanner(new File("cps.csv"));
            int row = 0;
            scanner.nextLine(); // Skip the header line
            while(scanner.hasNextLine()){
                String line = scanner.nextLine();
                String[] lineArr = line.split(",");
                // for(int i=0; i <data[row].length;i++){
                //    double a=scanner.nextDouble();
                //     data[row][i] = a;
                // }
                for(int i=0; i<lineArr.length;i++){
                    data[row][i] = Double.parseDouble(lineArr[i]);
                }
                row++;
            }
            scanner.close();
    
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    //this should return the column of data based
    //on the column number passed in -- the column number
    //is 0 indexed, so the first column is 0, the second
    //is 1, etc.
    //this should return a double array of the column
    //of data
    public double[] getColumn(int col){
        double[] column = new double[data.length];
        for(int i=0;i<data.length;i++){
            column[i] = data[i][col];
        }
        return column;
    }

    //this returns the standard deviation of the column
    //of data passed in
    //the standard deviation is the square root of the variance
    //the variance is the sum of the squares of the differences between each value and the mean, 
    //divided by the number of values - 1(sample variance)
    //Use Math.pow to square the difference
    //and Math.sqrt to take the square root
    public double stdDeviation(double[] arr){
        double sum = 0;
        double mean = 0;
        for(int i=0;i<arr.length;i++){
             sum += arr[i];
        }
        mean = sum/arr.length;
        return Math.sqrt(Math.pow(sum-(mean),2)); //sample variance!
    }
    
    //this returns the mean of the column of data passed in
    //the mean is the sum of the values divided by the number of values
    public double mean(double[] arr){
        double sum = 0;
        for(int i=0;i<arr.length;i++){
            sum += arr[i];
       }
        return sum/arr.length;
    }

    //this returns the values of a column in standard units
    //the standard units are the value minus the mean divided by the standard deviation
    //this should return a double array of the standard units
    public double[] standardUnits(double[] arr){
        double[] stdArr = new double[arr.length];
        double stdDeviation = stdDeviation(arr);
        double mean = mean(arr);
        for(int i=0; i <arr.length; i++){
            stdArr[i] = (arr[i]-mean)/stdDeviation;
        }
        return stdArr;
    }
    
    //this returns the correlation between the two columns of data passed in
    //the correlation is the sum of the products of the standard units
    //of the two columns divided by the number of values - 1
    //this should return a double
    //the correlation is a measure of the strength of the linear relationship
    //between the two columns of data
    //the correlation is between -1 and 1
    public double correlation(double[] x, double[] y){
            double sum=0;
            for( int i=0; i< (x.length) || i< (y.length);i++){
                sum+=x[i]*y[i];
            }
               
            return sum/(x.length-1);
    }
    
    public void runRegression(){
        double[] x = getColumn(7);
        double[] y = getColumn(9);
        double[] xStd = standardUnits(x);
        double[] yStd = standardUnits(y);
        double correlation = correlation(xStd, yStd);
        double slope = correlation * stdDeviation(y) / stdDeviation(x);
        double intercept = mean(y) - slope * mean(x);
        System.out.println("Correlation: " + correlation);
        System.out.println("Slope: " + slope);
        System.out.println("Intercept: " + intercept);
        Scatter s = new Scatter();
        s.displayScatterPlot(x, y);
    }

    //this prints the array passed in - you may want this for debugging
    public void print(double[] arr){
        for(int i = 0; i < arr.length; i++){
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }
    public static void main(String[] args) {
        ReadDataStudent rd = new ReadDataStudent();
        rd.read();
        rd.runRegression();
    }

}
