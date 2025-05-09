import java.util.Scanner;
import java.io.File;

public class ReadData {
    private double[][] data = new double[21908][14];

    
    public void read() {
        try {
            Scanner scanner = new Scanner(new File("cps.csv"));
            int row = 0;
            scanner.nextLine(); // Skip the header line
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] lineArr = line.split(",");
                for (int i = 0; i < lineArr.length; i++) {
                    data[row][i] = Double.parseDouble(lineArr[i]);
                }
                row++;
            }
            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    public double[][] getColumns(int col1, int col2) {
        double[][] column = new double[data.length][2];
        for (int i = 0; i < data.length; i++) {
            column[i][0] = data[i][col1];
            column[i][1] = data[i][col2];
        }
        return column;
    }

    
    public double[] stdDeviation(double[][] xy) {
        double sumx = 0, sumy = 0;
        for (int i = 0; i < xy.length; i++) {
            sumx += xy[i][0];
            sumy += xy[i][1];
        }
        double meanX = sumx / xy.length;
        double meanY = sumy / xy.length;

        double sumSqX = 0, sumSqY = 0;
        for (int i = 0; i < xy.length; i++) {
            sumSqX += Math.pow(xy[i][0] - meanX, 2);
            sumSqY += Math.pow(xy[i][1] - meanY, 2);
        }

        
        double stdDevX = Math.sqrt(sumSqX / (xy.length - 1));
        double stdDevY = Math.sqrt(sumSqY / (xy.length - 1));

        return new double[]{stdDevX, stdDevY};
    }

   
    public double[] mean(double[][] xy) {
        double sumX = 0, sumY = 0;
        for (int i = 0; i < xy.length; i++) {
            sumX += xy[i][0];
            sumY += xy[i][1];
        }
        return new double[]{sumX / xy.length, sumY / xy.length};
    }

    
    public double[][] standardUnits(double[][] xy) {
        double[][] stdArr = new double[xy.length][2];
        double[] stdDevs = stdDeviation(xy);
        double[] means = mean(xy);

        for (int i = 0; i < xy.length; i++) {
            stdArr[i][0] = (xy[i][0] - means[0]) / stdDevs[0]; 
            stdArr[i][1] = (xy[i][1] - means[1]) / stdDevs[1];  
        }
        return stdArr;
    }

  
    public double correlation(double[][] xy) {   
        double[][] stdUnits = standardUnits(xy);
        double sum = 0;
        for (int i = 0; i < xy.length; i++) {
            sum += stdUnits[i][0] * stdUnits[i][1];
        }
        return sum / (xy.length - 1);
    }

   
    public void runRegression() {
        double[][] xy = getColumns(7, 9); 
        double[][] xyStd = standardUnits(xy); 
        double correlation = correlation(xyStd);
        double[] stdDevs = stdDeviation(xy); 
        double slope = correlation * (stdDevs[1] / stdDevs[0]); 
        double[] means = mean(xy); 
        double intercept = means[1] - slope * means[0]; 

        // Print the results
        System.out.println("Correlation: " + correlation);
        System.out.println("Slope: " + slope);
        System.out.println("Intercept: " + intercept);
    }

    // Print the array (for debugging purposes)
    public void print(double[][] arr) {
        for (int row = 0; row < arr.length; row++) {
            for (int col = 0; col < arr[row].length; col++) {
                System.out.print(arr[row][col] + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        ReadData rd = new ReadData();
        rd.read(); // Read the data from the CSV file
        rd.runRegression(); // Run the regression on the 7th and 9th columns
    }
}
