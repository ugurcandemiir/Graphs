package lab11;

import java.util.function.DoubleFunction;

public class PointGen {
	
	private int nPoints;
	private double xmin;
	private double xmax;
	DoubleFunction<Double> func;
	/**
	 * @param nPoints
	 * @param xmin
	 * @param xmax
	 * @param func
	 */
	public PointGen(int nPoints, double xmin, double xmax, DoubleFunction<Double> func) {
		super();
		this.nPoints = nPoints;
		this.xmin = xmin;
		this.xmax = xmax;
		this.func = func;
	}
	public int getnPoints() {
		return nPoints;
	}
	public void setnPoints(int nPoints) {
		this.nPoints = nPoints;
	}
	public double getXmin() {
		return xmin;
	}
	public void setXmin(double xmin) {
		this.xmin = xmin;
	}
	public double getXmax() {
		return xmax;
	}
	public void setXmax(double xmax) {
		this.xmax = xmax;
	}
	public DoubleFunction<Double> getFunc() {
		return func;
	}
	public void setFunc(DoubleFunction<Double> func) {
		this.func = func;
	}
    public double[][] genPoints(){
        double[][] temp = new double[nPoints +1][2];
        double xInterval = ( xmax - xmin ) / (double) nPoints;
        double x = xmin;
        for (int i = 0; i < nPoints + 1; i++) {
            temp[i][0] = x;
            temp[i][1] = func.apply(x);
            x += xInterval;
        }
        return temp;
    }
		

}
